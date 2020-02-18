package ru.otus.l011;

import java.util.ArrayList;

public class MyNominalList {
    public static ArrayList<CashNominal> getDescendingSortedList() {
        ArrayList<CashNominal> result = new ArrayList<CashNominal>();
        result.add(CashNominal.NOM_5000);
        result.add(CashNominal.NOM_2000);
        result.add(CashNominal.NOM_1000);
        result.add(CashNominal.NOM_500);
        result.add(CashNominal.NOM_200);
        result.add(CashNominal.NOM_100);
        result.add(CashNominal.NOM_50);
        result.add(CashNominal.NOM_20);
        result.add(CashNominal.NOM_10);
        return result;
    }

    public static int getIntNominal(CashNominal nominal) {
        switch (nominal) {
            case NOM_10:
                return 10;
            case NOM_20:
                return 20;
            case NOM_50:
                return 50;
            case NOM_100:
                return 100;
            case NOM_200:
                return 200;
            case NOM_500:
                return 500;
            case NOM_1000:
                return 1000;
            case NOM_2000:
                return 2000;
            case NOM_5000:
                return 5000;
        }
        return 0;
    }

    public static ArrayList<CashNominal> getTypicalNominals(Currency currency) {
        ArrayList<CashNominal> result = new ArrayList<CashNominal>();
        switch (currency) {
            case RUB:
                result.add(CashNominal.NOM_50);
                result.add(CashNominal.NOM_100);
                result.add(CashNominal.NOM_200);
                result.add(CashNominal.NOM_500);
                result.add( CashNominal.NOM_1000);
                result.add( CashNominal.NOM_2000);
                result.add( CashNominal.NOM_5000);
                break;
            case EUR:
                result.add( CashNominal.NOM_10);
                result.add( CashNominal.NOM_20);
                result.add( CashNominal.NOM_50);
                result.add( CashNominal.NOM_100);
                result.add( CashNominal.NOM_500);
                break;
            case USD:
                result.add(CashNominal.NOM_10);
                result.add(CashNominal.NOM_20);
                result.add( CashNominal.NOM_50);
                result.add(CashNominal.NOM_100);
                break;
        }
        return result;
    }
}
