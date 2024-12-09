package com.neoflex.deal.repositories;

import com.neoflex.deal.model.entities.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassportRepository extends JpaRepository<Passport, UUID> {
}
