package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.FacturaEmpresa;
import com.example.poo_p1_g08.modelo.Persona;
import java.util.ArrayList;

public class ControladorFacturaEmpresa extends AppCompatActivity {
    private ArrayList<FacturaEmpresa> listaFacturas;
    private TextView tvListaFacturas;
    private Button btnGenerarFactura, btnCrearFactura, btnRegresar;
    private ScrollView scrollView, scrollViewGenerarFactura;
    private EditText etClienteEmpresaId, etMes, etAño;
    
    // Listas de datos de ejemplo
    private ArrayList<Persona> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistafacturas);

        // Inicializar componentes
        inicializarComponentes();
        
        // Inicializar datos de ejemplo
        inicializarDatos();

        // Mostrar lista inicial
        mostrarListaFacturas();

        // Eventos de botones
        configurarEventos();
    }

    private void inicializarComponentes() {
        // TextViews
        tvListaFacturas = findViewById(R.id.tvListaFacturas);
        
        // Botones
        btnGenerarFactura = findViewById(R.id.btnGenerarFactura);
        btnCrearFactura = findViewById(R.id.btnCrearFactura);
        btnRegresar = findViewById(R.id.btnRegresar);
        
        // ScrollViews
        scrollView = findViewById(R.id.scrollView);
        scrollViewGenerarFactura = findViewById(R.id.scrollViewGenerarFactura);
        
        // EditTexts
        etClienteEmpresaId = findViewById(R.id.etClienteEmpresaId);
        etMes = findViewById(R.id.etMes);
        etAño = findViewById(R.id.etAño);
    }

    private void configurarEventos() {
        btnGenerarFactura.setOnClickListener(v -> mostrarFormularioFactura());
        btnCrearFactura.setOnClickListener(v -> generarFactura());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void mostrarFormularioFactura() {
        // Ocultar lista y mostrar formulario
        scrollView.setVisibility(View.GONE);
        scrollViewGenerarFactura.setVisibility(View.VISIBLE);
        btnGenerarFactura.setVisibility(View.GONE);
        btnCrearFactura.setVisibility(View.VISIBLE);
        
        // Limpiar campos
        limpiarCampos();
    }

    private void generarFactura() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        try {
            String clienteId = etClienteEmpresaId.getText().toString().trim();
            int mes = Integer.parseInt(etMes.getText().toString().trim());
            int año = Integer.parseInt(etAño.getText().toString().trim());

            // Validar mes
            if (mes < 1 || mes > 12) {
                Toast.makeText(this, "Mes debe estar entre 1 y 12", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar año
            if (año < 2020 || año > 2030) {
                Toast.makeText(this, "Año debe estar entre 2020 y 2030", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simular generación de factura
            String mensaje = String.format("Factura generada exitosamente:\n\n" +
                    "Cliente: %s\n" +
                    "Mes: %d\n" +
                    "Año: %d\n\n" +
                    "La factura incluye todos los servicios del mes seleccionado.", 
                    clienteId, mes, año);

            Toast.makeText(this, "Factura generada", Toast.LENGTH_LONG).show();
            
            // Regresar a la vista de lista
            regresarALista();
            
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Mes y año deben ser números válidos", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al generar la factura", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos() {
        if (etClienteEmpresaId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del cliente", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etMes.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el mes", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etAño.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el año", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        etClienteEmpresaId.setText("");
        etMes.setText("");
        etAño.setText("");
    }

    private void regresarALista() {
        // Mostrar lista y ocultar formulario
        scrollView.setVisibility(View.VISIBLE);
        scrollViewGenerarFactura.setVisibility(View.GONE);
        btnGenerarFactura.setVisibility(View.VISIBLE);
        btnCrearFactura.setVisibility(View.GONE);
        
        // Actualizar lista
        mostrarListaFacturas();
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

