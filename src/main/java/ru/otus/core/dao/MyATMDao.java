package ru.otus.core.dao;

import ru.otus.core.model.MyATM;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface MyATMDao {

    Optional<MyATM> findById(long id);

    Optional<MyATM> findByName(String name);

    long saveMyATM(MyATM myATM);

    SessionManager getSessionManager();
}