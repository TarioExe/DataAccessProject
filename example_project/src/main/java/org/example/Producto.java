package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;

    public Producto() {}

    public Producto(String nombre, double price) {
        this.nombre = nombre;
        this.precio = price;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ID " + id + " → Nombre: " + nombre + ", Precio: " + precio + "€";
    }
}
