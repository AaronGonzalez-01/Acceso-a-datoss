package Opcionales;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {

    private static Map<String, String> variables = new HashMap<>();

    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {

        variables.clear();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(archivoEnv), StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                // Ignorar comentarios y líneas vacías
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                // Formato esperado: CLAVE=VALOR
                int idx = linea.indexOf('=');

                if (idx == -1) {
                    // Línea inválida -> ignorar
                    continue;
                }

                String clave = linea.substring(0, idx).trim();
                String valor = linea.substring(idx + 1).trim();

                variables.put(clave, valor);
            }
        }

        System.out.println("Cargadas " + variables.size() + " variables desde " + archivoEnv);
        return new HashMap<>(variables); // devolver copia
    }

    public static String getEnv(String clave, String valorPorDefecto) {
        return variables.getOrDefault(clave, valorPorDefecto);
    }

    public static void main(String[] args) throws IOException {

        Map<String, String> env = cargarEnv(".env");

        System.out.println("Base de datos: " + env.get("DB_HOST") + ":" + env.get("DB_PORT"));

        String debug = getEnv("DEBUG", "false");
        System.out.println("Debug mode: " + debug);
    }
}

