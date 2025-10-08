import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Fichero3 {
    // Una ruta con URI, crear el objeto, verificar que existe(else if), si es un directorio o un archivo(else if)(try catch)
    public static void main(String[] args) {
        // Utilizamos un try catch porque el objeto URI tiende a dar errores

        try{
            // Crear un objeto uri a partir de una cadema de texto
            String uriString = "File:///C:/Users/AlumnoAfternoon/Documents/PruebasJava";
            URI uri = new URI(uriString);

            // Verificar si el archivo existe y su es un directorio
            File pruebaURI = new File(uri);

            if(pruebaURI.exists()) {
                if (pruebaURI.isDirectory()) {
                    //Si en la ruta es un directorio, nos muestra un mensaje en pantalla que la ruta especificada es un directorio
                    System.out.println("La ruta presenta un directorio en " + pruebaURI.getAbsolutePath());
                } else if (pruebaURI.isFile()) {
                    //Si en la ruta es un archivo, nos muestra un mensaje en pantalla que la ruta especificada es un archivo
                    System.out.println("La ruta presenta un archivo en " + pruebaURI.getAbsolutePath());
                }
            }else{
                System.out.println("La URI no existe" + uri.toString());
            }
        }catch (URISyntaxException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}