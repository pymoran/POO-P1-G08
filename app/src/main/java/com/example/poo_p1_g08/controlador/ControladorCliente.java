package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControladorCliente extends AppCompatActivity {
    private static final String TAG = "ControladorCliente";
    private static final String FILE_CLIENTES = "clientes.ser";
    
    private TextView tvClientes;
    private Button btnAgregarCliente, btnRegresar, btnGuardarCliente;
    private ScrollView scrollViewListaClientes, scrollViewCliente;
    private EditText etIdCliente, etNombreCliente, etTelefonoCliente, etDireccionCliente;
    private RadioGroup rgTipoCliente;
    private RadioButton rbClienteIndividual, rbClienteEmpresarial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacliente);

        // Inicializar componentes
        inicializarComponentes();

        // Mostrar lista inicial
        mostrarListaClientes();

        // Eventos de botones
        configurarEventos();
    }
    /**
     * Variables necesarias para cada accion
     */
    private void inicializarComponentes() {
        // TextViews
        tvClientes = findViewById(R.id.tvClientes);//Ver el texview
        
        // Botones
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnGuardarCliente = findViewById(R.id.btnGuardarCliente);
        
        // ScrollViews
        scrollViewListaClientes = findViewById(R.id.scrollViewListaClientes); // visualizar la lista cliente
        scrollViewCliente = findViewById(R.id.scrollViewCliente);// formulario para agregar cliente
        
        // EditTexts
        etIdCliente = findViewById(R.id.etIdCliente);
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etTelefonoCliente = findViewById(R.id.etTelefonoCliente);
        etDireccionCliente = findViewById(R.id.etDireccionCliente);
        
        // RadioGroup y RadioButtons
        rgTipoCliente = findViewById(R.id.rgTipoCliente);
        rbClienteIndividual = findViewById(R.id.rbClienteIndividual);
        rbClienteEmpresarial = findViewById(R.id.rbClienteEmpresarial);
    }
    /**
     * Configuracion de los eventos mediante los botones en la pantalla
     */
    private void configurarEventos() {
        btnAgregarCliente.setOnClickListener(v -> mostrarFormularioAgregarCliente());
        btnRegresar.setOnClickListener(v -> {
            if (scrollViewCliente.getVisibility() == View.VISIBLE) {
                regresarALista();
            } else {
                finish();
            }
        });
        btnGuardarCliente.setOnClickListener(v -> crearCliente());
    }
    /**
     * Permite ocultar y/o mostrar el formulario
     */
    private void mostrarFormularioAgregarCliente() {
        scrollViewListaClientes.setVisibility(View.GONE);
        scrollViewCliente.setVisibility(View.VISIBLE);
        btnAgregarCliente.setVisibility(View.GONE);
        btnGuardarCliente.setVisibility(View.VISIBLE);
        
        // Limpiar campos
        limpiarCampos();
    }
    /**
     * Crea un nuevo cliente con los datos recopilados del scrollViewCliente
     */
    private void crearCliente() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        try {
            String id = etIdCliente.getText().toString().trim();
            String nombre = etNombreCliente.getText().toString().trim();
            String telefono = etTelefonoCliente.getText().toString().trim();
            String direccion = etDireccionCliente.getText().toString().trim();
            boolean tipoCliente = rbClienteEmpresarial.isChecked(); // true = Empresarial, false = Individual

            // Crear cliente
            Cliente CLnuevo = new Cliente(id, nombre, telefono, direccion, tipoCliente);

            // Verificar si ya existe
            Cliente existente = buscarClientePorId(CLnuevo.getId());
            if (existente != null) {
                Toast.makeText(this, "Ya existe un cliente con ese ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar cliente
            if (guardarCliente(CLnuevo)) {
                Toast.makeText(this, "Cliente creado exitosamente", Toast.LENGTH_LONG).show();
                regresarALista();
            } else {
                Toast.makeText(this, "Error al crear el cliente", Toast.LENGTH_SHORT).show();
            }
            
        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el cliente", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Muestra un mensaje de aviso para llenar el dato faltante
     */
    private boolean validarCampos() {
        if (etIdCliente.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del cliente", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNombreCliente.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etTelefonoCliente.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el teléfono del cliente", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etDireccionCliente.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la dirección del cliente", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Restablece los campos del formulario tras guardar el cliente
     */
    private void limpiarCampos() {
        etIdCliente.setText("");
        etNombreCliente.setText("");
        etTelefonoCliente.setText("");
        etDireccionCliente.setText("");
        rbClienteIndividual.setChecked(true);
    }
    /**
     * Regresa a la vista anterior, en este caso, la lista de clientes.
     */
    private void regresarALista() {
        // Mostrar lista y ocultar formulario
        scrollViewListaClientes.setVisibility(View.VISIBLE);
        scrollViewCliente.setVisibility(View.GONE);
        btnAgregarCliente.setVisibility(View.VISIBLE);
        btnGuardarCliente.setVisibility(View.GONE);
        
        // Actualizar lista
        mostrarListaClientes();
    }

    /**
     * Metodo para mostrar la lista de clientes en el TextView
     */
    private void mostrarListaClientes() {
        List<Cliente> clientes = obtenerTodosLosClientes();
        
        if (clientes == null || clientes.isEmpty()) {
            tvClientes.setText("No hay clientes registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE CLIENTES:\n\n");
        
        for (Cliente cliente : clientes) {
            String tipo = cliente.getTipoCliente() ? "EMPRESARIAL" : "PARTICULAR";
            sb.append(String.format("ID: %s\n", cliente.getId()));
            sb.append(String.format("Nombre: %s\n", cliente.getNombre()));
            sb.append(String.format("Teléfono: %s\n", cliente.getTelefono()));
            sb.append(String.format("Dirección: %s\n", cliente.getDireccion()));
            sb.append(String.format("Tipo: %s\n", tipo));
            sb.append("------------------------\n");
        }
        
        tvClientes.setText(sb.toString());
    }
    
    /**
     * Guarda un cliente en el archivo serializado
     */
    public boolean guardarCliente(Cliente cl) {
        try {
            List<Cliente> clientes = obtenerTodosLosClientes();
            
            // Verificar si ya existe
            for (Cliente c : clientes) {
                if (c.getId().equalsIgnoreCase(cl.getId())) {
                    return false; // Ya existe
                }
            }
            
            clientes.add(cl);
            return guardarClientesEnArchivo(clientes);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Obtiene todos los clientes desde el archivo serializado
     */
    @SuppressWarnings("unchecked")
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput(FILE_CLIENTES);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            clientes = (List<Cliente>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Archivo no existe o error al leer " + e.getMessage());
        }
        
        return clientes;
    }
    
    /**
     * Busca un cliente por ID
     */
    public Cliente buscarClientePorId(String id) {
        List<Cliente> clientes = obtenerTodosLosClientes();
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
    public Cliente buscarClienteEmpresarialPorId(String idBuscado) {
        if (idBuscado != null && !idBuscado.trim().isEmpty()) {
            List<Cliente> clientes = obtenerTodosLosClientes();
            for (Cliente c : clientes) {
                if (c.getId().equalsIgnoreCase(idBuscado) && c.getTipoCliente()) {
                    return c;
                }
            }
        }
        return null;
    }
    /**
     * Metodo que realiza la accion de guardar los datos
     */
    private boolean guardarClientesEnArchivo(List<Cliente> clientes) {
        try (FileOutputStream fos = openFileOutput(FILE_CLIENTES, MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(clientes);
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
}
