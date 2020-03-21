package ru.otus.core.model;

public class Account {
    @Id
    private long no;
    private String type;
    private float rest;

    public Account(long no, String type, float rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public float getRest() {
        return rest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRest(float rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest='" + rest + '\'' +
                '}';
    }
}
