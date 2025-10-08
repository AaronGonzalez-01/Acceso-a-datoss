package Deberes_1;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class Fichero9 {

    public static void mostrarMenu() {
        System.out.println("\n--- menu principal ---");
        System.out.println("1. verificar archivo");
        System.out.println("2. explorar carpeta");
        System.out.println("3. crear carpeta");
        System.out.println("4. crear archivo");
        System.out.println("5. trabajar con uris");
        System.out.println("6. salir");
        System.out.print("elige una opcion: ");
    }

    public static void verificarArchivo(Scanner sc) {
        System.out.print("introduce la ruta del archivo: ");
        String ruta = sc.nextLine();
        File archivo = new File(ruta);

        if (archivo.exists()) {
            if (archivo.isFile()) {
                System.out.println("es un archivo");
                System.out.println("tamaño: " + archivo.length() + " bytes");
            } else if (archivo.isDirectory()) {
                System.out.println("es una carpeta");
            }
        } else {
            System.out.println("la ruta no existe");
        }
    }

    public static void explorarDirectorio(Scanner sc) {
        System.out.print("introduce la ruta de la carpeta: ");
        String ruta = sc.nextLine();
        File carpeta = new File(ruta);

        if (carpeta.exists() && carpeta.isDirectory()) {
            String[] contenido = carpeta.list();
            if (contenido != null && contenido.length > 0) {
                System.out.println("contenido de la carpeta:");
                for (int i = 0; i < contenido.length; i++) {
                    System.out.println("- " + contenido[i]);
                }
            } else {
                System.out.println("la carpeta esta vacia");
            }
        } else {
            System.out.println("la ruta no es una carpeta");
        }
    }

    public static void crearCarpeta(Scanner sc) {
        System.out.print("introduce la ruta de la carpeta a crear: ");
        String ruta = sc.nextLine();
        File carpeta = new File(ruta);

        if (carpeta.exists()) {
            System.out.println("la carpeta ya existe");
        } else {
            if (carpeta.mkdir()) {
                System.out.println("carpeta creada correctamente");
            } else {
                System.out.println("no se pudo crear la carpeta");
            }
        }
    }

    public static void crearArchivo(Scanner sc) {
        System.out.print("introduce la ruta del archivo a crear: ");
        String ruta = sc.nextLine();
        File archivo = new File(ruta);

        if (archivo.exists()) {
            System.out.println("el archivo ya existe");
        } else {
            try {
                if (archivo.createNewFile()) {
                    System.out.println("archivo creado correctamente");
                } else {
                    System.out.println("no se pudo crear el archivo");
                }
            } catch (IOException e) {
                System.out.println("error al crear el archivo");
            }
        }
    }

    public static void trabajarConURI(Scanner sc) {
        System.out.print("introduce la ruta: ");
        String ruta = sc.nextLine();
        File archivo = new File(ruta);



        if (archivo.exists()) {
            URI uri = archivo.toURI();
            System.out.println("ruta original: " + archivo.getAbsolutePath());
            System.out.println("ruta en uri: " + uri.toString());
        } else {
            System.out.println("la ruta no existe");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    verificarArchivo(sc);
                    break;
                case 2:
                    explorarDirectorio(sc);
                    break;
                case 3:
                    crearCarpeta(sc);
                    break;
                case 4:
                    crearArchivo(sc);
                    break;
                case 5:
                    trabajarConURI(sc);
                    break;
                case 6:
                    System.out.println("saliendo del programa...");
                    break;
                default:
                    System.out.println("opcion no valida");
            }
        } while (opcion != 6);

        sc.close();
    }
}
