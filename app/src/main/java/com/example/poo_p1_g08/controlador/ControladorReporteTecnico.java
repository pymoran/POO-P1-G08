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

import java.util.Map;

public class ControladorReporteTecnico extends AppCompatActivity {

    private EditText inputAnio;
    private Spinner spinnerMes;
    private Button btnConsultar;
    private ListView listaTecnicos;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistareportetecnico);

        inputAnio = findViewById(R.id.inputAnioTec);
        spinnerMes = findViewById(R.id.spinnerMesTec);
        btnConsultar = findViewById(R.id.btnConsultarTec);
        listaTecnicos = findViewById(R.id.listaTecnicos);
        btnRegresar = findViewById(R.id.btnRegresarTec);

        // Cargar meses en spinner
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses));

        // Consultar
        btnConsultar.setOnClickListener(v -> {
            String anioTxt = inputAnio.getText().toString().trim();
            int indiceMes = spinnerMes.getSelectedItemPosition();

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

            int mes1a12 = indiceMes + 1;
            generarReporteTecnicos(anio, mes1a12);
        });

        // Botón regresar
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void generarReporteTecnicos(int anio, int mes1a12) {
        // Aquí debería ir tu lógica real
        Map<String, Double> datos = MainActivity.generarReporteMensualTecnicos(anio, mes1a12);

        if (datos == null || datos.isEmpty()) {
            listaTecnicos.setAdapter(null);
            Toast.makeText(this, "No hay datos para " + mes1a12 + "/" + anio, Toast.LENGTH_SHORT).show();
            return;
        }

        ControladorReporteTecnicoAdapter adapter = new ControladorReporteTecnicoAdapter(this, datos);
        listaTecnicos.setAdapter(adapter);

        Toast.makeText(this, "Reporte generado para " + mes1a12 + "/" + anio, Toast.LENGTH_SHORT).show();
        /*String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        ArrayList<String> datos = new ArrayList<>();
        datos.add("=== REPORTE DE TÉCNICOS - " + meses[mes] + " " + anio + " ===");
        datos.add("");
        datos.add("No hay datos disponibles para el período seleccionado.");
        datos.add("");
        datos.add("Para generar reportes reales, primero debe:");
        datos.add("• Registrar técnicos en el sistema");
        datos.add("• Asignar técnicos a órdenes de servicio");
        datos.add("• Completar las atenciones");

        listaTecnicos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos));

        Toast.makeText(this, "Reporte generado para " + meses[mes] + " " + anio, Toast.LENGTH_SHORT).show();*/
    }
}



