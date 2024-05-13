package main.java;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Interfaz Tarea que define los métodos que deben implementar las tareas
 */
public interface Tarea {

    // Estos son todo lo que debe de tener una tarea
    void construyeTarea(Usuario usuario);

    String getTipo();

    String getTitulo();

    void setTitulo(String titulo);

    String getDescripcion();

    void setDescripcion(String descripcion);

    LocalDate getFechaCreacion();

    void setFechaCreacion(LocalDate fechaCreacion);

    LocalDateTime getFechaVencimiento();

    void setFechaVencimiento(LocalDateTime fechaVencimiento);

    int getPrioridad();

    void setPrioridad(int prioridad);

    // String getPrioridad();
    String getEtiquetas();

    void setEtiquetas(String etiquetas);

    void setEstado(TareaEstado estado);

    TareaEstado getEstado();

    void iniciar();

    void completar();

    void volverPendiente();

    String estadoToString(TareaEstado estado);

}
