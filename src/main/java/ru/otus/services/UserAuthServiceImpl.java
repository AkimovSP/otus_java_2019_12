package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.core.service.DBServiceCard;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceCard dbServiceCard;

    public UserAuthServiceImpl(DBServiceCard dbServiceCard) {
        this.dbServiceCard = dbServiceCard;
    }

    @Override
    public boolean authenticate(String login, String password) {
        System.out.println("authenticate "+login+" "+password);
        return dbServiceCard.findByCardNumber(login)
                .map(user -> user.getPin().equals(password))
                .orElse(false);
    }
}
