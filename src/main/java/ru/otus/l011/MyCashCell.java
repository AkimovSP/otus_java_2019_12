package ru.otus.l011;

public interface MyCashCell {
    Currency getCurrency();

    CashNominal getNominal();

    int getBalance();

    int getCurrentValue();

    int getIntNominal();

    boolean setCurrentValue(int currentValue);
}
