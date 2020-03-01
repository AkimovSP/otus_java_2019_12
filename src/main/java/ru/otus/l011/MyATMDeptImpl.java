package ru.otus.l011;

import java.util.*;

public class MyATMDeptImpl implements MyATMDept {
    private List<MyATM> units = new ArrayList<>();
    private final Originator originator = new Originator();

    public void saveState() {
        originator.saveState(new State(units));
    }

    public void restoreState() {
        units = originator.restoreState().getArray();
    }

    public void addUnit(MyATM unit) {
        units.add(unit);
    }

    public void restoreInitialState() {
        units = originator.restoreInitialState().getArray();
    }

    public int getBalance(Currency currency) {
        int result = 0;

        for (MyATM myATM : units) {
            result += myATM.getBalance(currency);
        }
        return result;
    }

    public void addCurrency(Currency currency) {
        for (MyATM myATM : units) {
            myATM.addCurrency(currency);
        }
    }

    public boolean uploadCash(Currency currency, CashNominal nominal, int value) {
        boolean result = true;
        for (MyATM myATM : units) {
            result &= myATM.uploadCash(currency, nominal, value);
        }
        return result;
    }

    public boolean removeCell(Currency currency, CashNominal nominal) {
        boolean result = true;
        for (MyATM myATM : units) {
            result &= myATM.removeCell(currency, nominal);
        }
        return result;
    }

    public boolean addCell(Currency currency, CashNominal nominal) {
        boolean result = true;
        for (MyATM myATM : units) {
            result &= myATM.removeCell(currency, nominal);
        }
        return result;
    }
}
