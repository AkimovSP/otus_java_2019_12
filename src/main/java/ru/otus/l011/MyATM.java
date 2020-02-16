package ru.otus.l011;

/* to do
-- добавить максимальную вместимость ячеек - сейчас они бесконечные
 */

import java.util.ArrayList;

enum Currency {RUB, EUR, USD};

enum CashNominal {NOM_10, NOM_20, NOM_50, NOM_100, NOM_200, NOM_500, NOM_1000, NOM_2000, NOM_5000};

public class MyATM {
    private String name;
    private String address;

    private ArrayList<MyCashCell> cashCellsWithValue;

    public MyATM() {
        this.name = "";
        this.address = "";
        this.cashCellsWithValue = new ArrayList<MyCashCell>();
    }

    public MyATM(String name, String address) {
        this.name = name;
        this.address = address;
        this.cashCellsWithValue = new ArrayList<MyCashCell>();
    }

    private ArrayList<MyCashCell> getTypicalPairs(Currency currency) {
        ArrayList<MyCashCell> result = new ArrayList<MyCashCell>();
        switch (currency) {
            case RUB:
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_50, 0));
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_100, 0));
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_200, 0));
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_500, 0));
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_1000, 0));
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_2000, 0));
                result.add(new MyCashCell(Currency.RUB, CashNominal.NOM_5000, 0));
                break;
            case EUR:
                result.add(new MyCashCell(Currency.EUR, CashNominal.NOM_10, 0));
                result.add(new MyCashCell(Currency.EUR, CashNominal.NOM_20, 0));
                result.add(new MyCashCell(Currency.EUR, CashNominal.NOM_50, 0));
                result.add(new MyCashCell(Currency.EUR, CashNominal.NOM_100, 0));
                result.add(new MyCashCell(Currency.EUR, CashNominal.NOM_500, 0));
                break;
            case USD:
                result.add(new MyCashCell(Currency.USD, CashNominal.NOM_10, 0));
                result.add(new MyCashCell(Currency.USD, CashNominal.NOM_20, 0));
                result.add(new MyCashCell(Currency.USD, CashNominal.NOM_50, 0));
                result.add(new MyCashCell(Currency.USD, CashNominal.NOM_100, 0));
                break;
        }
        return result;
    }

    public void addCurrency(Currency currency) {
        for (MyCashCell cell : getTypicalPairs(currency)
        ) {
            this.cashCellsWithValue.add(new MyCashCell(cell.getCurrency(), cell.getNominal(), 0));
        }
    }

    private void removeCells(Currency currency, CashNominal nominal) {
        for (MyCashCell curCell : this.cashCellsWithValue) {
            if (curCell.getCurrency() == currency & curCell.getNominal() == nominal) {
                this.cashCellsWithValue.remove(curCell);
                return;
            }
        }
    }

    public int getBalance(Currency currency) {
        int result = 0;
        for (MyCashCell curCell : this.cashCellsWithValue) {
            if (curCell.getCurrency() == currency) {
                result += curCell.getBalance();
            }
        }
        return result;
    }

    public boolean uploadCash(Currency currency, CashNominal nominal, int value) {
        System.out.println("Upload cash " + currency + " " + nominal + " " + value);
        for (MyCashCell curCell : this.cashCellsWithValue) {
            if (curCell.getCurrency() == currency & curCell.getNominal() == nominal) {
                curCell.setCurrentValue(curCell.getCurrentValue() + value);
                return true;
            }
        }
        return false;
    }

    public ArrayList<MyCashCell> downloadCash(Currency currency, int value) {
        System.out.println("Download cash request " + value + " " + currency);

        ArrayList<MyCashCell> result = new ArrayList<MyCashCell>();
        ArrayList<MyCashCell> copyOfCashCellsWithValue = new ArrayList<MyCashCell>();
        for (MyCashCell curCell : cashCellsWithValue) {
            copyOfCashCellsWithValue.add(new MyCashCell(curCell.currency, curCell.nominal, curCell.currentValue));
        }

//идем по убыванию номинала
        for (CashNominal nominal : NominalList.getDescendingSortedList()) {
            if (value == 0) {
                break;
            }
            Object nom = nominal;
            for (MyCashCell curCell : copyOfCashCellsWithValue) {
                if (curCell.getCurrency() == currency & curCell.getNominal() == nominal) {
                    int numberOfBanknotes = value / curCell.getIntNominal();
                    numberOfBanknotes = Math.min(numberOfBanknotes, curCell.getCurrentValue());
                    if (numberOfBanknotes > 0) {
                        curCell.setCurrentValue(curCell.getCurrentValue() - numberOfBanknotes);
                        value -= numberOfBanknotes * curCell.getIntNominal();
                        result.add(new MyCashCell(currency, nominal, numberOfBanknotes));
                    }
                    if (value == 0) {
                        break;
                    }
                }
            }
        }

//Если вышли в 0 - принимаем новое состояние. Если нет - выдаем null
        if (value == 0) {
            this.cashCellsWithValue = copyOfCashCellsWithValue;
            return result;
        } else {
            return null;
        }
    }

    public void addCell(Currency currency, CashNominal nominal) {
        for (MyCashCell curCell : this.cashCellsWithValue) {
            if (curCell.getNominal() == nominal & curCell.getCurrency() == currency) {
                return;
            }
        }

        MyCashCell cell = new MyCashCell(currency, nominal, 0);
        this.cashCellsWithValue.add(cell);
    }

    public boolean removePair(Currency currency, CashNominal nominal) {
        for (MyCashCell curCell : this.cashCellsWithValue) {
            if (curCell.getNominal() == nominal & curCell.getCurrency() == currency) {
                if (curCell.getCurrentValue() != 0) {
                    return false;
                } else {
                    this.cashCellsWithValue.remove(curCell);
                    return true;
                }
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getAvailableCellsList() {
        String result = "";
        for (MyCashCell cell : this.cashCellsWithValue) {
            result = result.concat(cell.toString());
        }
        return result;
    }

    private class MyCashCell {
        private Currency currency;
        private CashNominal nominal;
        private int currentValue;

        public MyCashCell(Currency currency, CashNominal nominal, int value) {
            this.currency = currency;
            this.nominal = nominal;
            this.currentValue = value;
        }

        public Currency getCurrency() {
            return currency;
        }

        public CashNominal getNominal() {
            return nominal;
        }

        public int getBalance() {
            return getIntNominal() * currentValue;
        }

        public int getIntNominal() {
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

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }

        public String toString() {
            return currency.toString() + " " + nominal.toString() + " " + currentValue;
        }
    }

    private static class NominalList {
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
    }
}

