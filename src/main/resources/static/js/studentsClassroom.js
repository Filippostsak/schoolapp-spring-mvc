// Function to calculate the remaining time until the meeting starts
function calculateRemainingTime(startDate, startTime) {
    const startDateTime = new Date(`${startDate}T${startTime}`);
    const now = new Date();
    const timeDifference = startDateTime - now;

    if (timeDifference <= 0) {
        return null; // Indicate that the time has reached zero
    }

    const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeDifference / (1000 * 60 * 60)) % 24);
    const minutes = Math.floor((timeDifference / (1000 * 60)) % 60);
    const seconds = Math.floor((timeDifference / 1000) % 60);

    return `${days} days, ${hours} hours, ${minutes} minutes, ${seconds} seconds`;
}

document.addEventListener("DOMContentLoaded", function () {
    const meetingDatesElements = document.querySelectorAll(".meeting-dates");

    meetingDatesElements.forEach((element) => {
        const startDate = element.getAttribute("data-start-date");
        const startTime = element.getAttribute("data-start-time");
        const classroomUrl = element.getAttribute("data-classroom-url");

        if (startDate && startTime) {
            const countdownElement = element.querySelector(".countdown");
            const urlElement = element.querySelector(".classroom-url");

            function updateCountdown() {
                const remainingTime = calculateRemainingTime(startDate, startTime);

                if (remainingTime) {
                    countdownElement.innerHTML = remainingTime;
                    urlElement.style.display = 'none';
                    countdownElement.style.display = 'block';
                } else {
                    countdownElement.style.display = 'none';
                    urlElement.style.display = 'block';
                }
            }

            updateCountdown();
            setInterval(updateCountdown, 1000);
        }
    });
});
