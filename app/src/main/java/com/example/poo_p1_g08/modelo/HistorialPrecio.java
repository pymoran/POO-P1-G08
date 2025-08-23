package com.example.poo_p1_g08.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class HistorialPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    public HistorialPrecio(double precio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}

