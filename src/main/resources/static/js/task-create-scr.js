let taskNameElements = document.getElementsByClassName("task-name");
let errorText = document.getElementById("error-text");
let taskNames = Array.from(taskNameElements).map(element => {
    return element.innerText;
});

function showTaskCreate() {
    let newTaskDiv = document.getElementById("task-create-form")

    if (newTaskDiv == null) {
        return false;
    }

    newTaskDiv.style.display = "block";

    return false;
}

document.getElementById("task-create-form").addEventListener("input", function (ev) {
    let createButton = document.getElementById("task-create-form").querySelector("button");
    let correctLength = (event.target.value.length <= 0 || event.target.value.length > 16);
    let duplicatedName = false;
    for (let i of taskNames) {
        if (i == event.target.value) {
            duplicatedName = true;
            break;
        }
    }
    errorText.style.display = duplicatedName ? "block" : "none";
    createButton.disabled = correctLength || duplicatedName;
});

document.addEventListener("keydown", function (ev) {
    if (ev.key == "Escape") {
        let newTaskDiv = document.getElementById("task-create-form");
        if (newTaskDiv != null) {
            newTaskDiv.style.display = "none";
        }
    }
});
