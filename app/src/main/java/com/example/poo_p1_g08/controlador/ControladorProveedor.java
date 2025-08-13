package com.example.poo_p1_g08.controlador;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poo_p1_g08.R;
import com.example.poo_p1_g08.modelo.Persona;
import com.example.poo_p1_g08.modelo.Proveedor;

public class ControladorProveedor extends AppCompatActivity {
    private ArrayList<Persona> lista;
    private Button btnAgregarProveedor, btnRegresar, btnGuardarProveedor;
    private ScrollView scrollViewProveedor, scrollViewListaProveedores;
    private EditText etIdProveedor, etNombreProveedor, etTelefonoProveedor, etDescripcionProveedor;
    private TextView tvProveedores;
    
    public ControladorProveedor() {
        // esto es para el oncreate
    }
    
    public ControladorProveedor(ArrayList<Persona> lista){
        this.lista = lista;
    }

    public String agregarProveedor(Proveedor proveedor){
        for(Persona p : lista){
            if(p instanceof Proveedor){
                Proveedor i = (Proveedor)p;
                if(i.getId().equalsIgnoreCase(proveedor.getId())){
                    return ">>Proveedor ya existente intente nuevamente";
                }
            }
        }
        lista.add(proveedor);
        return "Proveedor agregado satisfactoriamente";
    }

    public ArrayList<Persona> getListaProveedores(){
        return lista;
    }

    public boolean crearOrden(){
        return true;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vistaproveedor);


        lista = new ArrayList<>();
        lista.add(new Proveedor("1204684450", "Repuestos Eléctricos R.E", "0991111111", "Suministro de repuestos y accesorios para vehículos"));
        lista.add(new Proveedor("1204684451", "Autopartes García", "0992222222", "Venta de autopartes y repuestos automotrices"));
        lista.add(new Proveedor("1204684452", "Mecánica Industrial", "0993333333", "Servicios de mecánica y repuestos industriales"));


        inicializar();
        mostrarListaProveedores();
    }


    private void inicializar() {
        btnAgregarProveedor = findViewById(R.id.btnAgregarProveedor);
        btnRegresar = findViewById(R.id.btnRegresar);
        
        scrollViewProveedor = findViewById(R.id.scrollViewProveedor);
        scrollViewListaProveedores = findViewById(R.id.scrollViewListaProveedores);
        tvProveedores = findViewById(R.id.tvProveedores);
        
        etIdProveedor = findViewById(R.id.etIdProveedor);
        etNombreProveedor = findViewById(R.id.etNombreProveedor);
        etTelefonoProveedor = findViewById(R.id.etTelefonoProveedor);
        etDescripcionProveedor = findViewById(R.id.etDescripcionProveedor);
        
        btnGuardarProveedor = findViewById(R.id.btnGuardarProveedor);
        btnAgregarProveedor.setOnClickListener(v -> mostrarFormularioProveedor());
        btnGuardarProveedor.setOnClickListener(v -> guardarProveedor());
        btnRegresar.setOnClickListener(v -> finish());
    }
    
    private void mostrarListaProveedores() {
        if (lista == null || lista.isEmpty()) {
            tvProveedores.setText("No hay proveedores registrados");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("PROVEEDORES REGISTRADOS:\n\n");
        
        for (Persona p : lista) {
            if (p instanceof Proveedor) {
                Proveedor prov = (Proveedor) p;
                sb.append(String.format("ID: %s\n", prov.getId()));
                sb.append(String.format("Nombre: %s\n", prov.getNombre()));
                sb.append(String.format("Teléfono: %s\n", prov.getTelefono()));
                sb.append(String.format("Descripción: %s\n", prov.getDescripcion()));
                sb.append("------------------------\n");
            }
        }
        
        String textoFinal = sb.toString();
        tvProveedores.setText(textoFinal);
        
        scrollViewListaProveedores.setVisibility(View.VISIBLE);
        scrollViewProveedor.setVisibility(View.GONE);
        btnAgregarProveedor.setVisibility(View.VISIBLE);
    }
    
    private void mostrarFormularioProveedor() {
        scrollViewProveedor.setVisibility(View.VISIBLE);
        scrollViewListaProveedores.setVisibility(View.GONE);
        btnAgregarProveedor.setVisibility(View.GONE);
        limpiarCampos();
    }
    
    private void limpiarCampos() {
        etIdProveedor.setText("");
        etNombreProveedor.setText("");
        etTelefonoProveedor.setText("");
        etDescripcionProveedor.setText("");
    }
    
    private void guardarProveedor() {
        String id = etIdProveedor.getText().toString().trim();
        String nombre = etNombreProveedor.getText().toString().trim();
        String telefono = etTelefonoProveedor.getText().toString().trim();
        String descripcion = etDescripcionProveedor.getText().toString().trim();
        
        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || descripcion.isEmpty()) {
            return;
        }
        
        Proveedor nuevoProveedor = new Proveedor(id, nombre, telefono, descripcion);
        String resultado = agregarProveedor(nuevoProveedor);
        
        if (resultado.equals("Proveedor agregado satisfactoriamente")) {
            scrollViewProveedor.setVisibility(View.GONE);
            scrollViewListaProveedores.setVisibility(View.VISIBLE);
            btnAgregarProveedor.setVisibility(View.VISIBLE);
            limpiarCampos();
            mostrarListaProveedores();
        }
    }
}
