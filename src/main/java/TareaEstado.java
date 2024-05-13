package main.java;
/**
 * Interfaz que define los métodos que deben implementar
 * las clases que representan los estados de una tarea
 */
public interface TareaEstado {
    void iniciar(Tarea tarea); // transicion a en progreso

    void completar(Tarea tarea); // transicion a completada

    void volverPendiente(Tarea tarea); // transicion a pendiente

    TareaEstado getEstado(String estado);

    String estadoToString(TareaEstado estado);
}