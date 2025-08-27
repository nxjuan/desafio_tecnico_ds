package com.example.desafio_tecnico_direct_solution.domain.ports;

import com.example.desafio_tecnico_direct_solution.domain.model.Transfer;

import java.util.List;
public interface TransferRepository {
    Transfer save(Transfer transfer);
    List<Transfer> findAll();
}
