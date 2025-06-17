package com.empresa.erp.utils;

import empresa.models.Productos;

public class ProductoConStock {
    private final Productos producto;
    private final int stock;

    public ProductoConStock(Productos producto, int stock) {
        this.producto = producto;
        this.stock = stock;
    }

    public Productos getProducto() {
        return producto;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return producto.getId_Prod() + " - " + producto.getNombreProd() + " (Stock: " + stock + ")";
    }
}
