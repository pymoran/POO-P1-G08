package com.example.poo_p1_g08.controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.modelo.Tecnico;
import com.example.poo_p1_g08.modelo.Vehiculo;

public class ControladorOrden {
    private ArrayList<OrdenServicio> lista;

    public ControladorOrden(ArrayList<OrdenServicio> lista) {
        this.lista = lista;
    }
    public OrdenServicio crearOrden(Cliente cliente, Vehiculo vehiculo,LocalDate fecha,Tecnico tecnico){
        OrdenServicio nueva = new OrdenServicio(cliente,vehiculo,tecnico,fecha);
        lista.add(nueva);
        return nueva;
    }
    public void mostrarTodasOrdenes(){
        for(OrdenServicio o : lista){
            System.out.println(o);
        }
    }
    public ArrayList<OrdenServicio> getListaOrdenes(){
        return lista;
    }
}
