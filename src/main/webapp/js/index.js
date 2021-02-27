"use strict";
const URL = "/job4j_todolist/tasks";
const addTaskBtn = document.getElementById("add_task");
const taskNameInput = document.getElementById("new_task");

document.addEventListener("DOMContentLoaded", pageReady);

function pageReady() {
    getAllTasks();
    addTaskBtn.onclick = function () {
        let newTaskDescription = {
            description: taskNameInput.value
        }
        fetch(URL, {
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

function getAllTasks() {
    fetch(URL, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(tasks => addAllTasksToPage(tasks));
}

function addAllTasksToPage(tasks) {
    for (let task of tasks) {
        createNewTask(task);
    }
}

function createNewTask(task) {
    let div = document.createElement('div');
    div.setAttribute('data-id', task.id);
    div.className = 'input-group mb-3';
    let input = document.createElement('input');
    input.className = 'form-control form-control';
    input.type = 'text';
    input.value = task.description;
    input.readOnly = true;
    input.setAttribute('aria-label', 'task');
    input.setAttribute('aria-describedby', 'basic-addon2');
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
    div.appendChild(input);
    div.appendChild(btnGroup);
    if (task.done) {
        document.getElementById('completed_tasks').appendChild(div);
    } else {
        document.getElementById('tasks').appendChild(div);
    }
}

function doneTask(task) {
    task.done = !task.done;
    fetch(URL, {
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
    fetch(URL, {
        method: 'DELETE',
        body: JSON.stringify(task)
    })
        .then(response => response.json())
        .then(resp => {
            if (resp) {
                document.querySelector(`[data-id="${task.id}"]`).remove();
                noty('Task ' + task.description +' deleted');
            }
        });
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