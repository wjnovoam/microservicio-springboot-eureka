package com.wjnovoam.app.item.controllers;

import com.wjnovoam.app.item.models.Item;
import com.wjnovoam.app.item.models.Producto;
import com.wjnovoam.app.item.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;


    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("nombre = " + nombre);
        System.out.println("token = " + token);

        return itemService.findAll();
    }

    //@HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return cbFactory.create("items").run(()-> itemService.findById(id, cantidad), e ->metodoAlternativo(id, cantidad, e));
//    return itemService.findById(id, cantidad);
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {

        logger.info(e.getMessage());
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
