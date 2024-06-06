document.addEventListener("DOMContentLoaded", function () {
    let teacherId;

    console.log("Document loaded, starting fetch process for received messages...");

    fetch('http://localhost:8080/messages/current/teacher')
        .then(response => {
            console.log("Fetching current teacher ID for received messages...");
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
                displayMessages(messages);
            })
            .catch(error => console.error('Error fetching messages:', error));
    }

    function displayMessages(messages) {
        const messagesContainer = document.getElementById('messages-container-view');

        if (!messagesContainer) {
            console.error("Error: 'messages-container-view' element not found.");
            return;
        }

        messagesContainer.innerHTML = '';

        messages.forEach(message => {
            const messageElement = document.createElement('div');
            messageElement.classList.add('message');
            messageElement.innerHTML = `
                <p><strong>From:</strong> ${message.senderUsername}</p>
                <p><strong>Message:</strong> ${message.content}</p>
                <p><strong>Sent at:</strong> ${new Date(message.timestamp).toLocaleString()}</p>
            `;
            messagesContainer.appendChild(messageElement);
        });
    }
});
