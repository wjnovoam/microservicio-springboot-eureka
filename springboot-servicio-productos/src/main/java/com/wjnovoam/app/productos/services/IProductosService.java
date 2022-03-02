package com.wjnovoam.app.productos.services;

import com.wjnovoam.app.productos.entity.Producto;

import java.util.List;

public interface IProductosService {
    List<Producto> findAll();
    Producto findById(Long id);
}
