package com.techlab.proyecto_final.controller;

import com.techlab.proyecto_final.dto.PedidoRequestDTO;
import com.techlab.proyecto_final.entity.Pedido;
import com.techlab.proyecto_final.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public Pedido crear(@RequestBody PedidoRequestDTO pedidoDTO) {
        return pedidoService.crearPedido(pedidoDTO);
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.obtenerPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtener(@PathVariable Long id) {  // Cambié int a Long
        return pedidoService.obtenerPedidoPorId(id);
    }
}

