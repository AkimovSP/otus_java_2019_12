package ru.otus.l011;

import java.util.*;

public class MyATMDeptImpl implements MyATMDept {
    private List<MyATM> units = new ArrayList<>();

    //stack
    private final Deque<Memento> stack = new ArrayDeque<>();

    public void saveState() {
        stack.push(new Memento(new State(units)));
    }

    public void restoreState() {
        units = stack.pop().getState().getArray();
    }

    public void addUnit(MyATM unit) {
        units.add(unit);
    }

    public void restoreInitialState() {
        do {
            try {
                restoreState();
            } catch (NoSuchElementException e) {
                break;
            }
        } while (true);
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
