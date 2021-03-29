"use strict";
const TASKS_URL = "/job4j_todolist/tasks";
const CATEGORIES_URL = "/job4j_todolist/categories";
const addTaskBtn = document.getElementById("add_task");
const taskNameInput = document.getElementById("new_task");

document.addEventListener("DOMContentLoaded", pageReady);

function pageReady() {
    getAllTasks();

    addTaskBtn.onclick = function () {
        let newTaskDescription = {
            description: taskNameInput.value,
            categories: getAllSelected()
        }
        console.log(JSON.stringify(newTaskDescription));
        fetch(TASKS_URL, {
            method: 'POST',
            body: JSON.stringify(newTaskDescription)
        })
            .then(response => response.json())
            .then(task => {
                createNewTask(task);
                taskNameInput.value = '';
            });
    }
}

function getAllSelected() {
    const result = [];
    class Category {
        constructor(id, name) {
            this.id = id;
            this.name = name;
        }
    }
    const selectedOptions = document.getElementById("select_category").selectedOptions;
    for (let cat of selectedOptions) {
        result.push(new Category(cat.dataset.id, cat.value));
    }
    return result;
}


function getAllTasks() {
    fetch(TASKS_URL, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(tasks => addAllTasksToPage(tasks));
}

function getAllCategories(cat) {
    return fetch(CATEGORIES_URL, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(categories => cat = categories);
}

function addAllTasksToPage(tasks) {
    for (let task of tasks) {
        createNewTask(task);
    }
}

function createNewTask(task) {
    let div = createTaskDiv();
    let input = createInput();
    let btnGroup = createBtnGroup();
    div.appendChild(input);
    div.appendChild(btnGroup);
    if (task.done) {
        document.getElementById('completed_tasks').appendChild(div);
    } else {
        document.getElementById('tasks').appendChild(div);
    }

    function createTaskDiv() {
        let div = document.createElement('div');
        div.setAttribute('data-id', task.id);
        div.className = 'input-group mb-3';
        return div;
    }

    function createInput() {
        let input = document.createElement('input');
        input.className = 'form-control form-control';
        input.type = 'text';
        input.value = task.description;
        input.readOnly = true;
        input.setAttribute('aria-label', 'task');
        input.setAttribute('aria-describedby', 'basic-addon2');
        return input;
    }

    function createBtnGroup() {
        let btnGroup = document.createElement('div');
        btnGroup.className = 'input-group-append';

        let btnDelete = document.createElement('button');
        btnDelete.className = 'btn btn-outline-secondary';
        btnDelete.type = 'button';
        btnDelete.innerHTML = '<i class="bi bi-x-circle"></i>';
        btnDelete.addEventListener("click", () => deleteTask(task));

        let btnDone = document.createElement('button');
        btnDone.className = 'btn btn-outline-secondary';
        btnDone.type = 'button';
        btnDone.innerHTML = '<i class="bi bi-check"></i>';
        btnDone.addEventListener("click", () => doneTask(task));

        btnGroup.appendChild(btnDelete);
        btnGroup.appendChild(btnDone);
        return btnGroup;
    }

}

function doneTask(task) {
    task.done = !task.done;
    fetch(TASKS_URL, {
        method: 'PUT',
        body: JSON.stringify(task)
    })
        .then(response => response.json())
        .then(updatedTask => {
            document.querySelector(`[data-id="${task.id}"]`).remove();
            createNewTask(updatedTask);
        });
}

function deleteTask(task) {
    fetch(TASKS_URL, {
        method: 'DELETE',
        body: JSON.stringify(task)
    })
        .then(response => response.json())
        .then(resp => {
            if (resp) {
                document.querySelector(`[data-id="${task.id}"]`).remove();
                noty('Task ' + task.description + ' deleted');
            }
        });
}

function createCategoriesMenu(categories) {
    for (let category of categories) {
        const liTag = document.createElement('li');
        liTag.className = 'nav-item';
        const aTag = document.createElement('a');
        aTag.className = 'nav-link';
        aTag.text = category.name;
        aTag.setAttribute('data-id', category.id);
        aTag.href = '#';
        liTag.appendChild(aTag);
        document.getElementById('categories').appendChild(liTag);
    }
}

function noty(text) {
    new Noty({
        type: 'info',
        layout: 'topRight',
        theme: 'metroui',
        timeout: 100,
        text: text
    }).show();
}