import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa la etiqueta EtiquetaHogar para "decorar" (agregarle a)
 * una tarea
 * Extiende de TareasDecorator
 */
public class EtiquetaHogar extends TareasDecorator {

    /**
     * Constructor de la clase EtiquetaHogar
     * 
     * @param tarea La tarea a decorar con la etiqueta Hogar
     */
    public EtiquetaHogar(Tarea tarea) {
        super(tarea);
    }

    /**
     * Obtiene la descripción de la tarea con la etiqueta Hogar
     * 
     * @return La descripción de la tarea con la etiqueta hogar
     */
    @Override
    public String getDescripcion() {
        return tarea.getDescripcion() + ", Est";
    }

    /**
     * Obtiene las etiquetas de la tarea
     * 
     * @return las etiquetas de la tarea
     */
    @Override
    public String getEtiquetas() {
        return tarea.getEtiquetas() + "#Hogar-";
    }

    /*
     * Este metodo solamente lo sobreescribimos para que no de error en la clase
     * pero no hace nada, solo por que extiende a tareas decorador
     */
    @Override
    public void construyeTarea(Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'construyeTarea'");
    }

    @Override
    public String getTitulo() {
        return tarea.getTitulo();
    }

    @Override
    public LocalDate getFechaCreacion() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime getFechaVencimiento() {
        return tarea.getFechaVencimiento();
    }

    @Override
    public int getPrioridad() {
        return tarea.getPrioridad();
    }

    @Override
    public String getTipo() {
        return tarea.getTipo();
    }

    @Override
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.tarea.setFechaCreacion(fechaCreacion);
    }

    @Override
    public void setPrioridad(int prioridad) {
        this.tarea.setPrioridad(prioridad);
    }

    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.tarea.setFechaVencimiento(fechaVencimiento);
    }

    @Override
    public void setTitulo(String titulo) {
        this.tarea.setTitulo(titulo);
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.tarea.setDescripcion(descripcion);
    }

    @Override
    public void setEtiquetas(String etiquetas) {
        tarea.setEtiquetas(etiquetas);
    }

    @Override
    public void setEstado(TareaEstado estado) {
        this.tarea.setEstado(estado);
    }

    @Override
    public TareaEstado getEstado() {
        return tarea.getEstado();
    }

    @Override
    public void iniciar() {
        this.tarea.iniciar();
    }

    @Override
    public void completar() {
        this.tarea.completar();
    }

    @Override
    public void volverPendiente() {
        this.tarea.volverPendiente();
    }

    @Override
    public String estadoToString(TareaEstado estado) {
        return tarea.estadoToString(estado);
    }
}
