package com.fidelidad.service;

import java.util.List;

import com.fidelidad.model.Cliente;
import com.fidelidad.repository.ClienteRepository;
import com.fidelidad.util.EmailValidator;

public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void agregarCliente(Cliente cliente) {
        if (!EmailValidator.esValido(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo electrónico inválido");
        }
        clienteRepository.agregarCliente(cliente);
    }

    public Cliente obtenerCliente(String id) {
        return clienteRepository.obtenerCliente(id);
    }

    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.obtenerTodosClientes();
    }

    public void actualizarCliente(Cliente cliente) {
        if (!EmailValidator.esValido(cliente.getCorreo())) {
            throw new IllegalArgumentException("Correo electrónico inválido");
        }
        fidelidadService.actualizarNivel(cliente);
        clienteRepository.actualizarCliente(cliente);
    }

    public void eliminarCliente(String id) {
        clienteRepository.eliminarCliente(id);
    }
}