package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.core.model.Card;
import ru.otus.core.model.CashNominal;
import ru.otus.core.model.MyATMImpl;
import ru.otus.core.service.DBServiceATM;
import ru.otus.core.service.DBServiceCard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
public class UploadCashPageController {
    private final DBServiceCard dbServiceCard;
    private final DBServiceATM dbServiceATM;

    private String operResult = "";

    public UploadCashPageController(DBServiceCard dbServiceCard, DBServiceATM dbServiceATM) {
        this.dbServiceCard = dbServiceCard;
        this.dbServiceATM = dbServiceATM;
    }

    @GetMapping({"/uploadCash"})
    public String UploadCash(Model model, HttpServletRequest request, HttpServletResponse response) {
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        MyATMImpl myATM = dbServiceATM.getMyATM(dbServiceATM.getCurrentATMId())
                .orElse(null);

        model.addAttribute("card", card);
        model.addAttribute("operResult", operResult);
        model.addAttribute("cashNominals", myATM.getAvailableNominals(card.getCurrency()));

        return "uploadCash.html";
    }

    @PostMapping(value = {"/uploadCash/run"}, consumes = "application/json")
    protected String UploadRun(
            @RequestBody ArrayList<UploadRequestBody> reqBody) {
        int val = 0;
        int nominal = 0;
        CashNominal cashNominal;

        operResult = "";

        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                .orElse(null);
        MyATMImpl myATM = dbServiceATM.getMyATM(dbServiceATM.getCurrentATMId())
                .orElse(null);

        if (card != null && myATM != null) {
            for (UploadRequestBody reqBodyElem : reqBody) {
                try {
                    val = Integer.parseInt(reqBodyElem.amount);
                    if (val > 0) {
                        cashNominal = CashNominal.valueOf(reqBodyElem.nominal);
                        myATM.uploadCash(card.getCurrency(), cashNominal, val);
                        card.setBalance(card.getBalance() + cashNominal.nominal * val);
                        operResult += "  Баланс увеличен, новый баланс = " + card.getBalance();
                    }
                } catch (Exception e) {
                    operResult += "  Ошибка выполнения операции - некорректное значение";
                }
            }
            dbServiceCard.saveCard(card);
            dbServiceATM.saveMyATM(myATM);
        } else {
            operResult = "Ошибка выполнения операции - ошибка окружения";
        }
        return "";
    }

    @GetMapping({"/uploadCash/exit"})
    public String UploadTransferExit(Model model) {
        operResult = "";
        return "redirect:/";
    }

    private class UploadRequestBody {
        public String amount;
        public String nominal;
    }
}
