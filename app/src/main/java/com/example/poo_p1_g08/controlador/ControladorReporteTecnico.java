package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.modelo.DetalledelServicio;
import com.example.poo_p1_g08.modelo.Tecnico;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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

        // Cargar meses en el spinner
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses));

        // Consultar al usuario el año y mes
        btnConsultar.setOnClickListener(v -> {
            String anioTxt = inputAnio.getText().toString().trim();
            int indiceMes = spinnerMes.getSelectedItemPosition();
            // Verificar si los datos ingresados son los correctos
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
            // Método que genera el reporte
            generarReporteTecnicos(anio, mes1a12);
        });

        // Botón regresar al menú principal
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void generarReporteTecnicos(int anio, int mes1a12) {
        // Generar reporte de técnicos
        Map<String, Double> datos = generarReporteMensualTecnicos(anio, mes1a12);
        // Verificación de datos vacíos
        if (datos == null || datos.isEmpty()) {
            listaTecnicos.setAdapter(null);
            Toast.makeText(this, "No hay datos para " + mes1a12 + "/" + anio, Toast.LENGTH_SHORT).show();
            return;
        }
        // Creación de la tabla con formato tecnico / total recaudado
        ControladorReporteTecnicoAdapter adapter = new ControladorReporteTecnicoAdapter(this, datos);
        listaTecnicos.setAdapter(adapter);

        Toast.makeText(this, "Reporte generado para " + mes1a12 + "/" + anio, Toast.LENGTH_SHORT).show();
    }

    /**
     * Genera reporte de técnicos por mes y año
     */
    private Map<String, Double> generarReporteMensualTecnicos(int anio, int mes) {
        Map<String, Double> reporte = new HashMap<>();
        try {
            List<OrdenServicio> ordenes = obtenerTodasLasOrdenes();
            if (ordenes == null || ordenes.isEmpty()) {
                return reporte;
            }
            
            for (OrdenServicio orden : ordenes) {
                try {
                    // Validar que la fecha no sea nula ni vacía
                    if (orden.getFecha() == null || orden.getFecha().trim().isEmpty()) {
                        continue; // saltar esta orden
                    }
                    // Parsear la fecha
                    LocalDate fecha = LocalDate.parse(
                            orden.getFecha().trim(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    );
                    // Verificar año y mes
                    if (fecha.getYear() == anio && fecha.getMonthValue() == mes) {
                        // Técnico de la orden
                        Tecnico tecnico = orden.getTecnico();
                        if (tecnico == null) {
                            continue;
                        }
                        // Calcular monto de la orden
                        double monto = 0.0;
                        if (orden.getDetalle() != null) {
                            for (DetalledelServicio detalle : orden.getDetalle()) {
                                if (detalle != null) {
                                    monto += detalle.getSubtotal();
                                }
                            }
                        }
                        String nombre = tecnico.getNombre();
                        reporte.put(nombre, reporte.getOrDefault(nombre, 0.0) + monto);
                    }
                } catch (Exception eFecha) {
                    // Continuar con la siguiente orden si hay error en la fecha
                }
            }
        } catch (Exception e) {
            // Manejar error general
        }
        return reporte;
    }

    /**
     * Obtiene todas las órdenes desde el archivo
     */
    @SuppressWarnings("unchecked")
    private List<OrdenServicio> obtenerTodasLasOrdenes() {
        List<OrdenServicio> ordenes = new ArrayList<>();
        
        try (FileInputStream fis = openFileInput("ordenes.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            ordenes = (List<OrdenServicio>) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no encontrado o error al leer
        }
        
        return ordenes;
    }
}




