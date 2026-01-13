package com.example.first.repository;
import com.example.first.entity.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByActiveTrue();
}
