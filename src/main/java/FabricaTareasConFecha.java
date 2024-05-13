package main.java;
public class FabricaTareasConFecha extends FabricaTareas {

    /**
     * Este metodo heredado de la fabrica de tareas se encarga de crear
     * una tarea con fecha, simplemente regresa la tarea pero todo el trabajo
     * lo hace la clase TareaConFecha.
     * 
     * @return la tarea con fecha
     */
    @Override
    public Tarea construyeTarea() {
        return new TareaConFecha("con fecha", null, null, null, null, null, 0, new TareaPendiente());
    }
}
