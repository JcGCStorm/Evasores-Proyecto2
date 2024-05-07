import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase que representa el ingrediente Cebolla para "decorar" (agregarle a) una
 * baguette
 * Extiende de IngredientesDecorator
 */
public class EtiquetaEstudio extends TareasDecorator {

    /**
     * Constructor de la clase Cebolla
     * 
     * @param pan La baguette a decorar con cebolla
     */
    public EtiquetaEstudio(Tarea tarea) {
        super(tarea);
    }

    /**
     * Obtiene la descripción de la baguette con cebolla
     * 
     * @return La descripción de la baguette con cebolla
     */
    public String getDescripcion() {
        return tarea.getDescripcion() + ", Est";
    }

    /**
     * Obtiene el precio de la baguette con cebolla
     * 
     * @return El precio de la baguette con cebolla
     */
    public String getEtiquetas() {
        return tarea.getEtiquetas() + " Estudio,";
    }

    @Override
    public void construyeTarea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'construyeTarea'");
    }

    @Override
    public String getTitulo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitulo'");
    }

    @Override
    public LocalDate getFechaCreacion() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime getFechaVencimiento() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFechaVencimiento'");
    }

    @Override
    public boolean isCompletada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCompletada'");
    }

    @Override
    public String getTipo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTipo'");
    }

    @Override
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.tarea.setFechaCreacion(fechaCreacion);
    }

    @Override
    public void setCompletada(boolean completada) {
        this.tarea.setCompletada(completada);
    }

    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.tarea.setFechaVencimiento(fechaVencimiento);
    }
}
