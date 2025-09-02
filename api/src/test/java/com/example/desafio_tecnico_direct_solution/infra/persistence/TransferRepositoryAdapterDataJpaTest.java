package com.example.desafio_tecnico_direct_solution.infra.persistence;

import com.example.desafio_tecnico_direct_solution.domain.model.*;
import com.example.desafio_tecnico_direct_solution.domain.ports.TransferRepository;
import com.example.desafio_tecnico_direct_solution.infra.persistence.jpa.SpringDataTransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TransferRepositoryAdapter.class) // importa o adapter para o slice JPA
class TransferRepositoryAdapterDataJpaTest {

    @Autowired SpringDataTransferRepository springRepo; // só para garantir que o contexto JPA subiu
    @Autowired TransferRepository adapter;

    @Test
    void should_persist_and_retrieve_transfer() {
        Transfer t = new Transfer(
                UUID.randomUUID(),
                new AccountNumber("1234567890"),
                new AccountNumber("9999999999"),
                new BigDecimal("250.00"),
                new BigDecimal("12.00"),
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                TransferStatus.SCHEDULED
        );

        Transfer saved = adapter.save(t);
        assertNotNull(saved.getId());

        List<Transfer> all = adapter.findAll();
        assertEquals(1, all.size());
        Transfer got = all.get(0);
        assertEquals(new BigDecimal("250.00"), got.getAmount());
        assertEquals(new BigDecimal("12.00"), got.getFee());
        assertEquals("1234567890", got.getSource().value());
        assertEquals("9999999999", got.getDestination().value());
        assertEquals(TransferStatus.SCHEDULED, got.getStatus());
    }
}