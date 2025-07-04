package com.mycompany.proyecto_edd;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.HashMap;

public class Contacto {
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private String tipoContacto;
    private String fechaCumpleanos;
    private ListaCircularDoble<String> fotos; 
    private Map<Contacto, String> contactosRelacionados;

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
        this.contactosRelacionados = new HashMap<>();
    }

public void agregarFoto(String foto) {
    // Carpeta donde se guardarán las fotos en el proyecto
    String folderPath = "imagenes";  // Carpeta dentro del proyecto donde se guardarán las fotos
    File folder = new File(folderPath);

    // Crear la carpeta si no existe
    if (!folder.exists()) {
        folder.mkdir();
    }

    // Verificar si el archivo existe en la ruta proporcionada
    File file = new File(foto);  // Foto es la ruta proporcionada por el usuario

    if (!file.exists()) {
        System.out.println("La foto no existe en la ruta proporcionada.");
        return;
    }

    // Definir el destino de la foto dentro de la carpeta del proyecto
    String destinationPath = folderPath + "/" + file.getName();

    // Copiar la foto al proyecto
    try {
        // Copiar el archivo de la ruta proporcionada a la carpeta dentro del proyecto
        Files.copy(file.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

        // Agregar la ruta de la foto en la lista del contacto
        fotos.agregarAlFinal(destinationPath);  // Esto agrega la ruta de la foto al contacto
        System.out.println("Foto agregada correctamente: " + file.getName());
    } catch (IOException e) {
        System.out.println("Error al guardar la foto: " + e.getMessage());
    }
}


 

     public void agregarContactoRelacionado(Contacto contactoRelacionado, String tipoRelacion) {
        this.contactosRelacionados.put(contactoRelacionado, tipoRelacion);
        contactoRelacionado.agregarContactoRelacionadoBidireccional(this, tipoRelacion);
    }

    // Método para agregar relación bidireccional
    public void agregarContactoRelacionadoBidireccional(Contacto contacto, String tipoRelacion) {
        this.contactosRelacionados.put(contacto, tipoRelacion);
    }

    // Obtener los contactos relacionados
    public Map<Contacto, String> getContactosRelacionados() {
        return contactosRelacionados;
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
        sb.append("Contactos relacionados: ");
        if (!contactosRelacionados.isEmpty()) {
            for (Map.Entry<Contacto, String> entry : contactosRelacionados.entrySet()) {
                sb.append(entry.getKey().getNombre()).append(" (").append(entry.getValue()).append(") | ");
            }
        } else {
            sb.append("Ninguno");
        }
        sb.append("\n");
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
}
