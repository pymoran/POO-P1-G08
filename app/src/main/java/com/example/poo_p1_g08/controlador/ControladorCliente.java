package com.example.poo_p1_g08.controlador;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.utils.DataManager;

import java.util.List;

public class ControladorCliente extends AppCompatActivity {
    private Button btnAgregarCliente, btnRegresar, btnGuardarCliente;
    private ScrollView scrollViewCliente, scrollViewListaClientes;
    private EditText etIdCliente, etNombreCliente, etTelefonoCliente, etDireccionCliente;
    private RadioGroup rgTipoCliente;
    private RadioButton rbClienteIndividual, rbClienteEmpresarial;
    private TextView tvClientes;
    
    public ControladorCliente(){
        // Constructor vacío
    }

    public String agregarCliente(Cliente CLnuevo){
        // Verificar si ya existe
        Cliente existente = DataManager.buscarClientePorId(this, CLnuevo.getId());
        if (existente != null) {
            return ">>Cliente ya existente intente nuevamente";
        }
        
        // Guardar en archivo
        if (DataManager.guardarCliente(this, CLnuevo)) {
            return "Cliente agregado satisfactoriamente";
        } else {
            return "Error al guardar cliente";
        }
    }
    
    public Cliente buscarClientePorId(String id, boolean soloTipoCliente) {
        if (id == null) return null;
        String idBuscado = id.trim();

        if (soloTipoCliente) {
            return DataManager.buscarClienteEmpresarialPorId(this, idBuscado);
        } else {
            return DataManager.buscarClientePorId(this, idBuscado);
        }
    }

    public Cliente buscarCliente(String id){
        return DataManager.buscarClientePorId(this, id);
    }

    public List<Cliente> getListaCliente(){
        return DataManager.obtenerTodosLosClientes(this);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistacliente);

        // Inicializar la aplicación si es necesario
        DataManager.inicializarApp(this);

        // Vinculamos los elementos del XML con Java y configuramos eventos
        inicializar();
        
        // Mostrar lista de clientes
        mostrarListaClientes();
    }
    
    private void inicializar() {
        // Botones principales
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        btnRegresar = findViewById(R.id.btnRegresar);
        
        // ScrollViews
        scrollViewCliente = findViewById(R.id.scrollViewCliente);
        scrollViewListaClientes = findViewById(R.id.scrollViewListaClientes);
        
        // TextView para lista de clientes
        tvClientes = findViewById(R.id.tvClientes);
        
        // ScrollView y campos del formulario
        etIdCliente = findViewById(R.id.etIdCliente);
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etTelefonoCliente = findViewById(R.id.etTelefonoCliente);
        etDireccionCliente = findViewById(R.id.etDireccionCliente);
        rgTipoCliente = findViewById(R.id.rgTipoCliente);
        rbClienteIndividual = findViewById(R.id.rbClienteIndividual);
        rbClienteEmpresarial = findViewById(R.id.rbClienteEmpresarial);
        
        // Botón del formulario
        btnGuardarCliente = findViewById(R.id.btnGuardarCliente);
        
        // Configurar eventos
        // Evento para mostrar formulario de agregar cliente
        btnAgregarCliente.setOnClickListener(v -> mostrarFormularioCliente());

        // Evento para guardar cliente
        btnGuardarCliente.setOnClickListener(v -> guardarCliente());

        btnRegresar.setOnClickListener(v -> finish());
    }
    
    private void mostrarListaClientes() {
        List<Cliente> clientes = DataManager.obtenerTodosLosClientes(this);
        
        if (clientes == null || clientes.isEmpty()) {
            tvClientes.setText("No hay clientes registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        
        for (Cliente c : clientes) {
            sb.append(String.format("ID: %s\n", c.getId()));
            sb.append(String.format("Nombre: %s\n", c.getNombre()));
            sb.append(String.format("Teléfono: %s\n", c.getTelefono()));
            sb.append(String.format("Dirección: %s\n", c.getDireccion()));
            sb.append(String.format("Tipo: %s\n", c.getTipoCliente() ? "Empresarial" : "Normal"));
            sb.append("------------------------\n");
        }
        
        String textoFinal = sb.toString();
        tvClientes.setText(textoFinal);
        
        // Asegurar que la lista sea visible
        scrollViewListaClientes.setVisibility(View.VISIBLE);
        scrollViewCliente.setVisibility(View.GONE);
        btnAgregarCliente.setVisibility(View.VISIBLE);
    }
    
    private void mostrarFormularioCliente() {
        scrollViewCliente.setVisibility(View.VISIBLE);
        scrollViewListaClientes.setVisibility(View.GONE);
        btnAgregarCliente.setVisibility(View.GONE);
        limpiarCampos();
    }
    
    private void limpiarCampos() {
        etIdCliente.setText("");
        etNombreCliente.setText("");
        etTelefonoCliente.setText("");
        etDireccionCliente.setText("");
        rbClienteIndividual.setChecked(true);
    }
    
    private void guardarCliente() {
        // Obtener valores de los campos
        String id = etIdCliente.getText().toString().trim();
        String nombre = etNombreCliente.getText().toString().trim();
        String telefono = etTelefonoCliente.getText().toString().trim();
        String direccion = etDireccionCliente.getText().toString().trim();
        boolean tipoCliente = rbClienteIndividual.isChecked(); // true = Individual, false = Empresarial
        
        // Validar campos obligatorios
        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            return;
        }
        
        // Crear nuevo cliente
        Cliente nuevoCliente = new Cliente(id, nombre, telefono, direccion, tipoCliente);
        String resultado = agregarCliente(nuevoCliente);
        
        // Si se agregó exitosamente, ocultar el formulario y actualizar lista
        if (resultado.equals("Cliente agregado satisfactoriamente")) {
            scrollViewCliente.setVisibility(View.GONE);
            scrollViewListaClientes.setVisibility(View.VISIBLE);
            btnAgregarCliente.setVisibility(View.VISIBLE);
            limpiarCampos();
            mostrarListaClientes();
        }
    }
}
