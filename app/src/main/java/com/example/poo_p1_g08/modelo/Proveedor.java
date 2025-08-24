package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

/**
 * Representa un proveedor de repuestos o servicios para el taller
 * Extiende de Persona agregando descripción de servicios/productos
 */
public class Proveedor extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Descripción de los productos o servicios que ofrece
    private String descripcion;
    
    /**
     * Constructor para crear un nuevo proveedor
     * @param descripcion Descripción de productos o servicios ofrecidos
     */
    public Proveedor(String id, String nombre, String telefono, String descripcion){
        super(id,nombre,telefono);
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }
    public void setDireccion(String descripcion){
        this.descripcion= descripcion;
    }

    /**
     * Retorna representación formateada incluyendo la descripción
     */
    @Override
    public String toString() {
        return String.format("%s | %-30s", super.toString(), descripcion);
    }


}
