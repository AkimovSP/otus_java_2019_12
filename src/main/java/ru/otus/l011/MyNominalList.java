package ru.otus.l011;

import java.util.TreeSet;

public class MyNominalList {
    public static TreeSet<CashNominal> getTypicalNominals(Currency currency) {
        TreeSet<CashNominal> result = new TreeSet<CashNominal>();
        switch (currency) {
            case RUB:
                result.add(CashNominal.NOM_50);
                result.add(CashNominal.NOM_100);
                result.add(CashNominal.NOM_200);
                result.add(CashNominal.NOM_500);
                result.add(CashNominal.NOM_1000);
                result.add(CashNominal.NOM_2000);
                result.add(CashNominal.NOM_5000);
                break;
            case EUR:
                result.add(CashNominal.NOM_10);
                result.add(CashNominal.NOM_20);
                result.add(CashNominal.NOM_50);
                result.add(CashNominal.NOM_100);
                result.add(CashNominal.NOM_500);
                break;
            case USD:
                result.add(CashNominal.NOM_10);
                result.add(CashNominal.NOM_20);
                result.add(CashNominal.NOM_50);
                result.add(CashNominal.NOM_100);
                break;
        }
        return result;
    }
}
