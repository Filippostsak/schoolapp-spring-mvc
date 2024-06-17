document.addEventListener('DOMContentLoaded', function() {
    // Show create user modal
    document.getElementById('create-users-btn').addEventListener('click', function() {
        const createUserModal = new bootstrap.Modal(document.getElementById('createUserModal'));
        createUserModal.show();
    });

    // Handle form submission for creating a new user
    document.getElementById('createUserForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const formData = new FormData(this);
        const data = Object.fromEntries(formData.entries());

        fetch(`/admin/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw new Error(err.error); });
                }
                return response.json();
            })
            .then(data => {
                alert(data.message);
                const createUserModal = bootstrap.Modal.getInstance(document.getElementById('createUserModal'));
                createUserModal.hide();
                // Optionally, reload the users table or update the UI as needed
            })
            .catch(error => {
                alert('Error creating user: ' + error.message);
                console.error('Error creating user:', error);
            });
    });
});
