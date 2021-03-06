package com.wjnovoam.app.item.controllers;

import com.wjnovoam.app.item.models.Item;
import com.wjnovoam.app.commons.models.entity.Producto;
import com.wjnovoam.app.item.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private Environment env;

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("serviceRestTemplate")
    private ItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;


    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("nombre = " + nombre);
        System.out.println("token = " + token);

        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return cbFactory.create("items").run(()-> itemService.findById(id, cantidad), e ->metodoAlternativo(id, cantidad, e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
        return  itemService.findById(id, cantidad);
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
    @TimeLimiter(name = "items")
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, cantidad));
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

    public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {

        logger.info(e.getMessage());
        Item item = new Item();
        Producto producto = new Producto();

        item.setCatidad(cantidad);
        producto.setId(id);
        producto.setNombre("ESte es del metodo alternativo");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto){
        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){
        return  itemService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto actualizar(@RequestBody Producto producto, @PathVariable Long id){
        return itemService.update(producto, id);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        itemService.delete(id);
    }
}
