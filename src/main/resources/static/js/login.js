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
                event.preventDefault(); // Prevent default form submission

                // Show spinner and hide the submit button
                const submitButton = form.querySelector('#submitButton');
                const spinner = submitButton.nextElementSibling;

                submitButton.style.display = 'none';
                spinner.style.display = 'inline-block';

                // Simulate form submission
                setTimeout(() => {
                    form.submit(); // Submit the form after a delay
                }, 500); // Adjust the delay if necessary
            }
            form.classList.add('was-validated');
        }, false);
    });
});
