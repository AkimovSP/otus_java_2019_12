package ru.otus.core.model;

public interface MyCashCell {
    Currency getCurrency();

    CashNominal getNominal();

    int getBalance();

    int getCurrentValue();

    int getIntNominal();

    boolean setCurrentValue(int currentValue);
}
