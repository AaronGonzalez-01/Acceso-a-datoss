package ObligatorioSegundoTrimestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Usuario {
    private int id;
    private String nombre;
    private String email;
    private int edad;

    public Usuario(int id, String nombre, String email, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getEdad() { return edad; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setEdad(int edad) { this.edad = edad; }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Nombre: " + nombre +
                ", Email: " + email +
                ", Edad: " + edad;
    }
}

public class SistemaUsuariosJDBC {

    public static void crearTabla(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "edad INT NOT NULL" +
                ")";
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Tabla 'usuarios' creada");
        }
    }

    public static int insertarUsuario(Connection conn, String nombre, String email, int edad)
            throws SQLException {

        String sql = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setInt(3, edad);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Usuario insertado con ID: " + id);
                    return id;
                }
            }
        }
        return -1;
    }

    public static List<Usuario> buscarPorNombre(Connection conn, String nombre)
            throws SQLException {

        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email, edad FROM usuarios WHERE nombre LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nombre");
                    String email = rs.getString("email");
                    int edad = rs.getInt("edad");

                    lista.add(new Usuario(id, nom, email, edad));
                }
            }
        }

        return lista;
    }

    public static boolean actualizarEmail(Connection conn, int id, String nuevoEmail)
            throws SQLException {

        String sql = "UPDATE usuarios SET email = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoEmail);
            ps.setInt(2, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Email actualizado para usuario ID: " + id);
                return true;
            }
            return false;
        }
    }

    public static boolean eliminarUsuario(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario eliminado: ID " + id);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Pruebajdbc";
        String user = "root";
        String pass = "contraseña";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            crearTabla(conn);

            int id1 = insertarUsuario(conn, "Juan Pérez", "juan@email.com", 25);
            int id2 = insertarUsuario(conn, "María García", "maria@email.com", 30);

            List<Usuario> usuarios = buscarPorNombre(conn, "Juan");
            System.out.println("Usuarios encontrados:");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }

            actualizarEmail(conn, id1, "juan.nuevo@email.com");
            eliminarUsuario(conn, id2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
