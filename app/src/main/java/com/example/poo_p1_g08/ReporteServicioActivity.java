package espol.poo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ReporteServicioActivity extends AppCompatActivity{

    EditText inputAnio;
    Spinner spinnerMes;
    Button btnConsultar;
    ListView listaServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_servicio);

        inputAnio = findViewById(R.id.inputAnio);
        spinnerMes = findViewById(R.id.spinnerMes);
        btnConsultar = findViewById(R.id.btnConsultar);
        listaServicios = findViewById(R.id.listaServicios);

        //Lista de meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses));

        btnConsultar.setOnClickListener(v -> {
            ArrayList<String> datos = new ArrayList<>();
            datos.add("Alineación - 2000 USD");
            datos.add("Balanceo - 2000 USD");
            datos.add("Cambio de aceite motor - 1000 USD");
            datos.add("Cambio filtro aceite - 1000 USD");

            listaServicios.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos));
        });
    }
}
