package ru.otus.l011;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Main {
    public static void main(String... args) {
        MyATM myATM = new MyATM();
        myATM.addCurrency(Currency.RUB);
        System.out.println("Available cells = "+myATM.getAvailableCellsList());
        myATM.removePair(Currency.RUB, CashNominal.NOM_5000);
        System.out.println("Available cells = "+myATM.getAvailableCellsList());

        System.out.println("ATM balance is "+myATM.getBalance(Currency.RUB)+" "+Currency.RUB);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_2000, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_1000, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_500, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_200, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_100, 5);
        System.out.println("ATM balance is "+myATM.getBalance(Currency.RUB)+" "+Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 8000));
        System.out.println("ATM balance is "+myATM.getBalance(Currency.RUB)+" "+Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 2600));
        System.out.println("ATM balance is "+myATM.getBalance(Currency.RUB)+" "+Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 2600));
        System.out.println("ATM balance is "+myATM.getBalance(Currency.RUB)+" "+Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 4880));
        System.out.println("ATM balance is "+myATM.getBalance(Currency.RUB)+" "+Currency.RUB);
    }
}
