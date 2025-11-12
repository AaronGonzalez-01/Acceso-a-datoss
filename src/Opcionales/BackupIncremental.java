package Opcionales;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupIncremental {

    public static void main(String[] args) {
        try {
            int archivosCopiados = backupIncremental(
                    "./documentos",
                    "./backup",
                    "./backup/.lastbackup"
            );
            System.out.println("Backup completado: " + archivosCopiados + " archivo(s)");
        } catch (IOException e) {
            System.err.println("Error durante el backup: " + e.getMessage());
        }
    }

    /**
     * Realiza backup incremental de una carpeta
     */
    public static int backupIncremental(String carpetaOrigen, String carpetaDestino, String archivoControl) throws IOException {
        System.out.println("Iniciando backup incremental...");

        long ultimoBackup = leerUltimoBackup(archivoControl);
        if (ultimoBackup == 0) {
            System.out.println("Último backup: nunca");
        } else {
            System.out.println("Último backup: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ultimoBackup)));
        }

        File origen = new File(carpetaOrigen);
        File destino = new File(carpetaDestino);

        if (!destino.exists()) {
            destino.mkdirs();
        }

        int archivosCopiados = copiarModificados(origen, destino, ultimoBackup);

        // Actualizar archivo de control con la fecha y hora actual
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoControl))) {
            long ahora = System.currentTimeMillis();
            bw.write(String.valueOf(ahora));
            System.out.println("Registro actualizado: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ahora)));
        }

        return archivosCopiados;
    }

    /**
     * Lee la fecha del último backup desde el archivo de control
     */
    private static long leerUltimoBackup(String archivoControl) throws IOException {
        File control = new File(archivoControl);
        if (!control.exists()) {
            return 0;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(control))) {
            String line = br.readLine();
            if (line != null && !line.isEmpty()) {
                try {
                    return Long.parseLong(line);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Copia un archivo de origen a destino
     */
    private static void copiarArchivo(File origen, File destino) throws IOException {
        Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Copia archivos modificados desde la carpeta origen a destino
     */
    private static int copiarModificados(File origen, File destino, long ultimoBackup) throws IOException {
        int contador = 0;

        if (!origen.isDirectory()) {
            return 0;
        }

        File[] archivos = origen.listFiles();
        if (archivos == null) return 0;

        for (File archivo : archivos) {
            File destinoArchivo = new File(destino, archivo.getName());

            if (archivo.isDirectory()) {
                if (!destinoArchivo.exists()) {
                    destinoArchivo.mkdirs();
                }
                contador += copiarModificados(archivo, destinoArchivo, ultimoBackup);
            } else {
                if (archivo.lastModified() > ultimoBackup) {
                    System.out.println("Copiando: " + archivo.getName());
                    copiarArchivo(archivo, destinoArchivo);
                    contador++;
                }
            }
        }

        return contador;
    }
}

