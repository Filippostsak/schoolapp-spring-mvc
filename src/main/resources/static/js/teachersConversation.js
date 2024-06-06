document.addEventListener("DOMContentLoaded", function () {
    let teacherId;
    let currentStudentId;

    console.log("Document loaded, starting fetch process for messages...");

    fetch('http://localhost:8080/messages/current/teacher')
        .then(response => {
            console.log("Fetching current teacher ID for messages...");
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            teacherId = data;
            console.log("Fetched Teacher ID:", teacherId);

            fetchMessages(teacherId);
        })
        .catch(error => console.error('Error fetching teacher ID:', error));

    function fetchMessages(teacherId) {
        fetch(`http://localhost:8080/messages/receiver/${teacherId}`)
            .then(response => {
                console.log("Fetching messages...");
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(messages => {
                console.log("Fetched messages:", messages);
                displayMessageList(messages);
            })
            .catch(error => console.error('Error fetching messages:', error));
    }

    function displayMessageList(messages) {
        const messagesList = document.getElementById('messages-list');
        messagesList.innerHTML = '';

        const studentConversations = new Map();

        messages.forEach(message => {
            const senderId = message.senderId;
            const senderUsername = message.senderUsername;

            if (!studentConversations.has(senderId)) {
                studentConversations.set(senderId, {
                    username: senderUsername,
                    messages: []
                });
            }

            studentConversations.get(senderId).messages.push(message);
        });

        studentConversations.forEach((conversation, studentId) => {
            const listItem = document.createElement('div');
            listItem.classList.add('message-list-item', 'border', 'rounded', 'p-3', 'mb-3', 'bg-light', 'text-dark');
            listItem.innerHTML = `
                <p><strong>${conversation.username}</strong></p>
                <p>${conversation.messages[0].content}</p>
                <button class="btn btn-primary view-conversation-button" data-student-id="${studentId}">View Conversation</button>
            `;
            messagesList.appendChild(listItem);

            listItem.querySelector('.view-conversation-button').addEventListener('click', () => {
                openConversationModal(studentId, conversation.username);
            });
        });
    }

    function openConversationModal(studentId, studentUsername) {
        const modalLabel = document.getElementById('conversationModalLabel');
        const conversationContainer = document.getElementById('conversation-container');
        const replyForm = document.getElementById('replyForm');

        modalLabel.textContent = `Conversation with ${studentUsername}`;
        conversationContainer.innerHTML = '';
        currentStudentId = studentId;

        fetch(`http://localhost:8080/messages/sender/${studentId}/receiver/${teacherId}`)
            .then(response => {
                console.log(`Fetching conversation with student ID: ${studentId}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(messages => {
                console.log(`Fetched conversation with student ID: ${studentId}`, messages);

                messages.forEach(message => {
                    const messageElement = document.createElement('div');
                    messageElement.classList.add('message');
                    messageElement.innerHTML = `
                        <p><strong>${message.senderUsername}:</strong> ${message.content}</p>
                        <p class="timestamp"><strong>Sent at:</strong> ${new Date(message.timestamp).toLocaleString()}</p>
                    `;
                    conversationContainer.appendChild(messageElement);
                });

                const conversationModal = new bootstrap.Modal(document.getElementById('conversationModal'));
                conversationModal.show();
            })
            .catch(error => console.error('Error fetching conversation:', error));

        replyForm.addEventListener('submit', handleReplyFormSubmit);
    }

    function handleReplyFormSubmit(event) {
        event.preventDefault();
        const replyContent = document.getElementById('reply-content').value;
        const replySpinner = document.getElementById('replySpinner');

        if (!replyContent) {
            console.error('Reply content is empty');
            return;
        }

        const replyMessageDTO = {
            content: replyContent,
            senderId: teacherId,
            receiverId: currentStudentId
        };

        replySpinner.style.display = 'inline-block';

        fetch('http://localhost:8080/messages/send', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(replyMessageDTO)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error('Network response was not ok: ' + JSON.stringify(err));
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log('Reply sent successfully:', data);
                document.getElementById('replyForm').reset();
                fetchMessages(teacherId);
                replySpinner.style.display = 'none';
            })
            .catch(error => {
                console.error('Error sending reply:', error);
                replySpinner.style.display = 'none';
            });
    }
});
