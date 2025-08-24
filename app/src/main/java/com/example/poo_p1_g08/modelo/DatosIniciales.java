package com.example.poo_p1_g08.modelo;

import java.util.ArrayList;

public class DatosIniciales {
    public static ArrayList<Tecnico> tecnicos = new ArrayList<>();
    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Servicio> servicios = new ArrayList<>();
    public static ArrayList<Proveedor> proveedores = new ArrayList<>();
    public static ArrayList<OrdenServicio> ordenes = new ArrayList<>();

    public static void inicializarApp() {
        if (!tecnicos.isEmpty()) return; // evitar recargar

        // === Técnicos ===
        Tecnico t1 = new Tecnico("T001", "Juan Pérez", "juan@taller.com", "123456789");
        Tecnico t2 = new Tecnico("T002", "Ana López", "ana@taller.com", "987654321");
        tecnicos.add(t1);
        tecnicos.add(t2);

        // === Clientes ===
        Cliente c1 = new Cliente("C001", "Carlos Ruiz", "carlos@mail.com", "111111111");
        Cliente c2 = new Cliente("C002", "María Torres", "maria@mail.com", "222222222");
        Cliente c3 = new Cliente("C003", "Pedro Gómez", "pedro@mail.com", "333333333");
        Cliente c4 = new Cliente("C004", "Lucía Ramírez", "lucia@mail.com", "444444444");
        clientes.add(c1); clientes.add(c2); clientes.add(c3); clientes.add(c4);

        // === Servicios ===
        Servicio s1 = new Servicio("S001", "Cambio de aceite", 50.0);
        Servicio s2 = new Servicio("S002", "Alineación y balanceo", 70.0);
        Servicio s3 = new Servicio("S003", "Revisión general", 120.0);
        Servicio s4 = new Servicio("S004", "Frenos", 90.0);
        Servicio s5 = new Servicio("S005", "Cambio de batería", 80.0);
        Servicio s6 = new Servicio("S006", "Cambio de llantas", 300.0);
        servicios.add(s1); servicios.add(s2); servicios.add(s3);
        servicios.add(s4); servicios.add(s5); servicios.add(s6);

        // === Proveedores ===
        Proveedor p1 = new Proveedor("P001", "Repuestos Quito", "Av. Amazonas", "099999999");
        Proveedor p2 = new Proveedor("P002", "Autopartes Guayaquil", "Av. 9 de Octubre", "088888888");
        proveedores.add(p1); proveedores.add(p2);

        // === Vehículos ===
        Vehiculo v1 = new Vehiculo("ABC-123", "Toyota", "Corolla", "2018");
        Vehiculo v2 = new Vehiculo("XYZ-789", "Chevrolet", "Spark", "2020");
        Vehiculo v3 = new Vehiculo("EMP-456", "Hyundai", "Tucson", "2019");
        Vehiculo v4 = new Vehiculo("EMP-321", "Kia", "Sportage", "2021");

        // === Órdenes ===
        OrdenServicio o1 = new OrdenServicio(c1, v1, t1, "2025-08-05");
        o1.agregarDetalle(s1, 1); // cambio aceite
        o1.agregarDetalle(s2, 1); // alineación

        OrdenServicio o2 = new OrdenServicio(c2, v2, t1, "2025-08-10");
        o2.agregarDetalle(s3, 1);
        o2.agregarDetalle(s4, 1);

        OrdenServicio o3 = new OrdenServicio(c3, v3, t2, "2025-08-15");
        o3.agregarDetalle(s2, 1);
        o3.agregarDetalle(s5, 1);

        OrdenServicio o4 = new OrdenServicio(c4, v4, t2, "2025-08-20");
        o4.agregarDetalle(s6, 1);
        o4.agregarDetalle(s1, 1);

        ordenes.add(o1); ordenes.add(o2); ordenes.add(o3); ordenes.add(o4);
    }
}
