document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('default-section').style.display = 'block';
    fetchClassroomsAndMeetings();
});

function showSection(event, sectionId) {
    event.preventDefault();
    const currentActive = document.querySelector('.content-section.active');
    if (currentActive) {
        currentActive.classList.remove('active');
        setTimeout(() => {
            currentActive.style.display = 'none';
        }, 600);
    }
    const newSection = document.getElementById(sectionId);
    newSection.style.display = 'block';
    setTimeout(() => {
        newSection.classList.add('active');
    }, 10);
    document.querySelectorAll('.list-group-item').forEach(item => {
        item.classList.remove('active');
    });
    event.target.classList.add('active');
}

function showSpinner(event, button) {
    event.preventDefault();
    const spinner = button.nextElementSibling;
    button.style.display = 'none';
    spinner.style.display = 'inline-block';

    setTimeout(() => {
        window.location.href = button.getAttribute('href');
    }, 1000);
}

function fetchClassroomsAndMeetings() {
    fetch('/teachers/classrooms-with-meetings')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('classrooms-and-meetings-list');
            list.innerHTML = '';

            if (data && Array.isArray(data) && data.length > 0) {
                const table = document.createElement('table');
                table.classList.add('table', 'table-striped', 'table-hover', 'mt-3', 'text-center');

                const thead = document.createElement('thead');
                thead.classList.add('thead-dark');
                thead.innerHTML = `
                    <tr>
                        <th scope="col">Classroom Name</th>
                        <th scope="col">Classroom URL</th>
                        <th scope="col">Starting Date</th>
                        <th scope="col">Starting Time</th>
                        <th scope="col">End Date</th>
                        <th scope="col">End Time</th>
                        <th scope="col">Actions</th>
                    </tr>
                `;
                table.appendChild(thead);

                const tbody = document.createElement('tbody');

                data.forEach(classroom => {
                    classroom.meetingDates.forEach(meeting => {
                        const tr = document.createElement('tr');
                        tr.dataset.meetingId = meeting.id;
                        tr.dataset.classroomId = classroom.id;
                        tr.innerHTML = `
                            <td>${classroom.name}</td>
                            <td><a href="${classroom.classroomUrl}" target="_blank">${classroom.classroomUrl}</a></td>
                            <td>${meeting.date || 'N/A'}</td>
                            <td>${meeting.time || 'N/A'}</td>
                            <td>${meeting.endDate || 'N/A'}</td>
                            <td>${meeting.endTime || 'N/A'}</td>
                            <td class="d-flex justify-content-center gap-2">
                                <button class="btn btn-warning btn-sm" onclick="showUpdateModal(${meeting.id}, ${classroom.id}, '${meeting.date}', '${meeting.time}', '${meeting.endDate}', '${meeting.endTime}', ${meeting.active})">Update</button>
                                <button class="btn btn-danger btn-sm" onclick="deleteMeeting(${meeting.id})">Remove</button>
                            </td>
                        `;
                        tbody.appendChild(tr);
                    });
                });

                table.appendChild(tbody);
                list.appendChild(table);
            } else {
                list.textContent = 'No classrooms found.';
            }
        })
        .catch(error => {
            console.error('Error fetching classrooms and meetings:', error);
            const list = document.getElementById('classrooms-and-meetings-list');
            list.textContent = 'Error fetching classrooms and meetings. Please try again later.';
        });
}

function showUpdateModal(meetingId, classroomId, date, time, endDate, endTime, active) {
    document.getElementById('updateMeetingId').value = meetingId;
    document.getElementById('updateClassroomId').value = classroomId; // Add this line
    document.getElementById('updateDate').value = date;
    document.getElementById('updateTime').value = time;
    document.getElementById('updateEndDate').value = endDate;
    document.getElementById('updateEndTime').value = endTime;
    document.getElementById('updateActive').checked = active;
    const updateMeetingModal = new bootstrap.Modal(document.getElementById('updateMeetingModal'));
    updateMeetingModal.show();
}


function updateMeetingDetails() {
    const meetingId = document.getElementById('updateMeetingId').value;
    const classroomId = document.getElementById('updateClassroomId').value; // Ensure classroomId is retrieved here
    const date = document.getElementById('updateDate').value;
    const time = document.getElementById('updateTime').value;
    const endDate = document.getElementById('updateEndDate').value;
    const endTime = document.getElementById('updateEndTime').value;
    const isActive = document.getElementById('updateActive').checked;

    const data = {
        id: meetingId,
        date: date,
        time: time,
        endDate: endDate,
        endTime: endTime,
        active: isActive
    };

    // Show spinner
    document.getElementById('updateMeetingSpinner').style.display = 'inline-block';
    document.getElementById('updateMeetingInfo').style.display = 'none';

    fetch(`/teachers/update-meeting-date/${classroomId}/${meetingId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('updateMeetingSpinner').style.display = 'none';
            document.getElementById('updateMeetingInfo').style.display = 'block';
            document.getElementById('updateMeetingInfo').innerText = data.message;

            // Update the table row with new data
            const row = document.querySelector(`tr[data-meeting-id="${meetingId}"]`);
            if (row) {
                row.children[2].innerText = date || 'N/A'; // Starting Date
                row.children[3].innerText = time || 'N/A'; // Starting Time
                row.children[4].innerText = endDate || 'N/A'; // End Date
                row.children[5].innerText = endTime || 'N/A'; // End Time
            }

            // Close the modal
            const updateMeetingModal = bootstrap.Modal.getInstance(document.getElementById('updateMeetingModal'));
            updateMeetingModal.hide();
        })
        .catch(error => {
            document.getElementById('updateMeetingSpinner').style.display = 'none';
            document.getElementById('updateMeetingInfo').style.display = 'block';
            document.getElementById('updateMeetingInfo').innerText = 'Error updating meeting!';
            console.error('Error updating meeting:', error);
        });
}

function deleteMeeting(meetingId) {
    if (confirm('Are you sure you want to delete this meeting?')) {
        fetch(`/teachers/delete-meeting/${meetingId}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('Meeting deleted successfully');
                fetchClassroomsAndMeetings();
            })
            .catch(error => {
                console.error('Error deleting meeting:', error);
                alert('Error deleting meeting. Please try again later.');
            });
    }
}
