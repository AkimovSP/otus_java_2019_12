package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.core.model.*;

import java.util.List;
import java.util.Optional;

public interface CardDao {

    Optional<Card> findById(long id);

    Optional<Card> findByCardNumber(String cardNumber);

    long saveCard(Card card);

    SessionManager getSessionManager();
}