// Function to show spinner and hide buttons
function showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer) {
    document.getElementById(spinnerId).style.display = 'block';
    document.getElementById(infoId).style.display = 'block';
    buttonContainer.style.display = 'none';
}

// Function to hide spinner and show buttons
function hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer) {
    document.getElementById(spinnerId).style.display = 'none';
    document.getElementById(infoId).style.display = 'none';
    buttonContainer.style.display = 'flex';
}

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

    const spinnerId = 'addTeacherSpinner';
    const infoId = 'addTeacherInfo';
    const buttonContainer = document.querySelector('#addTeacherModal .modal-footer');

    showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer);

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
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            console.log("Add teacher result:", result);
            if (result.status === "success") {
                alert(result.message);
                setTimeout(() => {
                    window.location.reload();
                }, 1000);  // Reload the page to reflect the changes
            } else {
                alert(result.message);  // Show error message if not successful
            }
        })
        .catch(error => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
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

    const spinnerId = 'deleteClassroomSpinner';
    const infoId = 'deleteClassroomInfo';
    const buttonContainer = document.querySelector('#deleteClassroomModal .modal-footer');

    showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer);

    if (confirm("Are you sure you want to delete this classroom?")) {
        fetch('/teachers/delete-classroom/' + classroomId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(result => {
                hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
                console.log("Delete result:", result);
                if (result.status === "success") {
                    alert(result.message);
                    setTimeout(() => {
                        window.location.reload();
                    }, 1000);  // Reload the page to reflect the changes
                } else {
                    alert(result.message);  // Show error message if not successful
                }
            })
            .catch(error => {
                hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
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

// Function to open the Update Classroom Modal
function openUpdateClassroomModal(classroomId) {
    console.log("Opening update modal for classroom ID:", classroomId);
    fetch(`/teachers/classroom/${classroomId}`)
        .then(response => response.json())
        .then(classroom => {
            document.getElementById('classroomIdUpdate').value = classroom.id;
            document.getElementById('classroomNameUpdate').value = classroom.name;
            document.getElementById('classroomDescriptionUpdate').value = classroom.description;
            document.getElementById('classroomUrlUpdate').value = classroom.classroomUrl;
            document.getElementById('imageUrlUpdate').value = classroom.imageUrl;
            const updateModal = new bootstrap.Modal(document.getElementById('updateClassroomModal'));
            updateModal.show();
        })
        .catch(error => {
            console.error("Error fetching classroom details:", error);
            alert("An error occurred while fetching classroom details.");
        });
}

// Function to update classroom details
function updateClassroomDetails() {
    const form = document.getElementById('updateClassroomForm');
    const formData = new FormData(form);
    const id = formData.get('classroomId');

    const updateDTO = {
        name: formData.get('classroomName'),
        description: formData.get('classroomDescription'),
        classroomUrl: formData.get('classroomUrl'),
        imageUrl: formData.get('imageUrl')
    };

    const spinnerId = 'updateClassroomSpinner';
    const infoId = 'updateClassroomInfo';
    const buttonContainer = document.querySelector('#updateClassroomModal .modal-footer');

    showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer);

    fetch(`/teachers/update-classroom/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updateDTO)
    })
        .then(response => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            if (response.ok) {
                alert('Classroom updated successfully.');
                setTimeout(() => {
                    window.location.reload();
                }, 1000);  // Reload the page to reflect the changes
            } else {
                alert('Failed to update classroom.');
            }
        })
        .catch(error => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            console.error('Error:', error);
            alert('An error occurred while updating the classroom.');
        });
}

// Function to open the Set Meeting Modal
function openSetMeetingModal(classroomId) {
    document.getElementById('classroomIdMeeting').value = classroomId;
    document.getElementById('meetingDate').value = '';
    document.getElementById('meetingTime').value = '';
    document.getElementById('meetingEndTime').value = '';
    document.getElementById('meetingEndDate').value = '';
    const setMeetingModal = new bootstrap.Modal(document.getElementById('setMeetingModal'));
    setMeetingModal.show();
}

// Function to set a meeting
function setMeeting() {
    const classroomId = document.getElementById('classroomIdMeeting').value;
    const meetingDate = document.getElementById('meetingDate').value;
    const meetingTime = document.getElementById('meetingTime').value;
    const meetingEndTime = document.getElementById('meetingEndTime').value;
    const meetingEndDate = document.getElementById('meetingEndDate').value;

    const spinnerId = 'setMeetingSpinner';
    const infoId = 'setMeetingInfo';
    const buttonContainer = document.querySelector('#setMeetingModal .modal-footer');

    showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer);

    fetch('/teachers/create-meeting-date/' + classroomId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            date: meetingDate,
            time: meetingTime,
            endTime: meetingEndTime,
            endDate: meetingEndDate
        })
    })
        .then(response => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            if (response.ok) {
                alert('Meeting set successfully.');
                setTimeout(() => {
                    const setMeetingModal = bootstrap.Modal.getInstance(document.getElementById('setMeetingModal'));
                    setMeetingModal.hide();
                    window.location.reload();
                }, 1000);  // Reload the page to reflect the changes
            } else {
                alert('Failed to set meeting.');
            }
        })
        .catch(error => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            console.error("Error setting meeting", error);
            alert('An error occurred while setting the meeting.');
        });
}

// Function to open the Remove Student Modal
function openRemoveStudentModal(classroomId, studentId) {
    console.log("Opening remove student modal for classroom ID:", classroomId, "and student ID:", studentId);
    document.getElementById('classroomIdRemove').value = classroomId;
    document.getElementById('studentIdRemove').value = studentId;
    const removeStudentModal = new bootstrap.Modal(document.getElementById('removeStudentModal'));
    removeStudentModal.show();
}

// Function to remove a student from a classroom
function removeStudent() {
    const classroomId = document.getElementById('classroomIdRemove').value;
    const studentId = document.getElementById('studentIdRemove').value;

    const spinnerId = 'removeStudentSpinner';
    const infoId = 'removeStudentInfo';
    const buttonContainer = document.querySelector('#removeStudentModal .modal-footer');

    showSpinnerAndHideButtons(spinnerId, infoId, buttonContainer);

    fetch(`/api/classrooms/${classroomId}/remove-student/${studentId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            if (response.ok) {
                alert('Student removed successfully.');
                setTimeout(() => {
                    window.location.reload();
                }, 1000);  // Reload the page to reflect the changes
            } else {
                alert('Failed to remove student. Please try again.');
            }
        })
        .catch(error => {
            hideSpinnerAndShowButtons(spinnerId, infoId, buttonContainer);
            console.error('Error:', error);
            alert('An error occurred while removing the student.');
        });
}
