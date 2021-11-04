package com.example.demo.repositories;

import com.example.demo.models.StationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StationAdminRepository extends JpaRepository<StationAdmin, Long> {
    Set<StationAdmin> findByRoleName(String role);
    StationAdmin findByEmail(String email);
}
