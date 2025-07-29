package com.techlab.proyecto_final.dto;

import java.util.Map;

public class PedidoRequestDTO {
    private Map<Long, Integer> productos;  // Cambié Integer a Long para IDs

    public PedidoRequestDTO() {}

    public PedidoRequestDTO(Map<Long, Integer> productos) {
        this.productos = productos;
    }

    public Map<Long, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<Long, Integer> productos) {
        this.productos = productos;
    }
}
