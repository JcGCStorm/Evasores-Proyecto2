public interface TareaEstado{
    void iniciar(Tarea tarea); // transicion a en progreso
    void completar(Tarea tarea); // transicion a completada
    void volverPendiente(Tarea tarea); // transicion a pendiente
}