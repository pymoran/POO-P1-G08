// Esta clase funciona como un puente entre los datos y la interfaz gráfica: toma la información de una fuente (listas, arreglos, objetos) 
// y la transforma en vista, mostrando el ListView. Su papel principal es decidir qué datos se muestran y 
// cómo se ven en pantalla, además de optimizar el rendimiento reutilizando vistas en lugar de crearlas desde cero.
package com.example.poo_p1_g08.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.poo_p1_g08.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControladorReporteTecnicoAdapter extends BaseAdapter {
    private Context context;
    private List<Map.Entry<String, Double>> datos;

    public ControladorReporteTecnicoAdapter(Context context, Map<String, Double> mapa) {
        this.context = context;
        this.datos = new ArrayList<>(mapa.entrySet());
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Método que personaliza cada fila o elemento de la lista, conectando los datos con su representación visual.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.vistaitemreportetecnico, parent, false);
        }

        TextView tvTecnico = convertView.findViewById(R.id.tvTecnico);
        TextView tvTotalRecaudado = convertView.findViewById(R.id.tvTotalRecaudado);

        Map.Entry<String, Double> item = datos.get(position);
        tvTecnico.setText(item.getKey());
        tvTotalRecaudado.setText(String.format("$%.2f", item.getValue()));

        return convertView;
    }
}
