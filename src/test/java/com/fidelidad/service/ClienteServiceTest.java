package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.repository.InMemoryClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {
    private ClienteService clienteService;
    private final InMemoryClienteRepository repository = new InMemoryClienteRepository();

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(repository);
    }

    @Test
    void agregarClienteValido() {
        Cliente cliente = new Cliente("1", "Ana", "ana@test.com");
        clienteService.agregarCliente(cliente);
        assertNotNull(clienteService.obtenerCliente("1"));
    }

    @Test
    void agregarClienteEmailInvalido() {
        Cliente cliente = new Cliente("2", "Juan", "juan-sin-email");
        assertThrows(IllegalArgumentException.class, () -> clienteService.agregarCliente(cliente));
    }
}