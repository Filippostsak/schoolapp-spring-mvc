document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('createClassroomForm');
    const submitButton = document.getElementById('submitButton');
    const spinner = document.getElementById('spinner');
    const customErrorAlert = document.getElementById('customErrorAlert');
    const customSuccessAlert = document.getElementById('customSuccessAlert');

    const validationRules = {
        name: {
            required: true,
            minlength: 2,
            maxlength: 100
        },
        description: {
            required: true,
            minlength: 5,
            maxlength: 500
        },
        classroomUrl: {
            required: true,
            minlength: 5,
            maxlength: 200
        }
    };

    const validationMessages = {
        name: {
            required: "Classroom name is required.",
            minlength: "Classroom name must be at least 2 characters.",
            maxlength: "Classroom name cannot exceed 100 characters."
        },
        description: {
            required: "Description is required.",
            minlength: "Description must be at least 5 characters.",
            maxlength: "Description cannot exceed 500 characters."
        },
        classroomUrl: {
            required: "Classroom URL is required.",
            minlength: "Classroom URL must be at least 5 characters.",
            maxlength: "Classroom URL cannot exceed 200 characters."
        }
    };

    async function checkClassroomNameExists(name) {
        try {
            const response = await fetch(`/rest/teachers/check-classroom-name?name=${encodeURIComponent(name)}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const result = await response.json();
            return result.exists;
        } catch (error) {
            console.error("Error checking classroom name:", error);
            return false;
        }
    }

    async function validateField(field) {
        const rules = validationRules[field.name];
        const messages = validationMessages[field.name];
        let valid = true;
        let message = '';

        if (rules.required && !field.value.trim()) {
            valid = false;
            message = messages.required;
        } else if (rules.minlength && field.value.trim().length < rules.minlength) {
            valid = false;
            message = messages.minlength;
        } else if (rules.maxlength && field.value.trim().length > rules.maxlength) {
            valid = false;
            message = messages.maxlength;
        }

        if (field.name === 'name' && valid) {
            const nameExists = await checkClassroomNameExists(field.value.trim());
            if (nameExists) {
                valid = false;
                message = "Classroom name already exists.";
            }
        }

        if (!valid) {
            field.classList.add('is-invalid');
            let errorElement = field.nextElementSibling;
            if (!errorElement || !errorElement.classList.contains('invalid-feedback')) {
                errorElement = document.createElement('div');
                errorElement.classList.add('invalid-feedback');
                field.parentNode.insertBefore(errorElement, field.nextSibling);
            }
            errorElement.textContent = message;
        } else {
            field.classList.remove('is-invalid');
            field.classList.add('is-valid');
            const errorElement = field.nextElementSibling;
            if (errorElement && errorElement.classList.contains('invalid-feedback')) {
                errorElement.textContent = '';
            }
        }

        return valid;
    }

    async function validateForm() {
        let isValid = true;
        const requiredFields = ['name', 'description', 'classroomUrl'];
        for (const field of form.elements) {
            if (field.name && validationRules[field.name]) {
                if (!(await validateField(field))) {
                    isValid = false;
                }
            }
        }

        const areRequiredFieldsValid = await Promise.all(requiredFields.map(async fieldName => {
            const field = form.querySelector(`[name="${fieldName}"]`);
            return await validateField(field);
        }));

        if (areRequiredFieldsValid.every(valid => valid)) {
            submitButton.style.display = 'block';
        } else {
            submitButton.style.display = 'none';
        }

        return isValid;
    }

    form.addEventListener('submit', async function (event) {
        event.preventDefault();
        event.stopPropagation();

        if (!(await validateForm())) {
            showCustomErrorMessage("Please fill out the form correctly.");
            return;
        }

        const classroomName = form.querySelector('[name="name"]').value.trim();
        const nameExists = await checkClassroomNameExists(classroomName);

        if (nameExists) {
            showCustomErrorMessage("The form is not submitted because this classroom name already exists.");
        } else {
            showCustomSuccessMessage("Classroom created successfully!");
            submitButton.style.display = 'none';
            spinner.style.display = 'inline-block';

            setTimeout(() => {
                form.submit();
            }, 2000); // 2 seconds timeout
        }
    });

    for (const field of form.elements) {
        if (field.name && validationRules[field.name]) {
            field.addEventListener('input', async function () {
                await validateField(field);
                await validateForm();
            });
        }
    }

    submitButton.style.display = 'none';
    validateForm();

    function showCustomErrorMessage(message) {
        customErrorAlert.textContent = message;
        customErrorAlert.style.display = 'block';
    }

    function showCustomSuccessMessage(message) {
        customSuccessAlert.textContent = message;
        customSuccessAlert.style.display = 'block';
    }
});
