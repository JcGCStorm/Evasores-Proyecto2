public abstract class TareasDecorator implements Tarea{
   protected Tarea tarea;

   /**
     * Constructor de la clase TareasDecorator
     * 
     * @param tarea La tarea a decorar con etiquetas
     */
    public TareasDecorator(Tarea tarea) {
        this.tarea = tarea;

    }

    /**
     * Obtiene la descripción de la tarea decorada con etiquetas
     * 
     * @return La descripción de la tarea decorada con etiquetas
     */
    public String getEtiquetas() {
        return tarea.getEtiquetas();
    }

    /**
     * Obtiene el precio de la baguette decorada con ingredientes
     * 
     * @return El precio de la baguette decorada con ingredientes
     */
    public String getDescripcion() {
        return tarea.getDescripcion();
    }

}
