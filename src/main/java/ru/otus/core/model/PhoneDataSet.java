package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "TPHONE")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long phone_id;

    @Column(name = "COLNUMBER")
    private String number;

    public PhoneDataSet() {
        this.phone_id = 0;
        this.number = "";
    }

    public PhoneDataSet(long phone_id, String number) {
        this.phone_id = phone_id;
        this.number = number;
    }

    public String toString() {
        return "Phone{" +
                "phone_id=" + phone_id +
                ", number='" + number +
                '}';
    }
}
