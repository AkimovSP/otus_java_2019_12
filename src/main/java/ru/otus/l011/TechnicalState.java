package ru.otus.l011;

/**
 * @author sergey
 * created on 12.09.18.
 */
public class TechnicalState implements State {
  @Override
  public State action() {
    return StateProvider.getWorkingState();
  }

  @Override
  public String toString() {
    return "Technical state";
  }
}
