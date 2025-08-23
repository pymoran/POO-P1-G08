package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Tecnico;
import com.example.poo_p1_g08.utils.DataManager;

import java.util.List;

public class ControladorTecnico extends AppCompatActivity {
	private Button btnAgregarTecnico, btnRegresar, btnGuardarTecnico;
	private ScrollView scrollViewTecnico, scrollViewListaTecnicos;
	private EditText etIdTecnico, etNombreTecnico, etTelefonoTecnico, etEspecialidadTecnico;
	private TextView tvTecnicos;

	public ControladorTecnico() {
		// Constructor vacío
	}

	public String agregarTecnico(Tecnico tecnico){
		// Verificar si ya existe
		Tecnico existente = DataManager.buscarTecnicoPorId(this, tecnico.getId());
		if (existente != null) {
			return ">>Técnico ya existente intente nuevamente";
		}
		
		// Guardar en archivo
		if (DataManager.guardarTecnico(this, tecnico)) {
			return "Técnico agregado satisfactoriamente";
		} else {
			return "Error al guardar técnico";
		}
	}

	public List<Tecnico> getListaTecnicos(){
		return DataManager.obtenerTodosLosTecnicos(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistatecnico);

		// Inicializar la aplicación si es necesario
		DataManager.inicializarApp(this);

		inicializar();
		mostrarListaTecnicos();
	}

	private void inicializar() {
		btnAgregarTecnico = findViewById(R.id.btnAgregarTecnico);
		btnRegresar = findViewById(R.id.btnRegresar);

		scrollViewTecnico = findViewById(R.id.scrollViewTecnico);
		scrollViewListaTecnicos = findViewById(R.id.scrollViewListaTecnicos);
		tvTecnicos = findViewById(R.id.tvTecnicos);

		etIdTecnico = findViewById(R.id.etIdTecnico);
		etNombreTecnico = findViewById(R.id.etNombreTecnico);
		etTelefonoTecnico = findViewById(R.id.etTelefonoTecnico);
		etEspecialidadTecnico = findViewById(R.id.etEspecialidadTecnico);

		btnGuardarTecnico = findViewById(R.id.btnGuardarTecnico);

		btnAgregarTecnico.setOnClickListener(v -> mostrarFormularioTecnico());
		btnGuardarTecnico.setOnClickListener(v -> guardarTecnico());
		btnRegresar.setOnClickListener(v -> finish());
	}

	private void mostrarListaTecnicos() {
		List<Tecnico> tecnicos = DataManager.obtenerTodosLosTecnicos(this);
		
		if (tecnicos == null || tecnicos.isEmpty()) {
			tvTecnicos.setText("No hay técnicos registrados");
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (Tecnico t : tecnicos) {
			sb.append(String.format("ID: %s\n", t.getId()));
			sb.append(String.format("Nombre: %s\n", t.getNombre()));
			sb.append(String.format("Teléfono: %s\n", t.getTelefono()));
			sb.append(String.format("Especialidad: %s\n", t.getEspecialidad()));
			sb.append("----------------------------\n");
		}

		tvTecnicos.setText(sb.toString());

		scrollViewListaTecnicos.setVisibility(View.VISIBLE);
		scrollViewTecnico.setVisibility(View.GONE);
		btnAgregarTecnico.setVisibility(View.VISIBLE);
	}

	private void mostrarFormularioTecnico() {
		scrollViewTecnico.setVisibility(View.VISIBLE);
		scrollViewListaTecnicos.setVisibility(View.GONE);
		btnAgregarTecnico.setVisibility(View.GONE);
		limpiarCampos();
	}

	private void limpiarCampos() {
		etIdTecnico.setText("");
		etNombreTecnico.setText("");
		etTelefonoTecnico.setText("");
		etEspecialidadTecnico.setText("");
	}

	private void guardarTecnico() {
		String id = etIdTecnico.getText().toString().trim();
		String nombre = etNombreTecnico.getText().toString().trim();
		String telefono = etTelefonoTecnico.getText().toString().trim();
		String especialidad = etEspecialidadTecnico.getText().toString().trim();

		if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
			return;
		}

		Tecnico nuevoTecnico = new Tecnico(id, nombre, telefono, especialidad);
		String resultado = agregarTecnico(nuevoTecnico);

		if (resultado.equals("Técnico agregado satisfactoriamente")) {
			scrollViewTecnico.setVisibility(View.GONE);
			scrollViewListaTecnicos.setVisibility(View.VISIBLE);
			btnAgregarTecnico.setVisibility(View.VISIBLE);
			limpiarCampos();
			mostrarListaTecnicos();
		}
	}
}
