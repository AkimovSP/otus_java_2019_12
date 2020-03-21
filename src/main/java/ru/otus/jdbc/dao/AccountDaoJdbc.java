package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.Account;
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

public class AccountDaoJdbc implements AccountDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<Account> dbExecutor;
    private final SQLMapperJDBC mapper;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor, SQLMapperJDBC mapper) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> findById(long id) {
        try {
            String sqlSelect = mapper.createSQLSelect(Account.class);
            System.out.println("Select SQL=" + sqlSelect);

            return dbExecutor.selectRecord(getConnection(), sqlSelect, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new Account(resultSet.getLong("no"), resultSet.getString("type"), resultSet.getFloat("rest"));
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
    public long saveAccount(Account account) {
        try {
            List<String> paramList = new ArrayList();
            paramList.add(account.getType());
            paramList.add(Float.toString(account.getRest()));

            String sqlInsert = mapper.createSQLInsert(account.getClass());
            System.out.println("Insert SQL=" + sqlInsert);

            return dbExecutor.insertRecord(getConnection(), sqlInsert,
                    paramList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            List<String> paramList = new ArrayList();
            paramList.add(account.getType());
            paramList.add(Float.toString(account.getRest()));
            paramList.add(Long.toString(account.getNo()));

            String sqlUpdate = mapper.createSQLUpdate(account.getClass());
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
