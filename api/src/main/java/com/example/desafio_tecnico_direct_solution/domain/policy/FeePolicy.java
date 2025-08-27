package com.example.desafio_tecnico_direct_solution.domain.policy;

import java.math.BigDecimal;

public interface FeePolicy {
    BigDecimal calculate(BigDecimal amount, long daysDiff);
}
