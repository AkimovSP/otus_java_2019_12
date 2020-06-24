package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.core.service.DBServiceATM;
import ru.otus.core.service.DBServiceCard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ExitPageController {
    private final DBServiceCard dbServiceCard;

    public ExitPageController(DBServiceATM dbServiceATM, DBServiceCard dbServiceCard) {
        this.dbServiceCard = dbServiceCard;
    }

    @GetMapping({"/exit"})
    public String Exit(HttpServletRequest request, HttpServletResponse
        response) throws IOException, ServletException {

            dbServiceCard.setLoggedCardId(0);
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/login.html";
    }
}
