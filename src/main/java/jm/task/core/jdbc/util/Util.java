package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class Util {
    private static final String URL_KEY = "db.url";
    private static final String LOGIN_KEY = "db.login";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static SessionFactory sessionFactory;

    private Util() {
    }

    public static Connection open() {
        Connection connection = null;
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
            connection = DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(LOGIN_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, PropertiesUtil.get(DRIVER_KEY));
                settings.put(Environment.URL, PropertiesUtil.get(URL_KEY));
                settings.put(Environment.USER, PropertiesUtil.get(LOGIN_KEY));
                settings.put(Environment.PASS, PropertiesUtil.get(PASSWORD_KEY));
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
}
