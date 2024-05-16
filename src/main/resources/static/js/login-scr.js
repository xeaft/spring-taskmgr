let afterLogout = window.location.href.includes("?logout");

if (afterLogout) {
    window.location.href = "/";
}

let registerForm = document.getElementById("regDiv");
let hasError = window.location.href.includes("?error");

if (hasError) {
    let errorText = document.getElementById("errorText");
    errorText.innerText = "Username or password is incorrect";
}

let usernamePrompt = document.getElementById("username");
let passwordPrompt = document.getElementById("password");
usernamePrompt.addEventListener("input", (event) => {
    passwordPrompt.disabled = !usernamePrompt.value.length;
    errorText.innerText = "";
});

