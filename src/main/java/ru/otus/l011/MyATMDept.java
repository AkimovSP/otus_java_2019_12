package ru.otus.l011;

public interface MyATMDept {
    void saveState() ;

    void restoreState();

    void restoreInitialState();

    void addUnit(MyATM unit);

    int getBalance(Currency currency);

    void addCurrency(Currency currency);

    boolean uploadCash(Currency currency, CashNominal nominal, int value);

    boolean removeCell(Currency currency, CashNominal nominal);

    boolean addCell(Currency currency, CashNominal nominal);
}
