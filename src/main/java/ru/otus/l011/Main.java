package ru.otus.l011;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Main {
    public static void main(String... args) {
        MyATM atm1 = new MyATMImpl();
        MyATM atm2 = new MyATMImpl();
        MyATM atm3 = new MyATMImpl();

        MyATMDept group = new MyATMDept();
        group.addUnit(atm1);
        group.addUnit(atm2);
        group.addUnit(atm3);

        System.out.println("ATMs state is " + group.getState());
        group.changeState();
        System.out.println("ATMs state is " + group.getState());

        group.addCurrency(Currency.RUB);
        atm1.uploadCash(Currency.RUB, CashNominal.NOM_5000, 10);
        atm2.uploadCash(Currency.RUB, CashNominal.NOM_1000, 10);
        atm3.uploadCash(Currency.RUB, CashNominal.NOM_100, 10);

        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);

        group.changeState();
        System.out.println("ATMs state is " + group.getState());

        //Error
        try {
            System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        } catch (Error e) {
            System.out.println("Wrong state for getting balance");
        }

        group.changeState();
        System.out.println("ATMs state is " + group.getState());

        //OK
        try {
            System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        } catch (Error e) {
            System.out.println("Wrong state for getting balance");
        }

    }


    public static void CheckAtm(String... args) {

        //RUB
        MyATM myATM = new MyATMImpl();
        myATM.addCurrency(Currency.RUB);
        System.out.println("Available cells = " + myATM.getAvailableCellsList());
        myATM.removeCell(Currency.RUB, CashNominal.NOM_5000);
        System.out.println("Available cells = " + myATM.getAvailableCellsList());

        System.out.println("ATM balance is " + myATM.getBalance(Currency.RUB) + " " + Currency.RUB);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_2000, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_1000, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_500, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_200, 5);
        myATM.uploadCash(Currency.RUB, CashNominal.NOM_100, 5);
        System.out.println("ATM balance is " + myATM.getBalance(Currency.RUB) + " " + Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 8000));
        System.out.println("ATM balance is " + myATM.getBalance(Currency.RUB) + " " + Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 2600));
        System.out.println("ATM balance is " + myATM.getBalance(Currency.RUB) + " " + Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 2600));
        System.out.println("ATM balance is " + myATM.getBalance(Currency.RUB) + " " + Currency.RUB);

        System.out.println(myATM.downloadCash(Currency.RUB, 4880));
        System.out.println("ATM balance is " + myATM.getBalance(Currency.RUB) + " " + Currency.RUB);

        //EUR
        myATM.addCurrency(Currency.EUR);
        System.out.println("Available cells = " + myATM.getAvailableCellsList());
        System.out.println("Adding cell for 2000 EUR is " + myATM.addCell(Currency.EUR, CashNominal.NOM_2000));//false
    }
}
