package com.example.demo.controllers;

import com.example.demo.models.Service;
import com.example.demo.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service")
public class ServiceController {
        private final ServiceRepository serviceRepository;

        @Autowired
        public ServiceController(ServiceRepository serviceRepository) {
                this.serviceRepository = serviceRepository;
        }


        @GetMapping("/add")
        public String add(Model model) {
                model.addAttribute("service", new Service());
                return "service/add";
        }

        @PostMapping("/add")
        public String add(Service service) {
                serviceRepository.save(service);
                return "redirect:/admin/profile";
        }
}
