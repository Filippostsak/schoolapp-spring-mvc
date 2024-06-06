document.addEventListener("DOMContentLoaded", function () {
    console.log("ReplyMessage.js loaded");

    let userId;

    // Fetch current teacher information and get userId
    fetch('http://localhost:8080/messages/current/teacher')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(teacherId => fetch(`http://localhost:8080/messages/${teacherId}/userId`))
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            userId = data.id;
            console.log("Fetched User ID:", userId);
        })
        .catch(error => console.error('Error fetching user ID:', error));

    // Function to open the reply modal and populate it with the current message details
    function openReplyModal(messageId) {
        fetch(`http://localhost:8080/messages/message/${messageId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(message => {
                console.log("Fetched message details:", message);
                const modalLabel = document.getElementById('replyModalLabel');
                const replyContent = document.getElementById('reply-modal-content');
                const replyTimestamp = document.getElementById('reply-modal-timestamp');
                const replySender = document.getElementById('reply-modal-sender');
                const sendReplyButton = document.getElementById('send-reply-button');

                modalLabel.textContent = `Reply to ${message.senderUsername}`;
                replyContent.innerHTML = `<p><strong>Content:</strong> ${message.content}</p>`;
                replyTimestamp.innerHTML = `<p class="timestamp"><strong>Sent at:</strong> ${new Date(message.timestamp).toLocaleString()}</p>`;
                replySender.innerHTML = `<p><strong>From:</strong> ${message.senderUsername}</p>`;
                sendReplyButton.dataset.receiverId = message.senderId; // Set receiver ID for reply

                const replyModal = new bootstrap.Modal(document.getElementById('replyModal'));
                replyModal.show();
            })
            .catch(error => console.error('Error fetching message details:', error));
    }

    // Function to send the reply
    function sendReply() {
        const replyContent = document.getElementById('reply-message-content').value;
        const receiverId = document.getElementById('send-reply-button').dataset.receiverId;

        if (!replyContent) {
            console.error("Please enter a reply message.");
            return;
        }

        const sendMessageDTO = {
            content: replyContent,
            senderId: userId,
            receiverId: receiverId
        };

        console.log("Preparing to send reply:", sendMessageDTO);

        // Show spinner and hide button
        document.getElementById('reply-spinner').style.display = 'block';
        document.getElementById('send-reply-button').style.display = 'none';

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
                setTimeout(() => {
                    // Hide spinner, show success message and reset button
                    document.getElementById('reply-spinner').style.display = 'none';
                    alert('Reply sent successfully!');
                    document.getElementById('send-reply-button').style.display = 'none';

                    setTimeout(() => {
                        document.getElementById('send-reply-button').style.display = 'block';
                        document.getElementById('reply-message-content').value = ''; // Clear textarea
                    }, 3000);

                    const replyModal = bootstrap.Modal.getInstance(document.getElementById('replyModal'));
                    replyModal.hide();
                }, 1000);
            })
            .catch(error => {
                console.error('Error sending reply:', error);
                document.getElementById('reply-spinner').style.display = 'none';
                document.getElementById('send-reply-button').style.display = 'block';
                alert('Error sending reply!');
            });
    }

    // Attach event listeners
    document.getElementById('send-reply-button').addEventListener('click', sendReply);

    // Add event listeners to dynamically added view message buttons
    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('view-message-button')) {
            const messageId = event.target.dataset.messageId;
            openReplyModal(messageId);
        }
    });
});
