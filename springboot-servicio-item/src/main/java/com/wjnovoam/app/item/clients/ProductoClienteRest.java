package com.wjnovoam.app.item.clients;

import org.springframework.cloud.openfeign.FeignClient;
import com.wjnovoam.app.commons.models.entity.Producto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "servicio-productos") //con esta anotacion se declara que esta interfaz es un cliente feing
public interface ProductoClienteRest {

    @GetMapping("/listar")
    List<Producto> listar();

    @GetMapping("/ver/{id}")
    Producto detalle(@PathVariable Long id);

    @PostMapping("/crear")
    Producto crear(@RequestBody Producto producto);

    @PutMapping("/editar/{id}")
    Producto update(@RequestBody Producto producto, @PathVariable Long id);

    @DeleteMapping("/eliminar/{id}")
    void eliminar(@PathVariable Long id);
}