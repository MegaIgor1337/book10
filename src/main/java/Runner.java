import lombok.Cleanup;
import org.example.entity.Order;
import org.example.entity.User;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Runner {


    public static void main(String[] args) {
        deleteUser(getUserById(4L).get());
    }


    // сохранение заказа
    private static void saveOrder(Order order, User user) {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        user.addUser(order);
        session.merge(user);
        session.getTransaction().commit();
    }

    // сохранение юзера
    private static void saveUser(User user) {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    // получение юзеров
    private static List<User> getUsers() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User",User.class);
        session.getTransaction().commit();
        return query.getResultList();
    }

    // получение заказов
    private static List<Order> getOrders() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(" FROM Order ", Order.class);
        session.getTransaction().commit();
        return (List<Order>) query.getResultList();
    }

    // удаление юзера
    private static void deleteUser(User user) {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }

    // удаление заказа
    private static void deleteOrder(Order order) {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(order);
        session.getTransaction().commit();

    }

    // получение одного юзера по id
    private static Optional<User> getUserById(Long id) {
        User user;
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        user = session.get(User.class, id);
        session.getTransaction().commit();
        return Optional.of(user);
    }

    // получение одного order по id

    private static Optional<Order> getOrderById(Long id) {
        Order order;
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        order = session.get(Order.class, id);
        session.getTransaction().commit();
        return Optional.of(order);
    }
}
