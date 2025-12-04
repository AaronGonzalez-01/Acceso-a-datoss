package ObligatorioSegundoTrimestre;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Producto implements Serializable {
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Nombre: " + nombre +
                ", Precio: " + precio +
                ", Stock: " + stock;
    }
}

public class GestorInventarioBinario {

    public static void escribirProducto(String archivo, Producto producto) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(archivo))) {
            dos.writeInt(producto.getId());
            dos.writeUTF(producto.getNombre());
            dos.writeDouble(producto.getPrecio());
            dos.writeInt(producto.getStock());
        }
        System.out.println("Producto guardado: " + producto.getNombre());
    }

    public static void agregarProducto(String archivo, Producto producto) throws IOException {
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(archivo, true);
            dos = new DataOutputStream(fos);

            dos.writeInt(producto.getId());
            dos.writeUTF(producto.getNombre());
            dos.writeDouble(producto.getPrecio());
            dos.writeInt(producto.getStock());

            System.out.println("Producto añadido: " + producto.getNombre());
        } finally {
            if (dos != null) {
                dos.close();
            } else if (fos != null) {
                fos.close();
            }
        }
    }


    public static List<Producto> leerProductos(String archivo) throws IOException {
        List<Producto> productos = new ArrayList<>();

        File f = new File(archivo);
        if (!f.exists() || f.length() == 0) {
            return productos;
        }

        try (DataInputStream dis = new DataInputStream(
                new FileInputStream(archivo))) {
            while (true) {
                try {
                    int id = dis.readInt();
                    String nombre = dis.readUTF();
                    double precio = dis.readDouble();
                    int stock = dis.readInt();

                    Producto p = new Producto(id, nombre, precio, stock);
                    productos.add(p);
                } catch (EOFException e) {
                    break;
                }
            }
        }

        return productos;
    }

    public static void main(String[] args) {
        String ruta = "C:\\Users\\Aaron\\Documents\\Programacion";

        try {
            Producto p1 = new Producto(1, "Laptop", 999.99, 10);
            Producto p2 = new Producto(2, "Mouse", 19.99, 50);

            escribirProducto(ruta, p1);
            agregarProducto(ruta, p2);

            List<Producto> productos = leerProductos(ruta);
            for (Producto p : productos) {
                System.out.println(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

