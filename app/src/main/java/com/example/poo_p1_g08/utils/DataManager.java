package com.example.poo_p1_g08.utils;

import android.content.Context;
import android.util.Log;

import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.modelo.Persona;
import com.example.poo_p1_g08.modelo.Proveedor;
import com.example.poo_p1_g08.modelo.Servicio;
import com.example.poo_p1_g08.modelo.Tecnico;
import com.example.poo_p1_g08.modelo.DetalledelServicio;
import com.example.poo_p1_g08.modelo.Vehiculo;
import com.example.poo_p1_g08.modelo.FacturaEmpresa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para manejar la persistencia de datos usando archivos de texto
 */
public class DataManager {
    private static final String TAG = "DataManager";
    
    // Nombres de archivos de texto
    private static final String FILE_TECNICOS = "tecnicos.txt";
    private static final String FILE_CLIENTES = "clientes.txt";
    private static final String FILE_SERVICIOS = "servicios.txt";
    private static final String FILE_PROVEEDORES = "proveedores.txt";
    private static final String FILE_ORDENES = "ordenes.txt";

    /**
     * Inicializa la aplicación creando archivos de texto si no existen
     */
    public static void inicializarApp(Context context) {
        try {
            Log.i(TAG, "Iniciando inicialización de archivos de texto...");
            
            // Crear directorio de datos si no existe
            File dataDir = new File(context.getFilesDir(), "data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            // Crear archivos de texto con datos de ejemplo si no existen
            inicializarArchivoTecnicos(context);
            inicializarArchivoClientes(context);
            inicializarArchivoServicios(context);
            inicializarArchivoProveedores(context);
            inicializarArchivoOrdenes(context);
            inicializarArchivoFacturas(context); // Inicializar archivo de facturas
            
            Log.i(TAG, "Inicialización de archivos de texto completada exitosamente");
            
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar archivos: " + e.getMessage(), e);
        }
    }

    // ==================== TÉCNICOS ====================
    
    /**
     * Guarda un técnico en el archivo de texto
     */
    public static boolean guardarTecnico(Context context, Tecnico tecnico) {
        try {
            List<Tecnico> tecnicos = obtenerTodosLosTecnicos(context);
            
            // Verificar si ya existe
            for (Tecnico t : tecnicos) {
                if (t.getId().equalsIgnoreCase(tecnico.getId())) {
                    return false; // Ya existe
                }
            }
            
            tecnicos.add(tecnico);
            return guardarTecnicosEnArchivo(context, tecnicos);
            
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar técnico: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene todos los técnicos desde el archivo de texto
     */
    public static List<Tecnico> obtenerTodosLosTecnicos(Context context) {
        List<Tecnico> tecnicos = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_TECNICOS);
        
        if (file.exists()) {
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
            } catch (Exception e) {
                Log.e(TAG, "Error al leer técnicos: " + e.getMessage(), e);
            }
        }
        
        return tecnicos;
    }
    
    /**
     * Busca un técnico por ID
     */
    public static Tecnico buscarTecnicoPorId(Context context, String id) {
        List<Tecnico> tecnicos = obtenerTodosLosTecnicos(context);
        for (Tecnico t : tecnicos) {
            if (t.getId().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }
    
    private static void inicializarArchivoTecnicos(Context context) {
        File file = new File(context.getFilesDir(), FILE_TECNICOS);
        if (!file.exists()) {
            List<Tecnico> tecnicosEjemplo = new ArrayList<>();
            tecnicosEjemplo.add(new Tecnico("T001", "Juan Pérez", "0991234567", "Motor"));
            tecnicosEjemplo.add(new Tecnico("T002", "María López", "0997654321", "Electricidad"));
            tecnicosEjemplo.add(new Tecnico("T003", "Pedro Gómez", "0995554443", "Suspensión"));
            
            guardarTecnicosEnArchivo(context, tecnicosEjemplo);
            Log.i(TAG, "Archivo de técnicos creado con datos de ejemplo");
        }
    }
    
    private static boolean guardarTecnicosEnArchivo(Context context, List<Tecnico> tecnicos) {
        try {
            File file = new File(context.getFilesDir(), FILE_TECNICOS);
            try (FileWriter writer = new FileWriter(file)) {
                for (Tecnico t : tecnicos) {
                    writer.write(String.format("%s,%s,%s,%s\n", 
                        t.getId(), t.getNombre(), t.getTelefono(), t.getEspecialidad()));
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar técnicos en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== CLIENTES ====================
    
    /**
     * Guarda un cliente en el archivo de texto
     */
    public static boolean guardarCliente(Context context, Cliente cliente) {
        try {
            List<Cliente> clientes = obtenerTodosLosClientes(context);
            
            // Verificar si ya existe
            for (Cliente c : clientes) {
                if (c.getId().equalsIgnoreCase(cliente.getId())) {
                    return false; // Ya existe
                }
            }
            
            clientes.add(cliente);
            return guardarClientesEnArchivo(context, clientes);
            
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar cliente: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene todos los clientes desde el archivo de texto
     */
    public static List<Cliente> obtenerTodosLosClientes(Context context) {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_CLIENTES);
        
        if (file.exists()) {
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
            } catch (Exception e) {
                Log.e(TAG, "Error al leer clientes: " + e.getMessage(), e);
            }
        }
        
        return clientes;
    }
    
    /**
     * Busca un cliente por ID
     */
    public static Cliente buscarClientePorId(Context context, String id) {
        List<Cliente> clientes = obtenerTodosLosClientes(context);
        for (Cliente c : clientes) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Busca un cliente empresarial por ID
     */
    public static Cliente buscarClienteEmpresarialPorId(Context context, String id) {
        List<Cliente> clientes = obtenerTodosLosClientes(context);
        for (Cliente c : clientes) {
            if (c.getId().equalsIgnoreCase(id) && c.getTipoCliente()) {
                return c;
            }
        }
        return null;
    }
    
    private static void inicializarArchivoClientes(Context context) {
        File file = new File(context.getFilesDir(), FILE_CLIENTES);
        if (!file.exists()) {
            List<Cliente> clientesEjemplo = new ArrayList<>();
            clientesEjemplo.add(new Cliente("C001", "Carlos Ruiz", "0991111111", "Av. Principal 123", false));
            clientesEjemplo.add(new Cliente("C002", "Ana García", "0992222222", "Calle Secundaria 456", false));
            clientesEjemplo.add(new Cliente("C003", "Empresa ABC", "0993333333", "Zona Industrial 789", true));
            clientesEjemplo.add(new Cliente("C004", "Corporación XYZ", "0994444444", "Centro Comercial 321", true));
            
            guardarClientesEnArchivo(context, clientesEjemplo);
            Log.i(TAG, "Archivo de clientes creado con datos de ejemplo");
        }
    }
    
    private static boolean guardarClientesEnArchivo(Context context, List<Cliente> clientes) {
        try {
            File file = new File(context.getFilesDir(), FILE_CLIENTES);
            try (FileWriter writer = new FileWriter(file)) {
                for (Cliente c : clientes) {
                    writer.write(String.format("%s,%s,%s,%s,%s\n", 
                        c.getId(), c.getNombre(), c.getTelefono(), c.getDireccion(), c.getTipoCliente()));
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar clientes en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== SERVICIOS ====================
    
    /**
     * Guarda un servicio en el archivo de texto
     */
    public static boolean guardarServicio(Context context, Servicio servicio) {
        try {
            List<Servicio> servicios = obtenerTodosLosServicios(context);
            
            // Verificar si ya existe
            for (Servicio s : servicios) {
                if (s.getCodigo().equalsIgnoreCase(servicio.getCodigo())) {
                    return false; // Ya existe
                }
            }
            
            servicios.add(servicio);
            return guardarServiciosEnArchivo(context, servicios);
            
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar servicio: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene todos los servicios desde el archivo de texto
     */
    public static List<Servicio> obtenerTodosLosServicios(Context context) {
        List<Servicio> servicios = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_SERVICIOS);
        
        if (file.exists()) {
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
            } catch (Exception e) {
                Log.e(TAG, "Error al leer servicios: " + e.getMessage(), e);
            }
        }
        
        return servicios;
    }
    
    /**
     * Busca un servicio por código
     */
    public static Servicio buscarServicioPorCodigo(Context context, String codigo) {
        List<Servicio> servicios = obtenerTodosLosServicios(context);
        for (Servicio s : servicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Actualiza un servicio existente en el archivo
     */
    public static boolean actualizarServicio(Context context, Servicio servicioActualizado) {
        try {
            List<Servicio> servicios = obtenerTodosLosServicios(context);
            
            // Buscar y reemplazar el servicio
            for (int i = 0; i < servicios.size(); i++) {
                if (servicios.get(i).getCodigo().equalsIgnoreCase(servicioActualizado.getCodigo())) {
                    servicios.set(i, servicioActualizado);
                    return guardarServiciosEnArchivo(context, servicios);
                }
            }
            
            return false; // No se encontró el servicio
        } catch (Exception e) {
            Log.e(TAG, "Error al actualizar servicio: " + e.getMessage(), e);
            return false;
        }
    }
    
    private static void inicializarArchivoServicios(Context context) {
        File file = new File(context.getFilesDir(), FILE_SERVICIOS);
        if (!file.exists()) {
            List<Servicio> serviciosEjemplo = new ArrayList<>();
            serviciosEjemplo.add(new Servicio("S001", "Cambio de aceite", 25.00));
            serviciosEjemplo.add(new Servicio("S002", "Frenos", 80.00));
            serviciosEjemplo.add(new Servicio("S003", "Suspensión", 120.00));
            serviciosEjemplo.add(new Servicio("S004", "Electricidad", 45.00));
            serviciosEjemplo.add(new Servicio("S005", "Motor", 200.00));
            serviciosEjemplo.add(new Servicio("S006", "Limpieza", 30.00));
            
            guardarServiciosEnArchivo(context, serviciosEjemplo);
            Log.i(TAG, "Archivo de servicios creado con datos de ejemplo");
        }
    }
    
    private static boolean guardarServiciosEnArchivo(Context context, List<Servicio> servicios) {
        try {
            File file = new File(context.getFilesDir(), FILE_SERVICIOS);
            try (FileWriter writer = new FileWriter(file)) {
                for (Servicio s : servicios) {
                    writer.write(String.format("%s,%s,%.2f\n", 
                        s.getCodigo(), s.getNombre(), s.getPrecio()));
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar servicios en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== PROVEEDORES ====================
    
    /**
     * Guarda un proveedor en el archivo de texto
     */
    public static boolean guardarProveedor(Context context, Proveedor proveedor) {
        try {
            List<Proveedor> proveedores = obtenerTodosLosProveedores(context);
            
            // Verificar si ya existe
            for (Proveedor p : proveedores) {
                if (p.getId().equalsIgnoreCase(proveedor.getId())) {
                    return false; // Ya existe
                }
            }
            
            proveedores.add(proveedor);
            return guardarProveedoresEnArchivo(context, proveedores);
            
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar proveedor: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene todos los proveedores desde el archivo de texto
     */
    public static List<Proveedor> obtenerTodosLosProveedores(Context context) {
        List<Proveedor> proveedores = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_PROVEEDORES);
        
        if (file.exists()) {
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
            } catch (Exception e) {
                Log.e(TAG, "Error al leer proveedores: " + e.getMessage(), e);
            }
        }
        
        return proveedores;
    }
    
    /**
     * Busca un proveedor por ID
     */
    public static Proveedor buscarProveedorPorId(Context context, String id) {
        List<Proveedor> proveedores = obtenerTodosLosProveedores(context);
        for (Proveedor p : proveedores) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }
    
    private static void inicializarArchivoProveedores(Context context) {
        File file = new File(context.getFilesDir(), FILE_PROVEEDORES);
        if (!file.exists()) {
            List<Proveedor> proveedoresEjemplo = new ArrayList<>();
            proveedoresEjemplo.add(new Proveedor("P001", "Repuestos Auto", "0995555555", "Repuestos para automóviles"));
            proveedoresEjemplo.add(new Proveedor("P002", "Herramientas Pro", "0996666666", "Herramientas profesionales"));
            
            guardarProveedoresEnArchivo(context, proveedoresEjemplo);
            Log.i(TAG, "Archivo de proveedores creado con datos de ejemplo");
        }
    }
    
    private static boolean guardarProveedoresEnArchivo(Context context, List<Proveedor> proveedores) {
        try {
            File file = new File(context.getFilesDir(), FILE_PROVEEDORES);
            try (FileWriter writer = new FileWriter(file)) {
                for (Proveedor p : proveedores) {
                    writer.write(String.format("%s,%s,%s,%s\n", 
                        p.getId(), p.getNombre(), p.getTelefono(), p.getDescripcion()));
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar proveedores en archivo: " + e.getMessage(), e);
            return false;
        }
    }

    // ==================== ÓRDENES ====================
    
    /**
     * Guarda una orden en el archivo de texto
     */
    public static boolean guardarOrden(Context context, OrdenServicio orden) {
        try {
            List<OrdenServicio> ordenes = obtenerTodasLasOrdenes(context);
            ordenes.add(orden);
            return guardarOrdenesEnArchivo(context, ordenes);
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar orden: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene todas las órdenes desde el archivo de texto
     */
    public static List<OrdenServicio> obtenerTodasLasOrdenes(Context context) {
        List<OrdenServicio> ordenes = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_ORDENES);
        
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] datos = line.split("\\|");
                        if (datos.length >= 6) {
                            // Formato: codigo|clienteId|vehiculoPlaca|vehiculoTipo|tecnicoId|fecha|servicios
                            String codigo = datos[0].trim();
                            String clienteId = datos[1].trim();
                            String vehiculoPlaca = datos[2].trim();
                            String vehiculoTipo = datos[3].trim();
                            String tecnicoId = datos[4].trim();
                            String fecha = datos[5].trim();
                            
                            // Buscar cliente, técnico y crear vehículo
                            Cliente cliente = buscarClientePorId(context, clienteId);
                            Tecnico tecnico = buscarTecnicoPorId(context, tecnicoId);
                            
                            if (cliente != null && tecnico != null) {
                                // Crear vehículo (simplificado)
                                Vehiculo vehiculo = new Vehiculo(vehiculoPlaca, Vehiculo.TipoVehiculo.valueOf(vehiculoTipo));
                                
                                // Crear orden
                                OrdenServicio orden = new OrdenServicio(cliente, vehiculo, tecnico, fecha);
                                
                                // Agregar servicios si existen
                                if (datos.length > 6) {
                                    String[] serviciosData = datos[6].split(",");
                                    for (String servicioData : serviciosData) {
                                        if (!servicioData.trim().isEmpty()) {
                                            String[] servicioInfo = servicioData.split(":");
                                            if (servicioInfo.length >= 2) {
                                                String codigoServicio = servicioInfo[0].trim();
                                                int cantidad = Integer.parseInt(servicioInfo[1].trim());
                                                Servicio servicio = buscarServicioPorCodigo(context, codigoServicio);
                                                if (servicio != null) {
                                                    orden.agregarDetalle(servicio, cantidad);
                                                }
                                            }
                                        }
                                    }
                                }
                                
                                ordenes.add(orden);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error al leer órdenes: " + e.getMessage(), e);
            }
        }
        
        return ordenes;
    }
    
    private static void inicializarArchivoOrdenes(Context context) {
        File file = new File(context.getFilesDir(), FILE_ORDENES);
        if (!file.exists()) {
            // Crear 4 órdenes de ejemplo
            List<OrdenServicio> ordenesEjemplo = crearOrdenesEjemplo(context);
            guardarOrdenesEnArchivo(context, ordenesEjemplo);
            Log.i(TAG, "Archivo de órdenes creado con 4 órdenes de ejemplo");
        }
    }
    
    private static List<OrdenServicio> crearOrdenesEjemplo(Context context) {
        List<OrdenServicio> ordenes = new ArrayList<>();
        
        try {
            // Obtener datos necesarios
            List<Cliente> clientes = obtenerTodosLosClientes(context);
            List<Tecnico> tecnicos = obtenerTodosLosTecnicos(context);
            List<Servicio> servicios = obtenerTodosLosServicios(context);
            
            if (clientes.size() >= 2 && tecnicos.size() >= 2 && servicios.size() >= 2) {
                // Orden 1: Cliente particular, Técnico 1, 2 servicios
                Cliente cliente1 = clientes.get(0); // Primer cliente
                Tecnico tecnico1 = tecnicos.get(0); // Primer técnico
                Vehiculo vehiculo1 = new Vehiculo("ABC-123", Vehiculo.TipoVehiculo.AUTOMOVIL);
                OrdenServicio orden1 = new OrdenServicio(cliente1, vehiculo1, tecnico1, "2024-12-15");
                orden1.agregarDetalle(servicios.get(0), 1); // Cambio de aceite
                orden1.agregarDetalle(servicios.get(1), 1); // Frenos
                ordenes.add(orden1);
                
                // Orden 2: Cliente particular, Técnico 2, 2 servicios
                Cliente cliente2 = clientes.get(1); // Segundo cliente
                Tecnico tecnico2 = tecnicos.get(1); // Segundo técnico
                Vehiculo vehiculo2 = new Vehiculo("XYZ-789", Vehiculo.TipoVehiculo.MOTOCICLETA);
                OrdenServicio orden2 = new OrdenServicio(cliente2, vehiculo2, tecnico2, "2024-12-14");
                orden2.agregarDetalle(servicios.get(2), 1); // Suspensión
                orden2.agregarDetalle(servicios.get(3), 1); // Electricidad
                ordenes.add(orden2);
                
                // Orden 3: Cliente empresarial, Técnico 1, 2 servicios
                Cliente cliente3 = null;
                for (Cliente c : clientes) {
                    if (c.getTipoCliente()) { // Buscar cliente empresarial
                        cliente3 = c;
                        break;
                    }
                }
                if (cliente3 != null) {
                    Vehiculo vehiculo3 = new Vehiculo("DEF-456", Vehiculo.TipoVehiculo.BUS);
                    OrdenServicio orden3 = new OrdenServicio(cliente3, vehiculo3, tecnico1, "2024-12-13");
                    orden3.agregarDetalle(servicios.get(4), 1); // Motor
                    orden3.agregarDetalle(servicios.get(5), 1); // Limpieza
                    ordenes.add(orden3);
                }
                
                // Orden 4: Cliente empresarial, Técnico 2, 2 servicios
                Cliente cliente4 = null;
                for (Cliente c : clientes) {
                    if (c.getTipoCliente() && !c.getId().equals(cliente3 != null ? cliente3.getId() : "")) {
                        cliente4 = c;
                        break;
                    }
                }
                if (cliente4 != null) {
                    Vehiculo vehiculo4 = new Vehiculo("GHI-789", Vehiculo.TipoVehiculo.AUTOMOVIL);
                    OrdenServicio orden4 = new OrdenServicio(cliente4, vehiculo4, tecnico2, "2024-12-12");
                    orden4.agregarDetalle(servicios.get(0), 1); // Cambio de aceite
                    orden4.agregarDetalle(servicios.get(2), 1); // Suspensión
                    ordenes.add(orden4);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al crear órdenes de ejemplo: " + e.getMessage(), e);
        }
        
        return ordenes;
    }
    
    private static boolean guardarOrdenesEnArchivo(Context context, List<OrdenServicio> ordenes) {
        try {
            File file = new File(context.getFilesDir(), FILE_ORDENES);
            try (FileWriter writer = new FileWriter(file)) {
                for (OrdenServicio orden : ordenes) {
                    // Formato: codigo|clienteId|vehiculoPlaca|vehiculoTipo|tecnicoId|fecha|servicios
                    StringBuilder sb = new StringBuilder();
                    sb.append(orden.getCodigo()).append("|");
                    sb.append(orden.getCliente().getId()).append("|");
                    sb.append(orden.getVehiculo().getPlaca()).append("|");
                    sb.append(orden.getVehiculo().getTipo()).append("|");
                    sb.append(orden.getTecnico().getId()).append("|");
                    sb.append(orden.getFecha()).append("|");
                    
                    // Agregar servicios
                    ArrayList<DetalledelServicio> detalles = orden.getDetalle();
                    if (detalles != null && !detalles.isEmpty()) {
                        for (int i = 0; i < detalles.size(); i++) {
                            DetalledelServicio detalle = detalles.get(i);
                            if (i > 0) sb.append(",");
                            sb.append(detalle.getServicio().getCodigo()).append(":").append(detalle.getCantidad());
                        }
                    }
                    
                    writer.write(sb.toString() + "\n");
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar órdenes en archivo: " + e.getMessage(), e);
            return false;
        }
    }
    
    // ==================== FACTURAS DE EMPRESA ====================
    
    private static final String FILE_FACTURAS = "facturas.txt";
    
    /**
     * Genera una factura mensual para un cliente empresarial
     */
    public static FacturaEmpresa generarFacturaEmpresa(Context context, String clienteId, int año, int mes) {
        try {
            // Buscar el cliente empresarial
            Cliente cliente = buscarClienteEmpresarialPorId(context, clienteId);
            if (cliente == null) {
                Log.e(TAG, "Cliente empresarial no encontrado: " + clienteId);
                return null;
            }
            
            // Obtener todas las órdenes del cliente en el mes y año especificado
            List<OrdenServicio> ordenesCliente = obtenerOrdenesClientePorMes(context, clienteId, año, mes);
            
            if (ordenesCliente.isEmpty()) {
                Log.i(TAG, "No hay órdenes para el cliente " + clienteId + " en " + mes + "/" + año);
                return null;
            }
            
            // Calcular total de servicios
            double totalServicios = 0.0;
            for (OrdenServicio orden : ordenesCliente) {
                totalServicios += orden.getTotal();
            }
            
            // Agregar cargo mensual de $50 por prioridad
            double cargoMensual = 50.0;
            double totalFactura = totalServicios + cargoMensual;
            
            // Generar código de factura
            String codigoFactura = generarCodigoFactura(context);
            
            // Crear factura
            FacturaEmpresa factura = new FacturaEmpresa(cliente, mes, año);
            // Agregar órdenes
            for (OrdenServicio orden : ordenesCliente) {
                factura.agregarOrden(orden);
            }
            
            // Guardar factura
            if (guardarFactura(context, factura)) {
                Log.i(TAG, "Factura generada exitosamente: " + codigoFactura);
                return factura;
            } else {
                Log.e(TAG, "Error al guardar la factura");
                return null;
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error al generar factura: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Obtiene las órdenes de un cliente en un mes y año específico
     */
    private static List<OrdenServicio> obtenerOrdenesClientePorMes(Context context, String clienteId, int año, int mes) {
        List<OrdenServicio> ordenesCliente = new ArrayList<>();
        List<OrdenServicio> todasLasOrdenes = obtenerTodasLasOrdenes(context);
        
        for (OrdenServicio orden : todasLasOrdenes) {
            if (orden.getCliente().getId().equals(clienteId)) {
                // Parsear fecha (formato: YYYY-MM-DD)
                String fecha = orden.getFecha();
                if (fecha != null && fecha.length() >= 7) {
                    try {
                        int ordenAño = Integer.parseInt(fecha.substring(0, 4));
                        int ordenMes = Integer.parseInt(fecha.substring(5, 7));
                        
                        if (ordenAño == año && ordenMes == mes) {
                            ordenesCliente.add(orden);
                        }
                    } catch (NumberFormatException e) {
                        Log.w(TAG, "Error al parsear fecha: " + fecha);
                    }
                }
            }
        }
        
        return ordenesCliente;
    }
    
    /**
     * Genera un código único para la factura
     */
    private static String generarCodigoFactura(Context context) {
        List<FacturaEmpresa> facturas = obtenerTodasLasFacturas(context);
        int siguienteNumero = facturas.size() + 1;
        return String.format("F%03d", siguienteNumero);
    }
    
    /**
     * Guarda una factura en el archivo
     */
    public static boolean guardarFactura(Context context, FacturaEmpresa factura) {
        try {
            List<FacturaEmpresa> facturas = obtenerTodasLasFacturas(context);
            facturas.add(factura);
            return guardarFacturasEnArchivo(context, facturas);
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar factura: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene todas las facturas
     */
    public static List<FacturaEmpresa> obtenerTodasLasFacturas(Context context) {
        List<FacturaEmpresa> facturas = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_FACTURAS);
        
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] datos = line.split("\\|");
                        if (datos.length >= 8) {
                            // Formato: codigo|clienteId|año|mes|totalServicios|cargoMensual|totalFactura|ordenes
                            String codigo = datos[0].trim();
                            String clienteId = datos[1].trim();
                            int año = Integer.parseInt(datos[2].trim());
                            int mes = Integer.parseInt(datos[3].trim());
                            double totalServicios = Double.parseDouble(datos[4].trim());
                            double cargoMensual = Double.parseDouble(datos[5].trim());
                            double totalFactura = Double.parseDouble(datos[6].trim());
                            
                            Cliente cliente = buscarClientePorId(context, clienteId);
                            if (cliente != null) {
                                // Crear factura (simplificada)
                                FacturaEmpresa factura = new FacturaEmpresa(cliente, mes, año);
                                facturas.add(factura);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error al leer facturas: " + e.getMessage(), e);
            }
        }
        
        return facturas;
    }
    
    /**
     * Guarda todas las facturas en el archivo
     */
    private static boolean guardarFacturasEnArchivo(Context context, List<FacturaEmpresa> facturas) {
        try {
            File file = new File(context.getFilesDir(), FILE_FACTURAS);
            try (FileWriter writer = new FileWriter(file)) {
                for (FacturaEmpresa factura : facturas) {
                    // Formato: codigo|clienteId|año|mes|totalServicios|cargoMensual|totalFactura|ordenes
                    StringBuilder sb = new StringBuilder();
                    sb.append("F" + facturas.indexOf(factura) + 1).append("|");
                    sb.append(factura.getCliente().getId()).append("|");
                    sb.append(factura.getAño()).append("|");
                    sb.append(factura.getMes()).append("|");
                    sb.append(factura.getTotalServicios()).append("|");
                    sb.append(FacturaEmpresa.getCostoPrioridad()).append("|");
                    sb.append(factura.getTotalFactura()).append("|");
                    
                    // Por ahora no guardamos las órdenes individuales para simplificar
                    writer.write(sb.toString() + "\n");
                }
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar facturas en archivo: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Inicializa el archivo de facturas
     */
    private static void inicializarArchivoFacturas(Context context) {
        File file = new File(context.getFilesDir(), FILE_FACTURAS);
        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("");
                writer.close();
                Log.i(TAG, "Archivo de facturas creado");
            } catch (IOException e) {
                Log.e(TAG, "Error al crear archivo de facturas: " + e.getMessage(), e);
            }
        }
    }
}
