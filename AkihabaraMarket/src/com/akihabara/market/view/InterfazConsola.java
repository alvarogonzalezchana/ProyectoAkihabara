package com.akihabara.market.view;

import com.akihabara.market.model.ProductoOtaku;

import java.util.List;
import java.util.Scanner;

public class InterfazConsola {
    final Scanner scanner;

    public InterfazConsola() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n=== MENÚ AKIHABARA MARKET ===");
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

    public int leerOpcion() {
        int opcion = -1;
        while (true) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion < 1 || opcion > 8) {
                    System.out.print("Opción inválida. Intentalo de nuevo: ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Introduce un número: ");
            }
        }
        return opcion;
    }

    public ProductoOtaku pedirDatosProducto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Categoría (Figura, Manga, Póster, Llavero, Ropa): ");
        String categoria = scanner.nextLine().trim();

        double precio = -1;
        while (precio < 0) {
            System.out.print("Precio: ");
            try {
                precio = Double.parseDouble(scanner.nextLine());
                if (precio < 0) System.out.println("El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número válido.");
            }
        }

        int stock = -1;
        while (stock < 0) {
            System.out.print("Stock: ");
            try {
                stock = Integer.parseInt(scanner.nextLine());
                if (stock < 0) System.out.println("El stock no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número válido.");
            }
        }

        // El id será manejado por la base de datos/autoincremental
        return new ProductoOtaku(0, nombre, categoria, precio, stock);
    }
    public String[] pedirTipoYFranquicia() {
        System.out.print("Introduce el tipo de producto (ej. figura, camiseta): ");
        String tipo = scanner.nextLine().trim();
        System.out.print("Introduce la franquicia (ej. Naruto, One Piece): ");
        String franquicia = scanner.nextLine().trim();
        return new String[]{tipo, franquicia};
    }
    
    public int pedirId() {
        int id = -1;
        while (id < 0) {
            System.out.print("Introduce el ID del producto: ");
            try {
                id = Integer.parseInt(scanner.nextLine());
                if (id < 0) System.out.println("ID debe ser 0 o mayor.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número entero.");
            }
        }
        return id;
    }

    public String pedirNombre() {
        System.out.print("Introduce el nombre del producto para búsqueda: ");
        return scanner.nextLine().trim();
    }

    public String pedirCategoria() {
        System.out.print("Introduce la categoría para búsqueda: ");
        return scanner.nextLine().trim();
    }

    public void mostrarProductos(List<ProductoOtaku> productos) {
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos.");
        } else {
            System.out.println("=== PRODUCTOS ===");
            productos.forEach(System.out::println);
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void cerrar() {
        scanner.close();
    }
}
