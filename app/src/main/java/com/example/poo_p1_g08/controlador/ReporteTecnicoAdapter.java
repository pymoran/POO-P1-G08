package com.example.poo_p1_g08.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReporteTecnicoAdapter extends BaseAdapter {
    private Context context;
    private List<Map.Entry<String, Double>> datos;

    public ReporteTecnicoAdapter(Context context, Map<String, Double> mapa) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_reporte_tecnico, parent, false);
        }

        TextView tvTecnico = convertView.findViewById(R.id.tvTecnico);
        TextView tvMonto = convertView.findViewById(R.id.tvMontoTec);

        Map.Entry<String, Double> item = datos.get(position);
        tvTecnico.setText(item.getKey());
        tvMonto.setText(String.format("$%.2f", item.getValue()));

        return convertView;
    }
}
