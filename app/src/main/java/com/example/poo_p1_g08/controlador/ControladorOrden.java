package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.modelo.Servicio;
import com.example.poo_p1_g08.modelo.Tecnico;
import com.example.poo_p1_g08.modelo.Vehiculo;
import com.example.poo_p1_g08.modelo.DetalledelServicio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

public class ControladorOrden extends AppCompatActivity {
    private static final String TAG = "ControladorOrden";
    private static final String FILE_ORDENES = "ordenes.ser";
    private static final String FILE_CLIENTES = "clientes.ser";
    private static final String FILE_TECNICOS = "tecnicos.ser";
    private static final String FILE_SERVICIOS = "servicios.ser";
    
    private TextView tvListaOrdenes;
    private Button btnCrearOrden, btnRegresar, btnGenerarOrden, btnAgregarServicio;
    private ScrollView scrollView, scrollViewCrearOrden;
    
    // Componentes para crear orden
    private EditText etClienteId, etFecha, etPlaca, etTipoVehiculo, etTecnicoId;
    private EditText etServicioCodigo, etCantidad;
    private TextView tvServiciosAgregados, tvTotal;
    
    // Lista temporal de servicios para la orden
    private List<DetalledelServicio> serviciosTemporales = new ArrayList<>();
    private double totalTemporal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaordenes);

        // Inicializar componentes
        inicializarComponentes();

        // Mostrar lista inicial
        mostrarListaOrdenes();

        // Eventos de botones
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Componentes principales
        tvListaOrdenes = findViewById(R.id.tvListaOrdenes);
        btnCrearOrden = findViewById(R.id.btnCrearOrden);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnGenerarOrden = findViewById(R.id.btnGenerarOrden);
        btnAgregarServicio = findViewById(R.id.btnAgregarServicio);
        scrollView = findViewById(R.id.scrollView);
        scrollViewCrearOrden = findViewById(R.id.scrollViewCrearOrden);
        
        // Componentes para crear orden
        etClienteId = findViewById(R.id.etClienteId);
        etFecha = findViewById(R.id.etFecha);
        etPlaca = findViewById(R.id.etPlaca);
        etTipoVehiculo = findViewById(R.id.etTipoVehiculo);
        etTecnicoId = findViewById(R.id.etTecnicoId);
        etServicioCodigo = findViewById(R.id.etServicioCodigo);
        etCantidad = findViewById(R.id.etCantidad);
        tvServiciosAgregados = findViewById(R.id.tvServiciosAgregados);
        tvTotal = findViewById(R.id.tvTotal);
    }

    private void configurarEventos() {
        btnCrearOrden.setOnClickListener(v -> mostrarVistaCrearOrden());
        btnRegresar.setOnClickListener(v -> {
            if (scrollViewCrearOrden.getVisibility() == View.VISIBLE) {
                regresarALista();
            } else {
                finish();
            }
        });
        btnGenerarOrden.setOnClickListener(v -> generarOrden());
        btnAgregarServicio.setOnClickListener(v -> agregarServicioTemporal());
    }

    // Mostrar vista de creación de orden
    private void mostrarVistaCrearOrden() {
        scrollView.setVisibility(View.GONE);
        scrollViewCrearOrden.setVisibility(View.VISIBLE);
        btnCrearOrden.setVisibility(View.GONE);
        btnGenerarOrden.setVisibility(View.VISIBLE);
        
        // Limpiar campos y listas
        limpiarCampos();
        serviciosTemporales.clear();
        totalTemporal = 0.0;
        actualizarVistaServicios();
        
        // Mostrar datos disponibles
        mostrarDatosDisponibles();
    }

    private void mostrarDatosDisponibles() {
        StringBuilder datos = new StringBuilder();
        
        // Mostrar clientes disponibles
        List<Cliente> clientes = obtenerTodosLosClientes();
        datos.append("CLIENTES:\n");
        for (Cliente cliente : clientes) {
            String tipo = cliente.getTipoCliente() ? "EMPRESARIAL" : "PARTICULAR";
            datos.append(String.format("%s (%s) - %s\n", cliente.getNombre(), cliente.getId(), tipo));
        }
        
        // Mostrar técnicos disponibles
        List<Tecnico> tecnicos = obtenerTodosLosTecnicos();
        datos.append("\nTÉCNICOS:\n");
        for (Tecnico tecnico : tecnicos) {
            datos.append(String.format("%s (%s) - %s\n", tecnico.getNombre(), tecnico.getId(), tecnico.getEspecialidad()));
        }
        
        // Mostrar servicios disponibles
        List<Servicio> servicios = obtenerTodosLosServicios();
        datos.append("\nSERVICIOS:\n");
        for (Servicio servicio : servicios) {
            datos.append(String.format("%s (%s) - $%.2f\n", servicio.getNombre(), servicio.getCodigo(), servicio.getPrecio()));
        }
        
        datos.append("\nTIPOS DE VEHÍCULO: AUTOMOVIL, MOTOCICLETA, BUS");
        
        tvListaOrdenes.setText(datos.toString());
    }

    private void agregarServicioTemporal() {
        String codigoServicio = etServicioCodigo.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        
        if (codigoServicio.isEmpty() || cantidadStr.isEmpty()) {
            return;
        }
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                return;
            }
            
            // Buscar el servicio
            Servicio servicio = buscarServicioPorCodigo(codigoServicio);
            if (servicio == null) {
                return;
            }
            
            // Crear detalle del servicio
            DetalledelServicio detalle = new DetalledelServicio(servicio, cantidad);
            serviciosTemporales.add(detalle);
            totalTemporal += detalle.getSubtotal();
            
            // Limpiar campos de servicio
            etServicioCodigo.setText("");
            etCantidad.setText("1");
            
            // Actualizar vista
            actualizarVistaServicios();
            
        } catch (NumberFormatException e) {
            // Error en formato de número
        }
    }

    private void actualizarVistaServicios() {
        if (serviciosTemporales.isEmpty()) {
            tvServiciosAgregados.setText("No hay servicios agregados");
            tvTotal.setText("Total: $0.00");
        } else {
            StringBuilder sb = new StringBuilder();
            for (DetalledelServicio detalle : serviciosTemporales) {
                sb.append(String.format("• %s - Cantidad: %d - Subtotal: $%.2f\n", 
                    detalle.getServicio().getNombre(), 
                    detalle.getCantidad(), 
                    detalle.getSubtotal()));
            }
            tvServiciosAgregados.setText(sb.toString());
            tvTotal.setText(String.format("Total: $%.2f", totalTemporal));
        }
    }

    private void generarOrden() {
        if (!validarCampos()) {
            return;
        }
        
        if (serviciosTemporales.isEmpty()) {
            return;
        }

        try {
            // Obtener datos del formulario
            String clienteId = etClienteId.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String placa = etPlaca.getText().toString().trim();
            String tipoVehiculoStr = etTipoVehiculo.getText().toString().trim().toUpperCase();
            String tecnicoId = etTecnicoId.getText().toString().trim();
            
            // Buscar cliente
            Cliente cliente = buscarClientePorId(clienteId);
            if (cliente == null) {
                return;
            }
            
            // Buscar técnico
            Tecnico tecnico = buscarTecnicoPorId(tecnicoId);
            if (tecnico == null) {
                return;
            }
            
            // Crear vehículo
            Vehiculo.TipoVehiculo tipoVehiculo;
            switch (tipoVehiculoStr) {
                case "AUTOMOVIL": tipoVehiculo = Vehiculo.TipoVehiculo.AUTOMOVIL; break;
                case "MOTOCICLETA": tipoVehiculo = Vehiculo.TipoVehiculo.MOTOCICLETA; break;
                case "BUS": tipoVehiculo = Vehiculo.TipoVehiculo.BUS; break;
                default:
                    return;
            }
            
            Vehiculo vehiculo = new Vehiculo(placa, tipoVehiculo);
            
            // Crear orden
            OrdenServicio nuevaOrden = new OrdenServicio(cliente, vehiculo, tecnico, fecha);
            
            // Agregar todos los servicios temporales
            for (DetalledelServicio detalle : serviciosTemporales) {
                nuevaOrden.agregarDetalle(detalle.getServicio(), detalle.getCantidad());
            }
            
            // Guardar orden
            if (guardarOrden(nuevaOrden)) {
                regresarALista();
            }
            
        } catch (Exception e) {
            // Error al crear orden
        }
    }

    private boolean validarCampos() {
        if (etClienteId.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etFecha.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etPlaca.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etTipoVehiculo.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etTecnicoId.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        etClienteId.setText("");
        etFecha.setText("");
        etPlaca.setText("");
        etTipoVehiculo.setText("");
        etTecnicoId.setText("");
        etServicioCodigo.setText("");
        etCantidad.setText("1");
    }

    private void regresarALista() {
        scrollView.setVisibility(View.VISIBLE);
        scrollViewCrearOrden.setVisibility(View.GONE);
        btnCrearOrden.setVisibility(View.VISIBLE);
        btnGenerarOrden.setVisibility(View.GONE);
        
        // Actualizar lista
        mostrarListaOrdenes();
    }

    // Método para mostrar la lista de órdenes en el TextView
    private void mostrarListaOrdenes() {
        List<OrdenServicio> ordenes = obtenerTodasLasOrdenes();
        
        if (ordenes == null || ordenes.isEmpty()) {
            tvListaOrdenes.setText("No hay órdenes registradas");
            return;
        }

        // Ordenar las órdenes por fecha (más recientes primero)
        ordenes.sort((o1, o2) -> {
            try {
                // Parsear fechas en formato YYYY-MM-DD
                String fecha1 = o1.getFecha();
                String fecha2 = o2.getFecha();
                
                // Comparar en orden descendente (más reciente primero)
                return fecha2.compareTo(fecha1);
            } catch (Exception e) {
                return 0; // Si hay error, mantener orden original
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE ÓRDENES (Ordenadas por fecha - más recientes primero):\n\n");
        
        for (OrdenServicio orden : ordenes) {
            sb.append(String.format("Código: %s\n", orden.getCodigo()));
            sb.append(String.format("Cliente: %s\n", orden.getCliente().getNombre()));
            sb.append(String.format("Vehículo: %s (%s)\n", orden.getVehiculo().getPlaca(), orden.getVehiculo().getTipo()));
            sb.append(String.format("Técnico: %s\n", orden.getTecnico().getNombre()));
            sb.append(String.format("Fecha: %s\n", orden.getFecha()));
            sb.append(String.format("Total: $%.2f\n", orden.getTotal()));
            sb.append("------------------------\n");
        }
        
        tvListaOrdenes.setText(sb.toString());
    }

    // Método para obtener la lista de órdenes (requerido por ControladorFacturaEmpresa)
    public List<OrdenServicio> getListaOrdenes() {
        return obtenerTodasLasOrdenes();
    }

    // ==================== PERSISTENCIA DE ÓRDENES ====================
    
    /**
     * Guarda una orden en el archivo serializado
     */
    public boolean guardarOrden(OrdenServicio orden) {
        try {
            List<OrdenServicio> ordenes = obtenerTodasLasOrdenes();
            ordenes.add(orden);
            return guardarOrdenesEnArchivo(ordenes);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene todas las órdenes desde el archivo serializado
     */
    @SuppressWarnings("unchecked")
    public List<OrdenServicio> obtenerTodasLasOrdenes() {
        List<OrdenServicio> ordenes = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_ORDENES);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            ordenes = (List<OrdenServicio>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }
        
        return ordenes;
    }
    
    private boolean guardarOrdenesEnArchivo(List<OrdenServicio> ordenes) {
        try (FileOutputStream fos = openFileOutput(FILE_ORDENES, MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(ordenes);
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }

    // ==================== PERSISTENCIA DE CLIENTES ====================
    
    @SuppressWarnings("unchecked")
    private List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_CLIENTES);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            clientes = (List<Cliente>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }
        
        return clientes;
    }
    
    private Cliente buscarClientePorId(String id) {
        List<Cliente> clientes = obtenerTodosLosClientes();
        for (Cliente c : clientes) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // ==================== PERSISTENCIA DE TÉCNICOS ====================
    
    @SuppressWarnings("unchecked")
    private List<Tecnico> obtenerTodosLosTecnicos() {
        List<Tecnico> tecnicos = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_TECNICOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            tecnicos = (List<Tecnico>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }
        
        return tecnicos;
    }
    
    private Tecnico buscarTecnicoPorId(String id) {
        List<Tecnico> tecnicos = obtenerTodosLosTecnicos();
        for (Tecnico t : tecnicos) {
            if (t.getId().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    // ==================== PERSISTENCIA DE SERVICIOS ====================
    
    @SuppressWarnings("unchecked")
    private List<Servicio> obtenerTodosLosServicios() {
        List<Servicio> servicios = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_SERVICIOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            servicios = (List<Servicio>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }
        
        return servicios;
    }
    
    private Servicio buscarServicioPorCodigo(String codigo) {
        List<Servicio> servicios = obtenerTodosLosServicios();
        for (Servicio s : servicios) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }
}
