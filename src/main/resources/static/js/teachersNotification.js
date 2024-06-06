let userId;
let teacherId;
let username;

document.addEventListener("DOMContentLoaded", function () {
    console.log("Document loaded, starting fetch process for notifications...");

    // Fetch current teacher information
    fetch('http://localhost:8080/messages/current/teacher')
        .then(response => {
            console.log("Fetching current teacher ID...");
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            teacherId = data;
            console.log("Fetched Teacher ID:", teacherId);

            // Fetch user ID using teacher ID
            return fetch(`http://localhost:8080/messages/${teacherId}/userId`);
        })
        .then(response => {
            console.log("Fetching user ID using teacher ID...");
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            userId = data.id;
            console.log("Fetched User ID:", userId);

            // Fetch username using user ID
            return fetch(`http://localhost:8080/messages/get-user-name/${userId}`);
        })
        .then(response => {
            console.log("Fetching username using user ID...");
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            username = data.username;
            console.log("Fetched Username:", username);

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
                <div class="spinner-border text-primary mt-3" id="delete-spinner-${notification.id}" role="status" style="display: none;">
                    <span class="visually-hidden">Loading...</span>
                </div>
            `;
            notificationsList.appendChild(notificationElement);

            notificationElement.querySelector('.delete-notification-button').addEventListener('click', () => {
                deleteNotification(notification.id);
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

    function deleteNotification(notificationId) {
        const spinner = document.getElementById(`delete-spinner-${notificationId}`);
        const deleteButton = document.querySelector(`.delete-notification-button[data-notification-id="${notificationId}"]`);

        deleteButton.style.display = 'none';
        spinner.style.display = 'inline-block';

        fetch(`http://localhost:8080/notifications/delete`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id: notificationId })
        })
            .then(response => {
                setTimeout(() => {
                    spinner.style.display = 'none';
                    if (!response.ok) {
                        deleteButton.style.display = 'inline-block';
                        throw new Error('Network response was not ok: ' + response.statusText);
                    }
                    console.log(`Notification ${notificationId} deleted successfully`);
                    fetchNotifications(userId);  // Refresh the notifications list
                    alert('Notification deleted successfully!');
                }, 1000);
            })
            .catch(error => {
                console.error('Error deleting notification:', error);
                spinner.style.display = 'none';
                deleteButton.style.display = 'inline-block';
                alert('Error deleting notification!');
            });
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
