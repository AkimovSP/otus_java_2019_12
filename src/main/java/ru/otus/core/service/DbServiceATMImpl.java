package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.core.dao.CardDao;
import ru.otus.core.dao.MyATMDao;
import ru.otus.core.model.Card;
import ru.otus.core.model.MyATM;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

@Service
public class DbServiceATMImpl implements DBServiceATM {
    private static Logger logger = LoggerFactory.getLogger(DbServiceATMImpl.class);

    private final MyATMDao myATMDao;

    public DbServiceATMImpl(MyATMDao myATMDao) {
        this.myATMDao = myATMDao;
    }

    private long currentATMId;

    @Override
    public void setCurrentATMId(long id) {
        this.currentATMId = id;
    }

    @Override
    public long getCurrentATMId() {
        return this.currentATMId;
    }
    @Override
    public long saveMyATM(MyATM myATM) {
        try (SessionManager sessionManager = myATMDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long myATMId = myATMDao.saveMyATM(myATM);
                sessionManager.commitSession();
                return myATMId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<MyATM> findByATMName(String name) {
        try (SessionManager sessionManager = myATMDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<MyATM> myATMOptional = myATMDao.findByName(name);

                logger.info("ATM: {}", myATMOptional.orElse(null));
                return myATMOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<MyATM> getMyATM(long id) {
        try (SessionManager sessionManager = myATMDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<MyATM> myATMOptional = myATMDao.findById(id);

                logger.info("ATM: {}", myATMOptional.orElse(null));
                return myATMOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
