package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

/**
 * Representa un técnico del sistema de servicios automotrices
 * Extiende de Persona agregando su especialidad técnica
 */
public class Tecnico extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Especialidad técnica del profesional
    private String especialidad;

    /**
     * Retorna la especialidad del técnico
     */
    public String getEspecialidad() {
        return especialidad;

    }

    /**
     * Constructor para crear un nuevo técnico
     * @param especialidad Área de especialización del técnico
     */

    public Tecnico(String id, String nombre, String telefono, String especialidad) {
        super(id,nombre,telefono);

        this.especialidad = especialidad;
    }
    
    /**
     * Retorna representación formateada incluyendo la especialidad
     */
    @Override
    public String toString() {
        return String.format("%s | %-20s", super.toString(), especialidad);
    }
}
