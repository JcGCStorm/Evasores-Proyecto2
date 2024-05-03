public class FabricaTareaSimple extends FabricaTareas {

    /**
     * Este metodo heredado de la fabrica de tareas se encarga de crear
     * una tarea simple, simplemente regresa la tarea pero todo el trabajo
     * lo hace la clase TareaSimple.
     */
    @Override
    protected Tarea construyeTarea() {
     return new TareaSimple("simple",null, null, null, false);
    }
    }    
    
