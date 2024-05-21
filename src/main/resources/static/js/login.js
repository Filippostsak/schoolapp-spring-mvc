document.addEventListener('DOMContentLoaded', () => {
    'use strict';

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
                // Show success message
                const successMessage = document.createElement('div');
                successMessage.className = 'alert alert-success mt-2';
                successMessage.textContent = 'Successfully logged in. Redirecting...';
                form.parentNode.insertBefore(successMessage, form);

                // Hide form and redirect after 2 seconds
                form.style.display = 'none';
                setTimeout(() => {
                    window.location.href = '/index';
                }, 2000);
            }

            // Add the was-validated class to the form to show validation feedback
            form.classList.add('was-validated');
        }, false);
    });
});
