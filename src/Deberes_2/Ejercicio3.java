package Obligatorio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum NivelLog {
    INFO, WARNING, ERROR
}

class SistemaLog {

    private String archivoLog;
    private long tamanoMaximo;
    private int numeroRotacion;

    public SistemaLog(String archivoLog, long tamanoMaximo) {
        if (archivoLog == null || archivoLog.isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser vacío");
        }
        if (tamanoMaximo <= 0) {
            throw new IllegalArgumentException("El tamaño máximo debe ser mayor que 0");
        }

        this.archivoLog = archivoLog;
        this.tamanoMaximo = tamanoMaximo;
        this.numeroRotacion = 1;
    }

    public void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        // Rotar si está lleno
        boolean seRotó = rotarSiNecesario();
        if (seRotó) {
            System.out.println("ROTACIÓN: " + archivoLog + " renombrado a " + archivoLog + "." + (numeroRotacion - 1));
        }

        // Formato de fecha ISO-8601
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String linea = "[" + timestamp + "] [" + nivel + "] " + mensaje;

        // Escribir en el archivo
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoLog), StandardCharsets.UTF_8,
                Files.exists(Paths.get(archivoLog)) ?
                        java.nio.file.StandardOpenOption.APPEND :
                        java.nio.file.StandardOpenOption.CREATE)) {

            bw.write(linea);
            bw.newLine();
            bw.flush();
        }

        System.out.println("Log escrito: " + mensaje);
    }

    private boolean rotarSiNecesario() throws IOException {
        Path ruta = Paths.get(archivoLog);

        long tamanoActual = obtenerTamanoLog();

        if (tamanoActual >= tamanoMaximo) {
            // Rotar archivo
            Path nuevoNombre = Paths.get(archivoLog + "." + numeroRotacion);
            numeroRotacion++;

            Files.move(ruta, nuevoNombre); // renombrar
            return true;
        }

        return false;
    }

    private long obtenerTamanoLog() {
        Path ruta = Paths.get(archivoLog);

        if (Files.exists(ruta)) {
            try {
                return Files.size(ruta);
            } catch (IOException e) {
                return 0;
            }
        }
        return 0;
    }
}
