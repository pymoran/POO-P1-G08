package espol.poo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ReporteTecnicoActivity extends AppCompatActivity {

    EditText inputAnio;
    Spinner spinnerMes;
    Button btnConsultar;
    ListView listaTecnicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_tecnico);

        inputAnio = findViewById(R.id.inputAnioTec);
        spinnerMes = findViewById(R.id.spinnerMesTec);
        btnConsultar = findViewById(R.id.btnConsultarTec);
        listaTecnicos = findViewById(R.id.listaTecnicos);

        //Lista de meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses));

        btnConsultar.setOnClickListener(v -> {
            ArrayList<String> datos = new ArrayList<>();
            datos.add("Álvaro López - 2000 USD");
            datos.add("Mario Barcos - 1500 USD");

            listaTecnicos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos));
        });
    }
}
