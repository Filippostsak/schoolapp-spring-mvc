// Function to open the Add Teacher Modal
function openAddTeacherModal(classroomId) {
    console.log("Opening modal for classroom ID:", classroomId);
    document.getElementById('classroomId').value = classroomId;
    document.getElementById('teacherUsername').value = '';
    document.getElementById('teacherSearchResults').style.display = 'none';
    document.getElementById('teacherSearchResults').innerHTML = '';
    const addTeacherModal = new bootstrap.Modal(document.getElementById('addTeacherModal'));
    addTeacherModal.show();
}

// Function to add a teacher to a classroom
function addTeacher() {
    const classroomId = document.getElementById('classroomId').value;
    const teacherUsername = document.getElementById('teacherUsername').value;

    fetch('/teachers/add-teacher', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            classroomId: classroomId,
            teacherUsername: teacherUsername
        })
    })
        .then(response => response.json())
        .then(result => {
            console.log("Add teacher result:", result);
            if (result.status === "success") {
                alert(result.message);
                window.location.reload();  // Reload the page to reflect the changes
            } else {
                alert(result.message);  // Show error message if not successful
            }
        })
        .catch(error => {
            console.error("Add teacher failed", error);
            alert("An error occurred while adding the teacher.");
        });
}

// Function to search classrooms
function searchClassrooms() {
    console.log("Searching classrooms");
    const input = document.getElementById('searchInput');
    const filter = input.value.toLowerCase();
    console.log("Search filter:", filter);
    const nodes = document.getElementsByClassName('col');

    for (let i = 0; i < nodes.length; i++) {
        console.log("Checking node", i, "with content:", nodes[i].innerText);
        if (nodes[i].innerText.toLowerCase().includes(filter)) {
            nodes[i].style.display = 'block';
        } else {
            nodes[i].style.display = 'none';
        }
    }
}

// Function to open the Delete Classroom Modal
function openDeleteModal(button) {
    const classroomId = button.getAttribute('data-id');
    const classroomName = button.getAttribute('data-name');
    console.log("Opening delete modal for classroom ID:", classroomId, "and classroom name:", classroomName);

    const confirmDeleteButton = document.getElementById('confirmDeleteButton');
    confirmDeleteButton.setAttribute('data-id', classroomId);
    confirmDeleteButton.setAttribute('data-name', classroomName);

    const deleteClassroomModal = new bootstrap.Modal(document.getElementById('deleteClassroomModal'));
    deleteClassroomModal.show();
}

// Event listener for confirming deletion of a classroom
document.getElementById('confirmDeleteButton').addEventListener('click', function() {
    const classroomId = this.getAttribute('data-id');
    const classroomName = this.getAttribute('data-name');
    const confirmClassroomName = document.getElementById('confirmClassroomName').value;

    if (classroomName === confirmClassroomName) {
        console.log("Classroom name confirmed. Attempting to delete classroom with ID:", classroomId);
        deleteClassroom(classroomId);
    } else {
        alert("Classroom name does not match. Please try again.");
    }
});

// Function to delete a classroom
function deleteClassroom(classroomId) {
    console.log("Attempting to delete classroom with ID:", classroomId);
    if (confirm("Are you sure you want to delete this classroom?")) {
        fetch('/teachers/delete-classroom/' + classroomId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                console.log("Delete result:", result);
                if (result.status === "success") {
                    alert(result.message);
                    window.location.reload();  // Reload the page to reflect the changes
                } else {
                    alert(result.message);  // Show error message if not successful
                }
            })
            .catch(error => {
                console.error("Delete failed", error);
                alert("An error occurred while deleting the classroom.");
            });
    } else {
        console.log("Deletion cancelled by user");
    }
}

// Function to search teacher usernames dynamically
function searchTeacherUsernames() {
    const input = document.getElementById('teacherUsername').value;
    if (input.length < 2) { // Start searching after 2 characters
        document.getElementById('teacherSearchResults').style.display = 'none';
        return;
    }
    fetch('/teachers/search-usernames?username=' + input)
        .then(response => response.json())
        .then(result => {
            const searchResults = document.getElementById('teacherSearchResults');
            searchResults.innerHTML = '';
            if (result.length > 0) {
                searchResults.style.display = 'block';
                result.forEach(username => {
                    const li = document.createElement('li');
                    li.className = 'list-group-item list-group-item-action';
                    li.textContent = username;
                    li.onclick = () => {
                        document.getElementById('teacherUsername').value = username;
                        searchResults.style.display = 'none';
                    };
                    searchResults.appendChild(li);
                });
            } else {
                searchResults.style.display = 'none';
            }
        })
        .catch(error => {
            console.error("Error searching teacher usernames:", error);
        });
}
