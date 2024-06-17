function getElementPosition(el) {
    const rect = el.getBoundingClientRect();
    const scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    return { top: rect.top + scrollTop, left: rect.left + scrollLeft,
             bottom: rect.bottom + scrollTop, right: rect.right + scrollLeft };
}

function doElsCollide(el1, el2) {
    const pos1 = getElementPosition(el1);
    const pos2 = getElementPosition(el2);

    return !(pos1.bottom < pos2.top ||
             pos1.top > pos2.bottom ||
             pos1.right < pos2.left ||
             pos1.left > pos2.right);
};

let cols = ["To Do", "In Progress", "Completed"];

function getColIndex(col) {
    return cols.indexOf(col);
}

let updateForm = document.getElementById("autoform");
document.getElementById('add-item').addEventListener('submit', function(event) {
    event.preventDefault();

    let form = event.target;
    let formData = new FormData(form);

    fetch(form.action, {
        method: form.method,
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        let id = data;
        addVisualItem(id, 0, "");
    })
    .catch(error => console.error('Error:', error));
});

updateForm.addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);

    fetch('/tasks/moveItem', {
        method: 'POST',
        body: formData
    });
});

let colContainers = {
    0: "todo-list",
    1: "in-progress-list",
    2: "completed-list"
}

function addVisualItem(id, column, textContent) {
    if (column < 0 || column > 2 || typeof column != "number") {
        column = 0;
    }

    textContent = textContent.slice(1, -1);

    let columnContainer = document.getElementById(colContainers[column]);
    let taskListContainer = columnContainer.querySelector(".task-list-content");

    let newItem = document.createElement("div");
    newItem.classList.add("task-item");

    let textField = document.createElement("input");
    textField.classList.add("form-control");
    textField.classList.add("task-item-text");
    textField.type = "text";
    textField.placeholder = "insert something";
    textField.value = textContent;
    newItem.appendChild(textField);

    taskListContainer.appendChild(newItem);
    newItem.id = id;

    let offsetX, offsetY;
    let dropElement = null;

    newItem.querySelector('input').addEventListener('mousedown', function(e) {
        e.stopPropagation();
    });

    newItem.addEventListener('mousedown', function (e) {
        let rect = newItem.getBoundingClientRect();
        offsetX = e.clientX - rect.left;
        offsetY = e.clientY - rect.top;

        let originalParent = newItem.parentNode;

        document.body.appendChild(newItem);
        newItem.style.position = "absolute";
        newItem.style.width = "auto";

        let isDragging = true;

        newItem.style.cursor = 'grabbing';

        const onMouseMove = function (e) {
            if (isDragging) {
                newItem.style.left = `${e.clientX - offsetX}px`;
                newItem.style.top = `${e.clientY - offsetY}px`;

                document.querySelectorAll(".task-list-content").forEach(taskList => {
                    let collision = doElsCollide(newItem, taskList);
                    if (collision) {
                        taskList.style.border = "2px dashed blue";
                        dropElement = taskList;
                    } else {
                        taskList.style.border = "none";
                    }
                })

            }
        };

        const onMouseUp = function () {
            isDragging = false;
            newItem.style.cursor = 'grab';
            newItem.style.position = "";
            newItem.style.width = "100%";

            document.removeEventListener('mousemove', onMouseMove);
            document.removeEventListener('mouseup', onMouseUp);

            if (dropElement) {
                dropElement.appendChild(newItem);
            } else {
                originalParent.appendChild(newItem);
            }

            document.querySelectorAll(".task-list-content").forEach(thing => {
                thing.style.border = "none";
            });

            updateForm.querySelector("input[name='taskItemId']").value = id;
            updateForm.querySelector("input[name='taskItemTargetColumn']").value = getColIndex(dropElement.parentElement.children[0].innerText);
            updateForm.querySelector("input[name='taskItemTextContent']").value = textField.value;
            updateForm.querySelector("input[type='submit']").click();
        };

        document.addEventListener('mousemove', onMouseMove);
        document.addEventListener('mouseup', onMouseUp);
    });

    textField.addEventListener('blur', function(event) {
        updateForm.querySelector("input[name='taskItemId']").value = id;
        updateForm.querySelector("input[name='taskItemTargetColumn']").value = getColIndex(textField.parentElement.parentElement.parentElement.querySelector("p").innerText);
        updateForm.querySelector("input[name='taskItemTextContent']").value = textField.value;
        updateForm.querySelector("input[type='submit']").click();
    });
}

let taskLists = document.getElementsByClassName("task-list");
for (let i = 0; i < taskLists.length; i++) {
    let taskList = taskLists[i];
    taskList.style.left = 7 + i * (18 + 7.66) + "vw";
}
