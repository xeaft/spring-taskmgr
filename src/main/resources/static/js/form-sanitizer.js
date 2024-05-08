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

forms.forEach(function(form) {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        let formData = new FormData(form);
        let formValid = validateFormDataCharacters("username", formData) && validateFormDataCharacters("task", formData);

        if (!formValid) {
            let errorMessage = document.createElement("p");
            errorMessage.textContent = "Invalid characters, use only letters and numbers";
            errorMessage.style.color = "red";
            form.appendChild(errorMessage);
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