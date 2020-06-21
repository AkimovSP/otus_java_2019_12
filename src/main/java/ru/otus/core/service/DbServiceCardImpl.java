package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.core.dao.CardDao;
import ru.otus.core.model.Card;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

@Service
public class DbServiceCardImpl implements DBServiceCard {
    private static Logger logger = LoggerFactory.getLogger(DbServiceCardImpl.class);

    private final CardDao cardDao;

    private long loggedCardId;

    public DbServiceCardImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public void setLoggedCardId(long cardId) {

        System.out.println("SET CArD_ID " + cardId);
        this.loggedCardId = cardId;
    }

    @Override
    public long getLoggedCardId() {
        System.out.println("GET CARD_ID " + loggedCardId);
        return this.loggedCardId;
    }

    @Override
    public long saveCard(Card card) {
        try (SessionManager sessionManager = cardDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long cardId = cardDao.saveCard(card);
                sessionManager.commitSession();
                return cardId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) {
        try (SessionManager sessionManager = cardDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Card> userOptional = cardDao.findByCardNumber(cardNumber);

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Card> getCard(long id) {
        try (SessionManager sessionManager = cardDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Card> userOptional = cardDao.findById(id);

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
