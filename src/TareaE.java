import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TareaE {
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
    String getEtiquetas();
    void setEstado(TareaEstado estado); 
    TareaEstado getEstado(); 
    void iniciar(); 
    void completar(); 
    void volverPendiente(); 
}
