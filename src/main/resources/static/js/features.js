function showSection(event, sectionId) {
    event.preventDefault();
    document.querySelectorAll('.content-section').forEach(function (section) {
        section.classList.remove('active');
    });
    document.querySelectorAll('.list-group-item').forEach(function (item) {
        item.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
    event.target.classList.add('active');
}

function showSection(event, sectionId) {
    event.preventDefault();
    // Get all sections and the currently active section
    const sections = document.querySelectorAll('.content-section');
    const currentActive = document.querySelector('.content-section.active');

    // If there's currently an active section, hide it first
    if (currentActive) {
        // Start by reversing the animation
        currentActive.style.opacity = '0';
        currentActive.style.transform = 'rotateY(-90deg)';

        // Wait for the animation to finish before hiding and switching
        setTimeout(() => {
            currentActive.style.display = 'none';
            currentActive.classList.remove('active');
            activateNewSection(sectionId);
        }, 600); // This should match the duration of your CSS transition
    } else {
        // If no section is active, simply show the new section
        activateNewSection(sectionId);
    }

    // Manage active state for the clicked list item
    document.querySelectorAll('.list-group-item').forEach(item => {
        item.classList.remove('active');
    });
    event.target.classList.add('active');
}

function activateNewSection(sectionId) {
    const newSection = document.getElementById(sectionId);
    newSection.style.display = 'block';
    // Start transition to make the new section visible
    setTimeout(() => {
        newSection.classList.add('active');
        newSection.style.transform = 'rotateY(0)';
        newSection.style.opacity = '1';
    }, 10); // Small timeout to ensure the styles apply for transition
}

