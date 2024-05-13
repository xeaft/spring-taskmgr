let registerForm = document.getElementById("regDiv");

let hasError = window.location.href.includes("?error=");
let errorMessage = hasError ? window.location.href.split("?error=")[1] : "";

let errorMessages = {
    "passwordTooShort": "Password must be at least 8 characters long",
    "nameTaken": "Username is already taken",
    "nameInvalid": "Username must only contain letters and numbers",
    "nameTooShort": "Username must be at least 3 characters long",
    "passwordsDontMatch": "Passwords don't match"
}

function getErrorMessage(message) {
    if (errorMessages[message] === undefined) {
        return "Unknown error";
    }

    return errorMessages[message];
}

function showError(message) {
    let errorText = document.createElement("p");
    errorText.id = "errorText";
    errorText.innerText = getErrorMessage(message);
    errorText.style.color = "red";
    errorText.style.fontSize = "1.5rem";
    registerForm.appendChild(errorText);
}

if (hasError) {
    showError(errorMessage);
}

