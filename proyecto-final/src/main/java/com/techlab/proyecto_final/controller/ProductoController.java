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

    // --- METODO NUEVO: Buscar producto y mostrar precio ---
    @GetMapping("/find/{productId}/price")
    public ResponseEntity<String> buscarProductoPrecioPorId(@PathVariable Long productId) {
        ProductResponseDTO product = productService.obtenerPorId(productId);
        return ResponseEntity.ok("El precio para el producto '" + product.getNombre() + "' es: $" + product.getPrecio());
    }
    // --- FIN DEL MTODO NUEVO ---

    // --- METODO INICIA: Buscar por nombre  y orden ---
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductResponseDTO>> buscarProductosPorNombreYOrden(
            @RequestParam String nombre,
            @RequestParam(defaultValue = "nombre") String campo,
            @RequestParam(defaultValue = "asc") String orden) {

        List<ProductResponseDTO> productos = productService.buscarPorNombreYOrden(nombre, campo, orden);
        return ResponseEntity.ok(productos);
    }
// --- METODO FINALIZA ---


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
