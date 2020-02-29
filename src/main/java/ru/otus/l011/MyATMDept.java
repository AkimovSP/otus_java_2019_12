package ru.otus.l011;

public interface MyATMDept {
    public void saveState() ;

    public void restoreState();

    public void restoreInitialState();

    public void addUnit(MyATM unit);

    public int getBalance(Currency currency);

    public void addCurrency(Currency currency);

    public boolean uploadCash(Currency currency, CashNominal nominal, int value);

    public boolean removeCell(Currency currency, CashNominal nominal);

    public boolean addCell(Currency currency, CashNominal nominal);

}
