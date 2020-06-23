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

    public ATMBalancePageController(DBServiceATM dbServiceATM) {
        this.dbServiceATM = dbServiceATM;
    }

    @GetMapping({"/ATMBalance"})
    @ResponseBody
    public String ATMBalance(Model model) {
        MyATM myATM = dbServiceATM.getMyATM(dbServiceATM.getCurrentATMId())
                        .orElse(null);
        System.out.println(dbServiceATM.getCurrentATMId());
        System.out.println(myATM);
      System.out.println("FFFFF "+myATM.getBalance());
      //  model.addAttribute("card", card);
        return "FFFFFFFF";
    }
}
