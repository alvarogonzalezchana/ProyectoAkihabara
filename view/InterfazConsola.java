package com.akihabara.market.view;

import com.akihabara.market.model.ProductoOtaku;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InterfazConsola {
    private Scanner scanner;

    public InterfazConsola() {
        this.scanner = new Scanner(System.in);
    }

    // Mostrar el menú principal
    public void mostrarMenu() {
        System.out.println("--- Menú Principal --- ");
        System.out.println("1. Añadir producto");
        System.out.println("2. Consultar producto por ID");
        System.out.println("3. Listar todos los productos");
        System.out.println("4. Listar productos por nombre");
        System.out.println("5. Listar productos por categoría");
        System.out.println("6. Actualizar producto");
        System.out.println("7. Eliminar producto");
        System.out.println("8. Salir");
        System.out.print("Elige una opción: ");
    }

    // Leer y validar la opción elegida por el usuario
    public int leerOpcion() {
        int opcion = 0;
        boolean valido = false;
        while (!valido) {
            try {
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > 8) {
                    System.out.println("Opción no válida. Intenta de nuevo.");
                } else {
                    valido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número.");
                scanner.next(); 
            }
        }
        return opcion;
    }

    // Pedir datos de un producto
    public ProductoOtaku pedirDatosProducto() {
        System.out.print("Nombre del producto: ");
        String nombre = scanner.next();
        System.out.print("Categoría del producto: ");
        String categoria = scanner.next();
        System.out.print("Precio del producto: ");
        double precio = scanner.nextDouble();
        System.out.print("Stock del producto: ");
        int stock = scanner.nextInt();
        return new ProductoOtaku(0, nombre, categoria, precio, stock); 
    }

    // Pedir un ID
    public int pedirId() {
        System.out.print("Introduce el ID del producto: ");
        return scanner.nextInt();
    }

    // Pedir un nombre de producto
    public String pedirNombreProducto() {
        System.out.print("Introduce el nombre del producto: ");
        return scanner.next();
    }

    // Pedir una categoría
    public String pedirCategoria() {
        System.out.print("Introduce la categoría: ");
        return scanner.next();
    }

    // Mostrar lista de productos
    public void mostrarProductos(List<ProductoOtaku> productos) {
        System.out.println("=== Lista de Productos ===");
        for (ProductoOtaku producto : productos) {
            System.out.println(producto);
        }
    }

    // Mostrar mensaje de confirmación
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Cerrar el scanner
    public void cerrar() {
        scanner.close();
    }
}
