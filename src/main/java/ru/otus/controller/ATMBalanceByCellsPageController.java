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
public class ATMBalanceByCellsPageController {
    private final DBServiceATM dbServiceATM;
    private final DBServiceCard dbServiceCard;

    public ATMBalanceByCellsPageController(DBServiceATM dbServiceATM, DBServiceCard dbServiceCard) {
        this.dbServiceATM = dbServiceATM;
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/ATMBalanceByCells"})
    @ResponseBody
    public String ATMBalance(Model model) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        if (card != null) {
            if (card.isServiceMode()) {
                MyATM myATM = dbServiceATM.getMyATM(dbServiceATM.getCurrentATMId())
                        .orElse(null);
                return myATM.getBalanceByCells().toString();
            } else
            return "not authorised";
        }else
            return "";
    }
}
