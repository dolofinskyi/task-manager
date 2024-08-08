const container = document.getElementById('task-container');

function autoResizeTextarea(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}

function loadTasks() {
    fetch('/api/task/all')
        .then(response => response.json())
        .then(tasks => {
            container.innerHTML = '';
            tasks.forEach(task => {
                addElementTask(task);
            });
        });
}

document.addEventListener('DOMContentLoaded', () => {
    loadTasks();
});

document.getElementById('add-task').addEventListener('click', () => {
    const task = {
        title: 'Title',
        description: 'Description',
        isDone: false
    };
    addTask(task);
});

function addTask(task) {
    fetch('/api/task/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(task)
        })
        .then(response => response.json())
        .then(addedTask => {
            addElementTask(addedTask);
        });
}

function updateTask(task) {
    fetch('/api/task/update', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(task)
    })
    .then(response => response.json())
    .then(updatedTask => {
        updateElementTask(updatedTask);
    });
}

function deleteTask(id) {
    fetch('/api/task/delete?id=' + id, { method: 'DELETE' })
    .then(() => {
        deleteElementTask(id);
    });
};

function taskElementInputsDblclick(event) {
    const element = event.target;

    if (!element.readOnly) {
        return;
    }

    element.readOnly = false;
    element.focus();

    element.addEventListener('blur', () => {
        element.readOnly = true;

        const taskElement = element.parentElement;
        const idInput = taskElement.querySelector('.task-id');
        const titleInput = taskElement.querySelector('.task-title');
        const descriptionInput = taskElement.querySelector('.task-description');
        const checkbox = taskElement.querySelector('.task-checkbox');

        if (!idInput || !titleInput || !checkbox) {
            return;
        }

        if (!idInput.value || !titleInput.value || checkbox.checked == null) {
            return;
        }

        const task = {
            id: idInput.value,
            title: titleInput.value,
            description: descriptionInput ? descriptionInput.value : null,
            isDone: checkbox.checked
        };

        updateTask(task);
    });

    element.addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            element.blur();
        }
    });
}

function taskElementCheckboxOnChange(event) {
    const checkbox = event.target;
    const taskElement = checkbox.parentElement.parentElement;
    const idInput = taskElement.querySelector('.task-id');
    const titleInput = taskElement.querySelector('.task-title');
    const descriptionInput = taskElement.querySelector('.task-description');

    if (!idInput || !titleInput || !checkbox) {
        return;
    }

    if (!idInput.value || !titleInput.value || checkbox.checked == null) {
        return;
    }

    const task = {
        id: idInput.value,
        title: titleInput.value,
        description: descriptionInput ? descriptionInput.value : null,
        isDone: checkbox.checked
    };

    updateTask(task);
}

function findTaskById(taskId) {
    const tasks = document.querySelectorAll('.task');

    for (const task of tasks) {
        const hiddenInput = task.querySelector('.task-id');
        if (hiddenInput && hiddenInput.value === taskId.toString()) {
            return task;
        }
    }

    return null;
}

function addElementTask(task) {
    const taskHtml = `
        <div class="task">
            <label class="task-label">
                <input type="checkbox" class="task-checkbox" ${task.isDone ? 'checked' : ''}>
                Done
            </label>
            <input type="hidden" class="task-id" name="task-id" value="${task.id}">
            <textarea readOnly="true" class="task-title" name="task-title">${task.title}</textarea>
            <textarea readOnly="true" class="task-description" name="task-title">${task.description}</textarea>
            <button onclick="deleteTask(${task.id})">Delete</button>
        </div>
    `;

    const temp = document.createElement('div');
    temp.innerHTML = taskHtml;

    const taskElement = temp.firstChild.nextElementSibling;

    taskElement.querySelectorAll('.task-title, .task-description').forEach(element => {
        element.addEventListener('dblclick', taskElementInputsDblclick);
        element.addEventListener('input', () => autoResizeTextarea(element));
        setTimeout(() => autoResizeTextarea(element), 0);
    });

    const checkbox = taskElement.querySelector('.task-checkbox');
    checkbox.addEventListener('change', taskElementCheckboxOnChange);

    if (checkbox.checked) {
        taskElement.classList.add('completed');
    } else {
        taskElement.classList.remove('completed');
    }

    container.appendChild(taskElement);
}

function updateElementTask(task) {
    const taskElement = findTaskById(task.id);

    const titleInput = taskElement.querySelector('.task-title');
    const descriptionInput = taskElement.querySelector('.task-description');
    const checkbox = taskElement.querySelector('.task-checkbox');

    titleInput.value = task.title;
    descriptionInput.value = task.description;
    checkbox.checked = task.isDone;

    if (checkbox.checked) {
        taskElement.classList.add('completed');
    } else {
        taskElement.classList.remove('completed');
    }
}

function deleteElementTask(id) {
    const taskElement = findTaskById(id);
    taskElement.remove();
}