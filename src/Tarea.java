import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Tarea {
    void construyeTarea();

    String getTipo();

    String getTitulo();

    String getDescripcion();

    LocalDate getFechaCreacion();

    void setFechaCreacion(LocalDate fechaCreacion);

    LocalDateTime getFechaVencimiento();

    void setFechaVencimiento(LocalDateTime fechaVencimiento);

    boolean isCompletada();

    void setCompletada(boolean completada);

    // String getPrioridad();
    String getEtiquetas();
}
