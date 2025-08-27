package com.example.desafio_tecnico_direct_solution.infra.persistence;

import com.example.desafio_tecnico_direct_solution.domain.model.Transfer;
import com.example.desafio_tecnico_direct_solution.domain.ports.TransferRepository;
import com.example.desafio_tecnico_direct_solution.infra.persistence.jpa.JpaTransferEntity;
import com.example.desafio_tecnico_direct_solution.infra.persistence.jpa.SpringDataTransferRepository;
import com.example.desafio_tecnico_direct_solution.infra.persistence.mapper.TransferMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.desafio_tecnico_direct_solution.infra.persistence.mapper.TransferMapper.*;

@Repository
public class TransferRepositoryAdapter implements TransferRepository{
    private final SpringDataTransferRepository jpa;

    public TransferRepositoryAdapter(SpringDataTransferRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Transfer save(Transfer transfer) {
        JpaTransferEntity saved = jpa.save(toJpa(transfer));
        return toDomain(saved);
    }

    @Override
    public List<Transfer> findAll() {
        return jpa.findAll().stream().map(TransferMapper::toDomain).collect(Collectors.toList());
    }
}
