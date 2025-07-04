
package com.mycompany.proyecto_edd;


public class Relacion {
    private Contacto contactoRelacionado;
    private String tipoRelacion;

    public Relacion(Contacto contactoRelacionado, String tipoRelacion) {
        this.contactoRelacionado = contactoRelacionado;
        this.tipoRelacion = tipoRelacion;
    }

    public Contacto getContactoRelacionado() {
        return contactoRelacionado;
    }

    public void setContactoRelacionado(Contacto contactoRelacionado) {
        this.contactoRelacionado = contactoRelacionado;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    @Override
    public String toString() {
        return contactoRelacionado.getNombre() + " (" + tipoRelacion + ")";
    }
}

