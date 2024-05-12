import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAlmacen {
    private static final String archivoUsuarios = "usuarios.txt";

    public static void agregarUsuario(Usuario usuario) {
        List<Usuario> usuarios = obtenerUsuarios();
        usuarios.add(usuario);
        guardarUsuarios(usuarios);
    }

    public static Usuario obtenerUsuario(String username) {
        List<Usuario> usuarios = obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    public static boolean verificarCredenciales(String username, String password) {
        Usuario usuario = obtenerUsuario(username);
        return usuario != null && usuario.getPassword().equals(password);
    }

    @SuppressWarnings("unchecked")
    private static List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(archivoUsuarios))) {
            usuarios = (List<Usuario>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // El archivo aún no existe o está vacío, se maneja aquí
        }
        return usuarios;
    }

    private static void guardarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(archivoUsuarios))) {
            outputStream.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

}