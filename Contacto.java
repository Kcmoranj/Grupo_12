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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }   

    public List<Contacto> getContactosRelacionados() {
        return contactosRelacionados;
    }

    public void setContactosRelacionados(List<Contacto> contactosRelacionados) {
        this.contactosRelacionados = contactosRelacionados;
    }
    
    public void agregarFoto(String foto) {
        this.fotos.add(foto);
    }

    public void agregarContactoRelacionado(Contacto contacto) {
        this.contactosRelacionados.add(contacto);
    }   
    
    @Override
    public String toString() {//
        return "Contacto [\nNombre: " + nombre +
            "\nTeléfono: " + telefono +
            "\nCorreo: " + correo +
            "\nDirección: " + direccion +
            "\nFotos: " + fotos + "]";
    }
    
}

