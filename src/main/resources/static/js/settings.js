let hasError = window.location.href.includes("?error=");
let errorMessage = hasError ? window.location.href.split("?error=")[1] : "";

let errorMessages = {
    "nofile": "No file was selected",
    "uploaderror": "An error occurred while uploading the file"
}

if (hasError) {
    let errorText = document.getElementById("errorText");
    errorText.innerText = errorMessage;
}