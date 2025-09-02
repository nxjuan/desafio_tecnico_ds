package com.example.desafio_tecnico_direct_solution.application.usecase;

import com.example.desafio_tecnico_direct_solution.domain.exception.DomainException;
import com.example.desafio_tecnico_direct_solution.domain.model.Transfer;
import com.example.desafio_tecnico_direct_solution.domain.policy.FeeTablePolicy;
import com.example.desafio_tecnico_direct_solution.domain.ports.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTransferUseCaseTest {

    static class InMemoryTransferRepository implements TransferRepository {
        final List<Transfer> store = new ArrayList<>();
        @Override public Transfer save(Transfer transfer) { store.add(transfer); return transfer; }
        @Override public List<Transfer> findAll() { return new ArrayList<>(store); }
    }

    private InMemoryTransferRepository repo;
    private ScheduleTransferUseCase useCase;

    @BeforeEach
    void setup() {
        repo = new InMemoryTransferRepository();
        useCase = new ScheduleTransferUseCase(repo, new FeeTablePolicy());
    }

    @Test
    void should_schedule_with_valid_data_and_calc_fee() {
        LocalDate d = LocalDate.now().plusDays(5);
        Transfer t = useCase.execute("1234567890", "9999999999", new BigDecimal("1000.00"), d);

        assertNotNull(t.getId());
        assertEquals(new BigDecimal("12.00"), t.getFee()); // 1–10 dias
        assertEquals(1, repo.store.size());
    }

    @Test
    void should_reject_same_accounts() {
        LocalDate d = LocalDate.now().plusDays(1);
        assertThrows(DomainException.class, () ->
                useCase.execute("1111111111", "1111111111", new BigDecimal("10.00"), d));
    }

    @Test
    void should_reject_non_positive_amount() {
        LocalDate d = LocalDate.now().plusDays(1);
        assertThrows(DomainException.class, () ->
                useCase.execute("1234567890", "9999999999", new BigDecimal("0.00"), d));
    }

    @Test
    void should_reject_past_date() {
        LocalDate d = LocalDate.now().minusDays(1);
        assertThrows(DomainException.class, () ->
                useCase.execute("1234567890", "9999999999", new BigDecimal("100.00"), d));
    }

    @Test
    void should_reject_over_50_days() {
        LocalDate d = LocalDate.now().plusDays(51);
        assertThrows(DomainException.class, () ->
                useCase.execute("1234567890", "9999999999", new BigDecimal("100.00"), d));
    }
}