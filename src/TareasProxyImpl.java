public class TareasProxyImpl implements TareasProxy {
    private TareasControlador controlador;

    public TareasProxyImpl() {
        this.controlador = new TareasControlador();
    }

    @Override
    public Usuario iniciarSesion(String username, String password) {
        if (UsuarioAlmacen.verificarCredenciales(username, password)) {
            return UsuarioAlmacen.obtenerUsuario(username);
        } else {
            return null;
        }
    }

    @Override
    public void modificarTarea(Usuario usuario) {
        try {
            controlador.modifica(usuario);
        } catch (Exception e) {
            System.err.println("Tarea no valida.");
        }
    }

}