document.addEventListener("DOMContentLoaded", function () {
    let studentId;
    let userId;
    let currentUserId; // for sending the reply

    console.log("Document loaded, starting fetch process...");

    // Fetch current student information
    fetch('http://localhost:8080/messages/current/student')
        .then(response => {
            console.log("Fetching current student ID...");
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            studentId = data.id;
            const username = data.username;
            console.log("Fetched Student ID:", studentId, "Username:", username);

            // Fetch user ID using username
            return fetch(`http://localhost:8080/messages/user/${username}`);
        })
        .then(response => {
            console.log("Fetching user ID using username...");
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            userId = data;
            currentUserId = userId; // Store the current user ID for replies
            console.log("Fetched User ID:", userId);

            setTimeout(() => {
                console.log("Fetching messages for User ID:", userId);
                fetchMessagesByUserId(userId);
            }, 1000);
        })
        .catch(error => console.error('Error handling response:', error));

    // Fetch messages by user ID
    function fetchMessagesByUserId(userId) {
        fetch(`http://localhost:8080/messages/receiver/${userId}`)
            .then(response => {
                console.log("Fetching messages...");
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(messages => {
                console.log("Fetched Messages:", messages);
                populateMessagesList(messages);
            })
            .catch(error => console.error('Error fetching messages:', error));
    }

    function populateMessagesList(messages) {
        const messagesContainer = document.getElementById('messages-container-view');
        messagesContainer.innerHTML = '';

        // Group messages by sender
        const messagesBySender = messages.reduce((groups, message) => {
            if (!groups[message.senderUsername]) {
                groups[message.senderUsername] = [];
            }
            groups[message.senderUsername].push(message);
            return groups;
        }, {});

        // Create sections for each sender
        for (const sender in messagesBySender) {
            const senderGroup = document.createElement('div');
            senderGroup.className = 'sender-group';

            const senderHeader = document.createElement('h5');
            senderHeader.textContent = `Messages from ${sender}`;
            senderGroup.appendChild(senderHeader);

            messagesBySender[sender].forEach(message => {
                const messageElement = document.createElement('div');
                messageElement.className = 'message-item';

                const senderElement = document.createElement('p');
                senderElement.className = 'message-sender';
                senderElement.textContent = `From: ${message.senderUsername}`;

                const contentElement = document.createElement('p');
                contentElement.className = 'message-content';
                contentElement.textContent = `Message: ${message.content}`;

                const timestampElement = document.createElement('p');
                timestampElement.className = 'message-timestamp';
                timestampElement.textContent = `Received: ${new Date(message.timestamp).toLocaleString()}`;

                const replyButton = document.createElement('button');
                replyButton.className = 'btn btn-primary btn-reply';
                replyButton.textContent = 'Reply';
                replyButton.onclick = () => showReplyForm(message);

                messageElement.appendChild(senderElement);
                messageElement.appendChild(contentElement);
                messageElement.appendChild(timestampElement);
                messageElement.appendChild(replyButton);

                senderGroup.appendChild(messageElement);
            });

            messagesContainer.appendChild(senderGroup);
        }

        console.log("Populated messages list with:", messages);
    }

    function showReplyForm(message) {
        const replyFormContainer = document.getElementById('reply-form-container');
        replyFormContainer.innerHTML = `
            <h5>Reply to ${message.senderUsername}</h5>
            <form id="replyForm" class="mt-3 mb-3 p-2 shadow-lg rounded">
                <div class="mb-3">
                    <textarea id="reply-content" class="form-control" placeholder="Enter your reply" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Send Reply</button>
                <div class="spinner-border text-primary mt-3" id="replySpinner" role="status" style="display: none;">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </form>
        `;

        document.getElementById('replyForm').addEventListener('submit', function (event) {
            event.preventDefault();
            sendReply(message.senderId);
        });
    }

    function sendReply(receiverId) {
        const replyContent = document.getElementById('reply-content').value;
        if (!replyContent) {
            console.error("Please enter a reply message.");
            return;
        }

        const sendMessageDTO = {
            content: replyContent,
            senderId: currentUserId,
            receiverId: receiverId
        };

        console.log("Preparing to send reply:", sendMessageDTO);

        fetch('http://localhost:8080/messages/send', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(sendMessageDTO)
        })
            .then(response => {
                console.log("Sending reply...");
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error('Network response was not ok: ' + JSON.stringify(err));
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log("Reply sent successfully:", data);
                document.getElementById('reply-form-container').innerHTML = '';
                document.getElementById('successAlert').style.display = 'block';
                setTimeout(() => {
                    document.getElementById('successAlert').style.display = 'none';
                }, 3000);
                fetchMessagesByUserId(currentUserId);
            })
            .catch(error => {
                console.error('Error sending reply:', error);
                document.getElementById('errorAlert').style.display = 'block';
                setTimeout(() => {
                    document.getElementById('errorAlert').style.display = 'none';
                }, 3000);
            });
    }
});
