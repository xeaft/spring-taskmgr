document.getElementById("add-item").addEventListener("click", function () {
    let newItem = document.createElement("div");
    newItem.classList.add("task-item");

    document.getElementById("todo-list").querySelector(".task-list-content").appendChild(newItem);
});

let taskLists = document.getElementsByClassName("task-list");
for (let i = 0; i < taskLists.length; i++) {
    let taskList = taskLists[i];
    taskList.style.left = 20 * (i + 1) + "%";
}
