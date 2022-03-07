package com.wjnovoam.app.item.services;

import com.wjnovoam.app.item.models.Item;
import com.wjnovoam.app.commons.models.entity.Producto;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item findById(Long id, Integer cantidad);

    Producto save(Producto producto);

    Producto update(Producto producto, Long id);

    void delete(Long id);
}
