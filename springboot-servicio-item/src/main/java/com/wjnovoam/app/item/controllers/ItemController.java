package com.wjnovoam.app.item.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wjnovoam.app.item.models.Item;
import com.wjnovoam.app.item.models.Producto;
import com.wjnovoam.app.item.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ItemController {

    @Autowired
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("nombre = " + nombre);
        System.out.println("token = " + token);

        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return itemService.findById(id, cantidad);
    }

    public Item metodoAlternativo(Long id, Integer cantidad) {
        Item item = new Item();
        Producto producto = new Producto();

        item.setCatidad(cantidad);
        producto.setId(id);
        producto.setNombre("ESte es del metodo alternativo");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return item;
    }

}
