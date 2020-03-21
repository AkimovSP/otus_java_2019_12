package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.SQLMapperJDBC;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> dbExecutor;
    private final SQLMapperJDBC mapper;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor, SQLMapperJDBC mapper) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            String sqlSelect = mapper.createSQLSelect(User.class);
            System.out.println("Select SQL=" + sqlSelect);

            return dbExecutor.selectRecord(getConnection(), sqlSelect, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            List<String> paramList = new ArrayList();
            paramList.add(user.getName());
            paramList.add(Integer.toString(user.getAge()));

            String sqlInsert = mapper.createSQLInsert(user.getClass());
            System.out.println("Insert SQL=" + sqlInsert);

            return dbExecutor.insertRecord(getConnection(), sqlInsert,
                    paramList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            List<String> paramList = new ArrayList();
            paramList.add(user.getName());
            paramList.add(Integer.toString(user.getAge()));
            paramList.add(Long.toString(user.getId()));

            String sqlUpdate = mapper.createSQLUpdate(user.getClass());
            System.out.println("Update SQL=" + sqlUpdate);

            dbExecutor.updateRecord(getConnection(), sqlUpdate,
                    paramList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
