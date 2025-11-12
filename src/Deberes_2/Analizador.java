package Obligatorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class EstadisticasTexto {

    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres;
    private String palabraMasLarga;

    public EstadisticasTexto() {}

    public EstadisticasTexto(int numeroLineas, int numeroPalabras, int numeroCaracteres, String palabraMasLarga) {
        this.numeroLineas = numeroLineas;
        this.numeroPalabras = numeroPalabras;
        this.numeroCaracteres = numeroCaracteres;
        this.palabraMasLarga = palabraMasLarga;
    }

    public int getNumeroLineas() {
        return numeroLineas;
    }

    public void setNumeroLineas(int numeroLineas) {
        this.numeroLineas = numeroLineas;
    }

    public int getNumeroPalabras() {
        return numeroPalabras;
    }

    public void setNumeroPalabras(int numeroPalabras) {
        this.numeroPalabras = numeroPalabras;
    }

    public int getNumeroCaracteres() {
        return numeroCaracteres;
    }

    public void setNumeroCaracteres(int numeroCaracteres) {
        this.numeroCaracteres = numeroCaracteres;
    }

    public String getPalabraMasLarga() {
        return palabraMasLarga;
    }

    public void setPalabraMasLarga(String palabraMasLarga) {
        this.palabraMasLarga = palabraMasLarga;
    }
}

public class Analizador {

    public static EstadisticasTexto analizarArchivo(String nombreArchivo) throws IOException {

        Path ruta = Paths.get(nombreArchivo);

        if (!Files.exists(ruta)) {
            throw new IOException("archivo no encontrado");
        }

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;
        String palabraMasLarga = "";

        try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = br.readLine()) != null) {

                lineas++;
                caracteres += linea.length();
                String[] partes = linea.trim().split("\\s+");

                if (!(partes.length == 1 && partes[0].isEmpty())) {
                    palabras += partes.length;

                    for (String p : partes) {
                        if (p.length() > palabraMasLarga.length()) {
                            palabraMasLarga = p;
                        }
                    }
                }
            }
        }

        return new EstadisticasTexto(lineas, palabras, caracteres, palabraMasLarga);
    }

    public static void guardarEstadisticas(EstadisticasTexto estadisticas, String archivoSalida)
            throws IOException {

        Path salida = Paths.get(archivoSalida);

        try (BufferedWriter bw = Files.newBufferedWriter(salida, StandardCharsets.UTF_8)) {

            bw.write("Estadisticas del archivo\n");
            bw.write("Lineas " + estadisticas.getNumeroLineas() + "\n");
            bw.write("Palabras " + estadisticas.getNumeroPalabras() + "\n");
            bw.write("Caracteres " + estadisticas.getNumeroCaracteres() + "\n");

            if (estadisticas.getPalabraMasLarga() != null) {
                bw.write("Palabra mas larga " + estadisticas.getPalabraMasLarga()
                        + " (" + estadisticas.getPalabraMasLarga().length() + ")\n");
            }
        }
    }

    public static void main(String[] args) {

        try {
            EstadisticasTexto est = analizarArchivo("archivo.txt");

            System.out.println("Estadisticas del archivo");
            System.out.println("Lineas " + est.getNumeroLineas());
            System.out.println("Palabras " + est.getNumeroPalabras());
            System.out.println("Caracteres " + est.getNumeroCaracteres());
            System.out.println("Palabra mas larga " + est.getPalabraMasLarga()
                    + " (" + est.getPalabraMasLarga().length() + ")");

            guardarEstadisticas(est, "salida.txt");

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
    }
}
