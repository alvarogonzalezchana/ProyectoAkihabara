package com.akihabara.market.view;

import com.akihabara.market.ai.LlmService;
import com.akihabara.market.dao.ProductoDAO;
import com.akihabara.market.model.ProductoOtaku;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        InterfazConsola vista = new InterfazConsola();
        ProductoDAO dao = new ProductoDAO();
        LlmService llmService = new LlmService();

        boolean salir = false;

        while (!salir) {
            vista.mostrarMenu();
            int opcion = vista.leerOpcion();

            switch (opcion) {
                case 1: // Añadir producto con sugerencia IA
                    String[] tipoYFranquicia = vista.pedirTipoYFranquicia();
                    String tipoProducto = tipoYFranquicia[0];
                    String franquiciaProducto = tipoYFranquicia[1];

                    String nombreSugerido = llmService.sugerirNombreProducto(tipoProducto, franquiciaProducto);
                    vista.mostrarMensaje("Nombre sugerido por IA: " + nombreSugerido);

                    // Pedir precio y stock al usuario, ya que el nombre y categoría están sugeridos
                    double precio = -1;
                    while (precio < 0) {
                        try {
                            System.out.print("Introduce el precio del producto: ");
                            precio = Double.parseDouble(vista.scanner.nextLine());
                            if (precio < 0) vista.mostrarMensaje("El precio no puede ser negativo.");
                        } catch (NumberFormatException e) {
                            vista.mostrarMensaje("Por favor ingresa un número válido para el precio.");
                        }
                    }

                    int stock = -1;
                    while (stock < 0) {
                        try {
                            System.out.print("Introduce el stock del producto: ");
                            stock = Integer.parseInt(vista.scanner.nextLine());
                            if (stock < 0) vista.mostrarMensaje("El stock no puede ser negativo.");
                        } catch (NumberFormatException e) {
                            vista.mostrarMensaje("Por favor ingresa un número válido para el stock.");
                        }
                    }

                    ProductoOtaku nuevoProd = new ProductoOtaku(0, nombreSugerido, tipoProducto, precio, stock);
                    dao.agregarProducto(nuevoProd);
                    vista.mostrarMensaje("Producto añadido exitosamente con el nombre sugerido.");
                    break;

                case 2: // Consultar producto por ID
                    int id = vista.pedirId();
                    ProductoOtaku pPorId = dao.obtenerProductoPorId(id);
                    if (pPorId != null) {
                        vista.mostrarMensaje("Producto encontrado: " + pPorId);
                    } else {
                        vista.mostrarMensaje("Producto no encontrado con ID: " + id);
                    }
                    break;

                case 3: // Listar todos los productos
                    List<ProductoOtaku> todos = dao.obtenerTodosLosProductos();
                    vista.mostrarProductos(todos);
                    break;

                case 4: // Listar productos por nombre
                    String nombreBusqueda = vista.pedirNombre();
                    List<ProductoOtaku> productosPorNombre = dao.buscarProductosPorNombre(nombreBusqueda);
                    vista.mostrarProductos(productosPorNombre);
                    break;

                case 5: // Listar productos por categoría
                    String categoriaBusqueda = vista.pedirCategoria();
                    List<ProductoOtaku> productosPorCategoria = dao.buscarProductoPorCategoria(categoriaBusqueda);
                    vista.mostrarProductos(productosPorCategoria);
                    break;

                case 6: // Actualizar producto
                    int idActualizar = vista.pedirId();
                    ProductoOtaku productoExistente = dao.obtenerProductoPorId(idActualizar);
                    if (productoExistente != null) {
                        vista.mostrarMensaje("Introduce los nuevos datos del producto:");
                        ProductoOtaku datosActualizados = vista.pedirDatosProducto();
                        datosActualizados.setId(idActualizar);
                        boolean actualizado = dao.actualizarProducto(datosActualizados);
                        vista.mostrarMensaje(actualizado ?
                                "Producto actualizado correctamente." :
                                "Error al actualizar el producto.");
                    } else {
                        vista.mostrarMensaje("Producto no encontrado con ID: " + idActualizar);
                    }
                    break;

                case 7: // Eliminar producto
                    int idEliminar = vista.pedirId();
                    boolean eliminado = dao.eliminarProducto(idEliminar);
                    vista.mostrarMensaje(eliminado ?
                            "Producto eliminado correctamente." :
                            "Error al eliminar el producto (ID no encontrado).");
                    break;

                case 8: // Salir
                    salir = true;
                    vista.mostrarMensaje("¡Gracias por usar Akihabara Market! Hasta pronto.");
                    break;

                default:
                    vista.mostrarMensaje("Opción no válida, intenta de nuevo.");
                    break;
            }
        }

        vista.cerrar();
        dao.cerrarConexion();
    }
}

