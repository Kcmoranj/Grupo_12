package com.mycompany.proyecto_edd;

import java.util.Comparator;

public class ComparadorCantidadAtributos implements Comparator<Contacto> {
    private int contarAtributos(Contacto c) {
        int count = 0;
        if (c.getNombre() != null && !c.getNombre().isEmpty()) count++;
        if (c.getApellido() != null && !c.getApellido().isEmpty()) count++;
        if (c.getTelefono() != null && !c.getTelefono().isEmpty()) count++;
        if (c.getCorreo() != null && !c.getCorreo().isEmpty()) count++;
        if (c.getDireccion() != null && !c.getDireccion().isEmpty()) count++;
        if (c.getFechaCumpleanos() != null && !c.getFechaCumpleanos().isEmpty()) count++;
        if (c.getFotos() != null && c.getFotos().tamaño() > 0) count++;
        if (c.getContactosRelacionados() != null && c.getContactosRelacionados().tamaño() > 0) count++;
        return count;
    }

    @Override
    public int compare(Contacto c1, Contacto c2) {
        return Integer.compare(contarAtributos(c1), contarAtributos(c2));
    }
}
