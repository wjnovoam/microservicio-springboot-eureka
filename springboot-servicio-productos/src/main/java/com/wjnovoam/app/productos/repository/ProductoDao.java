package com.wjnovoam.app.productos.repository;

import com.wjnovoam.app.commons.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoDao extends JpaRepository<Producto, Long> {
}
