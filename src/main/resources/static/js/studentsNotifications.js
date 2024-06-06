document.addEventListener("DOMContentLoaded", function () {
    let studentId;
    let userId; // For notifications

    console.log("Document loaded, starting fetch process for notifications...");

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
            console.log("Fetched User ID:", userId);

            fetchNotifications(userId);
        })
        .catch(error => console.error('Error handling response:', error));

    function fetchNotifications(userId) {
        fetch(`http://localhost:8080/notifications/receiver/${userId}`)
            .then(response => {
                console.log("Fetching notifications...");
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(notifications => {
                console.log("Fetched notifications:", notifications);
                updateNotificationCount(notifications);
                displayNotifications(notifications);
            })
            .catch(error => console.error('Error fetching notifications:', error));
    }

    function updateNotificationCount(notifications) {
        const unreadCount = notifications.filter(notification => notification.notify !== 'READ').length;
        const notificationCountElement = document.getElementById('notification-count');
        notificationCountElement.textContent = unreadCount;
        if (unreadCount === 0) {
            notificationCountElement.style.visibility = 'hidden';
        } else {
            notificationCountElement.style.visibility = 'visible';
        }
    }

    function displayNotifications(notifications) {
        const notificationsList = document.getElementById('notifications-list');
        notificationsList.innerHTML = '';

        notifications.forEach(notification => {
            const notificationElement = document.createElement('div');
            notificationElement.classList.add('notification', 'border', 'rounded', 'p-3', 'mb-3', 'bg-light', 'text-dark');
            notificationElement.innerHTML = `
                <p><strong>From:</strong> ${notification.senderUsername}</p>
                <p><strong>Content:</strong> ${notification.content}</p>
                <p class="timestamp"><strong>Sent at:</strong> ${new Date(notification.timestamp).toLocaleString()}</p>
                <div class="form-check">
                    <input class="form-check-input notification-read-checkbox" type="checkbox" id="notification-read-checkbox-${notification.id}" data-notification-id="${notification.id}" ${notification.notify === 'READ' ? 'checked' : ''}>
                    <label class="form-check-label" for="notification-read-checkbox-${notification.id}">
                        I have read it
                    </label>
                </div>
                <button class="btn btn-primary view-message-button" data-notification-id="${notification.id}" data-message-id="${notification.messageId}">View Message</button>
                <button class="btn btn-danger delete-notification-button" data-notification-id="${notification.id}">Delete</button>
            `;
            notificationsList.appendChild(notificationElement);

            notificationElement.querySelector('.delete-notification-button').addEventListener('click', () => {
                deleteNotification(notification.id);
            });

            notificationElement.querySelector('.view-message-button').addEventListener('click', () => {
                viewMessage(notification.messageId);
            });

            notificationElement.querySelector(`#notification-read-checkbox-${notification.id}`).addEventListener('change', (event) => {
                if (event.target.checked) {
                    markNotificationAsRead(notification.id);
                } else {
                    markNotificationAsUnread(notification.id);
                }
            });
        });

        updateNotificationCount(notifications);
    }

    function viewMessage(messageId) {
        fetch(`http://localhost:8080/messages/message/${messageId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(message => {
                console.log("Fetched message details:", message);
                const modalLabel = document.getElementById('viewMessagesModalLabel');
                const messagesContainerView = document.getElementById('messages-container-view');
                const replyFormContainer = document.getElementById('reply-form-container');

                modalLabel.textContent = `Message from ${message.senderUsername}`;
                messagesContainerView.innerHTML = `
                    <p><strong>From:</strong> ${message.senderUsername}</p>
                    <p><strong>Content:</strong> ${message.content}</p>
                    <p class="timestamp"><strong>Sent at:</strong> ${new Date(message.timestamp).toLocaleString()}</p>
                `;

                replyFormContainer.innerHTML = `
                    <h5>Reply</h5>
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

                const messageModal = new bootstrap.Modal(document.getElementById('viewMessagesModal'));
                messageModal.show();
            })
            .catch(error => console.error('Error fetching message details:', error));
    }

    function sendReply(receiverId) {
        const replyContent = document.getElementById('reply-content').value;
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
                fetchNotifications(userId); // Refresh notifications after sending reply
            })
            .catch(error => {
                console.error('Error sending reply:', error);
                document.getElementById('errorAlert').style.display = 'block';
                setTimeout(() => {
                    document.getElementById('errorAlert').style.display = 'none';
                }, 3000);
            });
    }

    function deleteNotification(notificationId) {
        fetch(`http://localhost:8080/notifications/delete`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id: notificationId })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                console.log(`Notification ${notificationId} deleted successfully`);
                fetchNotifications(userId);  // Refresh the notifications list
            })
            .catch(error => console.error('Error deleting notification:', error));
    }

    function markNotificationAsRead(notificationId) {
        fetch(`http://localhost:8080/notifications/read/${notificationId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                console.log(`Notification ${notificationId} marked as read`);
                fetchNotifications(userId);  // Refresh the notifications list
            })
            .catch(error => console.error('Error marking notification as read:', error));
    }

    function markNotificationAsUnread(notificationId) {
        fetch(`http://localhost:8080/notifications/unread/${notificationId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                console.log(`Notification ${notificationId} marked as unread`);
                fetchNotifications(userId);  // Refresh the notifications list
            })
            .catch(error => console.error('Error marking notification as unread:', error));
    }
});
