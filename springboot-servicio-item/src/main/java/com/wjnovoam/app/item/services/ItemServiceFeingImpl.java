package com.wjnovoam.app.item.services;

import com.wjnovoam.app.item.clients.ProductoClienteRest;
import com.wjnovoam.app.item.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("servicefeing")
@Primary
public class ItemServiceFeingImpl implements ItemService{

    @Autowired
    private ProductoClienteRest productoClienteRest;

    @Override
    public List<Item> findAll() {
        return productoClienteRest.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(productoClienteRest.detalle(id), cantidad);
    }
}
