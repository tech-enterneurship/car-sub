package com.example.demo.controllers;

import com.example.demo.models.Station;
import com.example.demo.repositories.CarModelRepository;
import com.example.demo.repositories.ServiceRepository;
import com.example.demo.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/station")
public class StationController {
    private final StationRepository stationRepository;
    private final ServiceRepository serviceRepository;
    private final CarModelRepository carModelRepository;

    @Autowired
    public StationController(StationRepository stationRepository, ServiceRepository serviceRepository, CarModelRepository carModelRepository) {
        this.stationRepository = stationRepository;
        this.serviceRepository = serviceRepository;
        this.carModelRepository = carModelRepository;
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("station", stationRepository.findById(id).orElse(null));
        return "station/profile";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("station", new Station());
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("car_models", carModelRepository.findAll());
        return "station/add";
    }

    @PostMapping("/add")
    public String add(Station station) {
        stationRepository.save(station);
        return "redirect:/admin/profile";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Station station = stationRepository.findById(id).orElse(null);
        if (station == null) {
            return "redirect:/admin/profile";
        }
        model.addAttribute("station", station);
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("car_models", carModelRepository.findAll());
        return "station/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable ("id") Long id, Station station) {
        Station updated_station = stationRepository.findById(id).orElse(null);
        if (updated_station == null) {
            return "redirect:/admin/profile";
        }
        updated_station.setName(station.getName());
        updated_station.setLocation(station.getLocation());
        updated_station.setStation_services(station.getStation_services());
        updated_station.setStation_subscribers(station.getStation_subscribers());
        updated_station.setStation_car_models(station.getStation_car_models());
        stationRepository.save(updated_station);
        return "redirect:/admin/profile";
    }
}
