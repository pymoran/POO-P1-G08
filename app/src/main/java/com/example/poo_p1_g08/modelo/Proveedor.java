package com.example.poo_p1_g08.modelo;

public class Proveedor extends Persona{
    private String descripcion;
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

    @Override
    public String toString() {
        return String.format("%s | %-30s", super.toString(), descripcion);
    }


}
