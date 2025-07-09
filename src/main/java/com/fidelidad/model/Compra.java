package com.fidelidad.model;

import java.time.LocalDate;

public class Compra {
    private String idCompra;
    private String idCliente;
    private double monto;
    private LocalDate fecha;

    public Compra(String idCompra, String idCliente, double monto, LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se permiten fechas futuras");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Getters
    public String getIdCompra() { return idCompra; }
    public String getIdCliente() { return idCliente; }
    public double getMonto() { return monto; }
    public LocalDate getFecha() { return fecha; }

    // Setters (opcionales, según necesidad)
    public void setMonto(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto debe ser positivo");
        this.monto = monto;
    }
}