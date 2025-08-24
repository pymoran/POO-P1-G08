package com.example.poo_p1_g08.modelo;

import java.io.Serializable;

/**
 * Representa un vehículo en el sistema de servicios automotrices
 * Define los tipos de vehículos que pueden recibir servicios
 */
public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Enum que define los tipos de vehículos soportados
     */
    public enum TipoVehiculo{
        AUTOMOVIL,MOTOCICLETA,BUS
    }

    // Identificación única del vehículo
    private String placa;
    private TipoVehiculo tipo;
    
    /**
     * Constructor para crear un nuevo vehículo
     * @param placa Placa/licencia del vehículo
     * @param tipo Tipo de vehículo (AUTOMOVIL, MOTOCICLETA, BUS)
     */
    public Vehiculo(String placa, TipoVehiculo tipo){
        this.placa= placa;
        this.tipo = tipo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public TipoVehiculo getTipo() {
        return tipo;
    }
    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }
    /**
     * Retorna representación del vehículo con tipo y placa
     */
    @Override
    public String toString(){
        return tipo + "- placa" + placa;
    }


}