package com.example.desafio_tecnico_direct_solution.infra.persistence.mapper;

import com.example.desafio_tecnico_direct_solution.domain.model.*;
import com.example.desafio_tecnico_direct_solution.infra.persistence.jpa.JpaTransferEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransferMapperTest {

    @Test
    void should_map_domain_to_jpa_and_back() {
        Transfer domain = new Transfer(
                UUID.randomUUID(),
                new AccountNumber("1234567890"),
                new AccountNumber("9999999999"),
                new BigDecimal("100.00"),
                new BigDecimal("12.34"),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                TransferStatus.SCHEDULED
        );

        JpaTransferEntity jpa = TransferMapper.toJpa(domain);
        assertEquals(domain.getId(), jpa.getId());
        assertEquals("1234567890", jpa.getSourceAccount());
        assertEquals("9999999999", jpa.getDestinationAccount());
        assertEquals(new BigDecimal("100.00"), jpa.getAmount());
        assertEquals(new BigDecimal("12.34"), jpa.getFee());
        assertEquals(domain.getTransferDate(), jpa.getTransferDate());
        assertEquals("SCHEDULED", jpa.getStatus());

        Transfer back = TransferMapper.toDomain(jpa);
        assertEquals(domain.getId(), back.getId());
        assertEquals(domain.getSource().value(), back.getSource().value());
        assertEquals(domain.getDestination().value(), back.getDestination().value());
        assertEquals(domain.getAmount(), back.getAmount());
        assertEquals(domain.getFee(), back.getFee());
        assertEquals(domain.getTransferDate(), back.getTransferDate());
        assertEquals(domain.getStatus(), back.getStatus());
    }
}