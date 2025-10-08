package Deberes_1;

import java.io.File;
import java.io.IOException;

public class Fichero5 {
    public static void main(String[] args) {
        // Ruta al archivo hijo
        String rutaArchivo = "C:\\Users\\AlumnoAfternoon\\Documents\\ProyectoJava\\hijo.txt";
        // Ruta al directorio padre
        String rutaDirectorio = "C:\\Users\\AlumnoAfternoon\\Documents\\ProyectoJava\\padre";

        File archivoHijo = new File(rutaArchivo);
        File directorioPadre = new File(rutaDirectorio);

        boolean fin = false;

        do {
            // Verificar existencia del directorio
            if (!directorioPadre.exists()) {
                System.out.println("El directorio no existe.");
                if (directorioPadre.mkdir()) {
                    System.out.println("El directorio fue creado.");
                } else {
                    System.out.println("No se pudo crear el directorio.");
                }
            }
            // Verificar existencia del archivo
            else if (!archivoHijo.exists()) {
                System.out.println("El archivo no existe.");
                try {
                    if (archivoHijo.createNewFile()) {
                        System.out.println("El archivo fue creado.");
                    } else {
                        System.out.println("No se pudo crear el archivo.");
                    }
                } catch (IOException e) {
                    System.out.println("Error al crear el archivo: " + e.getMessage());
                }
            }
            // Ambos existen
            else {
                System.out.println("El directorio y el archivo existen.");
                fin = true;
            }
        } while (!fin);
    }
}
