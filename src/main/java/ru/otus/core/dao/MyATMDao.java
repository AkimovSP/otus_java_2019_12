package ru.otus.core.dao;

import ru.otus.core.model.MyATMImpl;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface MyATMDao {

    Optional<MyATMImpl> findById(long id);

    Optional<MyATMImpl> findByName(String name);

    long saveMyATM(MyATMImpl myATM);

    SessionManager getSessionManager();
}