public interface TareasProxy {
    Usuario iniciarSesion(String username, String password);

    void modificarTarea(Usuario usuario);

}