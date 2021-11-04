package com.example.demo.repositories;

import com.example.demo.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Set<Driver> findByRoleName(String role);
    Driver findByEmail(String email);
}
