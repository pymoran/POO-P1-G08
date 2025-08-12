package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.FacturaEmpresa;
import com.example.poo_p1_g08.modelo.Persona;
import java.util.ArrayList;

public class ControladorFacturaEmpresa extends AppCompatActivity {
    private ArrayList<FacturaEmpresa> listaFacturas;
    private TextView tvListaFacturas;
    private Button btnGenerarFactura, btnRegresar;
    
    // Listas de datos de ejemplo
    private ArrayList<Persona> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistafacturas);

        // Inicializar componentes
        tvListaFacturas = findViewById(R.id.tvListaFacturas);
        btnGenerarFactura = findViewById(R.id.btnGenerarFactura);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Inicializar datos de ejemplo
        inicializarDatos();

        // Mostrar lista inicial
        mostrarListaFacturas();

        // Eventos de botones
        btnGenerarFactura.setOnClickListener(v -> mostrarMensajeGenerarFactura());
        btnRegresar.setOnClickListener(v -> finish());
    }

    // Método para mostrar la lista de facturas en el TextView
    private void mostrarListaFacturas() {
        if (listaFacturas == null || listaFacturas.isEmpty()) {
            tvListaFacturas.setText("No hay facturas generadas");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE FACTURAS:\n\n");
        
        for (FacturaEmpresa factura : listaFacturas) {
            sb.append(String.format("Cliente: %s\n", factura.getCliente().getNombre()));
            sb.append(String.format("Período: %s %d\n", factura.getNombreMes(), factura.getAño()));
            sb.append(String.format("Total Servicios: $%.2f\n", factura.getTotalServicios()));
            sb.append(String.format("Costo Prioridad: $%.2f\n", FacturaEmpresa.getCostoPrioridad()));
            sb.append(String.format("TOTAL FACTURA: $%.2f\n", factura.getTotalFactura()));
            sb.append("------------------------\n");
        }
        
        tvListaFacturas.setText(sb.toString());
    }

    // Diálogo para mostrar campos de generación de factura
    private void mostrarMensajeGenerarFactura() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Generar Factura Mensual");

        // Crear vista con campos visuales
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        // Campo para ID de la empresa
        android.widget.EditText etEmpresaId = new android.widget.EditText(this);
        etEmpresaId.setHint("ID de la Empresa (ej: C003)");
        layout.addView(etEmpresaId);

        // Campo para mes
        android.widget.EditText etMes = new android.widget.EditText(this);
        etMes.setHint("Mes (1-12)");
        etMes.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(etMes);

        // Campo para año
        android.widget.EditText etAño = new android.widget.EditText(this);
        etAño.setHint("Año (ej: 2024)");
        etAño.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        layout.addView(etAño);

        builder.setView(layout);

        builder.setPositiveButton("Generar", (dialog, which) -> {
            String empresaId = etEmpresaId.getText().toString().trim();
            String mesStr = etMes.getText().toString().trim();
            String añoStr = etAño.getText().toString().trim();
            
            if (empresaId.isEmpty() || mesStr.isEmpty() || añoStr.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int mes = Integer.parseInt(mesStr);
                int año = Integer.parseInt(añoStr);
                
                if (mes < 1 || mes > 12) {
                    Toast.makeText(this, "Mes debe estar entre 1 y 12", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mostrar mensaje de confirmación
                String mensaje = String.format("Factura solicitada para:\nEmpresa: %s\nMes: %d\nAño: %d\n\n(Implementación futura)", 
                                             empresaId, mes, año);
                tvListaFacturas.setText(mensaje);
                Toast.makeText(this, "Solicitud recibida", Toast.LENGTH_SHORT).show();
                
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Mes y año deben ser números", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    // Inicializar datos de ejemplo
    private void inicializarDatos() {
        // Clientes de ejemplo (incluyendo empresariales)
        clientes = new ArrayList<>();
        clientes.add(new Cliente("C001", "Carlos Ruiz", "0991111111", "Av. Principal 123", false));
        clientes.add(new Cliente("C002", "Ana García", "0992222222", "Calle Secundaria 456", false));
        clientes.add(new Cliente("C003", "Empresa ABC", "0993333333", "Zona Industrial 789", true));
        clientes.add(new Cliente("C004", "Corporación XYZ", "0994444444", "Centro Comercial 321", true));

        // Crear 2 facturas de ejemplo para mostrar en la lista
        listaFacturas = new ArrayList<>();
        
        // Factura 1
        Cliente cliente1 = (Cliente) clientes.get(2); // Empresa ABC
        FacturaEmpresa factura1 = new FacturaEmpresa(cliente1, 12, 2024); // Diciembre 2024
        listaFacturas.add(factura1);
        
        // Factura 2
        Cliente cliente2 = (Cliente) clientes.get(3); // Corporación XYZ
        FacturaEmpresa factura2 = new FacturaEmpresa(cliente2, 11, 2024); // Noviembre 2024
        listaFacturas.add(factura2);
    }
}

