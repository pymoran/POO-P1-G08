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
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.utils.DataManager;
import java.util.List;
import java.util.ArrayList;

public class ControladorFacturaEmpresa extends AppCompatActivity {
    private TextView tvListaFacturas;
    private Button btnGenerarFactura, btnCrearFactura, btnRegresar;
    private ScrollView scrollView, scrollViewGenerarFactura;
    private EditText etClienteEmpresaId, etMes, etAño;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistafacturas);

        // Inicializar la aplicación si es necesario
        DataManager.inicializarApp(this);

        // Inicializar componentes
        inicializarComponentes();

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
        
        // Mostrar clientes empresariales disponibles
        mostrarClientesEmpresariales();
    }

    private void mostrarClientesEmpresariales() {
        StringBuilder datos = new StringBuilder();
        
        List<Cliente> clientesEmpresariales = obtenerClientesEmpresariales();
        datos.append("CLIENTES EMPRESARIALES:\n");
        for (Cliente cliente : clientesEmpresariales) {
            datos.append(String.format("%s (%s)\n", cliente.getNombre(), cliente.getId()));
        }
        
        tvListaFacturas.setText(datos.toString());
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
                return;
            }

            // Validar año
            if (año < 2020 || año > 2030) {
                return;
            }

            // Verificar que el cliente sea empresarial
            Cliente cliente = DataManager.buscarClientePorId(this, clienteId);
            if (cliente == null) {
                return;
            }
            
            if (!cliente.getTipoCliente()) {
                return;
            }

            // Generar factura usando DataManager
            FacturaEmpresa factura = DataManager.generarFacturaEmpresa(this, clienteId, año, mes);
            
            if (factura != null) {
                // Mostrar detalle de la factura
                mostrarDetalleFactura(factura, mes, año);
                regresarALista();
            }
            
        } catch (NumberFormatException e) {
            // Error en formato de números
        } catch (Exception e) {
            // Error al generar factura
        }
    }

    private void mostrarDetalleFactura(FacturaEmpresa factura, int mes, int año) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== FACTURA EMPRESARIAL ===\n\n");
        sb.append(String.format("Cliente: %s\n", factura.getCliente().getNombre()));
        sb.append(String.format("ID Cliente: %s\n", factura.getCliente().getId()));
        sb.append(String.format("Período: %s %d\n\n", obtenerNombreMes(mes), año));
        
        // Obtener órdenes del cliente en el período
        List<OrdenServicio> ordenes = obtenerOrdenesClientePorPeriodo(factura.getCliente().getId(), año, mes);
        
        if (!ordenes.isEmpty()) {
            sb.append("SERVICIOS CONTRATADOS:\n");
            sb.append("------------------------\n");
            
            for (OrdenServicio orden : ordenes) {
                sb.append(String.format("Orden: %s\n", orden.getCodigo()));
                sb.append(String.format("Fecha: %s\n", orden.getFecha()));
                sb.append(String.format("Vehículo: %s\n", orden.getVehiculo().getPlaca()));
                sb.append(String.format("Técnico: %s\n", orden.getTecnico().getNombre()));
                sb.append(String.format("Total Orden: $%.2f\n", orden.getTotal()));
                sb.append("------------------------\n");
            }
        }
        
        sb.append("\nRESUMEN DE FACTURACIÓN:\n");
        sb.append("------------------------\n");
        sb.append(String.format("Total Servicios: $%.2f\n", factura.getTotalServicios()));
        sb.append(String.format("Cargo Mensual (Prioridad): $%.2f\n", FacturaEmpresa.getCostoPrioridad()));
        sb.append(String.format("TOTAL A PAGAR: $%.2f\n", factura.getTotalFactura()));
        
        // Mostrar factura en pantalla
        tvListaFacturas.setText(sb.toString());
    }

    private List<OrdenServicio> obtenerOrdenesClientePorPeriodo(String clienteId, int año, int mes) {
        List<OrdenServicio> todasLasOrdenes = DataManager.obtenerTodasLasOrdenes(this);
        List<OrdenServicio> ordenesCliente = new ArrayList<>();
        
        for (OrdenServicio orden : todasLasOrdenes) {
            if (orden.getCliente().getId().equals(clienteId)) {
                // Parsear fecha (formato: YYYY-MM-DD)
                String fecha = orden.getFecha();
                if (fecha != null && fecha.length() >= 7) {
                    try {
                        int ordenAño = Integer.parseInt(fecha.substring(0, 4));
                        int ordenMes = Integer.parseInt(fecha.substring(5, 7));
                        
                        if (ordenAño == año && ordenMes == mes) {
                            ordenesCliente.add(orden);
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar fechas mal formateadas
                    }
                }
            }
        }
        
        return ordenesCliente;
    }

    private String obtenerNombreMes(int mes) {
        String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes];
    }

    private List<Cliente> obtenerClientesEmpresariales() {
        List<Cliente> todosLosClientes = DataManager.obtenerTodosLosClientes(this);
        List<Cliente> clientesEmpresariales = new ArrayList<>();
        
        for (Cliente cliente : todosLosClientes) {
            if (cliente.getTipoCliente()) { // Solo clientes empresariales
                clientesEmpresariales.add(cliente);
            }
        }
        
        return clientesEmpresariales;
    }

    private boolean validarCampos() {
        if (etClienteEmpresaId.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etMes.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (etAño.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        etClienteEmpresaId.setText("");
        etMes.setText("");
        etAño.setText("2024");
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
        List<FacturaEmpresa> facturas = DataManager.obtenerTodasLasFacturas(this);
        
        if (facturas == null || facturas.isEmpty()) {
            tvListaFacturas.setText("No hay facturas generadas\n\n" +
                    "Use el formulario para generar nuevas facturas empresariales.\n\n" +
                    "Clientes empresariales disponibles:\n" +
                    obtenerListaClientesEmpresariales());
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE FACTURAS GENERADAS:\n\n");
        
        for (FacturaEmpresa factura : facturas) {
            sb.append(String.format("Cliente: %s\n", factura.getCliente().getNombre()));
            sb.append(String.format("Período: %s %d\n", obtenerNombreMes(factura.getMes()), factura.getAño()));
            sb.append(String.format("Total Servicios: $%.2f\n", factura.getTotalServicios()));
            sb.append(String.format("Cargo Prioridad: $%.2f\n", FacturaEmpresa.getCostoPrioridad()));
            sb.append(String.format("Total: $%.2f\n", factura.getTotalFactura()));
            sb.append("------------------------\n");
        }
        
        tvListaFacturas.setText(sb.toString());
    }

    private String obtenerListaClientesEmpresariales() {
        List<Cliente> clientesEmpresariales = obtenerClientesEmpresariales();
        StringBuilder sb = new StringBuilder();
        
        for (Cliente cliente : clientesEmpresariales) {
            sb.append("• ").append(cliente.getNombre()).append(" (").append(cliente.getId()).append(")\n");
        }
        
        return sb.toString();
    }
}

