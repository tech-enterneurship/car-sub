package com.example.demo.models;

import javax.persistence.*;

@Entity(name = "DriverEntity")
@Table(name = "drivers")
public class Driver extends User {
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "appointment_day")
    private Integer day;

    public Driver(User user, Car car, Integer day) {
        super(user);
        this.car = car;
        this.day = day;
    }

    public Driver(Car car, Integer day) {
        this.car = car;
        this.day = day;
    }

    public Driver() {

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
