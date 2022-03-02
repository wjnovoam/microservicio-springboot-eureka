package com.wjnovoam.app.productos.controllers;

import com.wjnovoam.app.productos.entity.Producto;
import com.wjnovoam.app.productos.services.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private Environment env;

    @Value("${server.port}") //Para inyectar valores que tenemosinyectados en las properties
    private Integer port;

    @Autowired
    private IProductosService iProductosService;

    @GetMapping("/listar")
    public List<Producto> listar(){
        return iProductosService.findAll().stream().map(producto -> {
            //producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id){
        Producto producto = iProductosService.findById(id);
        //producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        producto.setPort(port);
        return producto;
    }
}
