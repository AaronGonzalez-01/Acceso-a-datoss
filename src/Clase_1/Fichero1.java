package Clase_1;

import java.io.File;

public class Fichero1 {
    public static void main(String[] args) {
        File dirPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\ProyectoJava");

        String nomHijo = "hijo.txt";

        File archivo = new File(dirPadre, nomHijo);

        //combrobar si el archivo existe
        if (archivo.exists()) {
            System.out.println("El archivo existe en la ruta: " + archivo.getAbsolutePath());
        } else
            System.out.println("El archivo no existe en la ruta: " + archivo.getAbsolutePath());
    }
}
