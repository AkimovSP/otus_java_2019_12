package ru.otus.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

@Service
public class DatabaseInitServiceImpl implements DatabaseInitService {
@Autowired
    private final DBServiceUser dbServiceUser;

    public DatabaseInitServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
        fillDatabase();
    }

    @Override
    public void fillDatabase() {
        User user1 = new User(1, "Сергей", "Serg", "AAA");
        User user2 = new User(2, "Иван", "Ivan", "BBB");
        dbServiceUser.saveUser(user1);
        dbServiceUser.saveUser(user2);
    }
}
