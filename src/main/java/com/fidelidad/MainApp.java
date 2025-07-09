package com.fidelidad;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.model.Nivel;
import com.fidelidad.repository.InMemoryClienteRepository;
import com.fidelidad.repository.InMemoryCompraRepository;
import com.fidelidad.service.ClienteService;
import com.fidelidad.service.CompraService;
import com.fidelidad.service.FidelidadService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteService clienteService = 
        new ClienteService(new InMemoryClienteRepository());
    private static final CompraService compraService = 
        new CompraService(
            new InMemoryCompraRepository(),
            new InMemoryClienteRepository(),
            new FidelidadService()
        );

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Gestión de Compras");
            System.out.println("3. Mostrar Puntos/Nivel de Cliente");
            System.out.println("4. Salir");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();  // limpiar buffer
            
            switch (opcion) {
                case 1: gestionClientes(); break;
                case 2: gestionCompras(); break;
                case 3: mostrarPuntosCliente(); break;
                case 4: salir = true; break;
                default: System.out.println("Opción inválida");
            }
        }
    }

    private static void gestionClientes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Actualizar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1: agregarCliente(); break;
                case 2: listarClientes(); break;
                case 3: actualizarCliente(); break;
                case 4: eliminarCliente(); break;
                case 5: volver = true; break;
                default: System.out.println("Opción inválida");
            }
        }
    }

    private static void agregarCliente() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        try {
            Cliente cliente = new Cliente(id, nombre, correo);
            clienteService.agregarCliente(cliente);
            System.out.println("Cliente agregado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("\nLISTA DE CLIENTES:");
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + 
                                  " | Correo: " + c.getCorreo() + " | Puntos: " + c.getPuntos() + 
                                  " | Nivel: " + c.getNivel());
            }
        }
    }

    private static void actualizarCliente() {
        System.out.print("ID del cliente a actualizar: ");
        String id = scanner.nextLine();
        Cliente cliente = clienteService.obtenerCliente(id);
        
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        
        System.out.print("Nuevo nombre (" + cliente.getNombre() + "): ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo correo (" + cliente.getCorreo() + "): ");
        String correo = scanner.nextLine();
        
        cliente.setNombre(nombre);
        cliente.setCorreo(correo);
        
        try {
            clienteService.actualizarCliente(cliente);
            System.out.println("Cliente actualizado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        String id = scanner.nextLine();
        clienteService.eliminarCliente(id);
        System.out.println("Cliente eliminado exitosamente.");
    }

    private static void gestionCompras() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE COMPRAS ---");
            System.out.println("1. Registrar compra");
            System.out.println("2. Listar todas las compras");
            System.out.println("3. Buscar compra por ID");
            System.out.println("4. Actualizar compra");
            System.out.println("5. Eliminar compra");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer

            switch (opcion) {
                case 1: registrarCompra(); break;
                case 2: listarTodasCompras(); break;
                case 3: buscarCompraPorId(); break;
                case 4: actualizarCompra(); break;
                case 5: eliminarCompra(); break;
                case 6: volver = true; break;
                default: System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarCompra() {
        System.out.print("ID Compra: ");
        String idCompra = scanner.nextLine();
        System.out.print("ID Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Monto: ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Fecha (AAAA-MM-DD): ");
        String fechaStr = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(fechaStr);
        
        Compra compra = new Compra(idCompra, idCliente, monto, fecha);
        try {
            compraService.registrarCompra(compra);
            System.out.println("Compra registrada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarTodasCompras() {
        List<Compra> compras = compraService.obtenerTodasCompras();
        if (compras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            System.out.println("\nLISTA DE COMPRAS:");
            for (Compra c : compras) {
                System.out.println("ID Compra: " + c.getIdCompra() + " | Cliente: " + c.getIdCliente() + 
                                  " | Monto: $" + c.getMonto() + " | Fecha: " + c.getFecha());
            }
        }
    }

    private static void buscarCompraPorId() {
        System.out.print("ID de la compra: ");
        String id = scanner.nextLine();
        Compra compra = compraService.obtenerCompraPorId(id);
        
        if (compra != null) {
            System.out.println("\nDETALLE DE COMPRA:");
            System.out.println("ID: " + compra.getIdCompra());
            System.out.println("Cliente: " + compra.getIdCliente());
            System.out.println("Monto: $" + compra.getMonto());
            System.out.println("Fecha: " + compra.getFecha());
        } else {
            System.out.println("Compra no encontrada.");
        }
    }

    private static void actualizarCompra() {
        System.out.print("ID de la compra a actualizar: ");
        String idCompra = scanner.nextLine();
        Compra compra = compraService.obtenerCompraPorId(idCompra);
        
        if (compra == null) {
            System.out.println("Compra no encontrada.");
            return;
        }
        
        System.out.print("Nuevo ID Cliente (" + compra.getIdCliente() + "): ");
        String idCliente = scanner.nextLine();
        System.out.print("Nuevo monto ($" + compra.getMonto() + "): ");
        double monto = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Nueva fecha (" + compra.getFecha() + "): ");
        String fechaStr = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(fechaStr);
        
        Compra compraActualizada = new Compra(idCompra, idCliente, monto, fecha);
        compraService.actualizarCompra(compraActualizada);
        System.out.println("Compra actualizada exitosamente.");
    }

    private static void eliminarCompra() {
        System.out.print("ID de la compra a eliminar: ");
        String id = scanner.nextLine();
        compraService.eliminarCompra(id);
        System.out.println("Compra eliminada exitosamente.");
    }

    private static void mostrarPuntosCliente() {
        System.out.print("ID Cliente: ");
        String id = scanner.nextLine();
        Cliente cliente = clienteService.obtenerCliente(id);
        if (cliente != null) {
            System.out.println("Puntos: " + cliente.getPuntos());
            System.out.println("Nivel: " + cliente.getNivel());
        } else {
            System.out.println("Cliente no encontrado");
        }
    }
}