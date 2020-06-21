package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "TCARD")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CARDNUMBER")
    private String cardNumber;

    @Column(name = "COLPIN")
    private String pin;

    @Column(name = "COLCURRENCY")
    private Currency currency;

    @Column(name = "COLSERVICE")
    private boolean serviceMode;

    @Column(name = "COLBALANCE")
    private double balance;

    public Card() {
        this.id = 0;
        this.cardNumber = "";
        this.pin = "";
        this.currency = Currency.RUB;
        this.serviceMode = false;
        this.balance = 0;
    }

    public Card(long id, String cardNumber, String pin, Currency currency, boolean serviceMode, double balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.currency = currency;
        this.serviceMode = serviceMode;
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public boolean isServiceMode() {
        return serviceMode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
