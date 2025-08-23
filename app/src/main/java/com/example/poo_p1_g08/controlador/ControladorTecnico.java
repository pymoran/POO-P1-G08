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
import com.example.poo_p1_g08.modelo.Tecnico;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControladorTecnico extends AppCompatActivity {
    private static final String TAG = "ControladorTecnico";
    private static final String FILE_TECNICOS = "tecnicos.ser";

    private TextView tvTecnicos;
    private Button btnAgregarTecnico, btnRegresar, btnGuardarTecnico, btnEliminarTecnico, btnConfirmarEliminarTecnico, btnRegresarDesdeEliminar;
    private ScrollView scrollViewListaTecnicos, scrollViewTecnico, scrollViewEliminarTecnico;
    private EditText etIdTecnico, etNombreTecnico, etTelefonoTecnico, etEspecialidadTecnico, etIdTecnicoEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistatecnico);

        // Inicializar componentes
        inicializarComponentes();

        // Mostrar lista inicial
        mostrarListaTecnicos();

        // Eventos de botones
        configurarEventos();
    }
    /**
     * Variables necesarias para cada accion
     */
    private void inicializarComponentes() {
        // TextViews
        tvTecnicos = findViewById(R.id.tvTecnicos);

        // Botones
        btnAgregarTecnico = findViewById(R.id.btnAgregarTecnico);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnGuardarTecnico = findViewById(R.id.btnGuardarTecnico);
        btnEliminarTecnico = findViewById(R.id.btnEliminarTecnico);
        btnConfirmarEliminarTecnico = findViewById(R.id.btnConfirmarEliminarTecnico);
        btnRegresarDesdeEliminar = findViewById(R.id.btnRegresarDesdeEliminar);

        // ScrollViews
        scrollViewListaTecnicos = findViewById(R.id.scrollViewListaTecnicos);
        scrollViewTecnico = findViewById(R.id.scrollViewTecnico);
        scrollViewEliminarTecnico = findViewById(R.id.scrollViewEliminarTecnico);

        // EditTexts
        etIdTecnico = findViewById(R.id.etIdTecnico);
        etNombreTecnico = findViewById(R.id.etNombreTecnico);
        etTelefonoTecnico = findViewById(R.id.etTelefonoTecnico);
        etEspecialidadTecnico = findViewById(R.id.etEspecialidadTecnico);
        etIdTecnicoEliminar = findViewById(R.id.etIdTecnicoEliminar);
    }
    /**
     * Configuracion de los eventos mediante los botones en la pantalla
     */
    private void configurarEventos() {
        btnAgregarTecnico.setOnClickListener(v -> mostrarFormularioAgregarTecnico());
        btnRegresar.setOnClickListener(v -> {
            if (scrollViewTecnico.getVisibility() == View.VISIBLE || scrollViewEliminarTecnico.getVisibility() == View.VISIBLE) {
                regresarALista();
            } else {
                finish();
            }
        });
        btnGuardarTecnico.setOnClickListener(v -> crearTecnico());
        btnEliminarTecnico.setOnClickListener(v -> mostrarFormularioEliminarTecnico());
        btnConfirmarEliminarTecnico.setOnClickListener(v -> eliminarTecnicoConfirmado());
        btnRegresarDesdeEliminar.setOnClickListener(v -> regresarALista());
    }
    /**
     * Permite ocultar y/o mostrar el formulario
     */
    private void mostrarFormularioAgregarTecnico() {
        // Ocultar lista y mostrar formulario
        scrollViewListaTecnicos.setVisibility(View.GONE);
        scrollViewTecnico.setVisibility(View.VISIBLE);
        scrollViewEliminarTecnico.setVisibility(View.GONE);
        btnAgregarTecnico.setVisibility(View.GONE);
        btnGuardarTecnico.setVisibility(View.VISIBLE);
        btnEliminarTecnico.setVisibility(View.GONE);
        btnRegresar.setVisibility(View.VISIBLE); //

        // Limpiar campos
        limpiarCampos();
    }
    /**
     * Crea un nuevo Tecnico con los datos recopilados del scrollViewTecnico
     */
    private void crearTecnico() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        try {
            String id = etIdTecnico.getText().toString().trim();
            String nombre = etNombreTecnico.getText().toString().trim();
            String telefono = etTelefonoTecnico.getText().toString().trim();
            String especialidad = etEspecialidadTecnico.getText().toString().trim();

            // Crear técnico
            Tecnico tecnico = new Tecnico(id, nombre, telefono, especialidad);

            // Verificar si ya existe
            Tecnico existente = buscarTecnicoPorId(tecnico.getId());
            if (existente != null) {
                Toast.makeText(this, "Ya existe un técnico con ese ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar técnico
            if (guardarTecnico(tecnico)) {
                Toast.makeText(this, "Técnico creado exitosamente", Toast.LENGTH_LONG).show();
                regresarALista();
            } else {
                Toast.makeText(this, "Error al crear el técnico", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el técnico", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Muestra un mensaje de aviso para llenar el dato faltante
     */
    private boolean validarCampos() {
        if (etIdTecnico.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del técnico", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNombreTecnico.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del técnico", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etTelefonoTecnico.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el teléfono del técnico", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etEspecialidadTecnico.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese la especialidad del técnico", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Restablece los campos del formulario tras guardar el tecnico
     */
    private void limpiarCampos() {
        etIdTecnico.setText("");
        etNombreTecnico.setText("");
        etTelefonoTecnico.setText("");
        etEspecialidadTecnico.setText("");
    }
    /**
     * Regresa a la vista anterior, en este caso, la lista de tecnicos.
     */
    private void regresarALista() {
        scrollViewListaTecnicos.setVisibility(View.VISIBLE);
        scrollViewTecnico.setVisibility(View.GONE);
        scrollViewEliminarTecnico.setVisibility(View.GONE);
        btnAgregarTecnico.setVisibility(View.VISIBLE);
        btnGuardarTecnico.setVisibility(View.GONE);
        btnEliminarTecnico.setVisibility(View.VISIBLE);
        btnRegresar.setVisibility(View.VISIBLE);
        // Actualizar lista
        mostrarListaTecnicos();
    }

    /**
     * Metodo para mostrar la lista de técnicos en el TextView
     */
    private void mostrarListaTecnicos() {
        List<Tecnico> tecnicos = obtenerTodosLosTecnicos();

        if (tecnicos == null || tecnicos.isEmpty()) {
            tvTecnicos.setText("No hay técnicos registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE TÉCNICOS:\n\n");

        for (Tecnico tecnico : tecnicos) {
            sb.append(String.format("ID: %s\n", tecnico.getId()));
            sb.append(String.format("Nombre: %s\n", tecnico.getNombre()));
            sb.append(String.format("Teléfono: %s\n", tecnico.getTelefono()));
            sb.append(String.format("Especialidad: %s\n", tecnico.getEspecialidad()));
            sb.append("------------------------\n");
        }

        tvTecnicos.setText(sb.toString());
    }


    /**
     * Guarda un técnico en el archivo serializado
     */
    public boolean guardarTecnico(Tecnico tecnico) {
        try {
            List<Tecnico> tecnicos = obtenerTodosLosTecnicos();

            // Verificar si ya existe
            for (Tecnico t : tecnicos) {
                if (t.getId().equalsIgnoreCase(tecnico.getId())) {
                    return false; // Ya existe
                }
            }

            tecnicos.add(tecnico);
            return guardarTecnicosEnArchivo(tecnicos);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Elimina un tecnico mediante el ID
     */
    public String eliminarTecnico(String idTecnico) {
        List<Tecnico> tecnicos = obtenerTodosLosTecnicos();
        boolean eliminado = tecnicos.removeIf(t -> t.getId().equalsIgnoreCase(idTecnico));
        if (eliminado) {
            if (guardarTecnicosEnArchivo(tecnicos)) {
                return "Técnico eliminado satisfactoriamente";
            } else {
                return "Error al guardar los cambios después de eliminar el técnico";
            }
        } else {
            return "Técnico no encontrado";
        }
    }

    /**
     * Obtiene todos los técnicos desde el archivo serializado
     */
    @SuppressWarnings("unchecked")
    public List<Tecnico> obtenerTodosLosTecnicos() {
        List<Tecnico> tecnicos = new ArrayList<>();

        try (FileInputStream fis = openFileInput(FILE_TECNICOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            tecnicos = (List<Tecnico>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            // Archivo no existe o error al leer
        }

        return tecnicos;
    }

    /**
     * Busca un técnico por ID
     */
    public Tecnico buscarTecnicoPorId(String id) {
        List<Tecnico> tecnicos = obtenerTodosLosTecnicos();
        for (Tecnico t : tecnicos) {
            if (t.getId().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Guarda un tecnico en el archivo serializado
     */
    private boolean guardarTecnicosEnArchivo(List<Tecnico> tecnicos) {
        try (FileOutputStream fos = openFileOutput(FILE_TECNICOS, MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(tecnicos);
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * Muestra el formulario para eliminar un tecnico por el ID
     */
    private void mostrarFormularioEliminarTecnico() {
        scrollViewListaTecnicos.setVisibility(View.GONE);
        scrollViewTecnico.setVisibility(View.GONE);
        scrollViewEliminarTecnico.setVisibility(View.VISIBLE);
        btnAgregarTecnico.setVisibility(View.GONE);
        btnGuardarTecnico.setVisibility(View.GONE);
        btnEliminarTecnico.setVisibility(View.GONE);
        btnRegresar.setVisibility(View.GONE); // Ocultar botón global de regresar
        etIdTecnicoEliminar.setText(""); // Limpiar campo
    }
    /**
     * Accion para elimiar el tecnico
     */
    private void eliminarTecnicoConfirmado() {
        String idTecnico = etIdTecnicoEliminar.getText().toString().trim();
        if (idTecnico.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del técnico a eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        String mensaje = eliminarTecnico(idTecnico);
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        regresarALista(); // Regresar a la lista después de intentar eliminar
    }
}
