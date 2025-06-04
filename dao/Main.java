package com.akihabara.market.dao;
import com.akihabara.market.model.ProductoOtaku;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de ProductoDAO
        ProductoDAO productoDAO = new ProductoDAO();

        // Crear un nuevo producto
        ProductoOtaku nuevoProducto = new ProductoOtaku("Figura de acción de Luffy", "Figura", 30.00, 10);
        productoDAO.agregarProducto(nuevoProducto);

        // Obtener un producto por ID
        ProductoOtaku productoObtenido = productoDAO.obtenerProductoPorId(1); 
        System.out.println("Producto obtenido: " + productoObtenido);

        // Obtener todos los productos
        List<ProductoOtaku> todosLosProductos = productoDAO.obtenerTodosLosProductos();
        System.out.println("Todos los productos:");
        for (ProductoOtaku producto : todosLosProductos) {
            System.out.println(producto);
        }

        // Actualizar un producto
        if (productoObtenido != null) {
            productoObtenido.setPrecio(35.00); 
            boolean actualizado = productoDAO.actualizarProducto(productoObtenido);
            System.out.println("Producto actualizado: " + actualizado);
        }

        // Eliminar un producto
        boolean eliminado = productoDAO.eliminarProducto(1); 
        System.out.println("Producto eliminado: " + eliminado);

        // Buscar productos por nombre
        List<ProductoOtaku> productosBuscados = productoDAO.buscarProductosPorNombre("Luffy");
        System.out.println("Productos buscados por nombre:");
        for (ProductoOtaku producto : productosBuscados) {
            System.out.println(producto);
        }

        // Buscar productos por categoría
        List<ProductoOtaku> productosPorCategoria = productoDAO.buscarProductoPorCategoria("Figura");
        System.out.println("Productos buscados por categoría:");
        for (ProductoOtaku producto : productosPorCategoria) {
            System.out.println(producto);
        }

        // Cerrar la conexión
        productoDAO.cerrarConexion();
    }
}
