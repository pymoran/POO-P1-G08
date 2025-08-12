package com.example.poo_p1_g08.controlador;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.poo_p1_g08.modelo.Cliente;
import com.example.poo_p1_g08.modelo.FacturaEmpresa;
import com.example.poo_p1_g08.modelo.OrdenServicio;
import java.util.List;

public class ControladorFacturaEmpresa {
    private ControladorCliente controladorCliente;
    private ControladorOrden controladorOrdenes;

    public ControladorFacturaEmpresa(ControladorCliente controladorCliente, ControladorOrden controladorOrdenes) {
        this.controladorCliente = controladorCliente;
        this.controladorOrdenes = controladorOrdenes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FacturaEmpresa generarFacturaEmpresa(String codigo, int mes, int año) {
        // Busca el cliente por su código (implementa este método en ControladorCliente si no existe)
        Cliente cliente = controladorCliente.buscarClientePorId(, codigo);
        if (cliente == null) return null;

        // Solo generar factura si el cliente es empresarial
        if (!cliente.getTipoCliente()) return null;

        FacturaEmpresa factura = new FacturaEmpresa(cliente, mes, año);

        List<OrdenServicio> lista = controladorOrdenes.getListaOrdenes();
        if (lista == null) return factura;

        for (OrdenServicio o : lista) {
            if (o.getCliente() != null
                    && codigo.equals(o.getCliente().getId())
                    && o.getFecha().getMonth() == mes
                    && o.getFecha().getYear() == año) {
                factura.agregarOrden(o);
            }
        }
        return factura;
    }

    public boolean existeClienteEmpresarial(String codigo) {
        Cliente cliente = controladorCliente.buscarClientePorId(  id, codigo  );
        return cliente != null && cliente.getTipoCliente();
    }
}

