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
public class ATMBalancePageController {
    private final DBServiceATM dbServiceATM;
    private final DBServiceCard dbServiceCard;

    public ATMBalancePageController(DBServiceATM dbServiceATM, DBServiceCard dbServiceCard) {
        this.dbServiceATM = dbServiceATM;
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/ATMBalance"})
    @ResponseBody
    public String ATMBalance(Model model) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        if (card != null) {
            if (card.isServiceMode()) {
                MyATM myATM = dbServiceATM.getMyATM(dbServiceATM.getCurrentATMId())
                        .orElse(null);
                System.out.println("FFFFF " + myATM.getBalance());
                return myATM.getBalance().toString();
            } else
            return "Нет доступа";
        }else
            return "";
    }
}
