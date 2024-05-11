import java.util.HashMap;
import java.util.Map;

public class GestorUsuarios {
    private Map<String, String> usuarios = new HashMap<>();

    public void registrarUsuario(String username, String password) {
        usuarios.put(username, password);
    }

    public boolean verificarCredenciales(String username, String password) {
        return usuarios.containsKey(username) && usuarios.get(username).equals(password);
    }
}