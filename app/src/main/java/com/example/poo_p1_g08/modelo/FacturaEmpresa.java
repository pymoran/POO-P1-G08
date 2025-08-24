package com.example.poo_p1_g08.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Representa una factura mensual para clientes empresariales
 * Agrupa múltiples órdenes de servicio y aplica costo de prioridad
 */
public class FacturaEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Información de la factura
    private Cliente cliente;
    private int mes;
    private int año;
    private String fechaCreacion;
    private ArrayList<OrdenServicio> ordenes;
    private double totalServicios;
    // Costo adicional por servicio prioritario para empresas
    private static final double COSTO_PRIORIDAD = 50.0;

    /**
     * Constructor para crear una factura mensual
     * @param mes Mes de facturación (1-12)
     * @param año Año de facturación
     */
    public FacturaEmpresa(Cliente cliente, int mes, int año) {
        this.cliente = cliente;
        this.mes = mes;
        this.año = año;
        // Generar fecha de creación automáticamente en formato YYYY-MM-DD HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.fechaCreacion = sdf.format(new Date());
        this.ordenes = new ArrayList<>();
        this.totalServicios = 0.0;
    }

    /**
     * Agrega una orden de servicio a la factura y actualiza el total
     */
    public void agregarOrden(OrdenServicio orden) {
        ordenes.add(orden);
        totalServicios += orden.getTotal();
    }

    /**
     * Calcula el total de la factura incluyendo costo de prioridad para empresas
     */
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

    public String getFechaCreacion() {
        // Si la fecha de creación es null (facturas antiguas), generar una fecha por defecto
        if (fechaCreacion == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            fechaCreacion = sdf.format(new Date());
        }
        return fechaCreacion;
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

    /**
     * Retorna el nombre del mes en español
     */
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