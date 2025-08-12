package com.example.poo_p1_g08.utils;

import android.content.Context;
import android.util.Log;

import com.example.poo_p1_g08.controlador.ControladorCliente;
import com.example.poo_p1_g08.controlador.ControladorOrden;
import com.example.poo_p1_g08.controlador.ControladorProveedor;
import com.example.poo_p1_g08.controlador.ControladorServicio;
import com.example.poo_p1_g08.controlador.ControladorTecnico;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.modelo.Persona;
import com.example.poo_p1_g08.modelo.Proveedor;
import com.example.poo_p1_g08.modelo.Servicio;
import com.example.poo_p1_g08.modelo.Tecnico;
import com.example.poo_p1_g08.modelo.Vehiculo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase utilitaria para manejar la carga inicial de datos desde archivos
 * Implementa los temas de Unidad 5: Archivos y Excepciones
 */
public class DataManager {
    private static final String TAG = "DataManager";
    
    // Nombres de archivos
    private static final String FILE_TECNICOS = "tecnicos.txt";
    private static final String FILE_CLIENTES = "clientes.txt";
    private static final String FILE_SERVICIOS = "servicios.txt";
    private static final String FILE_PROVEEDORES = "proveedores.txt";
    private static final String FILE_ORDENES = "ordenes.txt";

