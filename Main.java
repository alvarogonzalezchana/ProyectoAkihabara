package com.akihabara.market.model;

import com.akihabara.market.model.ProductoOtaku;

public class Main {
    public static void main(String[] args) {
        ProductoOtaku producto = new ProductoOtaku("Figura de acción de Goku", "Figura", 45.00, 4);
        
        System.out.println(producto.toString());
    }
}

