package ru.otus.l011;

import java.util.ArrayList;

public interface MyATMInterface {

    void addCurrency(Currency currency);

    int getBalance(Currency currency);

    boolean uploadCash(Currency currency, CashNominal nominal, int value);

    ArrayList<MyCashCell> downloadCash(Currency currency, int value);

    boolean removeCell(Currency currency, CashNominal nominal);

    String getName();

    String getAddress();

    String getAvailableCellsList();
}
