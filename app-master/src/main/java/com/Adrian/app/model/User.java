package com.Adrian.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String contrasena;
    private String rfc;
    private String rol; // Puede ser "ADMIN" o "USER"

    public User() {}

    // Genera los Getters y Setters para todos los campos (id, nombre, contrasena, rfc, rol)
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}