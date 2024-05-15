/**
 * Clase que implementa la interfaz TareaEstado y define el comportamiento de
 * una
 * tarea en estado pendiente.
 */
public class TareaPendiente implements TareaEstado {

    /**
     * Metodo que inicia una tarea pendiente, la vuelve en progreso
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void iniciar(Tarea tarea) {
        System.out.println("La tarea ahora ya está en progreso :D");
        tarea.setEstado(new TareaEnProgreso());
    }

    /**
     * Metodo que completa una tarea, la vuelve completada, en este caso no pues
     * está pendiente y no puede ser completada directamente
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void completar(Tarea tarea) {
        System.out.println("La tarea está pendiente y no puede ser completada directamente");
    }

    /**
     * Metodo que vuelve una tarea a estado pendiente, en este caso no pues ya lo
     * está
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void volverPendiente(Tarea tarea) {
        System.out.println("La tarea ya está pendiente :/");
    }

    /**
     * Metodo que regresa el estado de la tarea según el string que se le pase
     * Este metodo solo lo usamos para recuperar las tareas del txt, pero no tiene
     * que ver con el patrón State
     * 
     * @param estado string que representa el estado de la tarea
     * 
     * @return estado de la tarea
     */
    @Override
    public TareaEstado getEstado(String estado) {
        TareaEstado estadoTarea = null;
        if (estado.equalsIgnoreCase("Tarea Pendiente")) {
            estadoTarea = new TareaPendiente();
        } else if (estado.equalsIgnoreCase("Completada")) {
            estadoTarea = new TareaCompletada();
        } else if (estado.equalsIgnoreCase("Tarea En Progreso")) {
            estadoTarea = new TareaEnProgreso();
        }
        return estadoTarea;
    }

    /**
     * Metodo que regresa el estado de la tarea en forma de string, de igual forma
     * solo
     * lo usamos para guardar las tareas en el txt, pero no tiene que ver con el
     * patrón State
     * 
     * @param estado estado de la tarea
     * 
     * @return estado de la tarea en forma de string
     */
    @Override
    public String estadoToString(TareaEstado estado) {
        String estadoString = "";
        if (estado instanceof TareaPendiente) {
            estadoString = "Tarea Pendiente";
        } else if (estado instanceof TareaCompletada) {
            estadoString = "Tarea Completada";
        } else if (estado instanceof TareaEnProgreso) {
            estadoString = "Tarea En Progreso";
        }
        return estadoString;
    }
}