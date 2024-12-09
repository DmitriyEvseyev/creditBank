package com.neoflex.deal.repositories;

import com.neoflex.deal.model.entities.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmploymentRepository extends JpaRepository<Employment, UUID> {
}
