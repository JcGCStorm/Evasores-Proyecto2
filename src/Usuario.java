import java.io.Serializable;

/**
 * Clase que representa un usuario.
 */
public class Usuario implements Serializable {
    // Atributos
    private String username;
    private String password;

    /*
     * Constructor de la clase Usuario
     * 
     * @param username El nombre de usuario
     * 
     * @Param password La contraseña del usuario
     */
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*
     * Obtiene el nombre de usuario
     * 
     * @return El nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /*
     * Obtiene la contraseña del usuario
     * 
     * @return La contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

}