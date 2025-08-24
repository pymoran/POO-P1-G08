package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

/**
 * Representa un detalle individual de servicio dentro de una orden
 * Contiene el servicio, cantidad y cálculos de subtotal
 */
public class DetalledelServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Servicio específico y cantidad solicitada
    private Servicio servicio;
    private int cantidad;
    
    /**
     * Constructor para crear un detalle de servicio
     * @param cantidad Cantidad del servicio solicitado
     */
    public DetalledelServicio(Servicio servicio, int cantidad) {
        this.servicio = servicio;
        this.cantidad = cantidad;
    }
    
    public Servicio getServicio() {
        return servicio;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * Calcula el precio unitario del servicio
     */
    public double getPrecioUnitario(){
        return servicio.getPrecio();
    }
    
    /**
     * Calcula el subtotal de este detalle (precio unitario * cantidad)
     */
    public double getSubtotal(){
        return getPrecioUnitario()*cantidad;
    }
    
    /**
     * Retorna representación formateada del detalle
     */
    @Override
    public String toString(){
        return servicio.getNombre() + " x " + cantidad + " = $ "+getSubtotal();
    }


}

