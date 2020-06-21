package ru.otus.core.model;

/* to do
-- добавить максимальную вместимость ячеек - сейчас они бесконечные
 */

import java.util.ArrayList;

public class MyATMImpl implements MyATM {
    private String name;
    private String address;

    private ArrayList<MyCashCell> cashCellsWithValue;

    public MyATMImpl() {
        this.name = "";
        this.address = "";
        this.cashCellsWithValue = new ArrayList<MyCashCell>();
    }

    public MyATMImpl(String name, String address) {
        this.name = name;
        this.address = address;
        this.cashCellsWithValue = new ArrayList<MyCashCell>();
    }

    private ArrayList<MyCashCell> getPossiblePairs(Currency currency) {
        ArrayList<MyCashCell> result = new ArrayList<MyCashCell>();

        for (CashNominal nominal : MyNominalList.getTypicalNominals(currency)
        ) {
            result.add(new MyCashCellImpl(currency, nominal, 0));
        }
        return result;
    }

    public void addCurrency(Currency currency) {
        for (MyCashCell cell : getPossiblePairs(currency)
        ) {
            this.cashCellsWithValue.add(new MyCashCellImpl(cell.getCurrency(), cell.getNominal(), 0));
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
            copyOfCashCellsWithValue.add(new MyCashCellImpl(curCell.getCurrency(), curCell.getNominal(), curCell.getCurrentValue()));
        }

//идем по убыванию номинала
        for (CashNominal nominal : MyNominalList.getDescendingSortedList()) {
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
                        result.add(new MyCashCellImpl(currency, nominal, numberOfBanknotes));
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

    public boolean addCell(Currency currency, CashNominal nominal) {
        boolean result = false;
        for (MyCashCell cell : getPossiblePairs(currency)
        ) {
            if (cell.getNominal() == nominal) {
                result = true;
                break;
            }
        }

        if (!result) {
            return result;
        }

        for (MyCashCell curCell : this.cashCellsWithValue) {
            if (curCell.getNominal() == nominal & curCell.getCurrency() == currency) {
                return true;
            }
        }

        MyCashCell cell = new MyCashCellImpl(currency, nominal, 0);
        this.cashCellsWithValue.add(cell);
        return true;
    }

    public boolean removeCell(Currency currency, CashNominal nominal) {
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
            result = result.concat(cell.toString()+" ");
        }
        return result;
    }
}

