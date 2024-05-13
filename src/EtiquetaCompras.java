import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa la etiqueta EtiquetaCompras para "decorar" (agregarle a)
 * una
 * tarea
 * Extiende de TareasDecorator
 */
public class EtiquetaCompras extends TareasDecorator {

    /**
     * Constructor de la clase EtiquetaCompras
     * 
     * @param tarea La tarea a decorar con la etiqueta compras
     */
    public EtiquetaCompras(Tarea tarea) {
        super(tarea);
    }

    /**
     * Obtiene la descripción de la tarea con la etiqueta compras
     * 
     * @return La descripción de la tarea con la etiqueta compras
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
        return tarea.getEtiquetas() + "#Compras-";
    }

    /*
     * Este metodo solamente lo sobreescribimos para que no de error en la clase
     * pero no hace nada, solo por que extiende a tareas decorador
     */
    @Override
    public void construyeTarea(Usuario usuario) {
        throw new UnsupportedOperationException("Unimplemented method 'construyeTarea'");
    }

    /*
     * Este metodo regresa el titulo de la tarea a la que agregamos etiquetas
     */
    @Override
    public String getTitulo() {
        return tarea.getTitulo();
    }

    /*
     * Este metodo regresa la fecha de creacion de la tarea
     * a la que le agregamos etiquetas
     * 
     * @return la fecha de creacion de la tarea
     */
    @Override
    public LocalDate getFechaCreacion() {
        return LocalDate.now();
    }

    /**
     * Obtiene la fecha de vencimiento de la tarea
     * 
     * @return la fecha de vencimiento de la tarea
     */
    @Override
    public LocalDateTime getFechaVencimiento() {
        return tarea.getFechaVencimiento();
    }

    /*
     * Este metodo regresa la prioridad de la tarea
     */
    @Override
    public int getPrioridad() {
        return tarea.getPrioridad();
    }

    /*
     * Este metodo regresa el tipo de la tarea
     */
    @Override
    public String getTipo() {
        return tarea.getTipo();
    }

    /*
     * Este metodo regresa la actualizacion la fecha de creacion de la tarea
     */
    @Override
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.tarea.setFechaCreacion(fechaCreacion);
    }

    /*
     * Este metodo regresa la actualizacion de la prioridad de la tarea
     */
    @Override
    public void setPrioridad(int prioridad) {
        this.tarea.setPrioridad(prioridad);
    }

    /*
     * Este metodo regresa la actualizacion de la fecha de vencimiento de la tarea
     */
    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.tarea.setFechaVencimiento(fechaVencimiento);
    }

    /*
     * Este metodo regresa la actualizacion del titulo de la tarea
     */
    @Override
    public void setTitulo(String titulo) {
        this.tarea.setTitulo(titulo);
    }

    /*
     * Este metodo regresa la actualizacion de la descripcion de la tarea
     */
    @Override
    public void setDescripcion(String descripcion) {
        this.tarea.setDescripcion(descripcion);
    }

    /*
     * Este metodo regresa la actualizacion de las etiquetas de la tarea
     */
    @Override
    public void setEtiquetas(String etiquetas) {
        tarea.setEtiquetas(etiquetas);
    }

    /*
     * Este metodo regresa la actualizacion del estado de la tarea
     */
    @Override
    public void setEstado(TareaEstado estado) {
        this.tarea.setEstado(estado);
    }

    /*
     * Este metodo regresa el estado de la tarea
     */
    @Override
    public TareaEstado getEstado() {
        return tarea.getEstado();
    }

    /*
     * Este metodo inicia una tarea pendiente, la vuelve en proceso
     */
    @Override
    public void iniciar() {
        this.tarea.iniciar();
    }

    /*
     * Este metodo vuelve a una tarea completada
     */
    @Override
    public void completar() {
        this.tarea.completar();
    }

    /*
     * Este metodo vuelve a una tarea pendiente
     */
    @Override
    public void volverPendiente() {
        this.tarea.volverPendiente();
    }

    /*
     * Este metodo regresa el estado de la tarea en forma de string
     */
    @Override
    public String estadoToString(TareaEstado estado) {
        return tarea.estadoToString(estado);
    }
}
