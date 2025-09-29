package Clase_1;

import java.io.File;

public class Fichero2 {
    public static void main(String[] args) {
        File dirPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\ProyectoJava");

        //verificar si la ruta existe
        if (dirPadre.exists()) {
            //verificar si la ruta especificada es un directorio
            if (dirPadre.isDirectory()) {
                System.out.println("La ruta presenta un archivo en: "+ dirPadre.getAbsolutePath());
            } else if (dirPadre.isFile()) {
                System.out.println("la ruta presenta un archivo en: "+ dirPadre.getAbsolutePath());
            }
        }

    }
}
