package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "TADDR")
public class AddressDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addrId;

    @Column(name = "COLSTREET")
    private String street;

    public AddressDataSet() {
        this.addrId = 0;
        this.street = "";
    }

    public AddressDataSet(long addr_id, String street) {
        this.addrId = addr_id;
        this.street = street;
    }

    public String toString() {
        return "Addr{" +
                "addr_id=" + addrId +
                ", street='" + street +
                '}';
    }
}
