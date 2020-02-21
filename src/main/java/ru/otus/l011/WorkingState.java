package ru.otus.l011;

/**
 * @author sergey
 * created on 12.09.18.
 */
public class WorkingState implements State {
  @Override
  public State action() {
    return StateProvider.getTechnicalState();
  }

  @Override
  public String toString() {
    return "Working state";
  }
}
