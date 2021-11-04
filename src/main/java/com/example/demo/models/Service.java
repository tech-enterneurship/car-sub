package com.example.demo.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ServiceEntity")
@Table(name = "services")
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;
    @Column(name = "service_name")
    private String name;
    @Column(name = "service_price")
    private int price;

    public Service(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Service() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
