package com.example.poo_p1_g08;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.controlador.ControladorCliente;
import com.example.poo_p1_g08.controlador.ControladorFacturaEmpresa;
import com.example.poo_p1_g08.controlador.ControladorOrden;
import com.example.poo_p1_g08.controlador.ControladorProveedor;
import com.example.poo_p1_g08.controlador.ControladorServicio;
import com.example.poo_p1_g08.controlador.ControladorTecnico;
import com.example.poo_p1_g08.utils.DataManager;

public class MainActivity extends AppCompatActivity {
    private Button btnCliente,btnProveedor,btnTecnico, btnServicios, btnOrdenes, btnFacturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar datos desde archivos (Unidad 5: Archivos y Excepciones)
        DataManager.inicializarApp(this);

        btnCliente = findViewById(R.id.btnCliente);
        btnProveedor = findViewById(R.id.btnProveedor);
        btnTecnico = findViewById(R.id.btnTecnico);
        btnServicios = findViewById(R.id.btnServicios);
        btnOrdenes = findViewById(R.id.btnOrdenes);
        btnFacturas = findViewById(R.id.btnFacturas);
        btnReporteServicios = findViewById(R.id.btnReporteServicios);
        btnReporteTecnicos = findViewById(R.id.btnReporteTecnicos);

        btnCliente.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorCliente.class));
        });

        btnProveedor.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorProveedor.class));
        });

        btnTecnico.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorTecnico.class));
        });

        // Abrir vista de servicios
        btnServicios.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorServicio.class));
        });

        // Abrir vista de órdenes
        btnOrdenes.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorOrden.class));
        });

        // Abrir vista de facturas
        btnFacturas.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ControladorFacturaEmpresa.class));
        });

        // Abrir vista reporte de ingresos por servicios
        btnServicios.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReporteServicioActivity.class));
        });

        // Abrir vista reporte de ateciones por tecnicos
        btnTecnicos.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReporteTecnicoActivity.class));
        });
    }


}
