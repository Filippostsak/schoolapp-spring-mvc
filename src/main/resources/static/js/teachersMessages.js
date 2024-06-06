document.addEventListener("DOMContentLoaded", function () {
    let teacherId;
    let studentUsernames = new Map();

    console.log("Document loaded, starting fetch process...");

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

            setTimeout(() => {
                console.log("Fetching classrooms for Teacher ID:", teacherId);
                fetchClassroomsByTeacherId(teacherId);
            }, 1000);
        })
        .catch(error => console.error('Error fetching teacher ID:', error));

    function fetchClassroomsByTeacherId(teacherId) {
        fetch(`http://localhost:8080/classrooms/classrooms/${teacherId}`)
            .then(response => {
                console.log("Fetching classrooms...");
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(classrooms => {
                console.log("Fetched Classrooms:", classrooms);

                const fetchPromises = classrooms.map(classroom =>
                    fetch(`http://localhost:8080/messages/classroom/${classroom.id}`)
                        .then(response => {
                            console.log(`Fetching student usernames and IDs for Classroom ID: ${classroom.id}...`);
                            if (!response.ok) {
                                throw new Error('Network response was not ok: ' + response.statusText);
                            }
                            return response.json();
                        })
                        .then(students => {
                            students.forEach(student => {
                                studentUsernames.set(student.username, student.id);
                            });
                            console.log(`Fetched and processed students for Classroom ID: ${classroom.id}`);
                        })
                        .catch(error => console.error(`Error fetching students for classroom ID ${classroom.id}:`, error))
                );

                Promise.all(fetchPromises)
                    .then(() => {
                        console.log("All usernames fetched and processed:", Array.from(studentUsernames.keys()));
                        populateUsernamesSelect(Array.from(studentUsernames.keys()));
                    })
                    .catch(error => console.error('Error fetching all students by classroom IDs:', error));
            })
            .catch(error => console.error('Error fetching classrooms:', error));
    }

    function populateUsernamesSelect(usernames) {
        const selectElement = document.getElementById('names-select');
        selectElement.innerHTML = '';

        usernames.forEach(username => {
            const optionElement = document.createElement('option');
            optionElement.value = username;
            optionElement.textContent = username;
            selectElement.appendChild(optionElement);
        });

        console.log("Populated <select> element with usernames:", usernames);
    }

    document.getElementById('sendMessageForm').addEventListener('submit', function (event) {
        event.preventDefault();
        createMessageToSelectedUser();
    });

    function createMessageToSelectedUser() {
        const selectElement = document.getElementById('names-select');
        const selectedUsername = selectElement.value;
        const messageContent = document.getElementById('message-content').value;

        if (!selectedUsername || !messageContent) {
            console.error("Please select a username and enter a message.");
            return;
        }

        // Fetch user ID using the selected username
        fetch(`http://localhost:8080/messages/user/${selectedUsername}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(userId => {
                console.log("Fetched User ID for selected username:", userId);

                const sendMessageDTO = {
                    content: messageContent,
                    senderId: teacherId,
                    receiverId: userId
                };

                console.log("Preparing to send message:", sendMessageDTO);

                // Hide the send message button and show the spinner
                const sendMessageButton = document.getElementById('sendMessageButton');
                const messageSpinner = document.getElementById('messageSpinner');
                sendMessageButton.style.display = 'none';
                messageSpinner.style.display = 'inline-block';

                fetch('http://localhost:8080/messages/send', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(sendMessageDTO)
                })
                    .then(response => {
                        console.log("Sending message...");
                        if (!response.ok) {
                            return response.json().then(err => {
                                throw new Error('Network response was not ok: ' + JSON.stringify(err));
                            });
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log("Message sent successfully:", data);
                        document.getElementById('successAlert').style.display = 'block';
                        setTimeout(() => {
                            document.getElementById('successAlert').style.display = 'none';
                        }, 3000);
                        document.getElementById('sendMessageForm').reset();
                        document.querySelector('#sendMessageModal .btn-close').click();
                        setTimeout(() => {
                            messageSpinner.style.display = 'none';
                            sendMessageButton.style.display = 'inline-block';
                        }, 1000);
                    })
                    .catch(error => {
                        console.error('Error sending message:', error);
                        messageSpinner.style.display = 'none';
                        sendMessageButton.style.display = 'inline-block';
                    });
            })
            .catch(error => console.error('Error fetching user ID for selected username:', error));
    }
});