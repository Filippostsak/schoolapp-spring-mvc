function showSection(event, sectionId) {
    event.preventDefault();
    const currentActive = document.querySelector('.content-section.active');
    if (currentActive) {
        currentActive.classList.remove('active');
        setTimeout(() => {
            currentActive.style.display = 'none';
        }, 600); // Duration must match the CSS transition
    }
    const newSection = document.getElementById(sectionId);
    newSection.style.display = 'block';
    setTimeout(() => {
        newSection.classList.add('active');
    }, 10); // Timeout for the transform to take effect
    document.querySelectorAll('.list-group-item').forEach(item => {
        item.classList.remove('active');
    });
    event.target.classList.add('active');
}

function showSpinner(event, button) {
    event.preventDefault();
    const spinner = button.nextElementSibling;
    button.style.display = 'none';
    spinner.style.display = 'inline-block';

    // Redirect after a short delay
    setTimeout(() => {
        window.location.href = button.getAttribute('href');
    }, 1000); // Adjust delay as needed
}

// Initially show the default section
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('default-section').style.display = 'block';
});
