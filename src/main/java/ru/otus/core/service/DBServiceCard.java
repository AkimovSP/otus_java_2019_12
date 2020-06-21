package ru.otus.core.service;

import ru.otus.core.model.Card;
import java.util.Optional;

public interface DBServiceCard {
  long saveCard(Card card);

  Optional<Card> findByCardNumber(String cardNumber);

  Optional<Card> getCard(long id);

  void setLoggedCardId(long cardId);

  long getLoggedCardId();
}
