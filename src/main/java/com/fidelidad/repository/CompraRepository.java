package com.fidelidad.repository;

import com.fidelidad.model.Compra;
import java.util.List;

public interface CompraRepository {
    void agregarCompra(Compra compra);
    Compra obtenerCompra(String idCompra);
    List<Compra> obtenerTodasCompras();
    List<Compra> obtenerComprasPorCliente(String idCliente);
    void actualizarCompra(Compra compra);
    void eliminarCompra(String idCompra);
}