package com.wjnovoam.app.productos.services;

import com.wjnovoam.app.commons.models.entity.Producto;

import java.util.List;

public interface IProductosService {
    List<Producto> findAll();

    Producto findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);
}
