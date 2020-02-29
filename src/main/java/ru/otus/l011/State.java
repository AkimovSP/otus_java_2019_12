package ru.otus.l011;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final List<MyATM> array;

    State(List<MyATM> arr) {
        this.array = arr;
    }

    State(State state) {
        this.array = new ArrayList<MyATM>();

        for (MyATM atm:state.getArray()) {
            this.array.add(new MyATMImpl(atm));
        }
    }

    List<MyATM> getArray() {
        return array;
    }
}
