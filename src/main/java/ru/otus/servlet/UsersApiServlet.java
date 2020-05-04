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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class UsersApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    private static final String TEMPLATE_ATTR_USERS = "users";
    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, TemplateProcessor templateProcessor, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = extractIdFromRequest(request);
        if (id == -1) {
            createResponseUserList(response);
        } else {
            createResponseGetUser(response, id);
        }
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return -1;
        } else {
            String[] path = pathInfo.split("/");
            String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
            System.out.println(id);
            return Long.parseLong(id);
        }
    }

    private void createResponseUserList(HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_USERS, dbServiceUser.getAllUsers());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    private void createResponseGetUser(HttpServletResponse response, long id) throws IOException {
        User user = dbServiceUser.getUser(id).orElse(null);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user1 = new User(0, request.getParameter("name"),
                    request.getParameter("login"),
                    request.getParameter("password"));
            var id = dbServiceUser.saveUser(user1);
            Optional<User> user2 = dbServiceUser.getUser(id);

            user2.orElseThrow(() -> new RuntimeException("User creation failed"));

            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print(gson.toJson(user2.get()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
