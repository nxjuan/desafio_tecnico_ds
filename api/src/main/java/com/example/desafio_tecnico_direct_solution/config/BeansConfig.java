package com.example.desafio_tecnico_direct_solution.config;

import com.example.desafio_tecnico_direct_solution.domain.policy.FeePolicy;
import com.example.desafio_tecnico_direct_solution.domain.policy.FeeTablePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    public FeePolicy feePolicy() {
        return new FeeTablePolicy();
    }
}
