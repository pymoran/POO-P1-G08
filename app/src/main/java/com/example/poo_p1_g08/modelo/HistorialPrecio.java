package com.example.poo_p1_g08.modelo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Registra un período de vigencia de un precio específico
 * Utilizado para mantener auditoría de cambios de precios en servicios
 */
public class HistorialPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Información del período de vigencia del precio
    private double precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin; // null si el precio está vigente
    
    /**
     * Constructor para crear un registro de precio
     * @param fechaFin null si el precio está vigente actualmente
     */
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

