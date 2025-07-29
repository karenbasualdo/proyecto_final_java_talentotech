package com.techlab.proyecto_final.service;

import com.techlab.proyecto_final.dto.PedidoRequestDTO;
import com.techlab.proyecto_final.entity.*;
import com.techlab.proyecto_final.exception.ProductNotFoundException;
import com.techlab.proyecto_final.exception.StockInsuficienteException;
import com.techlab.proyecto_final.repository.PedidoRepository;
import com.techlab.proyecto_final.repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public Pedido crearPedido(PedidoRequestDTO pedidoDTO) {
        List<LineaPedido> lineas = new ArrayList<>();
        double total = 0;

        // Cambié de Integer a Long para ID de producto
        for (Map.Entry<Long, Integer> entry : pedidoDTO.getProductos().entrySet()) {
            Long idProducto = entry.getKey();
            int cantidad = entry.getValue();

            Producto producto = productoRepository.findById(idProducto)
                    .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + idProducto));

            if (producto.getStock() < cantidad) {
                throw new StockInsuficienteException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - cantidad);
            productoRepository.save(producto);

            LineaPedido linea = new LineaPedido();
            linea.setProducto(producto);
            linea.setCantidad(cantidad);

            lineas.add(linea);

            double subtotal = producto.getPrecio() * cantidad;

            // aplicar descuento si existe
            if (producto.getDiscount() != null && producto.getDiscount() > 0) {
                subtotal -= subtotal * producto.getDiscount() / 100;
            }

            total += subtotal;
        }

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDate.now());
        pedido.setLineas(lineas);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }
}


