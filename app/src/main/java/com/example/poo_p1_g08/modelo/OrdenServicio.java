package com.example.poo_p1_g08.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Representa una orden de servicio en el taller automotriz
 * Es la entidad principal que relaciona cliente, vehículo, técnico y servicios
 */
public class OrdenServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Contador estático para generar códigos únicos de orden
    public static int contador =1;
    
    // Información de la orden
    private String codigo;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private String fecha;
    private ArrayList<DetalledelServicio>detalle;
    private double total;
    private Tecnico tecnico;



    /**
     * Constructor para crear una nueva orden de servicio
     * @param fecha Fecha de creación de la orden
     */
    public OrdenServicio(Cliente cliente, Vehiculo vehiculo,Tecnico tecnico, String fecha) {
        this.codigo = generarCodigo();
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.detalle = new ArrayList<>();
        this.total = 0;
        this.tecnico = tecnico;
    }

    /**
     * Genera un código único para la orden (ORD001, ORD002, etc.)
     */
    private String generarCodigo(){
        return "ORD" + String.format("%03d", contador ++);
    }
    
    /**
     * Agrega un servicio al detalle de la orden y actualiza el total
     * @param cantidad Cantidad del servicio solicitado
     */
    public void agregarDetalle(Servicio servicio, int cantidad) {
        DetalledelServicio ds = new DetalledelServicio(servicio,cantidad);
        detalle.add(ds);
        total += (servicio.getPrecio())*cantidad;
    }


    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<DetalledelServicio> getDetalle() {
        return detalle;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString(){
        return "Orden #" + codigo + " | Cliente: " + cliente.getNombre() +
                " | Vehículo: " + vehiculo.getPlaca() +
                " | Fecha: " + fecha +
                " | Total: $" + total;
    }

    public Tecnico getTecnico(){
        return tecnico;
    }

}
