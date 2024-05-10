import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Tarea {
    void construyeTarea();

    String getTipo();

    String getTitulo();

    void setTitulo(String titulo);

    String getDescripcion();

    void setDescripcion(String descripcion);

    LocalDate getFechaCreacion();

    void setFechaCreacion(LocalDate fechaCreacion);

    LocalDateTime getFechaVencimiento();

    void setFechaVencimiento(LocalDateTime fechaVencimiento);

    boolean isCompletada();

    void setCompletada(boolean completada);

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
