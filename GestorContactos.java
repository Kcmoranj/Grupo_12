package Grupo_12;

import java.io.*;
import java.util.*;

public class GestorContactos {
    private ListaCircularDoble<Contacto> contactos = new ListaCircularDoble<>();

    public void agregarContacto(Contacto contacto) {
        contactos.agregarAlFinal(contacto);
    }

    public ListaCircularDoble<Contacto> getContactos() {
        return contactos;
    }

    public void guardarContactoEnArchivo(Contacto contacto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contactos.txt", true))) {
            writer.write(formatoContacto(contacto));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el contacto: " + e.getMessage());
        }
    }

    public void guardarContactosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contactos.txt"))) {
            for (Contacto c : contactos) {
                writer.write(formatoContacto(c));
                writer.newLine();
            }
            System.out.println("Todos los contactos guardados en archivo.");
        } catch (IOException e) {
            System.out.println("Error al guardar contactos: " + e.getMessage());
        }
    }

    private String formatoContacto(Contacto c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c.getTipoContacto()).append(";")
          .append(c.getNombre()).append(";")
          .append(c.getApellido()).append(";")
          .append(c.getTelefono()).append(";")
          .append(c.getCorreo()).append(";")
          .append(c.getDireccion()).append(";")
          .append(c.getFechaCumpleanos()).append(";");

        for (String foto : c.getFotos()) {
            sb.append("FOTO:").append(foto).append(";");
        }

        for (Map.Entry<Contacto, String> entry : c.getContactosRelacionados().entrySet()) {
            sb.append("RELACION:").append(entry.getKey().getNombre())
              .append(":").append(entry.getValue()).append(";");
        }

        return sb.toString();
    }

    public void cargarContactosDesdeArchivo() {
        contactos = new ListaCircularDoble<>();
        Map<String, Contacto> mapaNombreContacto = new HashMap<>();
        List<String> lineas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("contactos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
                String[] partes = linea.split(";");
                Contacto contacto = new Contacto();
                contacto.setTipoContacto(partes[0]);
                contacto.setNombre(partes[1]);
                contacto.setApellido(partes[2]);
                contacto.setTelefono(partes[3]);
                contacto.setCorreo(partes[4]);
                contacto.setDireccion(partes[5]);
                contacto.setFechaCumpleanos(partes[6]);

                for (int i = 7; i < partes.length; i++) {
                    if (partes[i].startsWith("FOTO:")) {
                        contacto.agregarFoto(partes[i].substring(5));
                    }
                }

                contactos.agregarAlFinal(contacto);
                mapaNombreContacto.put(contacto.getNombre(), contacto);
            }

            // Segunda pasada: relaciones
            for (int idx = 0; idx < lineas.size(); idx++) {
                String[] partes = lineas.get(idx).split(";");
                Contacto actual = contactos.obtener(idx);

                for (int i = 7; i < partes.length; i++) {
                    if (partes[i].startsWith("RELACION:")) {
                        String[] relParts = partes[i].substring(9).split(":");
                        if (relParts.length == 2) {
                            String nombreRelacionado = relParts[0];
                            String tipoRelacion = relParts[1];
                            Contacto relacionado = mapaNombreContacto.get(nombreRelacionado);
                            if (relacionado != null && !relacionado.equals(actual)) {
                                actual.agregarContactoRelacionado(relacionado, tipoRelacion);
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar contactos: " + e.getMessage());
        }
    }

    public void mostrarContactos() {
        if (contactos.estaVacia()) {
            System.out.println("No hay contactos para mostrar.");
            return;
        }
        for (Contacto c : contactos) {
            System.out.println(c);
        }
    }

    public void ordenarContactos(Comparator<Contacto> comparador) {
        contactos.ordenar(comparador);
    }

    public Contacto buscarContactoPorNombre(String nombre) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }
}
