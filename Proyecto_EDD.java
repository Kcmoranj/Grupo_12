/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto;

/**
 *
 * @author kiara
 */
public class Proyecto_EDD {

     public static void main(String[] args) {

        GestorContactos gestor = new GestorContactos();
        gestor.cargarContactosDesdeArchivo();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear nuevo contacto");
            System.out.println("2. Ver lista de contactos");
            System.out.println("3. Guardar un contactos");
            System.out.println("4. Navegar entre contactos");
            System.out.println("5. Navegar en fotos de un contacto");
            System.out.println("6. Editar contacto");
            System.out.println("7. Eliminar contacto");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

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
                    System.out.println("Saliendo y guardando...");
                    gestor.guardarContactosEnArchivo();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 8);
    }
    private static void mostrarContactosConOpcionesOrdenamiento(GestorContactos gestor, Scanner scanner) {
        System.out.println("¿Desea ver los contactos ordenados?");
        System.out.println("1. Sí");
        System.out.println("2. No (mostrar sin ordenar)");
        System.out.print("Opción: ");
        int opcionOrden = scanner.nextInt();
        scanner.nextLine();

        if (opcionOrden == 1) {
            System.out.println("Seleccione el criterio de ordenamiento:");
            System.out.println("1. Apellido y primer nombre");
            System.out.println("2. Cantidad de atributos");
            System.out.println("3. Fecha de cumpleaños más cercana");
            System.out.print("Opción: ");
            int criterio = scanner.nextInt();
            scanner.nextLine();

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
                default:
                    System.out.println("Criterio no válido. Se mostrará sin ordenar.");
            }
        } else if (opcionOrden != 2) {
            System.out.println("Opción no válida. Mostrando sin ordenar.");
        }

        gestor.mostrarContactos();
    }

    private static void crearContacto(GestorContactos gestor, Scanner scanner) {
        System.out.println("Seleccione el tipo de contacto:");
        System.out.println("1. Persona");
        System.out.println("2. Empresa");
        System.out.print("Opción: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

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

            System.out.print("Fecha de cumpleaños (YYYY-MM-DD): ");
            contacto.setFechaCumpleanos(scanner.nextLine());

        } else if (tipo == 2) {
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
        } else {
            System.out.println("Tipo no válido.");
            return;
        }

        int numFotos = 0;
        do {
            System.out.print("¿Cuántas fotos desea agregar? (mínimo 2): ");
            numFotos = scanner.nextInt();
            scanner.nextLine();
            if (numFotos < 2) {
                System.out.println("Debe agregar al menos 2 fotos.");
            }
        } while (numFotos < 2);

        for (int i = 0; i < numFotos; i++) {
            System.out.print("Ruta o nombre de la foto " + (i + 1) + ": ");
            String foto = scanner.nextLine();
            contacto.agregarFoto(foto);
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

    for (Contacto c : contactos) {
        if (c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombreBuscado)) {
            encontrado = c;
            break;
        }
    }

    if (encontrado == null) {
        System.out.println("Contacto no encontrado.");
        return;
    }

    System.out.println("Editando contacto: " + encontrado.getNombre());

    // Editar según tipo
    if ("persona".equalsIgnoreCase(encontrado.getTipoContacto())) {
        System.out.print("Nuevo nombre: ");
        encontrado.setNombre(scanner.nextLine());

        System.out.print("Nuevo apellido: ");
        encontrado.setApellido(scanner.nextLine());

        System.out.print("Nuevo teléfono: ");
        encontrado.setTelefono(scanner.nextLine());

        System.out.print("Nuevo correo: ");
        encontrado.setCorreo(scanner.nextLine());

        System.out.print("Nueva dirección: ");
        encontrado.setDireccion(scanner.nextLine());

        System.out.print("Nueva fecha de cumpleaños (YYYY-MM-DD): ");
        encontrado.setFechaCumpleanos(scanner.nextLine());

    } else if ("empresa".equalsIgnoreCase(encontrado.getTipoContacto())) {
        System.out.print("Nuevo nombre de empresa: ");
        encontrado.setNombre(scanner.nextLine());

        System.out.print("Nuevo teléfono: ");
        encontrado.setTelefono(scanner.nextLine());

        System.out.print("Nuevo correo: ");
        encontrado.setCorreo(scanner.nextLine());

        System.out.print("Nueva dirección: ");
        encontrado.setDireccion(scanner.nextLine());
    }

    // Reemplazar fotos
    encontrado.getFotos().clear();
    System.out.print("¿Cuántas fotos desea ingresar nuevamente?: ");
    int numFotos = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < numFotos; i++) {
        System.out.print("Foto " + (i + 1) + ": ");
        encontrado.agregarFoto(scanner.nextLine());
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
}