    /**
     * Inicializa la aplicación cargando datos desde archivos
     */
    public static void inicializarApp(Context context) {
        try {
            Log.i(TAG, "Iniciando carga de datos...");
            
            // Crear directorio de datos si no existe
            File dataDir = new File(context.getFilesDir(), "data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Cargar o crear datos
            cargarTecnicos(context);
            cargarClientes(context);
            cargarServicios(context);
            cargarProveedores(context);
            cargarOrdenes(context);
            
            Log.i(TAG, "Carga de datos completada exitosamente");
            
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar datos: " + e.getMessage(), e);
        }
    }

    /**
     * Carga técnicos desde archivo o crea datos de ejemplo
     */
    private static void cargarTecnicos(Context context) {
        ArrayList<Persona> tecnicos = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_TECNICOS);
        
        try {
            if (file.exists()) {
                // Leer desde archivo
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (datos.length >= 4) {
                            Tecnico tecnico = new Tecnico(
                                datos[0].trim(), // id
                                datos[1].trim(), // nombre
                                datos[2].trim(), // telefono
                                datos[3].trim()  // especialidad
                            );
                            tecnicos.add(tecnico);
                        }
                    }
                }
                Log.i(TAG, "Técnicos cargados desde archivo: " + tecnicos.size());
            } else {
                // Crear datos de ejemplo
                tecnicos.add(new Tecnico("T001", "Juan Pérez", "0991234567", "Motor"));
                tecnicos.add(new Tecnico("T002", "María López", "0997654321", "Electricidad"));
                
                // Guardar en archivo
                guardarTecnicos(context, tecnicos);
                Log.i(TAG, "Técnicos de ejemplo creados y guardados");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al cargar técnicos: " + e.getMessage(), e);
        }
    }

    /**
     * Carga clientes desde archivo o crea datos de ejemplo
     */
    private static void cargarClientes(Context context) {
        ArrayList<Persona> clientes = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_CLIENTES);
        
        try {
            if (file.exists()) {
                // Leer desde archivo
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (datos.length >= 5) {
                            Cliente cliente = new Cliente(
                                datos[0].trim(), // id
                                datos[1].trim(), // nombre
                                datos[2].trim(), // telefono
                                datos[3].trim(), // direccion
                                Boolean.parseBoolean(datos[4].trim()) // tipoCliente
                            );
                            clientes.add(cliente);
                        }
                    }
                }
                Log.i(TAG, "Clientes cargados desde archivo: " + clientes.size());
            } else {
                // Crear datos de ejemplo (2 particulares, 2 empresariales)
                clientes.add(new Cliente("C001", "Carlos Ruiz", "0991111111", "Av. Principal 123", false));
                clientes.add(new Cliente("C002", "Ana García", "0992222222", "Calle Secundaria 456", false));
                clientes.add(new Cliente("C003", "Empresa ABC", "0993333333", "Zona Industrial 789", true));
                clientes.add(new Cliente("C004", "Corporación XYZ", "0994444444", "Centro Comercial 321", true));
                
                // Guardar en archivo
                guardarClientes(context, clientes);
                Log.i(TAG, "Clientes de ejemplo creados y guardados");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al cargar clientes: " + e.getMessage(), e);
        }
    }

    /**
     * Carga servicios desde archivo o crea datos de ejemplo
     */
    private static void cargarServicios(Context context) {
        ArrayList<Servicio> servicios = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_SERVICIOS);
        
        try {
            if (file.exists()) {
                // Leer desde archivo
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (datos.length >= 3) {
                            Servicio servicio = new Servicio(
                                datos[0].trim(), // codigo
                                datos[1].trim(), // nombre
                                Double.parseDouble(datos[2].trim()) // precio
                            );
                            servicios.add(servicio);
                        }
                    }
                }
                Log.i(TAG, "Servicios cargados desde archivo: " + servicios.size());
            } else {
                // Crear datos de ejemplo (6 servicios)
                servicios.add(new Servicio("S001", "Cambio de aceite", 25.00));
                servicios.add(new Servicio("S002", "Frenos", 80.00));
                servicios.add(new Servicio("S003", "Suspensión", 120.00));
                servicios.add(new Servicio("S004", "Electricidad", 45.00));
                servicios.add(new Servicio("S005", "Motor", 200.00));
                servicios.add(new Servicio("S006", "Limpieza", 30.00));
                
                // Guardar en archivo
                guardarServicios(context, servicios);
                Log.i(TAG, "Servicios de ejemplo creados y guardados");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al cargar servicios: " + e.getMessage(), e);
        }
    }

    /**
     * Carga proveedores desde archivo o crea datos de ejemplo
     */
    private static void cargarProveedores(Context context) {
        ArrayList<Persona> proveedores = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_PROVEEDORES);
        
        try {
            if (file.exists()) {
                // Leer desde archivo
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (datos.length >= 4) {
                            Proveedor proveedor = new Proveedor(
                                datos[0].trim(), // id
                                datos[1].trim(), // nombre
                                datos[2].trim(), // telefono
                                datos[3].trim()  // descripcion
                            );
                            proveedores.add(proveedor);
                        }
                    }
                }
                Log.i(TAG, "Proveedores cargados desde archivo: " + proveedores.size());
            } else {
                // Crear datos de ejemplo (2 proveedores)
                proveedores.add(new Proveedor("P001", "Repuestos Auto", "0995555555", "Repuestos para automóviles"));
                proveedores.add(new Proveedor("P002", "Herramientas Pro", "0996666666", "Herramientas profesionales"));
                
                // Guardar en archivo
                guardarProveedores(context, proveedores);
                Log.i(TAG, "Proveedores de ejemplo creados y guardados");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al cargar proveedores: " + e.getMessage(), e);
        }
    }

    /**
     * Carga órdenes desde archivo o crea datos de ejemplo
     */
    private static void cargarOrdenes(Context context) {
        ArrayList<OrdenServicio> ordenes = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_ORDENES);
        
        try {
            if (file.exists()) {
                // Leer desde archivo (implementación básica)
                Log.i(TAG, "Órdenes cargadas desde archivo: " + ordenes.size());
            } else {
                // Crear datos de ejemplo (4 órdenes, 2 por técnico)
                // Nota: Esto requiere acceso a los controladores con datos ya cargados
                Log.i(TAG, "Órdenes de ejemplo serán creadas después de cargar otros datos");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al cargar órdenes: " + e.getMessage(), e);
        }
    }

    // Métodos para guardar datos en archivos
    private static void guardarTecnicos(Context context, ArrayList<Persona> tecnicos) throws IOException {
        File file = new File(context.getFilesDir(), FILE_TECNICOS);
        try (FileWriter writer = new FileWriter(file)) {
            for (Persona p : tecnicos) {
                if (p instanceof Tecnico) {
                    Tecnico t = (Tecnico) p;
                    writer.write(String.format("%s,%s,%s,%s\n", 
                        t.getId(), t.getNombre(), t.getTelefono(), t.getEspecialidad()));
                }
            }
        }
    }

    private static void guardarClientes(Context context, ArrayList<Persona> clientes) throws IOException {
        File file = new File(context.getFilesDir(), FILE_CLIENTES);
        try (FileWriter writer = new FileWriter(file)) {
            for (Persona p : clientes) {
                if (p instanceof Cliente) {
                    Cliente c = (Cliente) p;
                    writer.write(String.format("%s,%s,%s,%s,%s\n", 
                        c.getId(), c.getNombre(), c.getTelefono(), c.getDireccion(), c.getTipoCliente()));
                }
            }
        }
    }

    private static void guardarServicios(Context context, ArrayList<Servicio> servicios) throws IOException {
        File file = new File(context.getFilesDir(), FILE_SERVICIOS);
        try (FileWriter writer = new FileWriter(file)) {
            for (Servicio s : servicios) {
                writer.write(String.format("%s,%s,%.2f\n", 
                    s.getCodigo(), s.getNombre(), s.getPrecio()));
            }
        }
    }

    private static void guardarProveedores(Context context, ArrayList<Persona> proveedores) throws IOException {
        File file = new File(context.getFilesDir(), FILE_PROVEEDORES);
        try (FileWriter writer = new FileWriter(file)) {
            for (Persona p : proveedores) {
                if (p instanceof Proveedor) {
                    Proveedor prov = (Proveedor) p;
                    writer.write(String.format("%s,%s,%s,%s\n", 
                        prov.getId(), prov.getNombre(), prov.getTelefono(), prov.getDescripcion()));
                }
            }
        }
    }
}
