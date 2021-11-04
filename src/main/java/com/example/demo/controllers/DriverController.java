package com.example.demo.controllers;

import com.example.demo.models.Driver;
import com.example.demo.models.Station;
import com.example.demo.models.User;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/driver")
public class DriverController {
    private final DriverRepository driverRepository;
    private final CarModelRepository carModelRepository;
    private final StationRepository stationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DriverController(DriverRepository driverRepository, CarModelRepository carModelRepository, StationRepository stationRepository) {
        this.driverRepository = driverRepository;
        this.carModelRepository = carModelRepository;
        this.stationRepository = stationRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @GetMapping("/profile")
    public String driver(Model model, Authentication authentication) {
        Driver driver = driverRepository.findByEmail(authentication.getName());
        Station my_station = null;
        for (Station station : stationRepository.findAll()) {
            if (station.getStation_subscribers().contains(driver)) {
                my_station = station;
            }
        }
        model.addAttribute("my_station", my_station);
        model.addAttribute("my_car", driver.getCar());
        model.addAttribute("appointment_day", driver.getDay());
        model.addAttribute("car_models", carModelRepository.findAll());
        return "driver/profile";
    }

    @GetMapping("/subscribe/{id}")
    public String subscribe(@PathVariable ("id") Long id, Authentication authentication) {
        Station station = stationRepository.findById(id).orElse(null);
        if (station == null) {
            return "redirect:/";
        }
        Driver driver = driverRepository.findByEmail(authentication.getName());
        Set<User> users = station.getStation_subscribers();
        users.add(driver);
        station.setStation_subscribers(users);
        stationRepository.save(station);
        return "redirect:/driver/profile";
    }

    @PostMapping("/appointment")
    public String appointment(@RequestParam ("day") Integer day, Authentication authentication) {
        Driver driver = driverRepository.findByEmail(authentication.getName());
        driver.setDay(day);
        driverRepository.save(driver);
        return "redirect:/driver/profile";
    }

    @GetMapping("/unsubscribe/{id}")
    public String unsubscribe(@PathVariable ("id") Long id, Authentication authentication) {
        Station station = stationRepository.findById(id).orElse(null);
        if (station == null) {
            return "redirect:/";
        }
        Driver driver = driverRepository.findByEmail(authentication.getName());
        Set<User> users = station.getStation_subscribers();
        users.remove(driver);
        station.setStation_subscribers(users);
        driver.setDay(null);
        driverRepository.save(driver);
        stationRepository.save(station);
        return "redirect:/driver/profile";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("driver", driverRepository.findById(id).orElse(null));
        return "driver/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, Driver driver) {
        Driver updated_driver = driverRepository.findById(id).orElse(null);
        if (updated_driver == null) {
            return "redirect:/admin/profile";
        }
        updated_driver.setEmail(driver.getEmail());
        updated_driver.setPassword(passwordEncoder.encode(driver.getPassword()));
        driverRepository.save(updated_driver);
        return "redirect:/admin/profile";
    }
}
