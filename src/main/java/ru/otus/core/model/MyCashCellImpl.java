package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "TCASHCELL")
public class MyCashCellImpl implements MyCashCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COLCURRENCY")
    private Currency currency;

    @Column(name = "COLNOMINAL")
    private CashNominal nominal;

    @Column(name = "COLVALUE")
    private int currentValue;

    public MyCashCellImpl(long id, Currency currency, CashNominal nominal, int value) {
        this.id = id;
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

