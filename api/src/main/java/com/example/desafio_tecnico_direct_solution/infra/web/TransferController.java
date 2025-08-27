package com.example.desafio_tecnico_direct_solution.infra.web;

import com.example.desafio_tecnico_direct_solution.application.usecase.ListTransfersUseCase;
import com.example.desafio_tecnico_direct_solution.application.usecase.ScheduleTransferUseCase;
import com.example.desafio_tecnico_direct_solution.domain.model.Transfer;
import com.example.desafio_tecnico_direct_solution.domain.ports.TransferRepository;
import com.example.desafio_tecnico_direct_solution.domain.policy.FeePolicy;
import com.example.desafio_tecnico_direct_solution.infra.web.dto.ScheduleTransferInput;
import com.example.desafio_tecnico_direct_solution.infra.web.dto.TransferResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final ScheduleTransferUseCase scheduleUseCase;
    private final ListTransfersUseCase listUseCase;

    public TransferController(TransferRepository repository, FeePolicy feePolicy) {
        this.scheduleUseCase = new ScheduleTransferUseCase(repository, feePolicy);
        this.listUseCase = new ListTransfersUseCase(repository);
    }

    @PostMapping
    public TransferResponse schedule(@Valid @RequestBody ScheduleTransferInput in) {
        Transfer t = scheduleUseCase.execute(
                in.getSourceAccount(),
                in.getDestinationAccount(),
                in.getAmount(),
                in.getTransferDate());

        return toResponse(t);
    }

    @GetMapping
    public List<TransferResponse> list() {
        return listUseCase.execute().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private TransferResponse toResponse(Transfer t) {
        TransferResponse r = new TransferResponse();
        r.id = t.getId();
        r.sourceAccount = t.getSource().value();
        r.destinationAccount = t.getDestination().value();
        r.amount = t.getAmount();
        r.fee = t.getFee();
        r.total = t.total();
        r.scheduledDate = t.getScheduledDate();
        r.transferDate = t.getTransferDate();
        r.status = t.getStatus().name();
        return r;
    }
}