package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.MainActivity;
import com.example.poo_p1_g08.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ControladorReporteServicio extends AppCompatActivity{

    private EditText inputAnio;
    private Spinner spinnerMes;
    private Button btnConsultar;
    private ListView listaServicios;
    private ReporteServicioAdapter adapter;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistareporteservicio);

        inputAnio = findViewById(R.id.inputAnio);
        spinnerMes = findViewById(R.id.spinnerMes);
        btnConsultar = findViewById(R.id.btnConsultar);
        listaServicios = findViewById(R.id.listaReporteServicios);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Cargar meses en el spinner
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses));
        
        // Consultar
        btnConsultar.setOnClickListener(v -> {
            String anioTxt = inputAnio.getText().toString().trim();
            int indiceMes = spinnerMes.getSelectedItemPosition();

            // Validaciones
            if (anioTxt.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese el año", Toast.LENGTH_SHORT).show();
                return;
            }
            int anio;
            try {
                anio = Integer.parseInt(anioTxt);
                if (anio < 1900 || anio > 3000) {
                    Toast.makeText(this, "Año inválido", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "El año debe ser numérico", Toast.LENGTH_SHORT).show();
                return;
            }

            if (indiceMes < 0) {
                Toast.makeText(this, "Por favor seleccione un mes", Toast.LENGTH_SHORT).show();
                return;
            }

            int mes1a12 = indiceMes + 1; // convertir 0..11 -> 1..12
            generarReporteServicio(anio, mes1a12);
        });

        // Regresar
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void generarReporteServicio(int anio, int mes1a12) {
        // Llamada al generador del reporte
        Map<String, Double> datos = MainActivity.generarReporteMensualServicios(anio, mes1a12);

        if (datos == null || datos.isEmpty()) {
            listaServicios.setAdapter(null);
            Toast.makeText(this, "No hay datos para " + mes1a12 + "/" + anio, Toast.LENGTH_SHORT).show();
            return;
        }

        // Ordenar por monto descendente
        Map<String, Double> ordenado = ordenarPorValorDesc(datos);

        // Tabla formato (fila: servicio | total USD)
        adapter = new ReporteServicioAdapter(this, ordenado);
        listaServicios.setAdapter(adapter);

        Toast.makeText(this, "Reporte generado para " + mes1a12 + "/" + anio, Toast.LENGTH_SHORT).show();
    }

    // Ordena un mapa por valor (Double) descendente y conserva el orden
    private Map<String, Double> ordenarPorValorDesc(Map<String, Double> mapa) {
        List<Map.Entry<String, Double>> lista = new ArrayList<>(mapa.entrySet());
        Collections.sort(lista, (a, b) -> Double.compare(b.getValue(), a.getValue()));

        Map<String, Double> linked = new LinkedHashMap<>();
        for (Map.Entry<String, Double> e : lista) {
            linked.put(e.getKey(), e.getValue());
        }
        return linked;
        /*String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
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

        Toast.makeText(this, "Reporte generado para " + meses[mes] + " " + anio, Toast.LENGTH_SHORT).show();*/
    }
}







