package ru.otus.l011;

public class StateProvider {
    private static final State technicalState = new TechnicalState ();
    private static final State workingState = new WorkingState();

    public static State getTechnicalState() {
        return technicalState;
    }

    public static State getWorkingState() {
        return workingState;
    }
}
