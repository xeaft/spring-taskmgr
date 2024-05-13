let forms = document.querySelectorAll('form');

// TODO: do this serverside as well

function validateFormDataCharacters(instance, formData) {
    let invalidChars = formData.getAll(instance).filter(function(value) {
        return !value.match(/^[a-zA-Z0-9]+$/);
    });

    if (invalidChars.length > 0) {
        return false;
    }

    return true;
}

function createErrorMessage(form, message) {
    let errorMessage = null;

    errorMessage = document.getElementById("errorText") || document.createElement("p");
    errorMessage.id = "errorText";
    errorMessage.innerText = message;
    errorMessage.style.color = "red";
    errorText.style.fontSize = "1.5rem";
    form.appendChild(errorMessage);
    form.reset();
}

forms.forEach(function(form) {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(form);
        let formValid = validateFormDataCharacters("username", formData) && validateFormDataCharacters("task", formData);

        if (!formValid) {
            createErrorMessage(form, "Invalid characters, use only letters and numbers");
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