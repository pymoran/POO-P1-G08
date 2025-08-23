package com.example.poo_p1_g08.modelo;

import android.os.Build;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String codigo, nombre;
    private double precio;
    private ArrayList<HistorialPrecio> historialPrecios = new ArrayList<>();

    public Servicio(String codigo, String nombre, double precioInicial) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precioInicial;
        this.historialPrecios = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.historialPrecios.add(new HistorialPrecio(precioInicial, LocalDate.now(), null));
        }
    }

    public void setPrecio(double nuevoPrecio) {
        if (!historialPrecios.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                historialPrecios.get(historialPrecios.size() - 1).setFechaFin(LocalDate.now());
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            historialPrecios.add(new HistorialPrecio(nuevoPrecio, LocalDate.now(), null));
        }
        this.precio = nuevoPrecio;
    }
    public ArrayList<HistorialPrecio> getHistorialPrecios() {
        return historialPrecios;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return String.format("Servicio [codigo=%s, nombre=%s, precio=%.2f]", codigo, nombre, precio);
    }
}