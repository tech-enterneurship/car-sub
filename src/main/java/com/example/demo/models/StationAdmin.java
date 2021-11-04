package com.example.demo.models;

import javax.persistence.*;

@Entity(name = "StationAdminEntity")
@Table(name = "station_admins")
public class StationAdmin extends User {
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    public StationAdmin(User user, Station station) {
        super(user);
        this.station = station;
    }

    public StationAdmin(Station station) {
        this.station = station;
    }

    public StationAdmin() {

    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
