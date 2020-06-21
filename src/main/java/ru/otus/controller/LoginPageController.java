package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.core.service.DBServiceCard;
import ru.otus.services.UserAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Controller
public class LoginPageController {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final int MAX_INACTIVE_INTERVAL = 30;

    private final DBServiceCard dbServiceCard;
    private final UserAuthService userAuthService;

    public LoginPageController(DBServiceCard dbServiceCard, UserAuthService userAuthService) {
        this.dbServiceCard = dbServiceCard;
        this.userAuthService = userAuthService;
    }

    @GetMapping({"/login"})
    public String userListView(Model model) {
        System.out.println("LOGIN!!!");
        return "login.html";
    }

    @PostMapping({"/login"})
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (userAuthService.authenticate(name, password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);

            long id =
                    dbServiceCard.findByCardNumber(name)
                            .map(user -> user.getId())
                            .orElse((long) 0);

            dbServiceCard.setLoggedCardId(id);

            return "redirect:/";
        } else {
            System.out.println("NO!");
            dbServiceCard.setLoggedCardId(0);
            response.setStatus(SC_UNAUTHORIZED);
            return "redirect:/login";
        }
    }

}
