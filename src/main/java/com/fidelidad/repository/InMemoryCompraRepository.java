package com.fidelidad.repository;

import com.fidelidad.model.Compra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCompraRepository implements CompraRepository {
    private final Map<String, Compra> compras = new HashMap<>();

    @Override
    public void agregarCompra(Compra compra) {
        compras.put(compra.getIdCompra(), compra);
    }

    @Override
    public Compra obtenerCompra(String idCompra) {
        return compras.get(idCompra);
    }

    @Override
    public List<Compra> obtenerTodasCompras() {
        return new ArrayList<>(compras.values());
    }

    @Override
    public List<Compra> obtenerComprasPorCliente(String idCliente) {
        List<Compra> resultado = new ArrayList<>();
        for (Compra compra : compras.values()) {
            if (compra.getIdCliente().equals(idCliente)) {
                resultado.add(compra);
            }
        }
        return resultado;
    }

    @Override
    public void actualizarCompra(Compra compra) {
        compras.put(compra.getIdCompra(), compra);
    }

    @Override
    public void eliminarCompra(String idCompra) {
        compras.remove(idCompra);
    }
}