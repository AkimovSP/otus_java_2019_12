package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.core.model.Card;
import ru.otus.core.service.DBServiceCard;

@Controller
public class MainMenuPageController {
    private final DBServiceCard dbServiceCard;

    public MainMenuPageController(DBServiceCard dbServiceCard) {
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/"})
    public String userListView(Model model) {
//        List<User> users = dbServiceUser.getAllUsers();
        Card card = dbServiceCard.getCard(dbServiceCard.getLoggedCardId())
                        .orElse(null);
        System.out.println(card);
        model.addAttribute("card", card);
        return "mainmenu.html";
    }
}
