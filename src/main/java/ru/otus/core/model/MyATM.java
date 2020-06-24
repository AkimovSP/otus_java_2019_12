package ru.otus.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MyATM {

    long getId();

    //настройка банкомата
    void addCurrency(Currency currency);

    //получение баланса в рамках сети банкомата
    int getBalance(Currency currency);

    //получение баланса в рамках сети банкомата
    HashMap<Currency, Integer> getBalance();

    //загрузка наличных
    boolean uploadCash(Currency currency, CashNominal nominal, int value);

    //выдача наличных
    ArrayList<MyCashCell> downloadCash(Currency currency, int value);

    //остатки купюр
    List<MyCashCell> getBalanceByCells();

    //настройка банкомата
    boolean removeCell(Currency currency, CashNominal nominal);

    //настройка банкомата
    boolean addCell(Currency currency, CashNominal nominal);

    //уточнение возможностей конкретного банкомата
    public String getAvailableCellsList();

    String getName();

    String getAddress();
}
