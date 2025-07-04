package com.mycompany.proyecto_edd;

import java.util.Comparator;

public class ComparadorApellidoNombre implements Comparator<Contacto> {
    @Override
    public int compare(Contacto c1, Contacto c2) {
        int cmp = c1.getApellido() == null ? "".compareToIgnoreCase(c2.getApellido() == null ? "" : c2.getApellido()) : c1.getApellido().compareToIgnoreCase(c2.getApellido() == null ? "" : c2.getApellido());
        if (cmp != 0) return cmp;
        return c1.getNombre() == null ? "".compareToIgnoreCase(c2.getNombre() == null ? "" : c2.getNombre()) : c1.getNombre().compareToIgnoreCase(c2.getNombre() == null ? "" : c2.getNombre());
    }
}
