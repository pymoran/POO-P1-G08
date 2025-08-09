package com.example.poo_p1_g08;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.controlador.ControladorCliente;
import com.example.poo_p1_g08.controlador.ControladorProveedor;
import com.example.poo_p1_g08.controlador.ControladorTecnico;

public class MainActivity extends AppCompatActivity {
    private Button btnCliente,btnProveedor,btnTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCliente = findViewById(R.id.btnCliente);
        btnProveedor = findViewById(R.id.btnProveedor);
        btnTecnico = findViewById(R.id.btnTecnico);

        btnCliente.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorCliente.class));
        });

        btnProveedor.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorProveedor.class));
        });

        btnTecnico.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorTecnico.class));
        });
    }


}