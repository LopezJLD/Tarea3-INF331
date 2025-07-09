package com.fidelidad.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {
    @Test
    void emailValido() {
        assertTrue(EmailValidator.esValido("test@example.com"));
    }

    @Test
    void emailInvalido() {
        assertFalse(EmailValidator.esValido("test.example.com"));
    }

    @Test
    void emailNulo() {
        assertFalse(EmailValidator.esValido(null));
    }
}