package com.example.poo_p1_g08.modelo;

public class Tecnico extends Persona {
    private String especialidad;
    public Tecnico(String id, String nombre, String telefono, String especialidad) {
        super(id,nombre,telefono);

        this.especialidad = especialidad;
    }
    public String getEspecialidad() { return especialidad; }
    public void   setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    @Override
    public String toString() {
        return String.format("%s | %-20s", super.toString(), especialidad);
    }
}
