package ru.otus.core.service;

import ru.otus.core.model.Account;
import java.util.Optional;

public interface DBServiceAccount {
    //=Create
    long saveAccount(Account account);

    //=load
    Optional<Account> getAccount(long id);

    //=update
    void updateAccount(Account account);
}
