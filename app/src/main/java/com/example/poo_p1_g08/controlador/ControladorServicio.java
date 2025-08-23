package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Servicio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControladorServicio extends AppCompatActivity {
    private static final String TAG = "ControladorServicio";
    private static final String FILE_SERVICIOS = "servicios.ser";
    
    private TextView tvListaServicios;
    private Button btnAgregarServicio, btnEditarServicio, btnCrearServicio, btnActualizarServicio, btnRegresar;
    private ScrollView scrollView, scrollViewAgregarServicio, scrollViewEditarServicio;
    private EditText etNombreServicio, etPrecioServicio;
    private EditText etCodigoServicioEditar, etNuevoPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaservicios);

        // Inicializar componentes
        inicializarComponentes();

        // Mostrar lista inicial
        mostrarListaServicios();

        // Eventos de botones
        configurarEventos();
    }

    private void inicializarComponentes() {
        // TextViews
        tvListaServicios = findViewById(R.id.tvListaServicios);
        
        // Botones
        btnAgregarServicio = findViewById(R.id.btnAgregarServicio);
        btnEditarServicio = findViewById(R.id.btnEditarServicio);
        btnCrearServicio = findViewById(R.id.btnCrearServicio);
        btnActualizarServicio = findViewById(R.id.btnActualizarServicio);
        btnRegresar = findViewById(R.id.btnRegresar);
        
        // ScrollViews
        scrollView = findViewById(R.id.scrollView);
        scrollViewAgregarServicio = findViewById(R.id.scrollViewAgregarServicio);
        scrollViewEditarServicio = findViewById(R.id.scrollViewEditarServicio);
        
        // EditTexts para agregar servicio
        etNombreServicio = findViewById(R.id.etNombreServicio);
        etPrecioServicio = findViewById(R.id.etPrecioServicio);
        
        // EditTexts para editar servicio
        etCodigoServicioEditar = findViewById(R.id.etCodigoServicioEditar);
        etNuevoPrecio = findViewById(R.id.etNuevoPrecio);
        
        // Cambiar el texto del botón de editar
        btnEditarServicio.setText("Editar Precio");
    }

    private void configurarEventos() {
        btnAgregarServicio.setOnClickListener(v -> mostrarFormularioAgregarServicio());
        btnEditarServicio.setOnClickListener(v -> mostrarFormularioEditarServicio());
        btnCrearServicio.setOnClickListener(v -> crearServicio());
        btnActualizarServicio.setOnClickListener(v -> actualizarServicio());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void mostrarFormularioAgregarServicio() {
        // Ocultar lista y mostrar formulario de agregar
        scrollView.setVisibility(View.GONE);
        scrollViewAgregarServicio.setVisibility(View.VISIBLE);
        scrollViewEditarServicio.setVisibility(View.GONE);
        
        btnAgregarServicio.setVisibility(View.GONE);
        btnEditarServicio.setVisibility(View.GONE);
        btnCrearServicio.setVisibility(View.VISIBLE);
        btnActualizarServicio.setVisibility(View.GONE);
        
        // Limpiar campos
        limpiarCamposAgregar();
    }

    private void mostrarFormularioEditarServicio() {
        // Ocultar lista y mostrar formulario de editar
        scrollView.setVisibility(View.GONE);
        scrollViewAgregarServicio.setVisibility(View.GONE);
        scrollViewEditarServicio.setVisibility(View.VISIBLE);
        
        btnAgregarServicio.setVisibility(View.GONE);
        btnEditarServicio.setVisibility(View.GONE);
        btnCrearServicio.setVisibility(View.GONE);
        btnActualizarServicio.setVisibility(View.VISIBLE);
        
        // Limpiar campos
        limpiarCamposEditar();
    }

    private void crearServicio() {
        // Validar campos
        if (!validarCamposAgregar()) {
            return;
        }

        try {
            String nombre = etNombreServicio.getText().toString().trim();
            double precio = Double.parseDouble(etPrecioServicio.getText().toString().trim());

            // Generar código automáticamente
            String codigo = generarCodigoServicio();

            // Crear y guardar el servicio
            Servicio nuevoServicio = new Servicio(codigo, nombre, precio);
            if (guardarServicio(nuevoServicio)) {
                Toast.makeText(this, "Servicio creado exitosamente", Toast.LENGTH_LONG).show();
                regresarALista();
            } else {
                Toast.makeText(this, "Error al crear el servicio", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el servicio", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarServicio() {
        // Validar campos
        if (!validarCamposEditar()) {
            return;
        }

        try {
            String codigo = etCodigoServicioEditar.getText().toString().trim();
            double nuevoPrecio = Double.parseDouble(etNuevoPrecio.getText().toString().trim());

            // Buscar el servicio y actualizarlo
            Servicio servicio = buscarServicioPorCodigo(codigo);
            if (servicio != null) {
                // Actualizar el precio del servicio
                servicio.setPrecio(nuevoPrecio);
                
                // Guardar el servicio actualizado en el archivo
                if (actualizarServicio(servicio)) {
                    Toast.makeText(this, "Precio actualizado exitosamente", Toast.LENGTH_LONG).show();
                    regresarALista();
                } else {
                    Toast.makeText(this, "Error al guardar el precio actualizado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Servicio no encontrado", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Toast.makeText(this, "Error al actualizar el servicio", Toast.LENGTH_SHORT).show();
        }
    }

    private String generarCodigoServicio() {
        // Generar código automáticamente basado en la cantidad de servicios existentes
        List<Servicio> servicios = obtenerTodosLosServicios();
        int siguienteNumero = servicios.size() + 1;
        return String.format("S%03d", siguienteNumero);
    }

    private boolean validarCamposAgregar() {
        if (etNombreServicio.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del servicio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPrecioServicio.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el precio del servicio", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            double precio = Double.parseDouble(etPrecioServicio.getText().toString().trim());
            if (precio <= 0) {
                Toast.makeText(this, "El precio debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un precio válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarCamposEditar() {
        if (etCodigoServicioEditar.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el código del servicio a editar", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNuevoPrecio.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nuevo precio", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            double precio = Double.parseDouble(etNuevoPrecio.getText().toString().trim());
            if (precio <= 0) {
                Toast.makeText(this, "El precio debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un precio válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limpiarCamposAgregar() {
        etNombreServicio.setText("");
        etPrecioServicio.setText("");
    }

    private void limpiarCamposEditar() {
        etCodigoServicioEditar.setText("");
        etNuevoPrecio.setText("");
    }

    private void regresarALista() {
        // Mostrar lista y ocultar formularios
        scrollView.setVisibility(View.VISIBLE);
        scrollViewAgregarServicio.setVisibility(View.GONE);
        scrollViewEditarServicio.setVisibility(View.GONE);
        
        // Mostrar botones principales
        btnAgregarServicio.setVisibility(View.VISIBLE);
        btnEditarServicio.setVisibility(View.VISIBLE);
        btnCrearServicio.setVisibility(View.GONE);
        btnActualizarServicio.setVisibility(View.GONE);
        
        // Actualizar lista
        mostrarListaServicios();
    }

    // Método para mostrar la lista de servicios en el TextView
    private void mostrarListaServicios() {
        List<Servicio> servicios = obtenerTodosLosServicios();
        
        if (servicios == null || servicios.isEmpty()) {
            tvListaServicios.setText("No hay servicios registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE SERVICIOS:\n\n");
        
        for (Servicio servicio : servicios) {
            sb.append(String.format("Código: %s\n", servicio.getCodigo()));
            sb.append(String.format("Nombre: %s\n", servicio.getNombre()));
            sb.append(String.format("Precio: $%.2f\n", servicio.getPrecio()));
            sb.append("------------------------\n");
        }
        
        tvListaServicios.setText(sb.toString());
    }

    // ==================== PERSISTENCIA DE SERVICIOS ====================
    
    /**
     * Guarda un servicio en el archivo serializado
     */
    public boolean guardarServicio(Servicio servicio) {
        try {
            List<Servicio> servicios = obtenerTodosLosServicios();
            
            // Verificar si ya existe
            for (Servicio s : servicios) {
                if (s.getCodigo().equalsIgnoreCase(servicio.getCodigo())) {
                    return false; // Ya existe
                }
            }
            
            servicios.add(servicio);
            return guardarServiciosEnArchivo(servicios);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene todos los servicios desde el archivo serializado
     */
    @SuppressWarnings("unchecked")
    public List<Servicio> obtenerTodosLosServicios() {
        List<Servicio> servicios = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_SERVICIOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            servicios = (List<Servicio>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }
        
        return servicios;
    }
    
    /**
     * Busca un servicio por código
     */
    public Servicio buscarServicioPorCodigo(String codigo) {
        List<Servicio> servicios = obtenerTodosLosServicios();
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
    public boolean actualizarServicio(Servicio servicioActualizado) {
        try {
            List<Servicio> servicios = obtenerTodosLosServicios();
            
            // Buscar y reemplazar el servicio
            for (int i = 0; i < servicios.size(); i++) {
                if (servicios.get(i).getCodigo().equalsIgnoreCase(servicioActualizado.getCodigo())) {
                    servicios.set(i, servicioActualizado);
                    return guardarServiciosEnArchivo(servicios);
                }
            }
            
            return false; // No se encontró el servicio
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean guardarServiciosEnArchivo(List<Servicio> servicios) {
        try (FileOutputStream fos = openFileOutput(FILE_SERVICIOS, MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(servicios);
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
}

