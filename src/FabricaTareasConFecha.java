public class FabricaTareasConFecha extends FabricaTareas {

    /**
     * Este metodo heredado de la fabrica de tareas se encarga de crear
     * una tarea con fecha, simplemente regresa la tarea pero todo el trabajo
     * lo hace la clase TareaConFecha.
     */
    @Override
    protected Tarea construyeTarea(Usuario usuario) {
        TareaConFecha tareaConFecha = new TareaConFecha("con fecha", null, null, null, null, null, 0, new TareaPendiente());
        tareaConFecha.construyeTarea(usuario);
        return tareaConFecha;
    }


}
