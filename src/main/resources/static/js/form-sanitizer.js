let forms = document.querySelectorAll('form');

function validateFormDataCharacters(instance, formData) {
    let invalidChars = formData.getAll(instance).filter(function(value) {
        return !value.match(/^[a-zA-Z0-9]+$/);
    });

    if (invalidChars.length > 0) {
        return false;
    }

    return true;
}

function setErrorMessage(form, message) {
    let errorMessage = null;
    errorMessage = document.getElementById("errorText");
    errorMessage.innerText = message;
    form.reset();
}

forms.forEach(function(form) {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(form);
        let formValid = validateFormDataCharacters("username", formData) && validateFormDataCharacters("task", formData);

        if (!formValid) {
            setErrorMessage(form, "Invalid characters, use only letters and numbers");
            form.reset();
            return;
        }

        fetch(form.action, {
            method: form.method,
            body: formData
        }).then(function(response) {
            window.location.href = response.url;
        });
    })
});