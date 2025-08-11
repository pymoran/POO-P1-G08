package com.example.poo_p1_g08.controlador;
import android.widget.Button;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.Persona;


public class ControladorCliente extends AppCompatActivity {
    private ArrayList<Persona> lista;
    private Button btnAgregarCliente, btnRegresar;
    public ControladorCliente(){

    }
    public ControladorCliente(ArrayList<Persona> lista){ // Se recibe la lista
        this.lista = lista; // Se asigna la lista recibida
    }

    public String agregarCliente(Cliente CLnuevo){
        for(Persona p : lista){
            if(p instanceof Cliente){
                Cliente c = (Cliente)p;
                if(c.getId().equalsIgnoreCase(CLnuevo.getId())){
                    System.out.printf(c.toString());
                    return ">>Cliente ya existente intente nuevamente";
                }
            }
        }
        lista.add(CLnuevo);
        return "Cliente agregado satisfactoriamente";

    }
    public Cliente buscarClientePorId(String id, boolean soloTipoCliente) {
        if (id == null) return null;
        String idBuscado = id.trim();

        for (Persona p : lista) {
            if (p instanceof Cliente) {
                Cliente c = (Cliente) p;
                String idCliente = c.getId();
                if (idCliente != null && idCliente.equalsIgnoreCase(idBuscado)) {
                    if (!soloTipoCliente || c.getTipoCliente()) { // usa el nombre real de tu método aquí
                        return c;
                    } else {
                        return null; // existe pero no es del tipo requerido
                    }
                }
            }
        }
        return null;
    }

    public Cliente buscarCliente(String id){
        for(Persona p: lista){
            if(p instanceof Cliente){
                Cliente c = (Cliente)p;
                if(id.equalsIgnoreCase(c.getId())){
                    return c;
                }
            }
        }
        return null;
    }

    public ArrayList<Persona> getListaCliente(){
        return lista;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacliente); // Aquí conectamos con el XML

        // Vinculamos los botones del XML con Java
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Evento para agregar cliente
        btnAgregarCliente.setOnClickListener(v -> {
            Toast.makeText(this, "Aquí iría la lógica para agregar cliente", Toast.LENGTH_SHORT).show();
        });

        // Botón regresar: vuelve al menú principal
        btnRegresar.setOnClickListener(v -> finish());
    }


}
