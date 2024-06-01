document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM fully loaded and parsed');
    document.getElementById('searchButton').addEventListener('click', () => {
        console.log('Search button clicked');
        searchStudents();
    });
});

function searchStudents() {
    const spinner = document.getElementById('spinner');
    spinner.style.display = 'inline-block';

    const lastname = document.getElementById('lastname').value;
    const limit = document.getElementById('limit').value;
    const url = `/teachers/search-students-dynamic?lastname=${encodeURIComponent(lastname)}&limit=${limit}`;

    console.log('Fetching data from URL:', url);

    fetch(url)
        .then(response => {
            spinner.style.display = 'none';
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Received data:', data);
            if (typeof data === 'string') {
                showError(data);
            } else {
                fetchTeacherClassrooms(data);
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            showError('An error occurred while retrieving students.');
        });
}

function showError(message) {
    console.log('Showing error:', message);
    const error = document.getElementById('error');
    error.style.display = 'block';
    error.innerText = message;
    document.getElementById('studentsContainer').style.display = 'none';
    document.getElementById('resultsTable').style.display = 'none';
}

let teacherClassroomsInfo = [];

async function fetchTeacherId() {
    const url = `/teachers/current-teacher-id/1`; // Adjust this URL and ID to your API endpoint
    console.log('Fetching teacher ID from URL:', url);

    try {
        const response = await fetch(url);
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        console.log('Received teacher data:', data);
        return data.id; // Extract the ID from the response
    } catch (error) {
        console.error('Error fetching teacher ID:', error);
        throw error;
    }
}

async function fetchTeacherClassrooms(students) {
    try {
        const teacherId = await fetchTeacherId();
        const url = `/teachers/find-classrooms-by-teacher-id/${teacherId}`;

        console.log('Fetching teacher classrooms from URL:', url);

        const response = await fetch(url);
        console.log('Response status:', response.status);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const classrooms = await response.json();
        console.log('Received classrooms:', classrooms);
        teacherClassroomsInfo = classrooms; // Store the classroom information
        updateResults(students, classrooms);
    } catch (error) {
        console.error('Error fetching teacher classrooms:', error);
        showError('An error occurred while retrieving teacher classrooms.');
    }
}

function updateResults(students, teacherClassrooms) {
    console.log('Updating results with students:', students);
    const studentsContainer = document.getElementById('studentsContainer');
    const resultsTable = document.getElementById('resultsTable');
    const resultsTableBody = resultsTable.querySelector('tbody');
    const noResults = document.getElementById('noResults');
    const error = document.getElementById('error');

    resultsTableBody.innerHTML = '';

    if (students.length === 0) {
        console.log('No students found');
        noResults.style.display = 'block';
        studentsContainer.style.display = 'none';
        resultsTable.style.display = 'none';
        error.style.display = 'none';
    } else {
        console.log('Students found:', students.length);
        noResults.style.display = 'none';
        studentsContainer.style.display = 'block';
        resultsTable.style.display = 'table';
        error.style.display = 'none';

        students.forEach(student => {
            console.log('Processing student:', student);
            const row = document.createElement('tr');
            const yourClassrooms = teacherClassrooms.map(classroom => `<option value="${classroom.id}">${classroom.name}</option>`).join('');
            row.innerHTML = `
                <td class="text-center">${student.firstname}</td>
                <td class="text-center">${student.lastname}</td>
                <td class="text-center">${student.email}</td>
                <td class="text-center">${student.country}</td>
                <td class="text-center student-classrooms" data-student-id="${student.id}">${student.classroomIds.map(id => teacherClassrooms.find(c => c.id === id)?.name || '').join(', ')}</td>
                <td class="text-center">
                    <select class="form-select text-center classroom-select">
                        ${yourClassrooms}
                    </select>
                </td>
                <td class="text-center">
                    <button class="btn btn-primary mt-2 add-student-btn" data-student-id="${student.id}">Add</button>
                    <button class="btn btn-danger mt-2 remove-student-btn" data-student-id="${student.id}">Remove</button>
                </td>
            `;
            resultsTableBody.appendChild(row);
        });

        addEventListenersToButtons(students);
    }
}

function addEventListenersToButtons(students) {
    document.querySelectorAll('.add-student-btn').forEach(button => {
        button.addEventListener('click', (event) => {
            const studentId = event.target.getAttribute('data-student-id');
            const selectElement = event.target.closest('tr').querySelector('.classroom-select');
            const classroomId = selectElement.value;
            const student = students.find(s => s.id === parseInt(studentId));

            // Check if student is already in the selected classroom
            const isAlreadyInClassroom = student.classroomIds.includes(parseInt(classroomId));

            if (isAlreadyInClassroom) {
                alert('Student is already registered in this classroom.');
            } else {
                addStudentToClassroom(studentId, classroomId, student);
            }
        });
    });

    document.querySelectorAll('.remove-student-btn').forEach(button => {
        button.addEventListener('click', (event) => {
            const studentId = event.target.getAttribute('data-student-id');
            const selectElement = event.target.closest('tr').querySelector('.classroom-select');
            const classroomId = selectElement.value;
            const student = students.find(s => s.id === parseInt(studentId));

            // Check if student is not in the selected classroom
            const isNotInClassroom = !student.classroomIds.includes(parseInt(classroomId));

            if (isNotInClassroom) {
                alert('Student is not registered in this classroom.');
            } else {
                removeStudentFromClassroom(studentId, classroomId, student);
            }
        });
    });
}

function addStudentToClassroom(studentId, classroomId, student) {
    const url = `/teachers/add-student-to-classroom/${classroomId}/student/${studentId}`;

    console.log('Adding student to classroom with URL:', url);

    fetch(url, {
        method: 'POST',
    })
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            console.log('Received response:', data);
            alert('Student added to classroom successfully.');
            student.classroomIds.push(parseInt(classroomId)); // Update the local state
            refreshStudentClassrooms(studentId, student);
        })
        .catch(error => {
            console.error('Error adding student to classroom:', error);
            alert('An error occurred while adding the student to the classroom.');
        });
}

