/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;

import java.util.List;

/**
 *
 *@author Derian y Kiara
 */
public class Contacto {
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private String tipoContacto;
    private String fechaCumpleanos;
    private ListaCircularDoble<String> fotos; 
    private ListaCircularDoble<Contacto> contactosRelacionados; 

    // Constructor
    public Contacto() {
        this.nombre = null;
        this.apellido = null;
        this.direccion = null;
        this.telefono = null;
        this.correo = null;
        this.tipoContacto = null;
        this.fechaCumpleanos = null;
        this.fotos = new ListaCircularDoble<>();
        this.contactosRelacionados = new ListaCircularDoble<>();
    }
    
    public void agregarFoto(String foto) {
        fotos.agregarAlFinal(foto);
    }

    public void agregarContactoRelacionado(Contacto contacto) {//
        contactosRelacionados.agregarAlFinal(contacto);
    }
    
    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Tipo: ").append(tipoContacto != null ? tipoContacto : "N/A").append("\n");
    sb.append("Nombre: ").append(nombre != null ? nombre : "N/A");
    if (apellido != null && !"empresa".equalsIgnoreCase(tipoContacto)) {
        sb.append(" ").append(apellido);
    }
    sb.append("\n");

    sb.append("Teléfono: ").append(telefono != null ? telefono : "N/A").append("\n");
    sb.append("Correo: ").append(correo != null ? correo : "N/A").append("\n");
    sb.append("Dirección: ").append(direccion != null ? direccion : "N/A").append("\n");

    // Mostrar cumpleaños solo si es persona
    if ("persona".equalsIgnoreCase(tipoContacto)) {
        sb.append("Fecha de cumpleaños: ").append(fechaCumpleanos != null ? fechaCumpleanos : "N/A").append("\n");
    }

    // Mostrar fotos
    sb.append("Fotos: ");
    if (fotos != null && fotos.tamaño() > 0) {
        for (String foto : fotos) {
            sb.append(foto).append(" ");
        }
    } else {
        sb.append("Ninguna");
    }
    sb.append("\n");

    // Mostrar contactos relacionados solo si es persona
    if ("persona".equalsIgnoreCase(tipoContacto)) {
        sb.append("Contactos relacionados: ");
        if (contactosRelacionados != null && contactosRelacionados.tamaño() > 0) {
            for (Contacto c : contactosRelacionados) {
                sb.append(c.getNombre());
                if (c.getApellido() != null) sb.append(" ").append(c.getApellido());
                sb.append(" | ");
            }
        } else {
            sb.append("Ninguno");
        }
        sb.append("\n");
    }

    return sb.toString();
}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getFechaCumpleanos() {
        return fechaCumpleanos;
    }

    public void setFechaCumpleanos(String fechaCumpleanos) {
        this.fechaCumpleanos = fechaCumpleanos;
    }

    public ListaCircularDoble<String> getFotos() {
        return fotos;
    }

    public void setFotos(ListaCircularDoble<String> fotos) {
        this.fotos = fotos;
    }

    public ListaCircularDoble<Contacto> getContactosRelacionados() {
        return contactosRelacionados;
    }

    public void setContactosRelacionados(ListaCircularDoble<Contacto> contactosRelacionados) {
        this.contactosRelacionados = contactosRelacionados;
    } 
    
}
