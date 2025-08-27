package com.example.desafio_tecnico_direct_solution.infra.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface SpringDataTransferRepository extends JpaRepository<JpaTransferEntity, UUID> {
}
