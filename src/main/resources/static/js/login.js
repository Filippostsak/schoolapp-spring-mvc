document.addEventListener('DOMContentLoaded', () => {
    'use strict';

    // Hide the error alert initially
    const errorAlert = document.getElementById('errorAlert');
    if (errorAlert) {
        errorAlert.style.display = 'none';
    }

    // Select all forms that need validation
    const forms = document.querySelectorAll('.needs-validation');

    // Iterate over each form
    Array.from(forms).forEach(form => {
        // Add submit event listener to each form
        form.addEventListener('submit', event => {
            // If the form is not valid, prevent submission and propagation
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                event.preventDefault(); // Prevent default form submission

                // Show spinner and hide the submit button
                const submitButton = form.querySelector('button[type="submit"]');
                const spinner = submitButton.nextElementSibling;

                submitButton.style.display = 'none';
                spinner.style.display = 'inline-block';
                setTimeout(() => {
                    const username = form.username.value.trim();
                    const password = form.password.value.trim();

                    fetch('/login', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({
                            username: username,
                            password: password
                        }),
                    })
                        .then(response => {
                            if (response.ok) {
                                form.submit(); // Submit the form if validation passes
                            } else {
                                // Show the error alert if validation fails
                                if (errorAlert) {
                                    errorAlert.textContent = 'Invalid username or password';
                                    errorAlert.style.display = 'block';
                                }

                                // Hide spinner and show the submit button again
                                submitButton.style.display = 'block';
                                spinner.style.display = 'none';
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            // Hide spinner and show the submit button again
                            submitButton.style.display = 'block';
                            spinner.style.display = 'none';
                        });
                }, 1000);
            }
            form.classList.add('was-validated');
        }, false);
    });
});
