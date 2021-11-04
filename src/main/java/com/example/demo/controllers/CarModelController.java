package com.example.demo.controllers;

import com.example.demo.models.CarModel;
import com.example.demo.repositories.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/model")
public class CarModelController {
    private final CarModelRepository carModelRepository;

    @Autowired
    public CarModelController(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("car_model", new CarModel());
        return "car_model/add";
    }

    @PostMapping("/add")
    public String add(CarModel carModel) {
        carModelRepository.save(carModel);
        return "redirect:/admin/profile";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable ("id") Long id, Model model) {
        CarModel car_model = carModelRepository.findById(id).orElse(null);
        if (car_model == null) {
            return "redirect:/admin/profile";
        }
        model.addAttribute("car_model", car_model);
        return "car_model/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable ("id") Long id, CarModel carModel) {
        CarModel car_model = carModelRepository.findById(id).orElse(null);
        if (car_model == null) {
            return "redirect:/admin/profile";
        }
        car_model.setName(car_model.getName());
        carModelRepository.save(car_model);
        return "redirect:/admin/profile";
    }
}
