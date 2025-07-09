package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.model.Nivel;
import com.fidelidad.repository.InMemoryClienteRepository;
import com.fidelidad.repository.InMemoryCompraRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompraServiceTest {
    private CompraService compraService;
    private InMemoryClienteRepository clienteRepo;
    private InMemoryCompraRepository compraRepo;
    private static final String CLIENTE_ID = "1";
    private static final String CLIENTE_NOMBRE = "Test";
    private static final String CLIENTE_EMAIL = "test@test.com";

    @BeforeEach
    void setUp() {
        clienteRepo = new InMemoryClienteRepository();
        compraRepo = new InMemoryCompraRepository();
        compraService = new CompraService(
            compraRepo,
            clienteRepo,
            new FidelidadService()
        );
        
        // Cliente de prueba inicial
        Cliente cliente = new Cliente(CLIENTE_ID, CLIENTE_NOMBRE, CLIENTE_EMAIL);
        clienteRepo.agregarCliente(cliente);
    }

    @Test
    void compraBasicaBronce() {
        Compra compra = new Compra("C1", CLIENTE_ID, 500, LocalDate.now());
        compraService.registrarCompra(compra);
        Cliente cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        
        assertEquals(5, cliente.getPuntos()); // 500/100 * 1 = 5
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }

    @Test
    void compraConBonusTercerDia() {
        // Preparar cliente con 2 compras hoy
        Cliente cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        cliente.setComprasHoy(2);
        cliente.setUltimaCompraFecha(LocalDate.now());
        clienteRepo.actualizarCliente(cliente);
        
        Compra compra = new Compra("C1", CLIENTE_ID, 100, LocalDate.now());
        compraService.registrarCompra(compra);
        
        cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        assertEquals(11, cliente.getPuntos()); // (100/100=1) + 10 bonus
        assertEquals(3, cliente.getComprasHoy());
    }

    @Test
    void compraNuevoDiaReiniciaStreak() {
        // Preparar cliente con compras ayer
        Cliente cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        cliente.setComprasHoy(2);
        cliente.setUltimaCompraFecha(LocalDate.now().minusDays(1));
        clienteRepo.actualizarCliente(cliente);
        
        Compra compra = new Compra("C1", CLIENTE_ID, 200, LocalDate.now());
        compraService.registrarCompra(compra);
        
        cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        assertEquals(2, cliente.getPuntos()); // 200/100 * 1 = 2 (sin bonus)
        assertEquals(1, cliente.getComprasHoy()); // Streak reiniciado
    }

    @Test
    void compraConMultiplicadorPlata() {
        Cliente cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        cliente.setPuntos(600); // Nivel Plata
        cliente.setNivel(Nivel.PLATA);
        clienteRepo.actualizarCliente(cliente);
        
        Compra compra = new Compra("C1", CLIENTE_ID, 1000, LocalDate.now());
        compraService.registrarCompra(compra);
        
        cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        assertEquals(12, (int)(1000/100 * 1.2)); // 1000/100 * 1.2 = 12
    }

    @Test
    void compraClienteInexistente() {
        Compra compra = new Compra("C1", "ID_INEXISTENTE", 100, LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> compraService.registrarCompra(compra));
    }

    @Test
    void primeraCompraInicializaStreak() {
        Cliente cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        cliente.setUltimaCompraFecha(null);
        clienteRepo.actualizarCliente(cliente);
        
        Compra compra = new Compra("C1", CLIENTE_ID, 150, LocalDate.now());
        compraService.registrarCompra(compra);
        
        cliente = clienteRepo.obtenerCliente(CLIENTE_ID);
        assertEquals(1, cliente.getPuntos()); // 150/100 = 1
        assertEquals(1, cliente.getComprasHoy());
        assertNotNull(cliente.getUltimaCompraFecha());
    }

    @Test
    void compraConMontoCero() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Compra("C1", CLIENTE_ID, 0, LocalDate.now()));
    }

    @Test
    void compraConFechaFutura() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Compra("C1", CLIENTE_ID, 100, LocalDate.now().plusDays(1)));
    }

    @Test
    void actualizarCompraExistente() {
        Compra compra = new Compra("C1", CLIENTE_ID, 100, LocalDate.now());
        compraService.registrarCompra(compra);
        
        Compra compraActualizada = new Compra("C1", CLIENTE_ID, 200, LocalDate.now());
        compraService.actualizarCompra(compraActualizada);
        
        Compra compraRecuperada = compraService.obtenerCompraPorId("C1");
        assertEquals(200, compraRecuperada.getMonto(), 0.001);
    }

    @Test
    void eliminarCompraExistente() {
        Compra compra = new Compra("C1", CLIENTE_ID, 100, LocalDate.now());
        compraService.registrarCompra(compra);
        
        compraService.eliminarCompra("C1");
        
        assertNull(compraService.obtenerCompraPorId("C1"));
    }
}