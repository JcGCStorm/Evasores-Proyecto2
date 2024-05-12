import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Tarea {
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
    public String toString();
}