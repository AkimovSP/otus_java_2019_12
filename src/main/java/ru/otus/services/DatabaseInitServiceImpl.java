package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.core.model.*;
import ru.otus.core.service.DBServiceATM;
import ru.otus.core.service.DBServiceCard;

@Service
public class DatabaseInitServiceImpl implements DatabaseInitService {

    private final DBServiceCard dbServiceCard;
    private final DBServiceATM dbServiceATM;

    public DatabaseInitServiceImpl(DBServiceCard dbServiceCard, DBServiceATM dbServiceATM) {
        this.dbServiceCard = dbServiceCard;
        this.dbServiceATM = dbServiceATM;
        fillDatabase();
    }

    @Override
    public void fillDatabase() {
        Card card1 = new Card(1, "1111222233334444", "1111", Currency.RUB, false, 10000);
        Card card2 = new Card(2, "2222333344445555", "2222", Currency.EUR, true, 10000);
        dbServiceCard.saveCard(card1);
        dbServiceCard.saveCard(card2);

        MyATM myATM = new MyATMImpl();
        myATM.addCurrency(Currency.RUB);
        myATM.addCurrency(Currency.EUR);
        myATM.addCurrency(Currency.USD);

        myATM.uploadCash(Currency.RUB, CashNominal.NOM_2000, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_1000, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_500, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_200, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_100, 5);

        myATM.uploadCash(Currency.EUR, CashNominal.NOM_500, 11);
        myATM.uploadCash(Currency.EUR, CashNominal.NOM_100, 11);
        myATM.uploadCash(Currency.EUR, CashNominal.NOM_50, 11);
        myATM.uploadCash(Currency.EUR, CashNominal.NOM_20, 11);
        myATM.uploadCash(Currency.EUR, CashNominal.NOM_10, 11);

        myATM.uploadCash(Currency.USD, CashNominal.NOM_100, 15);
        myATM.uploadCash(Currency.USD, CashNominal.NOM_50, 15);
        myATM.uploadCash(Currency.USD, CashNominal.NOM_20, 15);
        myATM.uploadCash(Currency.USD, CashNominal.NOM_10, 15);

        dbServiceATM.setCurrentATMId(dbServiceATM.saveMyATM(myATM));
        System.out.println(myATM.getBalance(Currency.RUB)+" RUB");
        System.out.println(myATM.getBalance(Currency.USD)+" USD");
        System.out.println(myATM.getBalance(Currency.EUR)+" EUR");
    }
}
