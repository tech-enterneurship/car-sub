package com.example.demo.controllers;

import com.example.demo.models.Car;
import com.example.demo.models.Driver;
import com.example.demo.repositories.CarModelRepository;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public CarController(CarRepository carRepository, CarModelRepository carModelRepository, DriverRepository driverRepository) {
        this.carRepository = carRepository;
        this.carModelRepository = carModelRepository;
        this.driverRepository = driverRepository;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("car_models", carModelRepository.findAll());
        model.addAttribute("car", new Car());
        return "car/add";
    }

    @PostMapping("/add")
    public String add(Car car, Authentication authentication) {
        Driver driver = driverRepository.findByEmail(authentication.getName());
        carRepository.save(car);
        driver.setCar(car);
        driverRepository.save(driver);
        return "redirect:/driver/profile";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return "redirect:/driver/profile";
        }
        model.addAttribute("car", car);
        model.addAttribute("car_models", carModelRepository.findAll());
        return "car/update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable ("id") Long id, Car car) {
        Car updated_car = carRepository.findById(id).orElse(null);
        if (updated_car == null) {
            return "redirect:/driver/profile";
        }
        updated_car.setNumber(car.getNumber());
        updated_car.setCarModel(car.getCarModel());
        updated_car.setYear(car.getYear());
        carRepository.save(updated_car);
        return "redirect:/driver/profile";
    }
}
