package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.core.dao.MyATMDao;
import ru.otus.core.model.MyATMImpl;
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
    public long saveMyATM(MyATMImpl myATM) {
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
    public Optional<MyATMImpl> findByATMName(String name) {
        try (SessionManager sessionManager = myATMDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<MyATMImpl> myATMOptional = myATMDao.findByName(name);

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
    public Optional<MyATMImpl> getMyATM(long id) {
        try (SessionManager sessionManager = myATMDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                System.out.println("step1");
                Optional<MyATMImpl> myATMOptional = myATMDao.findById(id);
                System.out.println("step2");
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
