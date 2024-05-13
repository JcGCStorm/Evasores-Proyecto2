package main.java;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa la etiqueta EtiquetaOtro para "decorar" (agregarle a)
 * una
 * tarea
 * Extiende de TareasDecorator
 */
public class EtiquetaOtro extends TareasDecorator {

    /**
     * Constructor de la clase EtiquetaOtro
     * 
     * @param tarea La tarea a decorar con la etiqueta Otro
     */
    public EtiquetaOtro(Tarea tarea) {
        super(tarea);
    }

    /**
     * Obtiene la descripción de la tarea con la etiqueta Otro
     * 
     * @return La descripción de la tarea con la etiqueta Otro
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
        return tarea.getEtiquetas() + "#Otro-";
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

    /**
     * Obtiene la fecha de creación de la tarea
     * 
     * @return la fecha de creación de la tarea
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
