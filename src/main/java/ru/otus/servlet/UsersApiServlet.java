package ru.otus.servlet;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersApiServlet {
    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser,
                           Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @GetMapping({"/", "/user/list"})
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getAllUsers();
        model.addAttribute("users", users);
        return "users.html";
    }

    @GetMapping(value = {"/users/{id}"}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String createResponseGetUser(@PathVariable("id") long id) {
        User user = dbServiceUser.getUser(id).orElse(null);
        return gson.toJson(user);
    }

    @PostMapping(value = {"/users"})
    @ResponseBody
    protected void createUser(@RequestParam(name = "name") String name,
                              @RequestParam(name = "login") String login,
                              @RequestParam(name = "password") String password) {
        try {
            User user1 = new User(0, name, login, password);
            var id = dbServiceUser.saveUser(user1);
            Optional<User> user2 = dbServiceUser.getUser(id);

            user2.orElseThrow(() -> new RuntimeException("User creation failed"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
