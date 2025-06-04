package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private DatabaseConnection dbConnection;
    private Connection conexion;

    public ProductoDAO() {
        dbConnection = new DatabaseConnection();
        this.conexion = dbConnection.getConexion(); 
    }
 // Método para agregar un producto evitando duplicados por nombre y categoría
 public void agregarProducto(ProductoOtaku producto) {
     String sqlCheck = "SELECT COUNT(*) FROM producto WHERE nombre = ? AND categoria = ?";
     try (PreparedStatement pstmtCheck = conexion.prepareStatement(sqlCheck)) {
         pstmtCheck.setString(1, producto.getNombre());
         pstmtCheck.setString(2, producto.getCategoria());
         ResultSet rs = pstmtCheck.executeQuery();
         if (rs.next() && rs.getInt(1) > 0) {
             System.out.println("El producto ya existe (nombre y categoría): " + producto.getNombre() + " - " + producto.getCategoria());
             return; 
         }
     } catch (SQLException e) {
         System.err.println("Error al verificar duplicados antes de agregar el producto: " + e.getMessage());
         return;
     }

     String sqlInsert = "INSERT INTO producto (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
     try (PreparedStatement pstmtInsert = conexion.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
         pstmtInsert.setString(1, producto.getNombre());
         pstmtInsert.setString(2, producto.getCategoria());
         pstmtInsert.setDouble(3, producto.getPrecio());
         pstmtInsert.setInt(4, producto.getStock());
         int affectedRows = pstmtInsert.executeUpdate();
         if (affectedRows == 0) {
             System.out.println("No se pudo agregar el producto: " + producto);
         } else {
             try (ResultSet generatedKeys = pstmtInsert.getGeneratedKeys()) {
                 if (generatedKeys.next()) {
                     producto.setId(generatedKeys.getInt(1)); 
                 }
             }
             System.out.println("Producto agregado: " + producto);
         }
     } catch (SQLException e) {
         System.err.println("Error al agregar el producto: " + e.getMessage());
     }
 }
    // Método para obtener un producto por ID
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?";
        ProductoOtaku producto = null;
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                producto = new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el producto: " + e.getMessage());
        }
        return producto;
    }

    // Método para obtener todos los productos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
             ProductoOtaku producto = new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los productos: " + e.getMessage());
        }
        return productos;
    }

    // Método para actualizar un producto
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getCategoria());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getId());
            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasEliminadas = pstmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar productos por nombre 
    public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por nombre: " + e.getMessage());
        }
        return productos;
    }

    // Método para buscar productos por categoría 
    public List<ProductoOtaku> buscarProductoPorCategoria(String categoria) {
        List<ProductoOtaku> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductoOtaku producto = new ProductoOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por categoría: " + e.getMessage());
        }
        return productos;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}



