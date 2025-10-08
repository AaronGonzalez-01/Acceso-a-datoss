package Deberes_1;

import java.io.File;
public class Fichero4 {
    public static void main(String[] args) {
        // Ruta al directorio que se desea listar
        String ruta = "C:\\Users\\AlumnoAfternoon\\Documents\\ProyectoJava";
        File directorio = new File(ruta);

        // Verificar si la ruta existe y es un directorio
        if (directorio.exists() && directorio.isDirectory()) {
            String[] contenido = directorio.list();

            // Verificar si se pudo obtener el contenido
            if (contenido != null) {
                System.out.println("Contenido del directorio '" + ruta + "':");
                for (String nombre : contenido) {
                    System.out.println(nombre);
                }
            } else {
                System.out.println("No se pudo listar el contenido del directorio.");
            }
        } else {
            System.out.println("La ruta '" + ruta + "' no es un directorio o no existe.");
        }
    }
}
