package ru.otus.core.service;

import ru.otus.core.model.Card;
import ru.otus.core.model.MyATM;

import java.util.Optional;

public interface DBServiceATM {
  long saveMyATM(MyATM myATM);

  Optional<MyATM> findByATMName(String ATMName);

  Optional<MyATM> getMyATM(long id);

  void setCurrentATMId(long id);

  long getCurrentATMId();
}
