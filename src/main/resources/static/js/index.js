const sectionTitle = document.getElementById('section-title');
let isVisible = true;

setInterval(() => {
    sectionTitle.style.opacity = isVisible ? 0 : 1;
    isVisible = !isVisible;
}, 3000);