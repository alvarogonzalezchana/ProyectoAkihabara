package com.akihabara.market.model;

public class Main {
    public static void main(String[] args) {
        ProductoOtaku producto = new ProductoOtaku("Figura de accion", "Figura", 25.99, 10);
        
        System.out.println(producto.toString());
    }
}

