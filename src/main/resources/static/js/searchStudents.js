document.getElementById('searchForm').addEventListener('submit', function(event) {
    event.preventDefault();
    var spinner = document.getElementById('spinner');
    spinner.style.display = 'inline-block';
    setTimeout(() => {
        spinner.style.display = 'none';
        this.submit();
    }, 1000);
});

document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('searchResults')) {
        document.getElementById('searchResults').classList.add('active');
    }
});