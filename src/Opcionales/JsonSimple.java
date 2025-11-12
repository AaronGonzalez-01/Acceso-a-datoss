package Opcionales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JsonSimple {

    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {

        Map<String, String> mapa = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(archivoJson), StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                // Omitir llaves
                if (linea.equals("{") || linea.equals("}")) continue;

                // Esperado: "key": "value"
                if (linea.contains(":")) {

                    // Eliminar coma al final si existe
                    if (linea.endsWith(",")) {
                        linea = linea.substring(0, linea.length() - 1);
                    }

                    String[] partes = linea.split(":");

                    if (partes.length == 2) {

                        String clave = partes[0].trim().replace("\"", "");
                        String valor = partes[1].trim().replace("\"", "");

                        mapa.put(clave, valor);
                    }
                }
            }
        }

        System.out.println("JSON leído: " + mapa.size() + " propiedades");
        return mapa;
    }

    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoJson), StandardCharsets.UTF_8)) {

            bw.write("{\n");

            int contador = 0;
            int tamanio = datos.size();

            for (Map.Entry<String, String> entry : datos.entrySet()) {
                contador++;

                String linea = "  \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"";

                // Agregar coma solo si no es la última
                if (contador < tamanio) {
                    linea += ",";
                }

                bw.write(linea + "\n");
            }

            bw.write("}\n");
        }

        System.out.println("JSON escrito: " + datos.size() + " propiedades en " + archivoJson);
    }

    public static void main(String[] args) throws IOException {

        Map<String, String> config = leerJsonSimple("config.json");

        System.out.println("Host: " + config.get("host"));

        config.put("version", "1.0");

        escribirJsonSimple(config, "config_nuevo.json");
    }
}

