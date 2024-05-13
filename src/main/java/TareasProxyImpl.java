package main.java;
/**
 * Clase que implementa la interfaz TareasProxy y se encarga de realizar las
 * operaciones de la aplicación para no comprometer la seguridad de los
 * usuarios.
 */
public class TareasProxyImpl implements TareasProxy {
    // Atributos
    private TareasControlador controlador;

    /**
     * Constructor de la clase TareasProxyImpl
     */
    public TareasProxyImpl() {
        this.controlador = new TareasControlador();
    }

    /**
     * Inicia sesión de un usuario en la aplicación
     * 
     * @param username El usuario del que se obtendrán las tareas
     * @param password La contraseña del usuario
     * @return
     */
    @Override
    public Usuario iniciarSesion(String username, String password) {
        if (UsuarioAlmacen.verificarCredenciales(username, password)) {
            return UsuarioAlmacen.obtenerUsuario(username);
        } else {
            return null;
        }
    }

    /**
     * Es el metodo que se encarga de modificar las tareas del usuario
     * lo llamamos con el proxy para no comprometer la seguridad de los usuarios
     * 
     * @param usuario
     */
    @Override
    public void modificarTarea(Usuario usuario) {
        try {
            controlador.modifica(usuario);
        } catch (Exception e) {
            System.err.println("Tarea no valida.");
        }
    }

}