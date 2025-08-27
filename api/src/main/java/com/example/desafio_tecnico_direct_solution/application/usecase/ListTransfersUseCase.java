package com.example.desafio_tecnico_direct_solution.application.usecase;

import com.example.desafio_tecnico_direct_solution.domain.model.Transfer;
import com.example.desafio_tecnico_direct_solution.domain.ports.TransferRepository;

import java.util.List;
public class ListTransfersUseCase {
    private final TransferRepository repository;

    public ListTransfersUseCase(TransferRepository repository) {
        this.repository = repository;
    }
    public List<Transfer> execute() { return repository.findAll(); }
}
