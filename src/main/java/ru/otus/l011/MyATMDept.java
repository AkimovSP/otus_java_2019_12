package ru.otus.l011;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 16.01.19.
 */
public class MyATMDept implements MyATM {
    private final List<MyATM> units = new ArrayList<>();

    public void addUnit(MyATM unit) {
        units.add(unit);
    }

    @Override
    public int getBalance(Currency currency) {
        int result = 0;

        for (MyATM myATM :
                units) {
            result += myATM.getBalance(currency);
        }
        return result;
    }

    @Override
    public void addCurrency(Currency currency) {
        for (MyATM myATM :
                units) {
            myATM.addCurrency(currency);
        }
    }

    @Override
    public boolean uploadCash(Currency currency, CashNominal nominal, int value) {
        boolean result = true;
        for (MyATM myATM :
                units) {
            result = result & myATM.uploadCash(currency, nominal, value);
        }
        return result;
    }

    @Override
    public ArrayList<MyCashCell> downloadCash(Currency currency, int value) {
        return null;
    }

    @Override
    public boolean removeCell(Currency currency, CashNominal nominal) {
        boolean result = true;
        for (MyATM myATM :
                units) {
            result = result & myATM.removeCell(currency, nominal);
        }
        return result;
    }

    @Override
    public boolean addCell(Currency currency, CashNominal nominal) {
        boolean result = true;
        for (MyATM myATM :
                units) {
            result = result & myATM.removeCell(currency, nominal);
        }
        return result;
    }

    @Override
    public String getAvailableCellsList() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public void changeState() {
        for (MyATM myATM :
                units) {
            myATM.changeState();
        }
    }

    @Override
    public String getState() {
        String result = "";
        for (MyATM myATM :
                units) {
            result = result + myATM.getState() + " ";
        }
        return result;
    }
}
