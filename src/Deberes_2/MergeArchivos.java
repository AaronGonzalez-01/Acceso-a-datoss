package Obligatorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MergeArchivos {

    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro)
            throws IOException {

        // Validar que los archivos existen
        for (String archivo : archivosEntrada) {
            if (!Files.exists(Paths.get(archivo))) {
                throw new IOException("El archivo no existe: " + archivo);
            }
        }

        int lineasEscritas = 0;

        // Abrir writer de salida
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoSalida), StandardCharsets.UTF_8)) {

            // Procesar cada archivo de entrada
            for (String archivo : archivosEntrada) {

                int lineasCoincidentes = 0;

                try (BufferedReader br = Files.newBufferedReader(Paths.get(archivo), StandardCharsets.UTF_8)) {
                    String linea;

                    while ((linea = br.readLine()) != null) {
                        if (cumpleFiltro(linea, filtro)) {
                            bw.write(linea);
                            bw.newLine();
                            lineasEscritas++;
                            lineasCoincidentes++;
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Error leyendo " + archivo + ": " + e.getMessage());
                }

                System.out.println("Procesando " + archivo + ": " + lineasCoincidentes + " líneas coinciden");
            }
        }

        System.out.println("Total: " + lineasEscritas + " líneas escritas en " + archivoSalida);
        return lineasEscritas;
    }

    private static boolean cumpleFiltro(String linea, String filtro) {
        if (filtro == null) return true;
        return linea.contains(filtro);
    }

    public static void main(String[] args) {

        String[] archivos = {"archivo1.txt", "archivo2.txt"};

        try {
            combinarArchivos(archivos, "combinado.txt", "Java");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

