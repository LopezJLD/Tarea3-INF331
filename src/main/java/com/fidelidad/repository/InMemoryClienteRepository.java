package com.fidelidad.repository;

import com.fidelidad.model.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryClienteRepository implements ClienteRepository {
    private final Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public void agregarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    @Override
    public Cliente obtenerCliente(String id) {
        return clientes.get(id);
    }

    @Override
    public List<Cliente> obtenerTodosClientes() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    @Override
    public void eliminarCliente(String id) {
        clientes.remove(id);
    }
}