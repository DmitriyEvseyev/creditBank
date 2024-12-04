package com.neoflex.deal.repositories;

import com.neoflex.deal.model.entities.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PassportRepository extends JpaRepository<Passport, UUID> {

}
