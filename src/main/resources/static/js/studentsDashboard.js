async function getCurrentStudentId() {
    try {
        const response = await fetch('/students/current');
        if (response.ok) {
            const student = await response.json();
            return student.id;
        } else {
            console.error('Failed to retrieve current student');
            return null;
        }
    } catch (error) {
        console.error('Error:', error);
        return null;
    }
}

async function getTeacherIdByUsername(username) {
    try {
        const response = await fetch(`/messages/teacher/${username}`);
        if (response.ok) {
            const teacherId = await response.json();
            return teacherId;
        } else {
            console.error('Failed to retrieve teacher ID');
            return null;
        }
    } catch (error) {
        console.error('Error:', error);
        return null;
    }
}

async function createMessage(message) {
    const button = document.querySelector('#sendMessageButton');
    const spinner = document.querySelector('#messageSpinner');
    const successAlert = document.getElementById('successAlert');
    const errorAlert = document.getElementById('errorAlert');

    button.style.display = 'none';
    spinner.style.display = 'inline-block';

    try {
        setTimeout(async () => {
            const response = await fetch(`/messages/send`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    content: message.content,
                    senderId: message.senderId,
                    receiverId: message.receiverId, // Send teacher's ID
                    receiverUsername: message.receiverUsername
                })
            });

            const responseText = await response.text(); // Read the response as text
            console.log('Response:', responseText);

            if (response.ok) {
                const createdMessage = JSON.parse(responseText);
                console.log('Message created:', createdMessage);
                successAlert.style.display = 'block';
                setTimeout(() => {
                    successAlert.style.display = 'none';
                }, 3000);
            } else {
                console.error('Failed to create message:', responseText);
                errorAlert.style.display = 'block';
                setTimeout(() => {
                    errorAlert.style.display = 'none';
                }, 3000);
            }

            button.style.display = 'inline-block';
            spinner.style.display = 'none';
        }, 1000);
    } catch (error) {
        console.error('Error:', error);
        errorAlert.style.display = 'block';
        setTimeout(() => {
            errorAlert.style.display = 'none';
        }, 3000);
        button.style.display = 'inline-block';
        spinner.style.display = 'none';
    }
}

async function fetchAndDisplayEntitiesForMessages() {
    const studentId = await getCurrentStudentId();
    if (!studentId) {
        console.error('No student ID available');
        return;
    }

    try {
        const response = await fetch(`/classrooms/student/${studentId}`);
        if (response.ok) {
            const classrooms = await response.json();
            const usernames = new Set();

            classrooms.forEach(classroom => {
                if (classroom.creator && classroom.creator.username) {
                    usernames.add(classroom.creator.username);
                }
                classroom.extraTeachers.forEach(teacher => {
                    if (teacher.username) {
                        usernames.add(teacher.username);
                    }
                });
            });

            const usernamesSelect = document.getElementById('usernames-select');
            usernamesSelect.innerHTML = '';

            if (usernames.size === 0) {
                const option = document.createElement('option');
                option.text = 'No users found to send messages to';
                usernamesSelect.add(option);
                return;
            }

            usernames.forEach(username => {
                const option = document.createElement('option');
                option.value = username;
                option.text = username;
                usernamesSelect.add(option);
            });
        } else {
            console.error('Failed to retrieve classrooms');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function createMessageToSelectedUser() {
    const usernamesSelect = document.getElementById('usernames-select');
    const content = document.getElementById('message-content').value;

    // Validate message content length
    if (!validateMessageContent(content)) {
        return;
    }

    const receiverUsername = usernamesSelect.value;
    const receiverId = await getTeacherIdByUsername(receiverUsername);

    if (!receiverId) {
        alert('Failed to get receiver ID');
        return;
    }

    const message = {
        senderId: await getCurrentStudentId(),
        receiverId: receiverId,
        receiverUsername: receiverUsername,
        content: content
    };

    await createMessage(message);
}

function validateMessageContent(content) {
    const errorAlert = document.getElementById('errorAlert');

    if (content.length < 1) {
        showErrorAlert("Message content cannot be empty");
        return false;
    }

    if (content.length > 500) {
        showErrorAlert("Message content cannot exceed 500 characters");
        return false;
    }

    return true;
}

function showErrorAlert(message) {
    const errorAlert = document.getElementById('errorAlert');
    errorAlert.textContent = message; // Set custom error message
    errorAlert.style.display = 'block';
    setTimeout(() => {
        errorAlert.style.display = 'none';
    }, 3000);
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('default-section').style.display = 'block';
    fetchAndDisplayEntitiesForMessages();
});

function showSection(event, sectionId) {
    event.preventDefault();
    const currentActive = document.querySelector('.content-section.active');
    if (currentActive) {
        currentActive.classList.remove('active');
        currentActive.style.display = 'none';
    }
    const newSection = document.getElementById(sectionId);
    newSection.style.display = 'block';
    newSection.classList.add('active');
    document.querySelectorAll('.list-group-item').forEach(item => {
        item.classList.remove('active');
    });
    event.target.classList.add('active');
}

function showSpinner() {
    const spinner = document.querySelector('.spinner-border');
    spinner.style.display = 'inline-block';
}

function hideSpinner() {
    const spinner = document.querySelector('.spinner-border');
    spinner.style.display = 'none';
}

function showSuccessAlert() {
    const successAlert = document.getElementById('successAlert');
    successAlert.style.display = 'block';
    setTimeout(() => {
        successAlert.style.display = 'none';
    }, 3000);
}

function showErrorAlert() {
    const errorAlert = document.getElementById('errorAlert');
    errorAlert.style.display = 'block';
    setTimeout(() => {
        errorAlert.style.display = 'none';
    }, 3000);
}

async function fetchAndDisplayMessages() {
    const studentId = await getCurrentStudentId();
    if (!studentId) {
        console.error('No student ID available');
        return;
    }

    const messages = await getMessagesBySenderId(studentId);
    const messagesContainer = document.getElementById('messages-container');
    messagesContainer.innerHTML = '';

    if (messages.length === 0) {
        messagesContainer.innerHTML = '<p>No messages found</p>';
        return;
    }

    messages.forEach(message => {
        const messageElement = document.createElement('div');
        messageElement.className = 'message';
        messageElement.innerHTML = `
            <h3>Message ID: ${message.id}</h3>
            <p>Sender: ${message.senderId}</p>
            <p>Receiver: ${message.receiverId}</p>
            <p>Content: ${message.content}</p>
            <button onclick="deleteMessage(${message.id})">Delete</button>
        `;
        messagesContainer.appendChild(messageElement);
    });
}
