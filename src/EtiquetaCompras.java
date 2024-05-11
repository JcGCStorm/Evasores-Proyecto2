import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa el ingrediente Cebolla para "decorar" (agregarle a) una
 * baguette
 * Extiende de IngredientesDecorator
 */
public class EtiquetaCompras extends TareasDecorator {

    /**
     * Constructor de la clase Cebolla
     * 
     * @param pan La baguette a decorar con cebolla
     */
    public EtiquetaCompras(Tarea tarea) {
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
     * Obtiene el precio de la baguette con cebolla
     * 
     * @return El precio de la baguette con cebolla
     */
    @Override
    public String getEtiquetas() {
        return tarea.getEtiquetas() + "#Compras- ";
    }

    @Override
    public void construyeTarea() {
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
