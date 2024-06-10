function getElementPosition(el) {
    const rect = el.getBoundingClientRect();
    const scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    return { top: rect.top + scrollTop, left: rect.left + scrollLeft,
             bottom: rect.bottom + scrollTop, right: rect.right + scrollLeft };
}

doElsCollide = function(el1, el2) {
    const pos1 = getElementPosition(el1);
    const pos2 = getElementPosition(el2);

    return !(pos1.bottom < pos2.top ||
             pos1.top > pos2.bottom ||
             pos1.right < pos2.left ||
             pos1.left > pos2.right);
};

// TODO: save. this. into. the. database.
document.getElementById("add-item").addEventListener("click", function () {
    let newItem = document.createElement("div");
    newItem.classList.add("task-item");

    let textField = document.createElement("input");
    textField.classList.add("form-control");
    textField.classList.add("task-item-text");
    textField.type = "text";
    textField.placeholder = "insert something";
    newItem.appendChild(textField);

    document.getElementById("todo-list").querySelector(".task-list-content").appendChild(newItem);


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
            })
        };

        document.addEventListener('mousemove', onMouseMove);
        document.addEventListener('mouseup', onMouseUp);
    });
});

let taskLists = document.getElementsByClassName("task-list");
for (let i = 0; i < taskLists.length; i++) {
    let taskList = taskLists[i];
    taskList.style.left = 7 + i * (18 + 7.66) + "vw";
}
