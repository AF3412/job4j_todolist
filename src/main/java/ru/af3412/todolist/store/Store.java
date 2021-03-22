package ru.af3412.todolist.store;

import ru.af3412.todolist.model.Task;
import ru.af3412.todolist.model.User;

import java.util.Collection;
import java.util.Optional;

public interface Store {
    Task findTaskById(int id);
    Collection<Task> findAllTask();

    Collection<Task> findAllTaskByUser(User user);

    Task saveTask(Task task);
    Task updateTask(Task task);
    boolean deleteTask(Task task);

    User findUserById(int id);

    Optional<User> findUserByName(String name);

    User saveUser(User user);
}
