package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.model.Nivel;
import com.fidelidad.repository.ClienteRepository;
import com.fidelidad.repository.CompraRepository;
import java.time.LocalDate;
import java.util.List;

public class CompraService {
    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final FidelidadService fidelidadService;

    public CompraService(CompraRepository compraRepository, 
                        ClienteRepository clienteRepository,
                        FidelidadService fidelidadService) {
        this.compraRepository = compraRepository;
        this.clienteRepository = clienteRepository;
        this.fidelidadService = fidelidadService;
    }

    public void registrarCompra(Compra compra) {
        Cliente cliente = clienteRepository.obtenerCliente(compra.getIdCliente());
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }


        gestionarStreakCompras(cliente, compra.getFecha());

        int puntosCalculados = calcularPuntosCompra(compra, cliente);

        actualizarCliente(cliente, puntosCalculados, compra.getFecha());

        compraRepository.agregarCompra(compra);
    }

    private void gestionarStreakCompras(Cliente cliente, LocalDate fechaCompra) {
        if (cliente.getUltimaCompraFecha() == null) {
            cliente.setUltimaCompraFecha(fechaCompra);
            cliente.setComprasHoy(0);
        } else if (!fechaCompra.isEqual(cliente.getUltimaCompraFecha())) {
            cliente.setComprasHoy(0);
        }
    }

    private int calcularPuntosCompra(Compra compra, Cliente cliente) {
        int puntosBase = (int) (compra.getMonto() / 100);
        
        // aplicar multiplicador de nivel
        double multiplicador = cliente.getNivel().getMultiplicador();
        int puntosCalculados = (int) (puntosBase * multiplicador);

        // bonus por 3 compras en el mismo día
        if (cliente.getComprasHoy() == 2) { 
            puntosCalculados += 10;
        }

        return puntosCalculados;
    }

    private void actualizarCliente(Cliente cliente, int puntosCalculados, LocalDate fechaCompra) {
        cliente.setPuntos(cliente.getPuntos() + puntosCalculados);
        cliente.setComprasHoy(cliente.getComprasHoy() + 1);
        cliente.setUltimaCompraFecha(fechaCompra);
        
        fidelidadService.actualizarNivel(cliente);
        clienteRepository.actualizarCliente(cliente);
    }

    public List<Compra> obtenerTodasCompras() {
        return compraRepository.obtenerTodasCompras();
    }

    public Compra obtenerCompraPorId(String idCompra) {
        return compraRepository.obtenerCompra(idCompra);
    }

    public List<Compra> obtenerComprasPorCliente(String idCliente) {
        return compraRepository.obtenerComprasPorCliente(idCliente);
    }

    public void actualizarCompra(Compra compra) {
        compraRepository.actualizarCompra(compra);
    }

    public void eliminarCompra(String idCompra) {
        compraRepository.eliminarCompra(idCompra);
    }
}