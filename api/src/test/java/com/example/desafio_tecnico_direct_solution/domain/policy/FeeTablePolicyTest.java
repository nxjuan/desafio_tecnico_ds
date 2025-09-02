package com.example.desafio_tecnico_direct_solution.domain.policy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
public class FeeTablePolicyTest {
    private final FeeTablePolicy policy = new FeeTablePolicy();

    @Test
    void should_calc_same_day_fee_fixed_plus_percent() {
        BigDecimal fee = policy.calculate(new BigDecimal("1000.00"), 0);
        assertEquals(new BigDecimal("28.00"), fee); // 3.00 + (2.5% de 1000 = 25.00)
    }

    @Test
    void should_calc_1_to_10_days_fixed_12() {
        BigDecimal fee = policy.calculate(new BigDecimal("1000.00"), 5);
        assertEquals(new BigDecimal("12.00"), fee);
    }

    @Test
    void should_calc_11_to_20_days_percent_8_2() {
        BigDecimal fee = policy.calculate(new BigDecimal("1000.00"), 15);
        assertEquals(new BigDecimal("82.00"), fee);
    }

    @Test
    void should_calc_21_to_30_days_percent_6_9() {
        BigDecimal fee = policy.calculate(new BigDecimal("1000.00"), 25);
        assertEquals(new BigDecimal("69.00"), fee);
    }

    @Test
    void should_calc_31_to_40_days_percent_4_7() {
        BigDecimal fee = policy.calculate(new BigDecimal("1000.00"), 35);
        assertEquals(new BigDecimal("47.00"), fee);
    }

    @Test
    void should_calc_41_to_50_days_percent_1_7() {
        BigDecimal fee = policy.calculate(new BigDecimal("1000.00"), 45);
        assertEquals(new BigDecimal("17.00"), fee);
    }

    @Test
    void should_throw_when_over_50_days() {
        assertThrows(IllegalArgumentException.class, () ->
                policy.calculate(new BigDecimal("1000.00"), 51));
    }
}
