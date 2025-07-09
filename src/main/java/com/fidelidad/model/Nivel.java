package com.fidelidad.model;

public enum Nivel {
    BRONCE(1.0),
    PLATA(1.2),
    ORO(1.5),
    PLATINO(2.0);

    private final double multiplicador;

    Nivel(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public static Nivel determinarNivel(int puntos) {
        if (puntos >= 3000) return PLATINO;
        else if (puntos >= 1500) return ORO;
        else if (puntos >= 500) return PLATA;
        else return BRONCE;
    }
}