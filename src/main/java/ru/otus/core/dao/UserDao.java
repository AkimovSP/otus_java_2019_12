package ru.otus.core.dao;

import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.core.model.*;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findByLogin(String login);

    long saveUser(User user);

    List<User> getAllUsers();

    SessionManager getSessionManager();

}