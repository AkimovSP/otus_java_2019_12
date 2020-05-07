package ru.otus.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import java.util.Optional;

@RestController
public class UsersRestController {
    private final DBServiceUser dbServiceUser;

    public UsersRestController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping(value = {"/users/{id}"}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public User createResponseGetUser(@PathVariable("id") long id) {
        User user = dbServiceUser.getUser(id).orElse(null);
        return user;
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
