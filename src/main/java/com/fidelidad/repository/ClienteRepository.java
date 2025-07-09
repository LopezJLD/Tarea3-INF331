package com.fidelidad.repository;

import com.fidelidad.model.Cliente;
import java.util.List;

public interface ClienteRepository {
    void agregarCliente(Cliente cliente);
    Cliente obtenerCliente(String id);
    List<Cliente> obtenerTodosClientes();
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(String id);
}