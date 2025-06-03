package com.akihabara.market.dao;

import com.akihabara.market.model.ProductoOtaku;
public class Main {
    public static void main(String[] args) {
        // Crear un objeto de tipo ProductoOtaku con valores de prueba
        ProductoOtaku producto = new ProductoOtaku("Figura de acción de Black Clover", "Figura", 45.00, 4);
        System.out.println(producto.toString());
        // Crear una instancia de DatabaseConnection para verificar la conexión
        DatabaseConnection dbConnection = new DatabaseConnection();
        
        // Comprobar si la conexión se ha establecido correctamente
        if (dbConnection.getConexion() != null) {
            System.out.println("Conexión a la base de datos está activa.");
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
        // Cerrar la conexión
        dbConnection.cerrarConexion();
    }
}