import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAlmacen {
    // El archivo donde se guardan los usuarios
    private static final String archivoUsuarios = "usuarios.txt";

    /**
     * Agrega un usuario a la lista de usuarios
     * 
     * @param usuario El usuario a agregar
     */
    public static void agregarUsuario(Usuario usuario) {
        List<Usuario> usuarios = obtenerUsuarios();
        usuarios.add(usuario);
        guardarUsuarios(usuarios);
    }

    /**
     * Obtiene un usuario de la lista a partir de su nombre de usuario
     * 
     * @param username El nombre de usuario del usuario a obtener
     * @return El usuario o null si no existe
     */
    public static Usuario obtenerUsuario(String username) {
        List<Usuario> usuarios = obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Verifica si un usuario existe y si la contraseña es correcta
     * 
     * @param username El nombre de usuario
     * @param password La contraseña
     * @return true si las credenciales son correctas, false en otro caso
     */
    public static boolean verificarCredenciales(String username, String password) {
        Usuario usuario = obtenerUsuario(username);
        return usuario != null && usuario.getPassword().equals(password);
    }

    /**
     * Obtiene la lista de usuarios
     * 
     * @return La lista de usuarios
     */
    @SuppressWarnings("unchecked")
    private static List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(archivoUsuarios))) {
            usuarios = (List<Usuario>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            // El archivo aún no existe, se puede crear uno vacío si se desea
        } catch (IOException | ClassNotFoundException e) {
            // Otros errores de lectura o deserialización, se manejan aquí
            System.err.println("Error al leer usuarios: " + e.getMessage());
            throw new RuntimeException("Error al leer usuarios", e);
        }

        return usuarios;
    }

    /**
     * Guarda usuarios en el txt de usuarios.
     * 
     * @param usuarios La lista de usuarios a guardar
     */
    private static void guardarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(archivoUsuarios))) {
            outputStream.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

}