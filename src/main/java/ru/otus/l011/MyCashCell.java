package ru.otus.l011;

public class MyCashCell implements MyCashCellInterface{
    private Currency currency;
    private CashNominal nominal;
    private int currentValue;

    public MyCashCell(Currency currency, CashNominal nominal, int value) {
        this.currency = currency;
        this.nominal = nominal;
        this.currentValue = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public CashNominal getNominal() {
        return nominal;
    }

    public int getBalance() {
        return MyNominalList.getIntNominal(this.nominal) * currentValue;
    }

    public int getIntNominal() {
        return MyNominalList.getIntNominal(this.nominal);
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String toString() {
        return currency.toString() + " " + nominal.toString() + " " + currentValue;
    }
}

