package com.techlab.proyecto_final.repository;

import com.techlab.proyecto_final.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
