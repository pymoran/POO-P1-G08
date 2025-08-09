package com.example.poo_p1_g08.controlador;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Persona;
import com.example.poo_p1_g08.modelo.Tecnico;

import java.util.ArrayList;
public class ControladorTecnico extends AppCompatActivity {
    ArrayList<Persona> lista;
    private Button btnAgregarTecnico;

    public ControladorTecnico(ArrayList<Persona> lista) {
        this.lista = lista;
    }

    public String agregarTecnico(Tecnico tecnico) {
        for (Persona p : lista) {
            if (p instanceof Tecnico) {
                Tecnico i = (Tecnico) p;
                if (i.getId().equalsIgnoreCase(tecnico.getId())) {
                    System.out.println(i.toString());
                    return "Tecnico ya existe intente nuevamente";
                }
            }

        }
        lista.add(tecnico);

        return "Tecnico agregado satisfactoriamente";
    }

    public String eliminarTecnico(String cedula) {
        for (Persona p : lista) {
            if (p instanceof Tecnico) {
                Tecnico i = (Tecnico) p;
                if (i.getId().equalsIgnoreCase(cedula)) {
                    lista.remove(i);
                    return "Tecnico eliminado satisfactoriamente";
                }
            }
        }
        return "Tecnico no encontrado";
    }

    public Tecnico buscarTecnico(String cedula) {
        for (Persona p : lista) {
            if (p instanceof Tecnico) {
                Tecnico t = (Tecnico) p;
                if (t.getId().equalsIgnoreCase(cedula)) {
                    return t;
                }
            }
        }
        return null;
    }

    public ArrayList<Persona> getListaTecnicos() {
        return lista;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistatecnico);

        btnAgregarTecnico = findViewById(R.id.btnAgregarTecnico);


        btnAgregarTecnico.setOnClickListener(v ->
                Toast.makeText(this, "Aquí se agregaría un técnico", Toast.LENGTH_SHORT).show());
    }
}
