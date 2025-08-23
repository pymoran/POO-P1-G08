package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

public class DetalledelServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Servicio servicio;
    private int cantidad;
    public Servicio getServicio() {
        return servicio;
    }
    public DetalledelServicio(Servicio servicio, int cantidad) {
        this.servicio = servicio;
        this.cantidad = cantidad;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getPrecioUnitario(){
        return servicio.getPrecio();
    }
    public double getSubtotal(){
        return getPrecioUnitario()*cantidad;
    }
    @Override
    public String toString(){
        return servicio.getNombre() + " x " + cantidad + " = $ "+getSubtotal();
    }


}

