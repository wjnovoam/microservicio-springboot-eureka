package com.wjnovoam.app.productos.controllers;

import com.wjnovoam.app.commons.models.entity.Producto;
import com.wjnovoam.app.productos.services.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping
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
            producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            //producto.setPort(port);
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) throws InterruptedException {

        if(id.equals(10L)){
            throw new IllegalStateException("Producto no encontrado");
        }
        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(6L);
        }
        Producto producto = iProductosService.findById(id);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
//        producto.setPort(port);
        return producto;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){
        return iProductosService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto actualizar(@RequestBody Producto producto, @PathVariable Long id){

        Producto productoDb = iProductosService.findById(id);

        productoDb.setNombre(producto.getNombre());
        productoDb.setPrecio(producto.getPrecio());

        return iProductosService.save(productoDb);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        iProductosService.deleteById(id);
    }
}
