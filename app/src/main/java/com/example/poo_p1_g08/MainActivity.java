package com.example.poo_p1_g08;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.controlador.ControladorCliente;
import com.example.poo_p1_g08.controlador.ControladorFacturaEmpresa;
import com.example.poo_p1_g08.controlador.ControladorOrden;
import com.example.poo_p1_g08.controlador.ControladorProveedor;
import com.example.poo_p1_g08.controlador.ControladorServicio;
import com.example.poo_p1_g08.controlador.ControladorTecnico;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.Proveedor;
import com.example.poo_p1_g08.modelo.Servicio;
import com.example.poo_p1_g08.modelo.Tecnico;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnCliente,btnProveedor,btnTecnico, btnServicios, btnOrdenes, btnFacturas,btnReporteServicios,btnReporteTecnicos;

    // Nombres de archivos serializados
    private static final String FILE_TECNICOS = "tecnicos.ser";
    private static final String FILE_CLIENTES = "clientes.ser";
    private static final String FILE_SERVICIOS = "servicios.ser";
    private static final String FILE_PROVEEDORES = "proveedores.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar datos desde archivos serializados (Unidad 5: Archivos y Excepciones)
        inicializarApp();

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
        btnReporteServicios.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReporteServicioActivity.class));
        });

        // Abrir vista reporte de ateciones por tecnicos
        btnReporteTecnicos.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReporteTecnicoActivity.class));
        });
    }

    /**
     * Inicializa la aplicación creando archivos serializados si no existen
     */
    private void inicializarApp() {
        try {
            Log.i(TAG, "Iniciando inicialización de archivos serializados...");
            
            // Crear archivos serializados con datos de ejemplo si no existen
            inicializarArchivoTecnicos();
            inicializarArchivoClientes();
            inicializarArchivoServicios();
            inicializarArchivoProveedores();
            
            Log.i(TAG, "Inicialización de archivos serializados completada exitosamente");
            
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar archivos: " + e.getMessage(), e);
        }
    }

    // ==================== TÉCNICOS ====================
    
    private void inicializarArchivoTecnicos() {
        try {
            List<Tecnico> tecnicos = obtenerTodosLosTecnicos();
            if (tecnicos.isEmpty()) {
                List<Tecnico> tecnicosEjemplo = new ArrayList<>();
                tecnicosEjemplo.add(new Tecnico("T001", "Juan Pérez", "0991234567", "Motor"));
                tecnicosEjemplo.add(new Tecnico("T002", "María López", "0997654321", "Electricidad"));
                tecnicosEjemplo.add(new Tecnico("T003", "Pedro Gómez", "0995554443", "Suspensión"));
                
                guardarTecnicosEnArchivo(tecnicosEjemplo);
                Log.i(TAG, "Archivo de técnicos creado con datos de ejemplo");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar técnicos: " + e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Tecnico> obtenerTodosLosTecnicos() {
        List<Tecnico> tecnicos = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_TECNICOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            tecnicos = (List<Tecnico>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            Log.w(TAG, "Archivo de técnicos no encontrado o error al leer: " + e.getMessage());
        }
        
        return tecnicos;
    }
    
    private boolean guardarTecnicosEnArchivo(List<Tecnico> tecnicos) {
        try (FileOutputStream fos = openFileOutput(FILE_TECNICOS, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(tecnicos);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar técnicos en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== CLIENTES ====================
    
    private void inicializarArchivoClientes() {
        try {
            List<Cliente> clientes = obtenerTodosLosClientes();
            if (clientes.isEmpty()) {
                List<Cliente> clientesEjemplo = new ArrayList<>();
                clientesEjemplo.add(new Cliente("C001", "Carlos Ruiz", "0991111111", "Av. Principal 123", false));
                clientesEjemplo.add(new Cliente("C002", "Ana García", "0992222222", "Calle Secundaria 456", false));
                clientesEjemplo.add(new Cliente("C003", "Empresa ABC", "0993333333", "Zona Industrial 789", true));
                clientesEjemplo.add(new Cliente("C004", "Corporación XYZ", "0994444444", "Centro Comercial 321", true));
                
                guardarClientesEnArchivo(clientesEjemplo);
                Log.i(TAG, "Archivo de clientes creado con datos de ejemplo");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar clientes: " + e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_CLIENTES);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            clientes = (List<Cliente>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            Log.w(TAG, "Archivo de clientes no encontrado o error al leer: " + e.getMessage());
        }
        
        return clientes;
    }
    
    private boolean guardarClientesEnArchivo(List<Cliente> clientes) {
        try (FileOutputStream fos = openFileOutput(FILE_CLIENTES, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(clientes);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar clientes en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== SERVICIOS ====================
    
    private void inicializarArchivoServicios() {
        try {
            List<Servicio> servicios = obtenerTodosLosServicios();
            if (servicios.isEmpty()) {
                List<Servicio> serviciosEjemplo = new ArrayList<>();
                serviciosEjemplo.add(new Servicio("S001", "Cambio de aceite", 25.00));
                serviciosEjemplo.add(new Servicio("S002", "Frenos", 80.00));
                serviciosEjemplo.add(new Servicio("S003", "Suspensión", 120.00));
                serviciosEjemplo.add(new Servicio("S004", "Electricidad", 45.00));
                serviciosEjemplo.add(new Servicio("S005", "Motor", 200.00));
                serviciosEjemplo.add(new Servicio("S006", "Limpieza", 30.00));
                
                guardarServiciosEnArchivo(serviciosEjemplo);
                Log.i(TAG, "Archivo de servicios creado con datos de ejemplo");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar servicios: " + e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Servicio> obtenerTodosLosServicios() {
        List<Servicio> servicios = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_SERVICIOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            servicios = (List<Servicio>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            Log.w(TAG, "Archivo de servicios no encontrado o error al leer: " + e.getMessage());
        }
        
        return servicios;
    }
    
    private boolean guardarServiciosEnArchivo(List<Servicio> servicios) {
        try (FileOutputStream fos = openFileOutput(FILE_SERVICIOS, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(servicios);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar servicios en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== PROVEEDORES ====================
    
    private void inicializarArchivoProveedores() {
        try {
            List<Proveedor> proveedores = obtenerTodosLosProveedores();
            if (proveedores.isEmpty()) {
                List<Proveedor> proveedoresEjemplo = new ArrayList<>();
                proveedoresEjemplo.add(new Proveedor("P001", "Repuestos Auto", "0995555555", "Repuestos para automóviles"));
                proveedoresEjemplo.add(new Proveedor("P002", "Herramientas Pro", "0996666666", "Herramientas profesionales"));
                
                guardarProveedoresEnArchivo(proveedoresEjemplo);
                Log.i(TAG, "Archivo de proveedores creado con datos de ejemplo");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar proveedores: " + e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Proveedor> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_PROVEEDORES);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            proveedores = (List<Proveedor>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            Log.w(TAG, "Archivo de proveedores no encontrado o error al leer: " + e.getMessage());
        }
        
        return proveedores;
    }
    
    private boolean guardarProveedoresEnArchivo(List<Proveedor> proveedores) {
        try (FileOutputStream fos = openFileOutput(FILE_PROVEEDORES, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(proveedores);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "Error al guardar proveedores en archivo: " + e.getMessage(), e);
            return false;
        }
    }
}
