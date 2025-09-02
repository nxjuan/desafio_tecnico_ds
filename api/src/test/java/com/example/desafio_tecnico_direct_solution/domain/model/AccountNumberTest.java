package com.example.desafio_tecnico_direct_solution.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountNumberTest {

    @Test
    void should_accept_10_digits() {
        AccountNumber a = new AccountNumber("1234567890");
        assertEquals("1234567890", a.value());
    }

    @Test
    void should_reject_null_or_wrong_format() {
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber(null));
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber(""));
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber("123"));
        assertThrows(IllegalArgumentException.class, () -> new AccountNumber("123456789A")); // letra
    }
}
