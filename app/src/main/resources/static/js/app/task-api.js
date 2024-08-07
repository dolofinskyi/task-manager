const container = document.getElementById('task-container');

function loadTasks() {
    fetch('/api/task/all')
        .then(response => response.json())
        .then(tasks => {
            container.innerHTML = '';
            tasks.forEach(task => {
                const taskHtml = `
                    <div class="task">
                        <div>
                            <input type="hidden" name="task-id" value="${task.id}">
                            <input type="checkbox" ${task.isDone ? 'checked' : ''}>
                            <p class="title">${task.title}</p>
                            <p class="description">${task.description}</p>
                            <button onclick="updateTask(${task.id})">Update</button>
                            <button onclick="deleteTask(${task.id})">Delete</button>
                        </div>
                    </div>
                `;
                container.insertAdjacentHTML('beforeend', taskHtml);
            });
        });
}

document.getElementById('add-task').addEventListener('click', () => {
    const task = {
        title: prompt('Enter task title:'),
        description: prompt('Enter task description:'),
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
        .then(task => {
            loadTasks();
        });
}

function updateTask(id) {
    const task = {
        id: id,
        title: prompt('Enter new task title:'),
        description: prompt('Enter new task description:'),
        isDone: confirm('Is the task done?')
    };

    fetch('/api/task/update', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(task)
    })
    .then(() => {
        loadTasks();
    });
}

function deleteTask(id) {
    fetch('/api/task/delete?id=' + id, { method: 'DELETE' })
    .then(() => {
        loadTasks();
    });
};

document.addEventListener('DOMContentLoaded', () => {
    loadTasks();
});