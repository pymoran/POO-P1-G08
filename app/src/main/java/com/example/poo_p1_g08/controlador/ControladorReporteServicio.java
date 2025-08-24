//package com.example.poo_p1_g08;
package espol.poo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ControladorReporteServicio extends AppCompatActivity{

    EditText inputAnio;
    Spinner spinnerMes;
    Button btnConsultar;
    ListView listaServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistareporteservicio);

        inputAnio = findViewById(R.id.inputAnio);
        spinnerMes = findViewById(R.id.spinnerMes);
        btnConsultar = findViewById(R.id.btnConsultar);
        listaServicios = findViewById(R.id.listaServicios);

        //Lista de meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses));

        btnConsultar.setOnClickListener(v -> {
            String anio = inputAnio.getText().toString().trim();
            int mesSeleccionado = spinnerMes.getSelectedItemPosition();

            if (anio.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese el año", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mesSeleccionado < 0) {
                Toast.makeText(this, "Por favor seleccione un mes", Toast.LENGTH_SHORT).show();
                return;
            }

            generarReporteServicios(anio, mesSeleccionado);
        });

        // Botón de regreso
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void generarReporteServicios(String anio, int mes) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        ArrayList<String> datos = new ArrayList<>();
        datos.add("=== REPORTE DE SERVICIOS - " + meses[mes] + " " + anio + " ===");
        datos.add("");
        datos.add("No hay datos disponibles para el período seleccionado.");
        datos.add("");
        datos.add("Para generar reportes reales, primero debe:");
        datos.add("• Registrar servicios en el sistema");
        datos.add("• Crear órdenes de servicio");
        datos.add("• Completar las transacciones");

        listaServicios.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos));

        Toast.makeText(this, "Reporte generado para " + meses[mes] + " " + anio, Toast.LENGTH_SHORT).show();
    }
}
