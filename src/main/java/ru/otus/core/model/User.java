package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "TUSER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COLNAME")
    private String name;

    @Column(name = "COLLOGIN")
    private String login;

    @Column(name = "COLPASSWORD")
    private String password;

    public User() {
        this.id = 0;
        this.name = "";
        this.login = "";
        this.password = "";
    }

    public User(long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        System.out.println(login + " " + password);
        return password;
    }
}
