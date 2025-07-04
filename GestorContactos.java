package com.mycompany.proyecto_edd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestorContactos {
     private ListaCircularDoble<Contacto> contactos;

    public GestorContactos() {
        contactos = new ListaCircularDoble<>();
    }

    public ListaCircularDoble<Contacto> getContactos() {
        return contactos;
    }

    public void agregarContacto(Contacto contacto) {
        contactos.agregarAlFinal(contacto);
    }

    public void mostrarContactos() {
        if (contactos.estaVacia()) {
            System.out.println("No hay contactos");
            return;
        }
        int i = 1;
        for (Contacto c : contactos) {
            System.out.println("=== Contacto " + i + "=== ");
            System.out.println(c);
            i++;
        }
    }

    // GUARDAR contactos en archivo
    public void guardarContactoEnArchivo(Contacto contacto) {
    java.io.File archivo = new java.io.File("contactos.txt");

    // Chequear si ya existe contacto (nombre + teléfono)
    boolean yaExiste = false;
    if (archivo.exists()) {
        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                if (linea.contains(contacto.getNombre()) && linea.contains(contacto.getTelefono())) {
                    yaExiste = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
            return;
        }
    }

    if (yaExiste) {
        System.out.println("Este contacto ya está guardado en el archivo.");
        return;
    }

    // Construir línea de texto para el contacto
    StringBuilder linea = new StringBuilder();
    linea.append(contacto.getNombre()).append(";");
    linea.append(contacto.getApellido() != null ? contacto.getApellido() : "").append(";");
    linea.append(contacto.getTelefono() != null ? contacto.getTelefono() : "").append(";");
    linea.append(contacto.getCorreo() != null ? contacto.getCorreo() : "").append(";");
    linea.append(contacto.getDireccion() != null ? contacto.getDireccion() : "").append(";");
    linea.append(contacto.getTipoContacto() != null ? contacto.getTipoContacto() : "").append(";");
    linea.append(contacto.getFechaCumpleanos() != null ? contacto.getFechaCumpleanos() : "");

    // Fotos separadas por "|"
    StringBuilder fotosLinea = new StringBuilder();
    for (String foto : contacto.getFotos()) {
        fotosLinea.append("|").append(foto);
    }
    linea.append(fotosLinea.toString());

    // Guardar al final del archivo
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) { // 'true' para append
        writer.write(linea.toString());
        writer.newLine();
        System.out.println("Contacto guardado incrementalmente en archivo.");
    } catch (IOException e) {
        System.out.println("Error al guardar el contacto.");
        e.printStackTrace();
    }
}

    // CARGAR contactos desde archivo
    public void cargarContactosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("contactos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", -1);
                if (partes.length >= 7) {
                    Contacto contacto = new Contacto();
                    contacto.setNombre(partes[0]);
                    contacto.setApellido(partes[1]);
                    contacto.setTelefono(partes[2]);
                    contacto.setCorreo(partes[3]);
                    contacto.setDireccion(partes[4]);
                    contacto.setTipoContacto(partes[5]);
                    contacto.setFechaCumpleanos(partes[6]);

                    // Leer fotos si existen
                    if (partes.length > 7) {
                        String[] fotos = partes[7].split("\\|");
                        for (String f : fotos) {
                            if (!f.isEmpty()) {
                                contacto.agregarFoto(f);
                            }
                        }
                    }

                    contactos.agregarAlFinal(contacto);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo. Se creará uno nuevo al guardar.");
        }
    }
    public void guardarContactosEnArchivo() {
    java.io.File archivo = new java.io.File("contactos.txt");
    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) { // Sin append, sobreescribe
        for (Contacto contacto : contactos) {
            StringBuilder linea = new StringBuilder();
            linea.append(contacto.getNombre() != null ? contacto.getNombre() : "").append(";");
            linea.append(contacto.getApellido() != null ? contacto.getApellido() : "").append(";");
            linea.append(contacto.getTelefono() != null ? contacto.getTelefono() : "").append(";");
            linea.append(contacto.getCorreo() != null ? contacto.getCorreo() : "").append(";");
            linea.append(contacto.getDireccion() != null ? contacto.getDireccion() : "").append(";");
            linea.append(contacto.getTipoContacto() != null ? contacto.getTipoContacto() : "").append(";");
            linea.append(contacto.getFechaCumpleanos() != null ? contacto.getFechaCumpleanos() : "").append(";");
            
            StringBuilder fotosLinea = new StringBuilder();
            for (String foto : contacto.getFotos()) {
                fotosLinea.append("|").append(foto);
            }
            linea.append(fotosLinea.toString());

            writer.write(linea.toString());
            writer.newLine();
        }
        System.out.println("Todos los contactos guardados en archivo.");
    } catch (IOException e) {
        System.out.println("Error al guardar contactos.");
        e.printStackTrace();
    }
}
    public void ordenarContactos(Comparator<Contacto> comparador) {
        contactos.ordenar(comparador);
    }
}
