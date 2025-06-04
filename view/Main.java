package com.akihabara.market.view;

import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.model.ProductoOtaku;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InterfazConsola vista = new InterfazConsola();
        ProductoDAO productoDAO = new ProductoDAO();
        boolean salir = false;

        while (!salir) {
            vista.mostrarMenu();
            int opcion = vista.leerOpcion();

            switch (opcion) {
                case 1: // Añadir producto
                    ProductoOtaku nuevoProducto = vista.pedirDatosProducto();
                    productoDAO.agregarProducto(nuevoProducto);
                    vista.mostrarMensaje("Producto añadido con éxito.");
                    break;
                case 2: // Consultar producto por ID
                    int id = vista.pedirId();
                    ProductoOtaku producto = productoDAO.obtenerProductoPorId(id);
                    if (producto != null) {
                        vista.mostrarMensaje("Producto encontrado: " + producto);
                    } else {
                        vista.mostrarMensaje("Producto no encontrado.");
                    }
                    break;
                case 3: // Listar todos los productos
                    List<ProductoOtaku> todosLosProductos = productoDAO.obtenerTodosLosProductos();
                    vista.mostrarProductos(todosLosProductos);
                    break;
                case 4: // Listar productos por nombre
                    String nombre = vista.pedirNombreProducto();
                    List<ProductoOtaku> productosPorNombre = productoDAO.buscarProductosPorNombre(nombre);
                    vista.mostrarProductos(productosPorNombre);
                    break;
                case 5: // Listar productos por categoría
                    String categoria = vista.pedirCategoria();
                    List<ProductoOtaku> productosPorCategoria = productoDAO.buscarProductoPorCategoria(categoria);
                    vista.mostrarProductos(productosPorCategoria);
                    break;
                case 6: // Actualizar producto
                    int idActualizar = vista.pedirId();
                    ProductoOtaku productoActualizar = productoDAO.obtenerProductoPorId(idActualizar);
                    if (productoActualizar != null) {
                        ProductoOtaku nuevosDatos = vista.pedirDatosProducto();
                        nuevosDatos.setId(idActualizar); 
                        boolean actualizado = productoDAO.actualizarProducto(nuevosDatos);
                        vista.mostrarMensaje(actualizado ? "Producto actualizado con éxito." : "Error al actualizar el producto.");
                    } else {
                        vista.mostrarMensaje("Producto no encontrado.");
                    }
                    break;
                case 7: // Eliminar producto
                    int idEliminar = vista.pedirId();
                    boolean eliminado = productoDAO.eliminarProducto(idEliminar);
                    vista.mostrarMensaje(eliminado ? "Producto eliminado con éxito." : "Error al eliminar el producto.");
                    break;
                case 8: // Salir
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida.");
            }
        }

        vista.cerrar();
        productoDAO.cerrarConexion(); 
    }
}
