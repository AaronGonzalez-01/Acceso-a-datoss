package ObligatorioSegundoTrimestre;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ConfiguradorApp {

    public static Properties cargarConfiguracion(String archivo) throws IOException {
        Properties props = new Properties();
        File f = new File(archivo);

        if (f.exists()) {

            try (FileInputStream fis = new FileInputStream(f)) {
                props.load(fis);
                System.out.println("Configuración cargada desde " + archivo);
            }
        } else {
            props.setProperty("app.nombre", "MiAplicacion");
            props.setProperty("app.version", "1.0");
            props.setProperty("db.host", "localhost");
            props.setProperty("db.port", "3306");
            System.out.println("Archivo no existe, creando configuración por defecto");

            try (FileOutputStream fos = new FileOutputStream(f)) {
                props.store(fos, "Configuración por defecto");
                System.out.println("Configuración por defecto guardada en " + archivo);
            }
        }

        return props;
    }

    public static String getPropStr(Properties props, String clave, String valorDefecto) {
        return props.getProperty(clave, valorDefecto);
    }

    public static int getPropInt(Properties props, String clave, int valorDefecto) {
        String valor = props.getProperty(clave);
        if (valor == null) {
            return valorDefecto;
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return valorDefecto;
        }
    }

    public static void guardarConfiguracion(String archivo, Properties props) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            props.store(fos, "Configuración actualizada");
            System.out.println("Configuración guardada en " + archivo);
        }
    }

    public static void main(String[] args) {
        String ruta = "C:\\Users\\Aaron\\Documents\\Programacion\\config.properties";

        try {
            Properties props = cargarConfiguracion(ruta);

            String nombreApp = getPropStr(props, "app.nombre", "AppSinNombre");
            String version = getPropStr(props, "app.version", "0.0");
            String host = getPropStr(props, "db.host", "127.0.0.1");
            int puerto = getPropInt(props, "db.port", 3306);

            System.out.println("Nombre app: " + nombreApp);
            System.out.println("Versión: " + version);
            System.out.println("DB host: " + host);
            System.out.println("DB port: " + puerto);

            props.setProperty("app.version", "2.0");
            guardarConfiguracion(ruta, props);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

