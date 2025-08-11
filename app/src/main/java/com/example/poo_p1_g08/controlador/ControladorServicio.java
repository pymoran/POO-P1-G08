package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Servicio;
import java.util.ArrayList;

public class ControladorServicio extends AppCompatActivity {
    private TextView tvListaServicios;
    private Button btnAgregarServicio, btnEditarServicio, btnRegresar;
    private ArrayList<Servicio> listaServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaservicios);

        // Inicializar componentes
        tvListaServicios = findViewById(R.id.tvListaServicios);
        btnAgregarServicio = findViewById(R.id.btnAgregarServicio);
        btnEditarServicio = findViewById(R.id.btnEditarServicio);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Inicializar datos de ejemplo
        inicializarDatos();

        // Mostrar lista inicial
        mostrarListaServicios();

        // Eventos de botones
        btnAgregarServicio.setOnClickListener(v -> mostrarFormularioCrearServicio());
        btnEditarServicio.setOnClickListener(v -> mostrarFormularioEditarServicio());
        btnRegresar.setOnClickListener(v -> finish());
    }

    // Método para mostrar la lista de servicios en el TextView
    private void mostrarListaServicios() {
        if (listaServicios == null || listaServicios.isEmpty()) {
            tvListaServicios.setText("No hay servicios registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE SERVICIOS:\n\n");
        
        for (Servicio servicio : listaServicios) {
            sb.append(String.format("Código: %s\n", servicio.getCodigo()));
            sb.append(String.format("Nombre: %s\n", servicio.getNombre()));
            sb.append(String.format("Precio: $%.2f\n", servicio.getPrecio()));
            sb.append("------------------------\n");
        }
        
        tvListaServicios.setText(sb.toString());
    }

    // Formulario para crear nuevo servicio
    private void mostrarFormularioCrearServicio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Nuevo Servicio");

        // Crear vista con campos del formulario
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        // Campo para código del servicio
        android.widget.EditText etCodigo = new android.widget.EditText(this);
        etCodigo.setHint("Código del Servicio (ej: S007)");
        layout.addView(etCodigo);

        // Campo para nombre del servicio
        android.widget.EditText etNombre = new android.widget.EditText(this);
        etNombre.setHint("Nombre del Servicio");
        layout.addView(etNombre);

        // Campo para precio del servicio
        android.widget.EditText etPrecio = new android.widget.EditText(this);
        etPrecio.setHint("Precio ($)");
        etPrecio.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(etPrecio);

        builder.setView(layout);

        builder.setPositiveButton("Crear", (dialog, which) -> {
            String codigo = etCodigo.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();
            
            if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                
                // Mostrar mensaje de confirmación
                String mensaje = String.format("Servicio solicitado:\nCódigo: %s\nNombre: %s\nPrecio: $%.2f\n\n(Implementación futura)", 
                                             codigo, nombre, precio);
                tvListaServicios.setText(mensaje);
                Toast.makeText(this, "Servicio solicitado", Toast.LENGTH_SHORT).show();
                
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Precio debe ser un número válido", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    // Formulario para editar servicio existente
    private void mostrarFormularioEditarServicio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Servicio Existente");

        // Crear vista con campos del formulario
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        // Campo para código del servicio a editar
        android.widget.EditText etCodigoEditar = new android.widget.EditText(this);
        etCodigoEditar.setHint("Código del Servicio a Editar (ej: S001)");
        layout.addView(etCodigoEditar);

        // Campo para nuevo precio
        android.widget.EditText etNuevoPrecio = new android.widget.EditText(this);
        etNuevoPrecio.setHint("Nuevo Precio ($)");
        etNuevoPrecio.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(etNuevoPrecio);

        builder.setView(layout);

        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            String codigo = etCodigoEditar.getText().toString().trim();
            String precioStr = etNuevoPrecio.getText().toString().trim();
            
            if (codigo.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                
                // Mostrar mensaje de confirmación
                String mensaje = String.format("Actualización solicitada:\nCódigo: %s\nNuevo Precio: $%.2f\n\n(Implementación futura)", 
                                             codigo, precio);
                tvListaServicios.setText(mensaje);
                Toast.makeText(this, "Actualización solicitada", Toast.LENGTH_SHORT).show();
                
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Precio debe ser un número válido", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    // Inicializar datos de ejemplo
    private void inicializarDatos() {
        listaServicios = new ArrayList<>();
        listaServicios.add(new Servicio("S001", "Cambio de aceite", 25.00));
        listaServicios.add(new Servicio("S002", "Frenos", 80.00));
        listaServicios.add(new Servicio("S003", "Suspensión", 120.00));
        listaServicios.add(new Servicio("S004", "Electricidad", 45.00));
        listaServicios.add(new Servicio("S005", "Motor", 200.00));
        listaServicios.add(new Servicio("S006", "Limpieza", 30.00));
    }
}
