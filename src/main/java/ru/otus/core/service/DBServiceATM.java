package ru.otus.core.service;

import ru.otus.core.model.MyATMImpl;

import java.util.Optional;

public interface DBServiceATM {
  long saveMyATM(MyATMImpl myATM);

  Optional<MyATMImpl> findByATMName(String ATMName);

  Optional<MyATMImpl> getMyATM(long id);

  void setCurrentATMId(long id);

  long getCurrentATMId();
}
