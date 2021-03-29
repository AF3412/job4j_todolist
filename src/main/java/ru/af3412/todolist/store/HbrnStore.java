package ru.af3412.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.af3412.todolist.model.Category;
import ru.af3412.todolist.model.Task;
import ru.af3412.todolist.model.User;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Optional;
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
    public Task findTaskById(int id) {
        return (Task) this.tx(session -> {
            Query query = session.createQuery("select t from Task t left join fetch t.categories where t.id = :id");
            query.setParameter("id", id);
            return query.getSingleResult();
        });
    }

    @Override
    public Collection<Task> findAllTask() {
        return this.tx(session -> session.createQuery("from Task").list());
    }

    @Override
    public Collection<Task> findAllTaskByUser(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select distinct t from Task t left join fetch t.categories where t.user.id = :user_id");
            query.setParameter("user_id", user.getId());
            var list = query.getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public Task saveTask(Task task) {
        int taskId = this.tx(session -> (int) session.save(task));
        return findTaskById(taskId);
    }

    @Override
    public Task updateTask(Task task) {
        this.vx(session -> session.update(task));
        return task;
    }

    @Override
    public boolean deleteTask(Task task) {
        this.vx(session -> session.delete(task));
        return true;
    }

    @Override
    public User findUserById(int id) {
        return this.tx(session -> session.get(User.class, id));
    }

    @Override
    public Optional<User> findUserByName(String name) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User where name = :name");
            query.setParameter("name", name);
            var list = query.getResultList();
            if (list.isEmpty()) {
                return Optional.empty();
            }
            session.getTransaction().commit();
            return Optional.of((User) list.get(0));
        }
    }

    @Override
    public User saveUser(User user) {
        int taskId = this.tx(session -> (int) session.save(user));
        return findUserById(taskId);
    }

    @Override
    public Collection<Category> findAllCategories() {
        return this.tx(session -> session.createQuery("select c from Category c").list());
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
