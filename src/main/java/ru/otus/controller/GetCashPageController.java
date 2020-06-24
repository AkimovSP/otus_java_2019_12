package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.core.model.Card;
import ru.otus.core.model.MyATM;
import ru.otus.core.model.MyATMImpl;
import ru.otus.core.model.MyCashCell;
import ru.otus.core.service.DBServiceATM;
import ru.otus.core.service.DBServiceCard;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GetCashPageController {
    private final DBServiceATM dbServiceATM;
    private final DBServiceCard dbServiceCard;

    private static final String PARAM_AMOUNT = "amount";

    private String operResult = "";
    private String operResultDetails = "";

    public GetCashPageController(DBServiceATM dbServiceATM, DBServiceCard dbServiceCard) {
        this.dbServiceATM = dbServiceATM;
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/getCash"})
    public String GetCash(Model model, HttpServletRequest request, HttpServletResponse response) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);

        model.addAttribute("card", card);
        model.addAttribute("operResult", operResult);
        model.addAttribute("operResultDetails", operResultDetails);

        return "getCash.html";
    }

    @PostMapping({"/getCash/run"})
    protected String GetCashRun(
            @RequestBody String amount,
            HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        MyATMImpl myATM = dbServiceATM.getMyATM(dbServiceATM.getCurrentATMId())
                .orElse(null);

        operResult = "";
        operResultDetails = "";

        try {
            System.out.println("AMOUNT = " + amount);
            int val = Integer.parseInt(amount);
            System.out.println("VAL=" + val);
            if (card != null && myATM != null) {
                if (card.getBalance() < val) {
                    System.out.println("Недостаточно средств на карте");
                    operResult = "Недостаточно средств на карте";
                } else {
                    System.out.println("GET CASH " + card);
                    List<MyCashCell> l = new ArrayList<MyCashCell>();
                    l = myATM.downloadCash(card.getCurrency(), val);
                    if (l != null) {
                        card.setBalance(card.getBalance() - val);
                        dbServiceCard.saveCard(card);
                        dbServiceATM.saveMyATM(myATM);
                        System.out.println("Выдача проведена успешно " + val + " " + card.getCurrency());
                        operResult = "Выдача проведена успешно " + val + " " + card.getCurrency();
                        operResultDetails = "Купюры " + l.toString();
                    } else {
                        System.out.println("Невозможно выдать запрошенную сумму");
                        operResult = "Невозможно выдать запрошенную сумму";
                    }
                }
            } else {
                System.out.println("Ошибка выполнения операции - ошибка окружения");
                operResult = "Ошибка выполнения операции - ошибка окружения";
            }
        } catch (Exception e) {
            System.out.println("Ошибка выполнения операции - некорректное значение");
            operResult = "Ошибка выполнения операции - некорректное значение";
        }
        return "";
    }

    @GetMapping({"/getCash/exit"})
    public String GetCashExit(Model model) {
        operResult = "";
        operResultDetails = "";

        return "redirect:/";
    }
}
