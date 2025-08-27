package com.example.desafio_tecnico_direct_solution.application.usecase;

import com.example.desafio_tecnico_direct_solution.domain.exception.DomainException;
import com.example.desafio_tecnico_direct_solution.domain.model.AccountNumber;
import com.example.desafio_tecnico_direct_solution.domain.model.Transfer;
import com.example.desafio_tecnico_direct_solution.domain.model.TransferStatus;
import com.example.desafio_tecnico_direct_solution.domain.policy.FeePolicy;
import com.example.desafio_tecnico_direct_solution.domain.ports.TransferRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
public class ScheduleTransferUseCase {
    private final TransferRepository repository;
    private final FeePolicy feePolicy;

    public ScheduleTransferUseCase(TransferRepository repository, FeePolicy feePolicy) {
        this.repository = repository;
        this.feePolicy = feePolicy;
    }

    public Transfer execute(String source, String destination, BigDecimal amount, LocalDate transferDate) {
        if (source.equals(destination)) {
            throw new DomainException("Conta de origem e destino não podem ser iguais.");
        }
        if (amount == null || amount.compareTo(new BigDecimal("0.00")) <= 0) {
            throw new DomainException("Valor deve ser maior que zero.");
        }

        LocalDate scheduled = LocalDate.now();
        if (transferDate.isBefore(scheduled)) {
            throw new DomainException("Data de transferência não pode ser no passado.");
        }

        long days = ChronoUnit.DAYS.between(scheduled, transferDate);
        if (days > 50) {
            throw new DomainException("Não há taxa aplicável acima de 50 dias.");
        }

        BigDecimal fee;
        try {
            fee = feePolicy.calculate(amount, days);
        } catch (IllegalArgumentException e) {
            throw new DomainException(e.getMessage());
        }

        Transfer aggregate = new Transfer(
                UUID.randomUUID(),
                new AccountNumber(source),
                new AccountNumber(destination),
                amount,
                fee,
                scheduled,
                transferDate,
                TransferStatus.SCHEDULED
        );

        return repository.save(aggregate);
    }
}
