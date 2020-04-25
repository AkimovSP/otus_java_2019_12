package ru.otus.servlet;

import com.google.gson.Gson;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class AddUserServlet extends HttpServlet {

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;
    private final Gson gson;

    public AddUserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser, Gson gson) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            User user1 = new User(0, request.getParameter("name"),
                    request.getParameter("login"),
                    request.getParameter("password"));
            var id = dbServiceUser.saveUser(user1);
            Optional<User> user2 = dbServiceUser.getUser(id);

            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print(gson.toJson(user2.get()));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
