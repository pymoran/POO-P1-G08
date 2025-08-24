package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

/**
 * Clase base para representar una persona en el sistema
 * Implementa Serializable para permitir persistencia de datos
 */
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributos básicos de identificación personal
    private String id,nombre,telefono;

    /**
     * Constructor para crear una nueva persona
     * @param id Identificador único de la persona
     * @param nombre Nombre completo de la persona
     * @param telefono Número de teléfono de contacto
     */
    Persona(String id, String nombre, String telefono){
        this.id = id;
        this.nombre= nombre;
        this.telefono = telefono;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Retorna una representación formateada de la persona para mostrar en tablas
     */
    @Override
    public String toString() {
        return String.format("%-11s | %-15s | %-12s", id, nombre, telefono);
    }
}
