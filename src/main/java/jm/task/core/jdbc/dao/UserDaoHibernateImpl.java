package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.getCurrentSession();
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                lastName VARCHAR(255),
                age TINYINT);
                """;
        session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.getCurrentSession();
        String sql = "DROP TABLE IF EXISTS users;";
        session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("from User").getResultList();
        session.close();
        return userList;
    }


    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.close();
    }
}
