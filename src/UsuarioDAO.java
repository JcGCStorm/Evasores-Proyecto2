import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
            String sql = "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
            conexion.createStatement().execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, usuario.getUsername());
            statement.setString(2, usuario.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public boolean autenticarUsuario(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario obtenerUsuario(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                return new Usuario(username, password);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }
}
