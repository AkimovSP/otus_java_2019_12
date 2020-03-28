package ru.otus.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TUSER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COLNAME")
    private String name;

    @Column(name = "COLAGE")
    private int age;

    @OneToOne(targetEntity = AddressDataSet.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDR_ID")
    private AddressDataSet addr;

    @OneToMany(targetEntity = PhoneDataSet.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private List<PhoneDataSet> phones;

    public User() {
        this.id = 0;
        this.name = "";
        this.age = 0;
        phones = new ArrayList<PhoneDataSet>();
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        phones = new ArrayList<PhoneDataSet>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getAge() {
        return age;
    }

    public void setAddr(AddressDataSet addr) {
        this.addr = addr;
    }

    public void addPhone(PhoneDataSet phone) {
        this.phones.add(phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address=" + addr +
                ", phones=" + phones.toString()+
                '}';
    }
}