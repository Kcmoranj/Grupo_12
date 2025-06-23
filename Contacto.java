/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;

import java.util.List;

/**
 *
 * @author kiara
 */
public class Contacto {
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private List<String> fotos; // Lista de fotos asociadas
    private List<Contacto> contactosRelacionados; // Lista de contactos asociados
// Constructor
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fotos = new ArrayList<>();
        this.contactosRelacionados = new ArrayList<>();
    }

    // Métodos getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void agregarFoto(String foto) {
        this.fotos.add(foto);
    }

    public List<Contacto> getContactosRelacionados() {
        return contactosRelacionados;
    }

    public void agregarContactoRelacionado(Contacto contacto) {
        this.contactosRelacionados.add(contacto);
    }
}

