package ru.otus.core.model;

public class MyCashCellImpl implements MyCashCell {
    private Currency currency;
    private CashNominal nominal;
    private int currentValue;

    public MyCashCellImpl(Currency currency, CashNominal nominal, int value) {
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
        return this.nominal.nominal * currentValue;
    }

    public int getIntNominal() {
        return this.nominal.nominal;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public boolean setCurrentValue(int currentValue) {
        if (currentValue >= 0) {
            this.currentValue = currentValue;
            return true;
        } else
            return false;
    }

    public String toString() {
        return currency.toString() + " " + nominal.toString(); //+ " " + currentValue;
    }
}

