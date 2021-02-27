package ru.af3412.todolist.store;

import ru.af3412.todolist.model.Task;

import java.util.Collection;

public interface Store {
    Task findById(int id);
    Collection<Task> findAllTask();
    Task save(Task task);
    Task update(Task task);
    boolean delete(Task task);

}
