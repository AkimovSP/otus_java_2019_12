package ru.otus.services;


import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

public class DatabaseInitImpl implements DatabaseInit {

    private final DBServiceUser dbServiceUser;

    public DatabaseInitImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public void fillDatabase() {
        User user1 = new User(1, "Сергей", "Serg", "AAA");
        User user2 = new User(2, "Иван", "Ivan", "BBB");
        dbServiceUser.saveUser(user1);
        dbServiceUser.saveUser(user2);
    }
}