async function removeStudentFromClassroom(studentId, classroomId, student) {
    const url = `/teachers/remove-student-from-classroom/${classroomId}/student/${studentId}`;
    console.log('Removing student from classroom with URL:', url);

    try {
        const response = await fetch(url, { method: 'DELETE' });
        console.log('Response status:', response.status);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.text();
        console.log('Received response:', data);
        alert('Student removed from classroom successfully.');

        const index = student.classroomIds.indexOf(parseInt(classroomId));
        if (index > -1) {
            student.classroomIds.splice(index, 1);
        }

        await refreshStudentClassrooms(studentId);
    } catch (error) {
        console.error('Error removing student from classroom:', error);
        alert('An error occurred while removing the student from the classroom.');
    }
}


async function refreshStudentClassrooms(studentId) {
    const url = `/teachers/find-classrooms-by-student-id/${studentId}`;

    console.log('Fetching updated classrooms for student with URL:', url);

    fetch(url)
        .then(response => {
            console.log('Response status:', response.status);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Received updated student classrooms:', data);
            const studentClassroomsElement = document.querySelector(`.student-classrooms[data-student-id="${studentId}"]`);
            if (data && data.classrooms) {
                studentClassroomsElement.innerHTML = data.classrooms.map(classroom => classroom.name).join(', ');
            } else {
                console.error('No valid classrooms received:', data);
                studentClassroomsElement.innerHTML = 'No classrooms available';
            }
        })
        .catch(error => {
            console.error('Error fetching updated student classrooms:', error);
            const studentClassroomsElement = document.querySelector(`.student-classrooms[data-student-id="${studentId}"]`);
            studentClassroomsElement.innerHTML = 'Error updating classrooms';
        });
}
