package com.techlab.proyecto_final.controller;

import com.techlab.proyecto_final.dto.ProductRequestDTO;
import com.techlab.proyecto_final.dto.ProductResponseDTO;
import com.techlab.proyecto_final.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductService productService;

    public ProductoController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> crearProducto(@RequestBody ProductRequestDTO producto) {
        ProductResponseDTO creado = productService.crearProducto(producto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PostMapping("/lote")
    public ResponseEntity<List<ProductResponseDTO>> crearProductos(@RequestBody List<ProductRequestDTO> productos) {
        List<ProductResponseDTO> creados = productService.crearVarios(productos);
        return new ResponseEntity<>(creados, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> actualizar(@PathVariable Long id, @RequestBody ProductRequestDTO producto) {
        return ResponseEntity.ok(productService.actualizarProducto(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
