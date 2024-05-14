function showTaskCreate() {
    let newTaskDiv = document.getElementById("task-create-form")

    if (newTaskDiv == null) {
        return false;
    }

    newTaskDiv.style.display = "block";

    return false;
}

document.getElementById("task-create-form").addEventListener("input", function (ev) {
    document.getElementById("task-create-form").querySelector("button").disabled = event.target.value.length <= 0;
});
