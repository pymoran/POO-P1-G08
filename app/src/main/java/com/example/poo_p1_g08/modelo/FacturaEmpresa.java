package com.example.poo_p1_g08.modelo;


import java.util.ArrayList;

public class FacturaEmpresa {
    private Cliente cliente;
    private int mes;
    private int año;
    private ArrayList<OrdenServicio> ordenes;
    private double totalServicios;
    private static final double COSTO_PRIORIDAD = 50.0;

    public FacturaEmpresa(Cliente cliente, int mes, int año) {
        this.cliente = cliente;
        this.mes = mes;
        this.año = año;
        this.ordenes = new ArrayList<>();
        this.totalServicios = 0.0;
    }

    public void agregarOrden(OrdenServicio orden) {
        ordenes.add(orden);
        totalServicios += orden.getTotal();
    }

    public double getTotalFactura() {
        return cliente.getTipoCliente()
                ? totalServicios + COSTO_PRIORIDAD
                : totalServicios;
    }

    // Getters y setters
    public Cliente getCliente() {
        return cliente;
    }

    public int getMes() {
        return mes;
    }

    public int getAño() {
        return año;
    }

    public ArrayList<OrdenServicio> getOrdenes() {
        return ordenes;
    }

    public double getTotalServicios() {
        return totalServicios;
    }

    public static double getCostoPrioridad() {
        return COSTO_PRIORIDAD;
    }

    public String getNombreMes() {
        String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes];
    }

    @Override
    public String toString() {
        return "FacturaEmpresa [Cliente=" + cliente +
                ", mes=" + mes + ", año=" + año +
                ", totalServicios=" + totalServicios + "]";
    }
}