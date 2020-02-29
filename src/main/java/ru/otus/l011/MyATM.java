package ru.otus.l011;

import java.util.TreeSet;

public interface MyATM {

    //настройка банкомата
    void addCurrency(Currency currency);

    //получение баланса в рамках сети банкомата
    int getBalance(Currency currency);

    //загрузка наличных
    boolean uploadCash(Currency currency, CashNominal nominal, int value);

    //выдача наличных
    TreeSet<MyCashCell> downloadCash(Currency currency, int value);

    //выдача наличных
    TreeSet<MyCashCell> getCashCellsList();

    //настройка банкомата
    boolean removeCell(Currency currency, CashNominal nominal);

    //настройка банкомата
    boolean addCell(Currency currency, CashNominal nominal);

    //уточнение возможностей конкретного банкомам
    public String getAvailableCellsList();

    String getName();

    String getAddress();
}
