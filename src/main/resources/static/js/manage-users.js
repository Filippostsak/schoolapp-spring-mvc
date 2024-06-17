document.addEventListener('DOMContentLoaded', function() {
    let currentPage = 0;
    const size = 10;
    const searchInput = document.getElementById('search-input');

    document.getElementById('load-users-btn').addEventListener('click', function() {
        loadUsers(currentPage);
    });

    document.getElementById('previous-page').addEventListener('click', function(event) {
        event.preventDefault();
        if (currentPage > 0) {
            currentPage--;
            loadUsers(currentPage);
        }
    });

    document.getElementById('next-page').addEventListener('click', function(event) {
        event.preventDefault();
        currentPage++;
        loadUsers(currentPage);
    });

    searchInput.addEventListener('input', function() {
        currentPage = 0;
        loadUsers(currentPage);
    });

    function loadUsers(page) {
        const searchQuery = searchInput.value;

        const params = new URLSearchParams({
            page: page,
            size: size,
            keyword: searchQuery // updated the parameter name to 'keyword'
        });

        fetch(`/admin/search?${params.toString()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const tbody = document.getElementById('users-table').querySelector('tbody');
                tbody.innerHTML = '';
                data.content.forEach(user => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>${user.status}</td>
                        <td>${user.firstname}</td>
                        <td>${user.lastname}</td>
                        <td>
                            <button class="btn btn-primary btn-sm update-btn" data-id="${user.id}">Update</button>
                            <button class="btn btn-danger btn-sm delete-btn" data-id="${user.id}">Delete</button>
                        </td>
                    `;
                    tbody.appendChild(row);
                });

                // Add event listeners for the update and delete buttons
                document.querySelectorAll('.update-btn').forEach(button => {
                    button.addEventListener('click', function() {
                        const userId = this.getAttribute('data-id');
                        handleUpdate(userId);
                    });
                });

                document.querySelectorAll('.delete-btn').forEach(button => {
                    button.addEventListener('click', function() {
                        const userId = this.getAttribute('data-id');
                        handleDelete(userId);
                    });
                });
            })
            .catch(error => console.error('Error loading users:', error));
    }

    // Function to open the modal and populate it with user data
    function openUpdateUserModal(userData) {
        document.getElementById('update-id').value = userData.id;
        document.getElementById('update-username').value = userData.username;
        document.getElementById('update-email').value = userData.email;
        document.getElementById('update-role').value = userData.role;
        document.getElementById('update-status').value = userData.status;
        document.getElementById('update-firstname').value = userData.firstname;
        document.getElementById('update-lastname').value = userData.lastname;
        document.getElementById('update-active').checked = userData.active;

        const updateUserModal = new bootstrap.Modal(document.getElementById('updateUserModal'));
        updateUserModal.show();
    }

    function handleUpdate(userId) {
        fetch(`/admin/get/id/${userId}`)
            .then(response => response.json())
            .then(userData => {
                openUpdateUserModal(userData);
            })
            .catch(error => console.error('Error fetching user data:', error));
    }

    // Handle form submission
    document.getElementById('updateUserForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const userId = document.getElementById('update-id').value;
        const formData = new FormData(this);
        const data = Object.fromEntries(formData.entries());

        fetch(`/admin/update/${userId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                alert("User updated successfully");
                const updateUserModal = bootstrap.Modal.getInstance(document.getElementById('updateUserModal'));
                updateUserModal.hide();
                loadUsers(currentPage);
            })
            .catch(error => console.error('Error updating user:', error));
    });

    function handleDelete(userId) {
        if (confirm("Are you sure you want to delete this user?")) {
            fetch(`/admin/delete/${userId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    alert("User deleted successfully");
                    loadUsers(currentPage);
                })
                .catch(error => console.error('Error deleting user:', error));
        }
    }
});
