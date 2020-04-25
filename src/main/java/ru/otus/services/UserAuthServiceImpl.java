package ru.otus.services;

import ru.otus.core.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        System.out.println("authenticate "+login+" "+password);
        return dbServiceUser.findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}
