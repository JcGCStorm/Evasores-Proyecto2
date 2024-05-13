import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa la etiqueta EtiquetaSalud para "decorar" (agregarle a)
 * una tarea
 * Extiende de TareasDecorator
 */
public class EtiquetaSalud extends TareasDecorator {

    /**
     * Constructor de la clase EtiquetaSalud
     * 
     * @param tarea La tarea a decorar con la etiqueta Salud
     */
    public EtiquetaSalud(Tarea tarea) {
        super(tarea);
    }

    /**
     * Obtiene la descripción de la baguette con cebolla
     * 
     * @return La descripción de la baguette con cebolla
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
        return tarea.getEtiquetas() + "#Salud-";
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
