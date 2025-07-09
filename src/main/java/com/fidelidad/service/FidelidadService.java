package com.fidelidad.service;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Nivel;

public class FidelidadService {
    public void actualizarNivel(Cliente cliente) {
        cliente.setNivel(Nivel.determinarNivel(cliente.getPuntos()));
    }
}