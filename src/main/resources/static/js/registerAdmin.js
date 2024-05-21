document.addEventListener('DOMContentLoaded', function() {
    // Check if there is an element with the class 'alert-success'
    if (document.querySelector('.alert-success')) {
        // Set a timeout to redirect to '/login' after 3 seconds
        setTimeout(function() {
            window.location.href = '/login';
        }, 3000);
    }
});
