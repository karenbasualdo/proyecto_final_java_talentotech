package com.techlab.proyecto_final.service;

import com.techlab.proyecto_final.dto.ProductRequestDTO;
import com.techlab.proyecto_final.dto.ProductResponseDTO;
import com.techlab.proyecto_final.entity.Producto;
import com.techlab.proyecto_final.exception.ProductNotFoundException;
import com.techlab.proyecto_final.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductoRepository productoRepository;

    public ProductService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductResponseDTO crearProducto(ProductRequestDTO dto) {
        Producto producto = toEntity(dto);
        Producto guardado = productoRepository.save(producto);
        return toResponseDTO(guardado);
    }

    public List<ProductResponseDTO> crearVarios(List<ProductRequestDTO> dtos) {
        List<Producto> productos = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        List<Producto> guardados = productoRepository.saveAll(productos);
        return guardados.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
        return toResponseDTO(producto);
    }

    public ProductResponseDTO actualizarProducto(Long id, ProductRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));

        producto.setNombre(dto.getNombre());
        producto.setImagen(dto.getImagen());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setDiscount(dto.getDiscount());

        Producto actualizado = productoRepository.save(producto);
        return toResponseDTO(actualizado);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    // Conversión entidad -> DTO
    private ProductResponseDTO toResponseDTO(Producto p) {
        return new ProductResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getImagen(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria(),
                p.getDescripcion(),
                p.getDiscount()
        );
    }

    // Conversión DTO -> entidad
    private Producto toEntity(ProductRequestDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setImagen(dto.getImagen());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());
        p.setDescripcion(dto.getDescripcion());
        p.setDiscount(dto.getDiscount());
        return p;
    }
}
