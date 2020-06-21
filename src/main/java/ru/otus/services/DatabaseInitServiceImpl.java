package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.core.model.Card;
import ru.otus.core.model.Currency;
import ru.otus.core.service.DBServiceCard;

@Service
public class DatabaseInitServiceImpl implements DatabaseInitService {

    private final DBServiceCard dbServiceCard;

    public DatabaseInitServiceImpl(DBServiceCard dbServiceCard) {
        this.dbServiceCard = dbServiceCard;
        fillDatabase();
    }

    @Override
    public void fillDatabase() {
        Card card1 = new Card(1, "1111222233334444", "1111", Currency.RUB, false, 10000);
        Card card2 = new Card(2, "2222333344445555", "2222", Currency.EUR, true, 10000);
        dbServiceCard.saveCard(card1);
        dbServiceCard.saveCard(card2);
    }
}
