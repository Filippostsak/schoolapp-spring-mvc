function openAddTeacherModal(classroomId) {
    document.getElementById('classroomId').value = classroomId;
    const addTeacherModal = new bootstrap.Modal(document.getElementById('addTeacherModal'));
    addTeacherModal.show();
}

function searchClassrooms() {
    const input = document.getElementById('searchInput');
    const filter = input.value.toLowerCase();
    const nodes = document.getElementsByClassName('col');

    for (let i = 0; i < nodes.length; i++) {
        if (nodes[i].innerText.toLowerCase().includes(filter)) {
            nodes[i].style.display = 'block';
        } else {
            nodes[i].style.display = 'none';
        }
    }
}
function deleteClassroom(classroomId) {
    if (confirm("Are you sure you want to delete this classroom?")) {
        $.ajax({
            url: '/teachers/delete-classroom/' + classroomId,
            type: 'DELETE',
            success: function(result) {
                location.reload();
            },
            error: function(err) {
                alert("An error occurred while deleting the classroom.");
            }
        });
    }
}
