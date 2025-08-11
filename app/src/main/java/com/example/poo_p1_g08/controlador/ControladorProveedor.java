package com.example.poo_p1_g08.controlador;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Persona;
import com.example.poo_p1_g08.modelo.Proveedor;

import java.util.ArrayList;

public class ControladorProveedor extends AppCompatActivity {
    private ArrayList<Persona> lista;
    private Button btnAgregarProveedor, btnRegresar;
    public ControladorProveedor(ArrayList<Persona> lista){
        this.lista = lista;
    }

    public String agregarProveedor(Proveedor proveedor){
        for(Persona p : lista){
            if(p instanceof Proveedor){
                Proveedor i = (Proveedor)p;
                if(i.getId().equalsIgnoreCase(proveedor.getId())){
                    System.out.printf(i.toString());
                    return ">>Proveedor ya existente intente nuevamente";
                }
            }

        }
        lista.add(proveedor);
        return "Proveedor agregado satisfactoriamente";
    }

    public ArrayList<Persona> getListaProveedores(){
        return lista;
    }

    public boolean crearOrden(){
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaproveedor);

        btnAgregarProveedor = findViewById(R.id.btnAgregarProveedor);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnAgregarProveedor.setOnClickListener(v ->
                Toast.makeText(this, "Aquí se agregaría un proveedor", Toast.LENGTH_SHORT).show());

        btnRegresar.setOnClickListener(v -> finish());

    }
}
