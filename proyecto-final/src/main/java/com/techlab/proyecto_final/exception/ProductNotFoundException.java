package com.techlab.proyecto_final.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String mensaje) {
        super(mensaje);
    }
}
