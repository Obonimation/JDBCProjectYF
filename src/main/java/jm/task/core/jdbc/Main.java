package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Petr", "Petrov", (byte) 21);
        System.out.println("User с именем Petr добавлен в базу данных");

        userService.saveUser("Ivan", "Ivanov", (byte) 22);
        System.out.println("User с именем Ivan добавлен в базу данных");

        userService.saveUser("Sergey", "Sergeev", (byte) 23);
        System.out.println("User с именем Sergey добавлен в базу данных");

        userService.saveUser("Fedor", "Fedorov", (byte) 24);
        System.out.println("User с именем Fedor добавлен в базу данных");

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
