package com.mycompany.Grupo_12;

import java.util.Scanner;

public class Grupo_12 {
    public static void main(String[] args) {

        GestorContactos gestor = new GestorContactos();
        gestor.cargarContactosDesdeArchivo();

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear nuevo contacto");
            System.out.println("2. Ver lista de contactos");
            System.out.println("3. Guardar cambios realizados en los contactos");
            System.out.println("4. Navegar entre contactos");
            System.out.println("5. Navegar en fotos de un contacto");
            System.out.println("6. Editar contacto");
            System.out.println("7. Eliminar contacto");
            System.out.println("8. Filtrar contactos");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            String entrada = scanner.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 8.");
                continue;
            }

            switch (opcion) {
                case 1:
                    crearContacto(gestor, scanner);
                    break;
                case 2:
                    mostrarContactosConOpcionesOrdenamiento(gestor, scanner);
                    break;
                case 3:
                    gestor.guardarContactosEnArchivo();
                    System.out.println("Contactos guardados.");
                    break;
                case 4:
                    navegarContactos(gestor, scanner);
                    break;
                case 5:
                    navegarFotosDeContacto(gestor, scanner);
                    break;
                case 6:
                    editarContacto(gestor, scanner);
                    break;
                case 7:
                    eliminarContacto(gestor, scanner);
                    break;
                case 8:
                    filtrarContactos(gestor, scanner);
                    break;
                case 9:
                    System.out.println("Saliendo y guardando...");
                    gestor.guardarContactosEnArchivo();
                    break;
                    
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 9);
    }

    private static void mostrarContactosConOpcionesOrdenamiento(GestorContactos gestor, Scanner scanner) {
        int opcionOrden = -1;
        do {
            System.out.println("¿Desea ver los contactos ordenados?");
            System.out.println("1. Sí");
            System.out.println("2. No (mostrar sin ordenar)");
            System.out.print("Opción: ");
            String entrada = scanner.nextLine();
            try {
                opcionOrden = Integer.parseInt(entrada);
                if (opcionOrden != 1 && opcionOrden != 2) {
                    System.out.println("Opción inválida. Intente de nuevo.");
                    opcionOrden = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese 1 o 2.");
            }
        } while (opcionOrden == -1);

        if (opcionOrden == 1) {
            int criterio = -1;
            do {
                System.out.println("Seleccione el criterio de ordenamiento:");
                System.out.println("1. Apellido y primer nombre");
                System.out.println("2. Cantidad de atributos");
                System.out.println("3. Fecha de cumpleaños más cercana");
                System.out.print("Opción: ");
                String entradaCriterio = scanner.nextLine();
                try {
                    criterio = Integer.parseInt(entradaCriterio);
                    if (criterio < 1 || criterio > 3) {
                        System.out.println("Criterio no válido. Intente nuevamente.");
                        criterio = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Ingrese un número entre 1 y 3.");
                }
            } while (criterio == -1);

            switch (criterio) {
                case 1:
                    gestor.ordenarContactos(new ComparadorApellidoNombre());
                    System.out.println("Lista ordenada por apellido y nombre:");
                    break;
                case 2:
                    gestor.ordenarContactos(new ComparadorCantidadAtributos());
                    System.out.println("Lista ordenada por cantidad de atributos:");
                    break;
                case 3:
                    gestor.ordenarContactos(new ComparadorCumpleanosProximo());
                    System.out.println("Lista ordenada por cumpleaños más cercano:");
                    break;
            }
        }

        gestor.mostrarContactos();
    }

    private static void crearContacto(GestorContactos gestor, Scanner scanner) {
        System.out.println("Seleccione el tipo de contacto:");
        System.out.println("1. Persona");
        System.out.println("2. Empresa");
        int tipo = -1;
        do {
            System.out.print("Opción: ");
            String entrada = scanner.nextLine();
            try {
                tipo = Integer.parseInt(entrada);
                if (tipo != 1 && tipo != 2) {
                    System.out.println("Tipo no válido. Intente nuevamente.");
                    tipo = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese 1 o 2.");
            }
        } while (tipo == -1);

        Contacto contacto = new Contacto();

        if (tipo == 1) {
            contacto.setTipoContacto("persona");

            System.out.print("Nombre: ");
            contacto.setNombre(scanner.nextLine());
            
            System.out.print("Apellido: ");
            contacto.setApellido(scanner.nextLine());

            System.out.print("Teléfono: ");
            contacto.setTelefono(scanner.nextLine());

            System.out.print("Correo: ");
            contacto.setCorreo(scanner.nextLine());

            System.out.print("Dirección: ");
            contacto.setDireccion(scanner.nextLine());
            
           
            
            System.out.print("Fecha de cumpleaños (MM-DD): ");
            contacto.setFechaCumpleanos(scanner.nextLine());

        } else {
            contacto.setTipoContacto("empresa");

            System.out.print("Nombre de la empresa: ");
            contacto.setNombre(scanner.nextLine());

            contacto.setApellido(null);

            System.out.print("Teléfono: ");
            contacto.setTelefono(scanner.nextLine());

            System.out.print("Correo: ");
            contacto.setCorreo(scanner.nextLine());

            System.out.print("Dirección: ");
            contacto.setDireccion(scanner.nextLine());

            contacto.setFechaCumpleanos(null);
        }

        int numFotos = 0;
        do {
            System.out.print("¿Cuántas fotos desea agregar? (mínimo 2): ");
            String entradaFotos = scanner.nextLine();
            try {
                numFotos = Integer.parseInt(entradaFotos);
                if (numFotos < 2) {
                    System.out.println("Debe agregar al menos 2 fotos.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
            }
        } while (numFotos < 2);

        for (int i = 0; i < numFotos; i++) {
            System.out.print("Ruta o nombre de la foto " + (i + 1) + ": ");
            String foto = scanner.nextLine();
            contacto.agregarFoto(foto);
        }
        System.out.print("¿Desea agregar relaciones a este contacto? (s/n): ");
String agregarRelaciones = scanner.nextLine().trim().toLowerCase();

if (agregarRelaciones.equals("s")) {
    System.out.print("¿Cuántas relaciones desea agregar?: ");
    int numRelaciones = 0;
    try {
        numRelaciones = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Número inválido. No se agregarán relaciones.");
        numRelaciones = 0;
    }

    for (int i = 0; i < numRelaciones; i++) {
        System.out.print("Ingrese el nombre del contacto relacionado " + (i + 1) + ": ");
        String nombreRelacionado = scanner.nextLine();

        Contacto contactoRelacionado = null;
        for (Contacto c : gestor.getContactos()) {
            if (c.getNombre() != null && c.getNombre().trim().equalsIgnoreCase(nombreRelacionado.trim())) {
                contactoRelacionado = c;
                break;
            }
        }

        if (contactoRelacionado != null && !contactoRelacionado.equals(contacto)) {
            System.out.print("Ingrese el tipo de relación (ej. amigo, cliente, jefe): ");
            String tipoRelacion = scanner.nextLine();

            if (!contacto.getContactosRelacionados().containsKey(contactoRelacionado)) {
                contacto.agregarContactoRelacionado(contactoRelacionado, tipoRelacion);
                System.out.println("Relación agregada correctamente.");
            } else {
                System.out.println("Ya existe una relación con este contacto.");
            }
        } else {
            System.out.println("No se encontró el contacto relacionado o intentó relacionarlo consigo mismo.");
        }
    }
}

        gestor.agregarContacto(contacto);
        gestor.guardarContactoEnArchivo(contacto);
        System.out.println("Contacto creado con éxito.");
    }

    private static void navegarContactos(GestorContactos gestor, Scanner scanner) {
        ListaCircularDoble<Contacto> lista = gestor.getContactos();

        if (lista.estaVacia()) {
            System.out.println("No hay contactos para navegar.");
            return;
        }

        int tamaño = lista.tamaño();
        int indiceActual = 0;

        while (true) {
            Contacto actual = lista.obtener(indiceActual);
            System.out.println("\n--- Contacto actual ---");
            System.out.println(actual);

            System.out.print("Ingrese [n] siguiente, [p] anterior, [x] salir: ");
            String opcion = scanner.nextLine().toLowerCase();

            if (opcion.equals("n")) {
                indiceActual = (indiceActual + 1) % tamaño;
            } else if (opcion.equals("p")) {
                indiceActual = (indiceActual - 1 + tamaño) % tamaño;
            } else if (opcion.equals("x")) {
                break;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    private static void navegarFotosDeContacto(GestorContactos gestor, Scanner scanner) {
        ListaCircularDoble<Contacto> contactos = gestor.getContactos();
        if (contactos.estaVacia()) {
            System.out.println("No hay contactos disponibles.");
            return;
        }

        System.out.print("Ingrese el nombre del contacto: ");
        String nombreBuscado = scanner.nextLine();

        Contacto encontrado = null;
        for (Contacto c : contactos) {
            if (c.getNombre() != null && c.getNombre().trim().equalsIgnoreCase(nombreBuscado.trim())) {
                encontrado = c;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Contacto no encontrado.");
            return;
        }

        ListaCircularDoble<String> fotos = encontrado.getFotos();
        if (fotos.tamaño() < 2) {
            System.out.println("Este contacto no tiene suficientes fotos (mínimo 2 requeridas).\n");
            return;
        }

        int indiceActual = 0;

        while (true) {
            String fotoActual = fotos.obtener(indiceActual);
            System.out.println("Foto actual: " + fotoActual);
            System.out.print("Comandos: [n] siguiente | [p] anterior | [x] salir → ");
            String comando = scanner.nextLine().trim().toLowerCase();

            switch (comando) {
                case "n":
                    indiceActual = (indiceActual + 1) % fotos.tamaño();
                    break;
                case "p":
                    indiceActual = (indiceActual - 1 + fotos.tamaño()) % fotos.tamaño();
                    break;
                case "x":
                    System.out.println("Saliendo de navegación de fotos.");
                    return;
                default:
                    System.out.println("Comando no reconocido.");
            }
        }
    }
    
    private static void editarContacto(GestorContactos gestor, Scanner scanner) {
    ListaCircularDoble<Contacto> contactos = gestor.getContactos();

    if (contactos.estaVacia()) {
        System.out.println("No hay contactos para editar.");
        return;
    }

    System.out.print("Ingrese el nombre del contacto que desea editar: ");
String nombreBuscado = scanner.nextLine();
Contacto encontrado = null;

// Buscar el contacto por nombre
for (Contacto c : contactos) {
    if (c.getNombre() != null && c.getNombre().trim().equalsIgnoreCase(nombreBuscado.trim())) {
        encontrado = c;
        break;
    }

}

    if (encontrado == null) {
        System.out.println("Contacto no encontrado.");
        return;
    }

    System.out.println("Editando contacto: " + encontrado.getNombre());

    // Menú de opciones para editar campos específicos
    System.out.println("Seleccione qué desea editar:");
    System.out.println("1. Nombre");
    System.out.println("2. Apellido");
    System.out.println("3. Teléfono");
    System.out.println("4. Correo");
    System.out.println("5. Dirección");
    System.out.println("6. Fecha de cumpleaños");
    System.out.println("7. Contactos relacionados");
    System.out.println("8. Fotos");
    System.out.print("Opción: ");
    int opcion = scanner.nextInt();
    scanner.nextLine();  // Limpiar el buffer

    switch (opcion) {
        case 1:
            System.out.print("Nuevo nombre: ");
            encontrado.setNombre(scanner.nextLine());
            break;
        case 2:
            System.out.print("Nuevo apellido: ");
            encontrado.setApellido(scanner.nextLine());
            break;
        case 3:
            System.out.print("Nuevo teléfono: ");
            encontrado.setTelefono(scanner.nextLine());
            break;
        case 4:
            System.out.print("Nuevo correo: ");
            encontrado.setCorreo(scanner.nextLine());
            break;
        case 5:
            System.out.print("Nueva dirección: ");
            encontrado.setDireccion(scanner.nextLine());
            break;
        case 6:
            System.out.print("Nueva fecha de cumpleaños (YYYY-MM-DD): ");
            encontrado.setFechaCumpleanos(scanner.nextLine());
            break;
        case 7:
            System.out.print("¿Cuántas relaciones desea agregar?: ");
    int numRelaciones = scanner.nextInt();
    scanner.nextLine();  // Limpiar el buffer

    for (int i = 0; i < numRelaciones; i++) {
        System.out.print("Ingrese el nombre del contacto relacionado " + (i + 1) + ": ");
        String nombreRelacionado = scanner.nextLine();

        // Buscar el contacto relacionado
        Contacto contactoRelacionado = null;
        for (Contacto c : gestor.getContactos()) {
            if (c.getNombre() != null && c.getNombre().trim().equalsIgnoreCase(nombreRelacionado.trim())) {
                contactoRelacionado = c;
                break;
            }
        }

        if (contactoRelacionado != null && !contactoRelacionado.equals(encontrado)) {
            System.out.print("Ingrese el tipo de relación (por ejemplo, mamá, papá, médico, asistente): ");
            String tipoRelacion = scanner.nextLine();

            // Verificar si ya existe la relación
            if (!encontrado.getContactosRelacionados().containsKey(contactoRelacionado)) {
                // Agregar la relación bidireccional
                encontrado.agregarContactoRelacionado(contactoRelacionado, tipoRelacion);
                System.out.println("Relación agregada correctamente.");
            } else {
                System.out.println("Ya existe una relación con este contacto. No se ha agregado duplicado.");
            }
        } else {
            System.out.println("No se encontró el contacto relacionado o intentó relacionarlo consigo mismo.");
        }
    }
    
    break;
        case 8:
            // Reemplazar fotos
            encontrado.getFotos().clear();  // Limpiar fotos antiguas
            System.out.print("¿Cuántas fotos desea agregar nuevamente?: ");
            int numFotos = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numFotos; i++) {
                System.out.print("Foto " + (i + 1) + ": ");
                encontrado.agregarFoto(scanner.nextLine());
            }
            break;
        default:
            System.out.println("Opción no válida.");
    }

    System.out.println("Contacto editado correctamente.");
}
    
    private static void eliminarContacto(GestorContactos gestor, Scanner scanner) {
    ListaCircularDoble<Contacto> contactos = gestor.getContactos();

    if (contactos.estaVacia()) {
        System.out.println("No hay contactos para eliminar.");
        return;
    }

    System.out.print("Ingrese el nombre del contacto a eliminar: ");
    String nombreBuscado = scanner.nextLine();

    for (int i = 0; i < contactos.tamaño(); i++) {
        Contacto actual = contactos.obtener(i);
        if (actual.getNombre() != null && actual.getNombre().equalsIgnoreCase(nombreBuscado)) {
            contactos.eliminar(i);
            System.out.println("Contacto eliminado exitosamente.");
            return;
        }
    }

    System.out.println("Contacto no encontrado.");
    }
    
    private static void ordenarContactosMenu(GestorContactos gestor, Scanner scanner) {
    System.out.println("Seleccione el criterio de ordenamiento:");
    System.out.println("1. Apellido y primer nombre");
    System.out.println("2. Cantidad de atributos");
    System.out.println("3. Fecha de cumpleaños más cercana");
    System.out.print("Opción: ");
    int opcion = scanner.nextInt();
    scanner.nextLine();

    switch (opcion) {
        case 1:
            gestor.ordenarContactos(new ComparadorApellidoNombre());
            System.out.println("Lista ordenada por apellido y nombre.");
            break;
        case 2:
            gestor.ordenarContactos(new ComparadorCantidadAtributos());
            System.out.println("Lista ordenada por cantidad de atributos.");
            break;
        case 3:
            gestor.ordenarContactos(new ComparadorCumpleanosProximo());
            System.out.println("Lista ordenada por fecha de cumpleaños próxima.");
            break;
        default:
            System.out.println("Opción no válida.");
    }
    }
    
    
    private static void filtrarContactos(GestorContactos gestor, Scanner scanner) {
    System.out.println("Seleccione el criterio de filtrado:");
    System.out.println("1. Por apellido");
    System.out.println("2. Por nombre");
    System.out.println("3. Por tipo de contacto (persona o empresa)");

    System.out.print("Opción: ");
    String entrada = scanner.nextLine();
    int opcion;

    try {
        opcion = Integer.parseInt(entrada);
    } catch (NumberFormatException e) {
        System.out.println("Entrada inválida.");
        return;
    }

    ListaCircularDoble<Contacto> filtrados = new ListaCircularDoble<>();

    switch (opcion) {
        case 1:
            System.out.print("Ingrese el apellido a buscar: ");
            String apellido = scanner.nextLine().trim().toLowerCase();
            for (Contacto c : gestor.getContactos()) {
                if (c.getApellido() != null && c.getApellido().toLowerCase().contains(apellido)) {
                    filtrados.agregarAlFinal(c);
                }
            }
            break;
        case 2:
            System.out.print("Ingrese el nombre a buscar: ");
            String nombre = scanner.nextLine().trim().toLowerCase();
            for (Contacto c : gestor.getContactos()) {
                if (c.getNombre() != null && c.getNombre().toLowerCase().contains(nombre)) {
                    filtrados.agregarAlFinal(c);
                }
            }
            break;
        case 3:
            System.out.print("Ingrese el tipo de contacto (persona o empresa): ");
            String tipo = scanner.nextLine().trim().toLowerCase();
            for (Contacto c : gestor.getContactos()) {
                if (c.getTipoContacto() != null && c.getTipoContacto().toLowerCase().equals(tipo)) {
                    filtrados.agregarAlFinal(c);
                }
            }
            break;
        default:
            System.out.println("Opción no válida.");
            return;
    }

    if (filtrados.estaVacia()) {
        System.out.println("No se encontraron contactos con ese filtro.");
    } else {
        System.out.println("Contactos encontrados:");
        for (Contacto c : filtrados) {
            System.out.println(c);
        }
    }
}

    
}
