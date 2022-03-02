package com.wjnovoam.app.item.models;


public class Item {

    private Producto producto;
    private Integer catidad;

    public Item() {
    }

    public Item(Producto producto, Integer catidad) {
        this.producto = producto;
        this.catidad = catidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCatidad() {
        return catidad;
    }

    public void setCatidad(Integer catidad) {
        this.catidad = catidad;
    }

    public Double getTotal(){
        return producto.getPrecio() * catidad.doubleValue();
    }
}
