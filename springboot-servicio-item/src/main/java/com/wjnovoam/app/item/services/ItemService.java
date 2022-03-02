package com.wjnovoam.app.item.services;

import com.wjnovoam.app.item.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item findById(Long id, Integer cantidad);
}
