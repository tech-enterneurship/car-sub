package com.example.demo.controllers;

import com.example.demo.models.Driver;
import com.example.demo.models.User;
import com.example.demo.repositories.DriverRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.StationRepository;
import com.example.demo.repositories.UserRepository;
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

@Controller
@RequestMapping("/")
public class MainController {
    private final StationRepository stationRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(StationRepository stationRepository, RoleRepository roleRepository, UserRepository userRepository, DriverRepository driverRepository) {
        this.stationRepository = stationRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("stations", stationRepository.findAll());
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName());
            if (user != null && user.getRole().getName().equals("ROLE_DRIVER")) {
                model.addAttribute("driver", driverRepository.findByEmail(authentication.getName()));
            } else {
                model.addAttribute("driver", null);
            }
        } else {
            model.addAttribute("driver", null);
        }
        return "index";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("driver", new Driver());
        return "registration";
    }

    @PostMapping("register")
    public String register(Driver driver) {
        driver.setRole(roleRepository.findByName("ROLE_DRIVER"));
        driver.setPassword(passwordEncoder.encode(driver.getPassword()));
        driverRepository.save(driver);
        return "redirect:/";
    }
}