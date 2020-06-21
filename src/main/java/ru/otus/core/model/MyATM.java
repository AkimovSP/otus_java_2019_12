package ru.otus.core.model;

import java.util.ArrayList;

public interface MyATM {

    //настройка банкомата
    void addCurrency(Currency currency);

    //получение баланса в рамках сети банкомата
    int getBalance(Currency currency);

    //загрузка наличных
    boolean uploadCash(Currency currency, CashNominal nominal, int value);

    //выдача наличных
    ArrayList<MyCashCell> downloadCash(Currency currency, int value);

    //настройка банкомата
    boolean removeCell(Currency currency, CashNominal nominal);

    //настройка банкомата
    boolean addCell(Currency currency, CashNominal nominal);

    //уточнение возможностей конкретного банкомата
    public String getAvailableCellsList();

    String getName();

    String getAddress();
}
