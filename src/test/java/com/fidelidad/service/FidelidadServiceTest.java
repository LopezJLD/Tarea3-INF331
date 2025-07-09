package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Nivel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FidelidadServiceTest {
    private final FidelidadService service = new FidelidadService();

    @Test
    void nivelBronce() {
        Cliente cliente = new Cliente("1", "Test", "test@test.com");
        cliente.setPuntos(300);
        service.actualizarNivel(cliente);
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }

    @Test
    void nivelPlata() {
        Cliente cliente = new Cliente("2", "Test", "test@test.com");
        cliente.setPuntos(600);
        service.actualizarNivel(cliente);
        assertEquals(Nivel.PLATA, cliente.getNivel());
    }

    @Test
    void nivelOro() {
        Cliente cliente = new Cliente("3", "Test", "test@test.com");
        cliente.setPuntos(1600);
        service.actualizarNivel(cliente);
        assertEquals(Nivel.ORO, cliente.getNivel());
    }

    @Test
    void nivelPlatino() {
        Cliente cliente = new Cliente("4", "Test", "test@test.com");
        cliente.setPuntos(3500);
        service.actualizarNivel(cliente);
        assertEquals(Nivel.PLATINO, cliente.getNivel());
    }
}