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
import com.example.poo_p1_g08.modelo.Proveedor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControladorProveedor extends AppCompatActivity {
    private static final String TAG = "ControladorProveedor";
    private static final String FILE_PROVEEDORES = "proveedores.ser";
    
    private TextView tvProveedores;
    private Button btnAgregarProveedor, btnRegresar, btnGuardarProveedor;
    private ScrollView scrollViewListaProveedores, scrollViewProveedor;
    private EditText etIdProveedor, etNombreProveedor, etTelefonoProveedor, etDescripcionProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaproveedor);

        // Inicializar componentes
        inicializarComponentes();

        // Mostrar lista inicial
        mostrarListaProveedores();

        // Eventos de botones
        configurarEventos();
    }
    /**
     * Variables necesarias para cada accion
     */
    private void inicializarComponentes() {
        // TextViews
        tvProveedores = findViewById(R.id.tvProveedores);
        
        // Botones
        btnAgregarProveedor = findViewById(R.id.btnAgregarProveedor);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnGuardarProveedor = findViewById(R.id.btnGuardarProveedor);
        
        // ScrollViews
        scrollViewListaProveedores = findViewById(R.id.scrollViewListaProveedores);
        scrollViewProveedor = findViewById(R.id.scrollViewProveedor);
        
        // EditTexts
        etIdProveedor = findViewById(R.id.etIdProveedor);
        etNombreProveedor = findViewById(R.id.etNombreProveedor);
        etTelefonoProveedor = findViewById(R.id.etTelefonoProveedor);
        etDescripcionProveedor = findViewById(R.id.etDescripcionProveedor);
    }

    /**
     * Configuracion de los eventos mediante los botones en la pantalla
     */
    private void configurarEventos() {
        btnAgregarProveedor.setOnClickListener(v -> mostrarFormularioAgregarProveedor());
        btnRegresar.setOnClickListener(v -> {
            if (scrollViewProveedor.getVisibility() == View.VISIBLE) {
                regresarALista();
            } else {
                finish();
            }
        });
        btnGuardarProveedor.setOnClickListener(v -> crearProveedor());
    }
    /**
     * Permite ocultar y/o mostrar el formulario
     */
    private void mostrarFormularioAgregarProveedor() {
        scrollViewListaProveedores.setVisibility(View.GONE);
        scrollViewProveedor.setVisibility(View.VISIBLE);
        btnAgregarProveedor.setVisibility(View.GONE);
        btnGuardarProveedor.setVisibility(View.VISIBLE);
        
        // Limpiar campos
        limpiarCampos();
    }
    /**
     * Crea un nuevo Proveedor con los datos recopilados del scrollViewCliente
     */
    private void crearProveedor() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        try {
            String id = etIdProveedor.getText().toString().trim();
            String nombre = etNombreProveedor.getText().toString().trim();
            String telefono = etTelefonoProveedor.getText().toString().trim();
            String descripcion = etDescripcionProveedor.getText().toString().trim();

            // Crear proveedor
            Proveedor proveedor = new Proveedor(id, nombre, telefono, descripcion);

            // Verificar si ya existe
            Proveedor existente = buscarProveedorPorId(proveedor.getId());
            if (existente != null) {
                Toast.makeText(this, "Ya existe un proveedor con ese ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar proveedor
            if (guardarProveedor(proveedor)) {
                Toast.makeText(this, "Proveedor creado exitosamente", Toast.LENGTH_LONG).show();
                regresarALista();
            } else {
                Toast.makeText(this, "Error al crear el proveedor", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el proveedor", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Muestra un mensaje de aviso para llenar el dato faltante
     */
    private boolean validarCampos() {
        if (etIdProveedor.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del proveedor", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNombreProveedor.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del proveedor", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etTelefonoProveedor.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el teléfono del proveedor", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etDescripcionProveedor.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la descripción del proveedor", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Restablece los campos del formulario tras guardar el proveedor
     */
    private void limpiarCampos() {
        etIdProveedor.setText("");
        etNombreProveedor.setText("");
        etTelefonoProveedor.setText("");
        etDescripcionProveedor.setText("");
    }
    /**
     * Regresa a la vista anterior, en este caso, la lista de proveedores.
     */
    private void regresarALista() {
        // Mostrar lista y ocultar formulario
        scrollViewListaProveedores.setVisibility(View.VISIBLE);
        scrollViewProveedor.setVisibility(View.GONE);
        btnAgregarProveedor.setVisibility(View.VISIBLE);
        btnGuardarProveedor.setVisibility(View.GONE);
        
        // Actualizar lista
        mostrarListaProveedores();
    }

    /**
     * Metodo para mostrar la lista de clientes en el TextView
     */
    private void mostrarListaProveedores() {
        List<Proveedor> proveedores = obtenerTodosLosProveedores();
        
        if (proveedores == null || proveedores.isEmpty()) {
            tvProveedores.setText("No hay proveedores registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE PROVEEDORES:\n\n");
        
        for (Proveedor proveedor : proveedores) {
            sb.append(String.format("ID: %s\n", proveedor.getId()));
            sb.append(String.format("Nombre: %s\n", proveedor.getNombre()));
            sb.append(String.format("Teléfono: %s\n", proveedor.getTelefono()));
            sb.append(String.format("Descripción: %s\n", proveedor.getDescripcion()));
            sb.append("------------------------\n");
        }
        
        tvProveedores.setText(sb.toString());
    }

    
    /**
     * Guarda un proveedor en el archivo serializado
     */
    public boolean guardarProveedor(Proveedor proveedor) {
        try {
            List<Proveedor> proveedores = obtenerTodosLosProveedores();
            
            // Verificar si ya existe
            for (Proveedor p : proveedores) {
                if (p.getId().equalsIgnoreCase(proveedor.getId())) {
                    return false; // Ya existe
                }
            }
            
            proveedores.add(proveedor);
            return guardarProveedoresEnArchivo(proveedores);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene todos los proveedores desde el archivo serializado
     */
    @SuppressWarnings("unchecked")
    public List<Proveedor> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_PROVEEDORES);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            proveedores = (List<Proveedor>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }
        
        return proveedores;
    }
    
    /**
     * Busca un proveedor por ID
     */
    public Proveedor buscarProveedorPorId(String id) {
        List<Proveedor> proveedores = obtenerTodosLosProveedores();
        for (Proveedor p : proveedores) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }
    /**
     * Metodo que realiza la accion de guardar los datos
     */
    private boolean guardarProveedoresEnArchivo(List<Proveedor> proveedores) {
        try (FileOutputStream fos = openFileOutput(FILE_PROVEEDORES, MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(proveedores);
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
}
