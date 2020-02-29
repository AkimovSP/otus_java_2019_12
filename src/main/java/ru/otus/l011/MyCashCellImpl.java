package ru.otus.l011;

public class MyCashCellImpl implements MyCashCell, Comparable<MyCashCell> {
    private Currency currency;
    private CashNominal nominal;
    private int currentValue;

    public MyCashCellImpl(Currency currency, CashNominal nominal, int value) {
        this.currency = currency;
        this.nominal = nominal;
        this.currentValue = value;
    }

    public MyCashCellImpl(MyCashCell cell) {
        this.currency = cell.getCurrency();
        this.nominal = cell.getNominal();
        this.currentValue = cell.getCurrentValue();
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

    @Override
    public int compareTo(MyCashCell myCashCell) {
        if (myCashCell.getNominal() == this.nominal) {
            return 0;
        } else {
            if (this.getIntNominal() < myCashCell.getIntNominal()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}

