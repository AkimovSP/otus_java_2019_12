package ru.otus.l011;

public interface MyCashCellInterface {
    Currency getCurrency();

    CashNominal getNominal();

    int getBalance();

    int getIntNominal();

    int getCurrentValue();

    void setCurrentValue(int currentValue);
}
