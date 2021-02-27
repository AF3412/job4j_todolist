package ru.af3412.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.af3412.todolist.model.Task;

import java.util.Collection;
import java.util.List;

public class HbrnStore implements Store, AutoCloseable {

    private HbrnStore() {
    }

    private static final class Lazy {
        private static final Store INST = new HbrnStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public Task findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Task task = session.get(Task.class, id);
            session.getTransaction().commit();
            return task;
        }
    }

    @Override
    public Collection<Task> findAllTask() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<Task> result = session.createQuery("from ru.af3412.todolist.model.Task").list();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public Task save(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            int taskId = (int) session.save(task);
            session.getTransaction().commit();
            return findById(taskId);
        }
    }

    @Override
    public Task update(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            return task;
        }
    }

    @Override
    public boolean delete(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.delete(task);
            session.getTransaction().commit();
            return true;
        }
    }
}
