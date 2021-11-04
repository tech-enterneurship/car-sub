package com.example.demo.controllers;

import com.example.demo.models.Driver;
import com.example.demo.models.Station;
import com.example.demo.models.StationAdmin;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.StationAdminRepository;
import com.example.demo.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/station_admin")
public class StationAdminController {
    private final StationAdminRepository stationAdminRepository;
    private final StationRepository stationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StationAdminController(StationAdminRepository stationAdminRepository, StationRepository stationRepository, RoleRepository roleRepository) {
        this.stationAdminRepository = stationAdminRepository;
        this.stationRepository = stationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        StationAdmin stationAdmin = stationAdminRepository.findByEmail(authentication.getName());
        List<Driver> subscribers = new ArrayList<>();
        for (User user : stationAdmin.getStation().getStation_subscribers()) {
            subscribers.add((Driver) user);
        }
        model.addAttribute("station_admin", stationAdmin);
        model.addAttribute("subscribers", subscribers);
        return "station_admin/profile";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Station> all_stations = stationRepository.findAll();
        List<Station> current_stations = new ArrayList<>();
        for (StationAdmin station_admin : stationAdminRepository.findByRoleName("ROLE_STATION")) {
            current_stations.add(station_admin.getStation());
        }
        List<Station> free_stations = new ArrayList<>();
        for (Station station : all_stations) {
            if (!current_stations.contains(station)) {
                free_stations.add(station);
            }
        }
        model.addAttribute("station_admin", new StationAdmin());
        model.addAttribute("stations", free_stations);
        return "station_admin/add";
    }

    @PostMapping("/add")
    public String add(StationAdmin stationAdmin) {
        stationAdmin.setRole(roleRepository.findByName("ROLE_STATION"));
        stationAdmin.setPassword(passwordEncoder.encode(stationAdmin.getPassword()));
        stationAdminRepository.save(stationAdmin);
        return "redirect:/admin/profile";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("station_admin", stationAdminRepository.findById(id).orElse(null));
        return "station_admin/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, StationAdmin stationAdmin) {
        StationAdmin updated_station_admin = stationAdminRepository.findById(id).orElse(null);
        if (updated_station_admin == null) {
            return "redirect:/admin/profile";
        }
        updated_station_admin.setEmail(stationAdmin.getEmail());
        updated_station_admin.setPassword(passwordEncoder.encode(stationAdmin.getPassword()));
        stationAdminRepository.save(updated_station_admin);
        return "redirect:/admin/profile";
    }
}
