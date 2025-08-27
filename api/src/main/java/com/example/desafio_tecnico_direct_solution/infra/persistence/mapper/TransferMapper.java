package com.example.desafio_tecnico_direct_solution.infra.persistence.mapper;

import com.example.desafio_tecnico_direct_solution.domain.model.*;
import com.example.desafio_tecnico_direct_solution.infra.persistence.jpa.JpaTransferEntity;

public class TransferMapper {
    private TransferMapper(){}

    public static JpaTransferEntity toJpa(Transfer t) {
        JpaTransferEntity e = new JpaTransferEntity();
        e.setId(t.getId());
        e.setSourceAccount(t.getSource().value());
        e.setDestinationAccount(t.getDestination().value());
        e.setAmount(t.getAmount());
        e.setFee(t.getFee());
        e.setScheduledDate(t.getScheduledDate());
        e.setTransferDate(t.getTransferDate());
        e.setStatus(t.getStatus().name());
        return e;
    }

    public static Transfer toDomain(JpaTransferEntity e) {
        return new Transfer(
                e.getId(),
                new AccountNumber(e.getSourceAccount()),
                new AccountNumber(e.getDestinationAccount()),
                e.getAmount(),
                e.getFee(),
                e.getScheduledDate(),
                e.getTransferDate(),
                TransferStatus.valueOf(e.getStatus())
        );
    }
}
