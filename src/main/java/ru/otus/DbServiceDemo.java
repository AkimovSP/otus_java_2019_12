package ru.otus;

import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.SQLMapperJDBC;
import ru.otus.jdbc.SQLMapperJDBCImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DbServiceDemo {
    private static boolean useCache = true;
    //false - запускаем без кэша
    //true - запускаем с кэшем

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        System.out.println("PREPARATION");
        demo.createTable(dataSource);

        //common
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        SQLMapperJDBC mapper = new SQLMapperJDBCImpl();

        //user
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        UserDao userDao = new UserDaoJdbc(sessionManager, dbExecutor, mapper);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, useCache);


        System.out.println("Saving USERs");
        long beginTime = System.currentTimeMillis();
        System.out.println(beginTime);

        for (int i = 1; i < 1000; i++) {
            User user = new User(i, "dbServiceUser" + i, 10 + i);
            long id = dbServiceUser.saveUser(user);
            System.out.println("Inserted " + user);
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        long delta = endTime - beginTime;
        System.out.println("Difference = " + delta);

        System.out.println("");
        System.out.println("-----");
        System.out.println("");

        System.out.println("Saving USERs");
        beginTime = System.currentTimeMillis();
        System.out.println(beginTime);

        dbServiceUser.initReadCache();
        AtomicInteger totalAge = new AtomicInteger();
        for (int i = 1; i < 1000; i++) {
            Optional<User> user = dbServiceUser.getUser(i);
            user.ifPresent(crUser -> totalAge.addAndGet(crUser.getAge()));
        }
        System.out.println("Total age = " + totalAge);
        System.out.println("Total read from cache = " + dbServiceUser.getReadCache());
        endTime = System.currentTimeMillis();
        System.out.println(endTime);
        delta = endTime - beginTime;
        System.out.println("Difference = " + delta);
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int)")) {
                pst.executeUpdate();
            }
            System.out.println("table USER created");
        }
    }
}
