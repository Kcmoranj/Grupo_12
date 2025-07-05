package Grupo_12;

import java.util.Scanner;
import java.io.File;

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
            System.out.println("3. Navegar entre contactos");
            System.out.println("4. Navegar en fotos de un contacto");
            System.out.println("5. Editar contacto");
            System.out.println("6. Eliminar contacto");
            System.out.println("7. Filtrar contactos");
            System.out.println("8. Guardar y salir");
            System.out.print("Seleccione una opción: ");
            String entrada = scanner.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 9.");
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
                    navegarContactos(gestor, scanner);
                    break;
                case 4:
                    navegarFotosDeContacto(gestor, scanner);
                    break;
                case 5:
                    editarContacto(gestor, scanner);
                    break;
                case 6:
                    eliminarContacto(gestor, scanner);
                    break;
                case 7:
                    filtrarContactos(gestor, scanner);
                    break;
                case 8:
                    System.out.println("Saliendo y guardando...");
                    gestor.guardarContactosEnArchivo();
                    break;
                    
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 8);
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
            String telefono;
            do {
                System.out.print("Teléfono (10 dígitos): ");
                telefono = scanner.nextLine().trim();
                if (!telefono.matches("\\d{10}")) {
                    System.out.println("Número inválido. Debe contener exactamente 10 dígitos.");
                }
            } while (!telefono.matches("\\d{10}"));
            contacto.setTelefono(telefono);
            String correo;
            do {
                System.out.print("Correo (debe contener '@'): ");
                correo = scanner.nextLine().trim();
                if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                    System.out.println("Correo inválido. Ejemplo válido: nombre@dominio.com");
                }
            } while (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"));
            contacto.setCorreo(correo);
            System.out.print("Dirección: ");
            contacto.setDireccion(scanner.nextLine());
            System.out.print("Fecha de cumpleaños (MM-DD): ");
            contacto.setFechaCumpleanos(scanner.nextLine());
        } else {
            contacto.setTipoContacto("empresa");
            System.out.print("Nombre de la empresa: ");
            contacto.setNombre(scanner.nextLine());
            contacto.setApellido(null);
            String telefono;
            do {
                System.out.print("Teléfono (10 dígitos): ");
                telefono = scanner.nextLine().trim();
                if (!telefono.matches("\\d{10}")) {
                    System.out.println("Número inválido. Debe contener exactamente 10 dígitos.");
                }
            } while (!telefono.matches("\\d{10}"));
            contacto.setTelefono(telefono);
            String correo;
            do {
                System.out.print("Correo (debe contener '@'): ");
                correo = scanner.nextLine().trim();
                if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                    System.out.println("Correo inválido. Ejemplo válido: nombre@dominio.com");
                }
            } while (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"));
            contacto.setCorreo(correo);
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
        //creamos la carpeta imagenes solo si no existe
        java.io.File carpetaImagenes = new java.io.File("imagenes");
        if (!carpetaImagenes.exists()) {
            if (carpetaImagenes.mkdir()) {
                System.out.println("Carpeta 'imagenes' creada exitosamente.");
            } else {
                System.out.println("No se pudo crear la carpeta 'imagenes'.");
                // Opcional: aquí puedes decidir si continuar o no.
            }
        }
        for (int i = 0; i < numFotos; i++) {
            String foto;
            do {
                System.out.print("Ruta o nombre de la foto " + (i + 1) + ": ");
                foto = scanner.nextLine();
                if (!esFotoValida(foto)) {
                    System.out.println("Ruta inválida o extensión no permitida. Debe ser un archivo existente con extensión jpg, jpeg o png.");
                }
            } while (!esFotoValida(foto));
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
        for (Contacto c : gestor.getContactos()) {
            if (c.getNombre() != null && contacto.getNombre() != null &&
                c.getNombre().trim().equalsIgnoreCase(contacto.getNombre().trim()) &&
                (
                    (c.getApellido() == null && contacto.getApellido() == null) ||
                    (c.getApellido() != null && contacto.getApellido() != null &&
                     c.getApellido().trim().equalsIgnoreCase(contacto.getApellido().trim()))
                ) &&
                c.getTipoContacto() != null && contacto.getTipoContacto() != null &&
                c.getTipoContacto().trim().equalsIgnoreCase(contacto.getTipoContacto().trim())
            ) {
                System.out.println("Ya existe un contacto con el mismo nombre, apellido y tipo. No se agregará.");
                return;
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
        int tipoNum = -1;
        String tipo = null;
        do {
            System.out.println("Seleccione el tipo de contacto:");
            System.out.println("1. Persona");
            System.out.println("2. Empresa");
            System.out.print("Opción: ");
            String entrada = scanner.nextLine();
            try {
                tipoNum = Integer.parseInt(entrada);
                if (tipoNum == 1) {
                    tipo = "persona";
                } else if (tipoNum == 2) {
                    tipo = "empresa";
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    tipoNum = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese 1 o 2.");
            }
        } while (tipoNum == -1);
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine().trim().toLowerCase();
        String apellido = null;
        if (tipo.equals("persona")) {
            System.out.print("Ingrese el apellido: ");
            apellido = scanner.nextLine().trim().toLowerCase();
        }
        Contacto encontrado = null;
        for (Contacto c : contactos) {
            if (c.getNombre() != null && c.getNombre().trim().toLowerCase().equals(nombre) &&
                c.getTipoContacto() != null && c.getTipoContacto().trim().toLowerCase().equals(tipo)) {
                if (tipo.equals("empresa")) {
                    encontrado = c;
                    break;
                } else if (c.getApellido() != null && c.getApellido().trim().toLowerCase().equals(apellido)) {
                    encontrado = c;
                    break;
                }
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
        if (!encontrado.getTipoContacto().equalsIgnoreCase("empresa")) {
            System.out.println("2. Apellido");
        }
        System.out.println("3. Teléfono");
        System.out.println("4. Correo");
        System.out.println("5. Dirección");
        if (!encontrado.getTipoContacto().equalsIgnoreCase("empresa")) {
            System.out.println("6. Fecha de cumpleaños");
        }   
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
                if (encontrado.getTipoContacto().equalsIgnoreCase("empresa")) {
                    System.out.println("Este contacto es una empresa. No tiene apellido.");
                    break;
                }
                System.out.print("Nuevo apellido: ");
                encontrado.setApellido(scanner.nextLine());
                break;
            case 3:
                String nuevoTelefono;
                do {
                    System.out.print("Nuevo teléfono (10 dígitos): ");
                    nuevoTelefono = scanner.nextLine().trim();
                    if (!nuevoTelefono.matches("\\d{10}")) {
                        System.out.println("Número inválido. Debe contener exactamente 10 dígitos.");
                    }
                } while (!nuevoTelefono.matches("\\d{10}"));
                encontrado.setTelefono(nuevoTelefono);
                break;
            case 4:
                String nuevoCorreo;
                do {
                    System.out.print("Nuevo correo (debe contener '@'): ");
                    nuevoCorreo = scanner.nextLine().trim();
                    if (!nuevoCorreo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                        System.out.println("Correo inválido. Ejemplo válido: nombre@dominio.com");
                    }
                } while (!nuevoCorreo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"));
                encontrado.setCorreo(nuevoCorreo);
                break;                
            case 5:
                System.out.print("Nueva dirección: ");
                encontrado.setDireccion(scanner.nextLine());
                break;
            case 6:
                if (encontrado.getTipoContacto().equalsIgnoreCase("empresa")) {
                    System.out.println("Este contacto es una empresa. No tiene fecha de cumpleaños.");
                    break;
                }
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
                    String foto;
                    do {
                        System.out.print("Ruta o nombre de la foto " + (i + 1) + ": ");
                        foto = scanner.nextLine();
                        if (!esFotoValida(foto)) {
                            System.out.println("Ruta inválida o extensión no permitida. Debe ser un archivo existente con extensión jpg, jpeg o png.");
                        }
                    } while (!esFotoValida(foto));
                    encontrado.agregarFoto(foto);
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
        int tipoNum = -1;
        String tipo = null;
        do {
            System.out.println("Seleccione el tipo de contacto:");
            System.out.println("1. Persona");
            System.out.println("2. Empresa");
            System.out.print("Opción: ");
            String entrada = scanner.nextLine();
            try {
                tipoNum = Integer.parseInt(entrada);
                if (tipoNum == 1) {
                    tipo = "persona";
                } else if (tipoNum == 2) {
                    tipo = "empresa";
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    tipoNum = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese 1 o 2.");
            }
        } while (tipoNum == -1);
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine().trim().toLowerCase();
        String apellido = null;
        if (tipo.equals("persona")) {
            System.out.print("Ingrese el apellido: ");
            apellido = scanner.nextLine().trim().toLowerCase();
        }
        Contacto encontrado = null;
        for (Contacto c : contactos) {
            if (c.getNombre() != null && c.getNombre().trim().toLowerCase().equals(nombre) &&
                c.getTipoContacto() != null && c.getTipoContacto().trim().toLowerCase().equals(tipo)) {
                if (tipo.equals("empresa")) {
                    encontrado = c;
                    break;
                } else if (c.getApellido() != null && c.getApellido().trim().toLowerCase().equals(apellido)) {
                    encontrado = c;
                    break;
                }
            }
        }
        if (encontrado != null) {
            for (int i = 0; i < contactos.tamaño(); i++) {
                if (contactos.obtener(i).equals(encontrado)) {
                    contactos.eliminar(i);
                    System.out.println("Contacto eliminado exitosamente.");
                    return;
                }
            }
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }
       
    private static void filtrarContactos(GestorContactos gestor, Scanner scanner) {
        System.out.println("Seleccione el criterio de filtrado:");
        System.out.println("1. Por apellido y nombre");
        System.out.println("2. Por tipo de contacto (persona o empresa)");
        System.out.println("3. Por mes de cumpleaños (1-12)");
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
                System.out.print("Ingrese el apellido o nombre (puede ser parcial): ");
                String texto = scanner.nextLine().trim().toLowerCase();
                for (Contacto c : gestor.getContactos()) {
                    String nombre = c.getNombre() != null ? c.getNombre().toLowerCase() : "";
                    String apellido = c.getApellido() != null ? c.getApellido().toLowerCase() : "";
                    if (nombre.contains(texto) || apellido.contains(texto)) {
                        filtrados.agregarAlFinal(c);
                    }
                }
                break;
            case 2:
                System.out.print("Ingrese el tipo de contacto (persona o empresa): ");
                String tipo = scanner.nextLine().trim().toLowerCase();
                for (Contacto c : gestor.getContactos()) {
                    if (c.getTipoContacto() != null && c.getTipoContacto().toLowerCase().equals(tipo)) {
                        filtrados.agregarAlFinal(c);
                    }
                }
                break;
            case 3:
                System.out.print("Ingrese el mes de cumpleaños (1-12): ");
                String mesStr = scanner.nextLine().trim();
                int mes;
                try {
                    mes = Integer.parseInt(mesStr);
                    if (mes < 1 || mes > 12) {
                        System.out.println("Mes inválido.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida.");
                    return;
                }
                for (Contacto c : gestor.getContactos()) {
                    String fecha = c.getFechaCumpleanos(); // suponemos formato "YYYY-MM-DD" o "MM-DD"
                    if (fecha != null && !fecha.isEmpty()) {
                        int mesCumple = -1;
                        try {
                            String[] partes = fecha.split("-");
                            if (partes.length == 3) {
                                mesCumple = Integer.parseInt(partes[1]); // formato YYYY-MM-DD
                            } else if (partes.length == 2) {
                                mesCumple = Integer.parseInt(partes[0]); // formato MM-DD
                            }
                        } catch (Exception e) {
                            mesCumple = -1;
                        }
                        if (mesCumple == mes) {
                            filtrados.agregarAlFinal(c);
                        }
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
    public static boolean esFotoValida(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists() || archivo.isDirectory()) {
            return false;  // No existe o es una carpeta
        }
        String rutaMinuscula = ruta.toLowerCase();
        return rutaMinuscula.endsWith(".jpg") || rutaMinuscula.endsWith(".jpeg") || rutaMinuscula.endsWith(".png");
    }
}
