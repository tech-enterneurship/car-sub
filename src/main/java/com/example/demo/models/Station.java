package com.example.demo.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "StationEntity")
@Table(name = "stations")
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;
    @Column(name = "station_name")
    private String name;
    @Column(name = "station_location")
    private String location;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "station_services",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> station_services;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "station_car_models",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModel> station_car_models;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "station_subscribers",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> station_subscribers;

    public Station() {
    }

    public Station(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.station_services = new HashSet<>();
        this.station_car_models = new HashSet<>();
        this.station_subscribers = new HashSet<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String author) {
        this.location = author;
    }

    public Set<Service> getStation_services() {
        return station_services;
    }

    public void setStation_services(Set<Service> station_services) {
        this.station_services = station_services;
    }

    public void addServices(Service service) {
        station_services.add(service);
    }

    public void removeServices(Service service) {
        station_services.remove(service);
    }

    public Set<CarModel> getStation_car_models() {
        return station_car_models;
    }

    public void setStation_car_models(Set<CarModel> station_car_models) {
        this.station_car_models = station_car_models;
    }

    public Set<User> getStation_subscribers() {
        return station_subscribers;
    }

    public void setStation_subscribers(Set<User> station_subscribers) {
        this.station_subscribers = station_subscribers;
    }
}
