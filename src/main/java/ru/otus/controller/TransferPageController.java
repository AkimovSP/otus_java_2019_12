package ru.otus.controller;

import com.google.gson.Gson;
import org.h2.util.json.JSONString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.core.model.Card;
import ru.otus.core.model.MyATMImpl;
import ru.otus.core.model.MyCashCell;
import ru.otus.core.service.DBServiceATM;
import ru.otus.core.service.DBServiceCard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransferPageController {
    private final DBServiceCard dbServiceCard;

    private static final String PARAM_AMOUNT = "amount";

    private String operResult = "";

    public TransferPageController(DBServiceCard dbServiceCard) {
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/transfer"})
    public String GetTransfer(Model model, HttpServletRequest request, HttpServletResponse response) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);

        model.addAttribute("card", card);
        model.addAttribute("operResult", operResult);

        return "transfer.html";
    }

    @PostMapping(value = {"/transfer/run"}, consumes = "application/json")
    protected String TransferRun(
            @RequestBody TransferRequestBody reqBody) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        operResult = "";

        try {
            float val = Float.parseFloat(reqBody.amount);

            if (card != null) {
                if (card.getBalance() < val) {
                    operResult = "Недостаточно средств на карте";
                } else {
                    System.out.println("TRANSFER  " + card);
                    card.setBalance(card.getBalance() - val);
                    dbServiceCard.saveCard(card);
                    operResult = "Перевод проведен успешно " + val + " " + card.getCurrency() + " отправлено " + reqBody.target;
                }
            } else {
                operResult = "Ошибка выполнения операции - ошибка окружения";
            }
        } catch (Exception e) {
            operResult = "Ошибка выполнения операции - некорректное значение";
        }
        return "";
    }

    @GetMapping({"/transfer/exit"})
    public String GetTransferExit(Model model) {
        operResult = "";

        return "redirect:/";
    }

    private class TransferRequestBody {
        public String amount;
        public String target;
    }
}
