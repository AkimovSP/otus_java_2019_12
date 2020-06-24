package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.core.model.Card;
import ru.otus.core.model.MyATM;
import ru.otus.core.service.DBServiceATM;
import ru.otus.core.service.DBServiceCard;

@Controller
public class CardBalancePageController {
    private final DBServiceCard dbServiceCard;

    public CardBalancePageController(DBServiceCard dbServiceCard) {
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/CardBalance"})
    @ResponseBody
    public String ATMBalance(Model model) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        if (card != null) {
            return card.getBalance() + " " + card.getCurrency();
        } else
            return "";
    }
}
