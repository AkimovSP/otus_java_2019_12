package ru.otus.l011;

import java.util.ArrayDeque;
import java.util.Deque;

class Originator {
    //stack
    private final Deque<Memento> stack = new ArrayDeque<>();

    void saveState(State state) {
        stack.push(new Memento(state));
    }

    State restoreState() {
        return stack.pop().getState();
    }

    public State restoreInitialState() {
        State res = null;
        while (!stack.isEmpty()) {
            res = restoreState();
        }
        ;
        return res;
    }
}
