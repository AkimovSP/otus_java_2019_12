package ru.otus.core.model;

/* to do
-- добавить максимальную вместимость ячеек - сейчас они бесконечные
 */

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TATM")
public class MyATMImpl implements MyATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COLNAME")
    private String name;

    @Column(name = "COLADDR")
    private String address;

    @OneToMany(targetEntity = MyCashCellImpl.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CASHCELL_ID")
    private List<MyCashCell> cashCellsWithValue;

    public MyATMImpl() {
        this.id = 0;
        this.name = "";
        this.address = "";
        this.cashCellsWithValue = new ArrayList<MyCashCell>();
    }

    public MyATMImpl(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cashCellsWithValue = new ArrayList<MyCashCell>();
    }

    private ArrayList<MyCashCell> getPossiblePairs(Currency currency) {
        ArrayList<MyCashCell> result = new ArrayList<MyCashCell>();

        for (CashNominal nominal : MyNominalList.getTypicalNominals(currency)
        ) {
            result.add(new MyCashCellImpl(0, currency, nominal, 0));
        }
        return result;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void addCurrency(Currency currency) {
        for (MyCashCell cell : getPossiblePairs(currency)
        ) {
            this.cashCellsWithValue.add(new MyCashCellImpl(0, cell.getCurrency(), cell.getNominal(), 0));
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

    private Set<Currency> getAvailCurrency() {
        Set<Currency> s = new HashSet();

        for (MyCashCell curCell : this.cashCellsWithValue) {
            s.add(curCell.getCurrency());
        }
        return s;
    }

    //получение баланса по всем валютам
    public HashMap<Currency, Integer> getBalance() {
        HashMap l = new HashMap<Currency, Integer>();
        for (Currency cur : this.getAvailCurrency()) {
            l.put(cur, 0);
        }

        for (MyCashCell curCell : this.cashCellsWithValue) {
            Integer i = (Integer) l.get(curCell.getCurrency());
            l.put(curCell.getCurrency(), i + curCell.getBalance());
        }

        return l;
    }

    public List<MyCashCell> getBalanceByCells() {
        return cashCellsWithValue;
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
        if (value <= 0)
            return null;

        ArrayList<MyCashCell> result = new ArrayList<MyCashCell>();
        ArrayList<MyCashCell> copyOfCashCellsWithValue = new ArrayList<MyCashCell>();
        for (MyCashCell curCell : cashCellsWithValue) {
            copyOfCashCellsWithValue.add(new MyCashCellImpl(0, curCell.getCurrency(), curCell.getNominal(), curCell.getCurrentValue()));
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
                        result.add(new MyCashCellImpl(0, currency, nominal, numberOfBanknotes));
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

        MyCashCell cell = new MyCashCellImpl(0, currency, nominal, 0);
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
            result = result.concat(cell.toString() + " ");
        }
        return result;
    }

    @Override
    public List<CashNominal> getAvailableNominals(Currency currency) {
        List<CashNominal> l = new ArrayList<CashNominal>();

        for (MyCashCell cell : this.cashCellsWithValue) {
            if (cell.getCurrency() == currency) {
                l.add(cell.getNominal());
            }
        }
        return l;
    }
}

