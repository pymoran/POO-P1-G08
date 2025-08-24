package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

/**
 * Representa un cliente del sistema de servicios automotrices
 * Extiende de Persona agregando información específica del cliente
 */
public class Cliente extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Información adicional específica del cliente
    private String direccion;
    private Boolean tipoCliente; // true = empresa, false = particular
    
    /**
     * Constructor para crear un nuevo cliente
     * @param tipoCliente true para empresa, false para particular
     */
    public Cliente(String id, String nombre, String telefono, String direccion, Boolean tipoCliente){
        super(id,nombre,telefono);
        this.direccion = direccion;
        this.tipoCliente = tipoCliente;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean  getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Boolean  tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     * Retorna representación formateada incluyendo dirección y tipo de cliente
     */
    @Override
    public String toString() {
        return String.format("%s | %-20s | %-10s",
                super.toString(),  // Mantiene el formato de id, nombre, teléfono
                direccion,         // Nueva columna: dirección
                tipoCliente);      // Nueva columna: tipo de cliente
    }
}
