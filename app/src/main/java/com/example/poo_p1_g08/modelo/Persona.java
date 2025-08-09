package com.example.poo_p1_g08.modelo;

public class Persona {

    private String id,nombre,telefono;

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

    @Override
    public String toString() {
        return String.format("%-10s | %-15s | %-12s", id, nombre, telefono);
    }
}
