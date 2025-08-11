package com.example.poo_p1_g08.controlador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import com.example.poo_p1_g08.modelo.Persona;
import com.example.poo_p1_g08.modelo.Servicio;
import com.example.poo_p1_g08.modelo.Tecnico;
import com.example.poo_p1_g08.modelo.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorOrden extends AppCompatActivity {
    private ArrayList<OrdenServicio> lista;
    private TextView tvListaOrdenes;
    private Button btnCrearOrden, btnRegresar;
    private ScrollView scrollView, scrollViewCrearOrden;
    
    // Listas de datos de ejemplo
    private ArrayList<Persona> clientes;
    private ArrayList<Persona> tecnicos;
    private ArrayList<Servicio> servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaordenes);

        // Inicializar componentes
        tvListaOrdenes = findViewById(R.id.tvListaOrdenes);
        btnCrearOrden = findViewById(R.id.btnCrearOrden);
        btnRegresar = findViewById(R.id.btnRegresar);
        scrollView = findViewById(R.id.scrollView);
        scrollViewCrearOrden = findViewById(R.id.scrollViewCrearOrden);

        // Inicializar datos de ejemplo
        inicializarDatos();

        // Mostrar lista inicial
        mostrarListaOrdenes();

        // Eventos de botones
        btnCrearOrden.setOnClickListener(v -> mostrarVistaCrearOrden());
        btnRegresar.setOnClickListener(v -> finish());
    }

    // Mostrar vista de creación de orden
    private void mostrarVistaCrearOrden() {
        scrollView.setVisibility(View.GONE);
        scrollViewCrearOrden.setVisibility(View.VISIBLE);
        btnCrearOrden.setVisibility(View.GONE);
    }

    // Método para mostrar la lista de órdenes en el TextView
    private void mostrarListaOrdenes() {
        if (lista == null || lista.isEmpty()) {
            tvListaOrdenes.setText("No hay órdenes registradas");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DE ÓRDENES:\n\n");
        
        for (OrdenServicio orden : lista) {
            sb.append(String.format("Código: %s\n", orden.getCodigo()));
            sb.append(String.format("Cliente: %s\n", orden.getCliente().getNombre()));
            sb.append(String.format("Vehículo: %s (%s)\n", orden.getVehiculo().getPlaca(), orden.getVehiculo().getTipo()));
            sb.append(String.format("Técnico: %s\n", orden.getTecnico().getNombre()));
            sb.append(String.format("Fecha: %s\n", orden.getFecha()));
            sb.append(String.format("Total: $%.2f\n", orden.getTotal()));
            sb.append("------------------------\n");
        }
        
        tvListaOrdenes.setText(sb.toString());
    }

    // Método para obtener la lista de órdenes (requerido por ControladorFacturaEmpresa)
    public ArrayList<OrdenServicio> getListaOrdenes() {
        return lista;
    }

    // Inicializar datos de ejemplo con 4 órdenes
    private void inicializarDatos() {
        // Clientes de ejemplo
        clientes = new ArrayList<>();
        clientes.add(new Cliente("C001", "Carlos Ruiz", "0991111111", "Av. Principal 123", false));
        clientes.add(new Cliente("C002", "Ana García", "0992222222", "Calle Secundaria 456", false));
        clientes.add(new Cliente("C003", "Empresa ABC", "0993333333", "Zona Industrial 789", true));

        // Técnicos de ejemplo
        tecnicos = new ArrayList<>();
        tecnicos.add(new Tecnico("T001", "Juan Pérez", "0991234567", "Motor"));
        tecnicos.add(new Tecnico("T002", "María López", "0997654321", "Electricidad"));

        // Servicios de ejemplo
        servicios = new ArrayList<>();
        servicios.add(new Servicio("S001", "Cambio de aceite", 25.00));
        servicios.add(new Servicio("S002", "Frenos", 80.00));
        servicios.add(new Servicio("S003", "Suspensión", 120.00));
        servicios.add(new Servicio("S004", "Electricidad", 45.00));

        // Crear 4 órdenes de ejemplo
        lista = new ArrayList<>();
        
        // Orden 1
        Cliente cliente1 = (Cliente) clientes.get(0);
        Tecnico tecnico1 = (Tecnico) tecnicos.get(0);
        Vehiculo vehiculo1 = new Vehiculo("ABC-123", Vehiculo.TipoVehiculo.AUTOMOVIL);
        OrdenServicio orden1 = new OrdenServicio(cliente1, vehiculo1, tecnico1, LocalDate.now());
        orden1.agregarDetalle(servicios.get(0), 1); // Cambio de aceite
        orden1.agregarDetalle(servicios.get(1), 1); // Frenos
        lista.add(orden1);

        // Orden 2
        Cliente cliente2 = (Cliente) clientes.get(1);
        Tecnico tecnico2 = (Tecnico) tecnicos.get(1);
        Vehiculo vehiculo2 = new Vehiculo("XYZ-789", Vehiculo.TipoVehiculo.MOTOCICLETA);
        OrdenServicio orden2 = new OrdenServicio(cliente2, vehiculo2, tecnico2, LocalDate.now().minusDays(1));
        orden2.agregarDetalle(servicios.get(2), 1); // Suspensión
        lista.add(orden2);

        // Orden 3
        Cliente cliente3 = (Cliente) clientes.get(2);
        Vehiculo vehiculo3 = new Vehiculo("DEF-456", Vehiculo.TipoVehiculo.BUS);
        OrdenServicio orden3 = new OrdenServicio(cliente3, vehiculo3, tecnico1, LocalDate.now().minusDays(2));
        orden3.agregarDetalle(servicios.get(3), 2); // Electricidad x2
        orden3.agregarDetalle(servicios.get(0), 1); // Cambio de aceite
        lista.add(orden3);

        // Orden 4
        Vehiculo vehiculo4 = new Vehiculo("GHI-789", Vehiculo.TipoVehiculo.AUTOMOVIL);
        OrdenServicio orden4 = new OrdenServicio(cliente1, vehiculo4, tecnico2, LocalDate.now().minusDays(3));
        orden4.agregarDetalle(servicios.get(1), 1); // Frenos
        orden4.agregarDetalle(servicios.get(2), 1); // Suspensión
        orden4.agregarDetalle(servicios.get(3), 1); // Electricidad
        lista.add(orden4);
    }
}
