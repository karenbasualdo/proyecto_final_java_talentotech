package com.techlab.proyecto_final.service;

import com.techlab.proyecto_final.dto.ProductRequestDTO;
import com.techlab.proyecto_final.dto.ProductResponseDTO;
import com.techlab.proyecto_final.entity.Producto;
import com.techlab.proyecto_final.exception.ProductNotFoundException;
import com.techlab.proyecto_final.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

@Service
public class ProductService {

    private final ProductoRepository productoRepository;

    public ProductService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;

        // Carga automática de productos si la tabla está vacía
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto(null, "Pan sin TACC lactal", "", 2200.0, 10, "Panificados", "Pan lactal sin gluten", 5.0));
            productoRepository.save(new Producto(null, "Galletitas dulces sin TACC", "", 1600.0, 5, "Galletitas", "Galletitas de vainilla libres de gluten", 25.0));
            productoRepository.save(new Producto(null, "Fideos de arroz sin TACC", "", 1900.0, 8, "Pastas", "Fideos libres de gluten hechos a base de arroz", 0.0));
            productoRepository.save(new Producto(null, "Premezcla sin TACC", "", 2500.0, 15, "Harinas", "Mezcla apta para panificados sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Tapa de empanadas sin TACC", "", 1800.0, 20, "Panificados", "Tapas para empanadas sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Bizcochuelo de vainilla sin TACC", "", 2100.0, 10, "Repostería", "Bizcochuelo instantáneo sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Alfajores sin TACC", "", 2300.0, 12, "Dulces", "Alfajores de maicena sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Galletitas saladas sin TACC", "", 1700.0, 9, "Galletitas", "Galletitas tipo crackers sin gluten", 10.0));
            productoRepository.save(new Producto(null, "Cereales sin TACC", "", 2600.0, 7, "Desayuno", "Cereal de maíz sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Pan rallado sin TACC", "", 1500.0, 14, "Panificados", "Rebozador sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Snacks de arroz sin TACC", "", 1400.0, 18, "Snacks", "Snacks saludables libres de gluten", 0.0));
            productoRepository.save(new Producto(null, "Pizza prelista sin TACC", "", 2800.0, 6, "Comidas listas", "Base de pizza lista para hornear sin gluten", 15.0));
            productoRepository.save(new Producto(null, "Tarta prelista sin TACC", "", 2900.0, 6, "Comidas listas", "Tarta de verduras lista para calentar sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Galletitas de avena sin TACC", "", 1600.0, 5, "Galletitas", "Galletitas dulces con avena certificada sin gluten", 0.0));
            productoRepository.save(new Producto(null, "Muffins sin TACC", "", 2700.0, 7, "Repostería", "Muffins esponjosos sin gluten", 35.0));
            productoRepository.save(new Producto(null, "Pan dulce sin TACC", "", 3400.0, 5, "Repostería", "Pan dulce apto para celíacos", 0.0));
            productoRepository.save(new Producto(null, "Harina de arroz sin TACC", "", 2000.0, 15, "Harinas", "Harina natural de arroz libre de gluten", 0.0));
            productoRepository.save(new Producto(null, "Tostadas de arroz sin TACC", "", 1500.0, 20, "Snacks", "Tostadas crujientes de arroz", 20.0));
            productoRepository.save(new Producto(null, "Budín sin TACC", "", 2900.0, 10, "Repostería", "Budín de limón libre de gluten", 0.0));
            productoRepository.save(new Producto(null, "Empanadas congeladas sin TACC", "", 3000.0, 8, "Comidas listas", "Empanadas congeladas listas para hornear", 0.0));
            productoRepository.save(new Producto(null, "Helado sin TACC", "", 3200.0, 9, "Postres", "Helado artesanal apto para celíacos", 40.0));
            productoRepository.save(new Producto(null, "Tortillas congeladas sin TACC", "", 2500.0, 10, "Comidas listas", "Tortillas sin gluten listas para calentar", 0.0));
        }
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

    // --- METODO INICIA: buscar por nombre con orden
    public List<ProductResponseDTO> buscarPorNombreYOrden(String nombre, String campo, String orden) {
        Comparator<Producto> comparator;

        if ("precio".equalsIgnoreCase(campo)) {
            comparator = Comparator.comparing(Producto::getPrecio);
        } else {
            comparator = Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER);
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        return productoRepository.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .sorted(comparator)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
// --- METODO FINALIZA ---

}
