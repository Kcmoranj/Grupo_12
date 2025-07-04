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
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear nuevo contacto");
            System.out.println("2. Ver lista de contactos");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearContacto(gestor, scanner);
                    break;
                case 2:
                    gestor.mostrarContactos();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 3);
    }

    private static void crearContacto(GestorContactos gestor, Scanner scanner) {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        Contacto nuevo = new Contacto(nombre, telefono);

        System.out.print("Ingrese el correo (opcional): ");
        String correo = scanner.nextLine();
        nuevo.setCorreo(correo);

        System.out.print("Ingrese la dirección (opcional): ");
        String direccion = scanner.nextLine();
        nuevo.setDireccion(direccion);

        System.out.print("¿Cuántas fotos desea agregar?: ");
        int numFotos = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numFotos; i++) {
            System.out.print("Ruta o nombre de la foto " + (i + 1) + ": ");
            String foto = scanner.nextLine();
            nuevo.agregarFoto(foto);
        }

        gestor.agregarContacto(nuevo);
        System.out.println("Contacto creado con éxito.");
    }
}
