package com.fidelidad.model;

import java.time.LocalDate;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int comprasHoy;
    private LocalDate ultimaCompraFecha;

    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.comprasHoy = 0;
    }

    // getters y setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getPuntos() { return puntos; }
    public Nivel getNivel() { return nivel; }
    public int getComprasHoy() { return comprasHoy; }
    public LocalDate getUltimaCompraFecha() { return ultimaCompraFecha; }

    public void setPuntos(int puntos) { this.puntos = puntos; }
    public void setNivel(Nivel nivel) { this.nivel = nivel; }
    public void setComprasHoy(int comprasHoy) { this.comprasHoy = comprasHoy; }
    public void setUltimaCompraFecha(LocalDate fecha) { this.ultimaCompraFecha = fecha; }
}