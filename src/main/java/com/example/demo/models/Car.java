package com.example.demo.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "CarEntity")
@Table(name = "cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;
    @Column(name = "car_number", unique = true)
    private String number;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id")
    private CarModel carModel;
    @Column(name = "car_year")
    private Integer year;

    public Car(Long id, String number, CarModel carModel, Integer year) {
        this.id = id;
        this.number = number;
        this.carModel = carModel;
        this.year = year;
    }

    public Car() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
