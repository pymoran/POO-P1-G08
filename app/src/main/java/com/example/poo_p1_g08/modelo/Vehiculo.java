package com.example.poo_p1_g08.modelo;

public class Vehiculo {
    public enum TipoVehiculo{
        AUTOMOVIL,MOTOCICLETA,BUS
    }

    private String placa;
    private TipoVehiculo tipo;
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
    @Override
    public String toString(){
        return tipo + "- placa" + placa;
    }


}