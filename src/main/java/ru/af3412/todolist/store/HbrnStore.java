package ru.af3412.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.af3412.todolist.model.Task;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

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
        return this.tx(session -> session.get(Task.class, id));
    }

    @Override
    public Collection<Task> findAllTask() {
        return this.tx(session -> session.createQuery("from ru.af3412.todolist.model.Task").list());
    }

    @Override
    public Task save(Task task) {
        int taskId = this.tx(session -> (int) session.save(task));
        return findById(taskId);
    }

    @Override
    public Task update(Task task) {
        this.vx(session -> session.update(task));
        return task;
    }

    @Override
    public boolean delete(Task task) {
        this.vx(session -> session.delete(task));
        return true;
    }

    private <T> T tx(final Function<Session, T> command) {
        try (Session session = sf.openSession()) {
            try {
                Transaction tx = session.beginTransaction();
                T rsl = command.apply(session);
                tx.commit();
                return rsl;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    private void vx(final Consumer<Session> command) {
        try (Session session = sf.openSession()) {
            try {
                Transaction tx = session.beginTransaction();
                command.accept(session);
                tx.commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }
}
