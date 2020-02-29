package ru.otus.l011;

public class Main {
    public static void main(String... args) {
        MyATM atm1 = new MyATMImpl("ATM1","");
        MyATM atm2 = new MyATMImpl("ATM2","");
        MyATM atm3 = new MyATMImpl("ATM3","");

        MyATMDept group = new MyATMDeptImpl();
        group.addUnit(atm1);
        group.addUnit(atm2);
        group.addUnit(atm3);

        group.addCurrency(Currency.RUB);
        group.uploadCash(Currency.RUB, CashNominal.NOM_5000, 10);
        atm2.uploadCash(Currency.RUB, CashNominal.NOM_1000, 10);
        atm3.uploadCash(Currency.RUB, CashNominal.NOM_100, 10);

        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        System.out.println("-------------Saving stateATMs state-------------");
        group.saveState();
//161 000

        atm3.downloadCash(Currency.RUB, 20000);
        atm1.uploadCash(Currency.RUB, CashNominal.NOM_50, 5);
        atm2.downloadCash(Currency.RUB, 1000);
        atm3.uploadCash(Currency.RUB, CashNominal.NOM_200, 8);
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        System.out.println("-------------Saving stateATMs state-------------");
        group.saveState();
//141 850

        atm3.uploadCash(Currency.RUB, CashNominal.NOM_200, 8);
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        System.out.println("-------------Saving stateATMs state-------------");
        group.saveState();
//143 450

        atm3.uploadCash(Currency.RUB, CashNominal.NOM_200, 8);
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        System.out.println("-------------Saving stateATMs state-------------");
        group.saveState();
//145 050

        atm3.uploadCash(Currency.RUB, CashNominal.NOM_200, 8);
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
        System.out.println("-------------NOT SAVED STATE-------------");
//146 650

        System.out.println("-------------Restoring stateATMs state-------------");
        group.restoreState();
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
//145 450

        System.out.println("-------------Restoring stateATMs state-------------");
        group.restoreState();
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
//143 450

        System.out.println("-------------Restoring INITIAL stateATMs state-------------");
        group.restoreInitialState();
        System.out.println("Group balance is " + group.getBalance(Currency.RUB) + " " + Currency.RUB);
//161 000
    }

    public static void CheckAtm(String... args) {
        // not used for a while

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
