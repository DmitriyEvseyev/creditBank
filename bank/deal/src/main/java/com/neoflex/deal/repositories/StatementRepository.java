package com.neoflex.deal.repositories;

import com.neoflex.deal.model.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatementRepository extends JpaRepository<Statement, UUID> {


}
