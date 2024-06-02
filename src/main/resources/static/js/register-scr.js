let registerForm = document.getElementById("regDiv");

let hasError = window.location.href.includes("?error=");
let errorMessage = hasError ? window.location.href.split("?error=")[1] : "";

let errorMessages = {
    "passwordTooShort": "Password must be at least 8 characters long",
    "nameTaken": "Username is already taken",
    "nameInvalid": "Username must only contain letters and numbers",
    "nameTooShort": "Username must be between 3 and 16 characters long",
    "passwordsDontMatch": "Passwords don't match",
    "noUsername": "..we arent mind readers... (yet)",
    "passwordTooLong": "maybe a bit too much security, don't you think?",
    "passwordNumberReq": "Password must contain at least one number",
}

function getErrorMessage(message) {
    if (errorMessages[message] === undefined) {
        return "Unknown error";
    }

    return errorMessages[message];
}

function setErrorMessage(message) {
    let errorText = document.getElementById("errorText");
    errorText.innerText = getErrorMessage(message);
}

function resetErrorMessage() {
    let errorText = document.getElementById("errorText");
    errorText.innerText = "";
}

if (hasError) {
    setErrorMessage(errorMessage);
}

let passPrompt = registerForm.querySelector("input[name='password']");
let repeatPassPrompt = registerForm.querySelector("input[name='repeatpassword']");
let usernamePrompt = registerForm.querySelector("input[name='username']");
let usernameRegex = /^[a-zA-Z0-9]+$/;

registerForm.addEventListener("input", (event) => {
    let passMatch = (passPrompt.value === repeatPassPrompt.value);
    let passLength = passPrompt.value.length;
    let passValidLength = passLength >= 8 && passLength <= 64;
    let passContainsNumbers = /[0-9]/.test(passPrompt.value);

    let pass1Valid = passValidLength && passContainsNumbers;
    let passValid = pass1Valid && passMatch;

    let usernameMeetsRegex = usernameRegex.test(usernamePrompt.value);
    let usernameMeetsLength = usernamePrompt.value.length >= 3 && usernamePrompt.value.length <= 16;
    let usernameValid = usernameMeetsRegex && usernameMeetsLength;


    passPrompt.disabled = !usernameValid;


    let errorType = "";

    if (!passMatch && pass1Valid) {
        errorType = "passwordsDontMatch";
    }

    if (!passContainsNumbers) {
       errorType = "passwordNumberReq";
    }

    if (passLength < 8) {
        errorType = "passwordTooShort";
    }

    if (passLength > 64) {
        errorType = "passwordTooLong";
    }

    if (!usernameValid) {
        errorType = "nameInvalid";
    }

    if (!usernameMeetsLength) {
        errorType = "nameTooShort";
    }

    if (usernamePrompt.value.length == 0) {
        errorType = "noUsername";
    }

    repeatPassPrompt.disabled = !pass1Valid;

    let validLogin = passValid && usernameValid && errorType == "";
    registerForm.querySelector("input[type='submit']").disabled = !validLogin;

    if (errorType != "") {
        setErrorMessage(errorType);
    } else {
        resetErrorMessage();
    }
});