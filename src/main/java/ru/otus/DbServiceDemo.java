package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceAccount;
import ru.otus.core.service.DBServiceAccountImpl;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.SQLMapperJDBC;
import ru.otus.jdbc.SQLMapperJDBCImpl;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

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

        //account
        DbExecutor<Account> dbExecutorAcc = new DbExecutor<>();
        AccountDao accountDao = new AccountDaoJdbc(sessionManager, dbExecutorAcc, mapper);

        System.out.println("USER");
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        long id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 10));
        Optional<User> user = dbServiceUser.getUser(id);
        user.ifPresent(crUser -> crUser.setName("NEWNAME"));
        user.ifPresent(crUser -> dbServiceUser.updateUser(crUser));
        user = dbServiceUser.getUser(id);
        System.out.println(user);


        System.out.println("ACCOUNT");
        DBServiceAccount dbServiceAccount = new DBServiceAccountImpl(accountDao);
        long idAcc = dbServiceAccount.saveAccount(new Account(0, "debit", 10f));
        Optional<Account> account = dbServiceAccount.getAccount(idAcc);
        account.ifPresent(crAccount -> crAccount.setType("CREDIT"));
        account.ifPresent(crAccount -> crAccount.setRest(200f));
        account.ifPresent(crAccount -> dbServiceAccount.updateAccount(crAccount));
        account = dbServiceAccount.getAccount(idAcc);
        System.out.println(account);
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int)")) {
                pst.executeUpdate();
            }
            System.out.println("table USER created");

            try (PreparedStatement pst = connection.prepareStatement("create table account (no long auto_increment, type varchar(255), rest number)")) {
                pst.executeUpdate();
            }
            System.out.println("table ACCOUNT created");
        }
    }
}
