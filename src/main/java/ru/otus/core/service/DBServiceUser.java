package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.Optional;

public interface DBServiceUser {
  //=Create
  long saveUser(User user);

  //=load
  Optional<User> getUser(long id);

  //=update
  void updateUser(User user);
}
