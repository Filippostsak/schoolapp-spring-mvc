document.addEventListener('DOMContentLoaded', function() {
    // Check if an element with the class 'alert-success' exists in the DOM
    if (document.querySelector('.alert-success')) {
        // If such an element is found, set a timeout to redirect to '/login' after 3 seconds
        setTimeout(function() {
            window.location.href = '/login';
        }, 3000);
    }
});
