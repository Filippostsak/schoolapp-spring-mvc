document.addEventListener('DOMContentLoaded', function() {
    // Wait until the DOM is fully loaded

    // Check if there is an element with the class 'alert-success' on the page
    if (document.querySelector('.alert-success')) {
        // If such an element is found, set a timeout to redirect to the '/login' page after 3 seconds
        setTimeout(function() {
            window.location.href = '/login';
        }, 3000);
    }
});
