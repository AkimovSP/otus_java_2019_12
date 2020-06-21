package ru.otus.core.model;

public enum CashNominal {
    NOM_10(10),
    NOM_20(20),
    NOM_50(50),
    NOM_100(100),
    NOM_200(200),
    NOM_500(500),
    NOM_1000(1000),
    NOM_2000(2000),
    NOM_5000(5000);

    int nominal;

    CashNominal(int nominal) {
        this.nominal = nominal;
    }
}
