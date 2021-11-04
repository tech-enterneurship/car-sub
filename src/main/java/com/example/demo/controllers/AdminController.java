package com.example.demo.controllers;

import com.example.demo.repositories.CarModelRepository;
import com.example.demo.repositories.StationAdminRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;
    private final StationAdminRepository stationAdminRepository;

    @Autowired
    public AdminController(UserRepository userRepository, CarModelRepository carModelRepository, StationAdminRepository stationAdminRepository) {
        this.userRepository = userRepository;
        this.carModelRepository = carModelRepository;
        this.stationAdminRepository = stationAdminRepository;
    }


    @GetMapping("/profile")
    public String admin(Model model) {
        model.addAttribute("drivers", userRepository.findUsersByRoleName("ROLE_DRIVER"));
        model.addAttribute("station_admins", stationAdminRepository.findByRoleName("ROLE_STATION"));
        model.addAttribute("car_models", carModelRepository.findAll());
        return "admin/profile";
    }
}
