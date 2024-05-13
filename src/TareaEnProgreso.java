/**
 * Clase que implementa la interfaz TareaEstado y define el comportamiento
 * de una tarea en progreso.
 */
public class TareaEnProgreso implements TareaEstado {

    /**
     * Metodo que inicia una tarea, la vuelve en progreso, en este caso no
     * pues ya lo está
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void iniciar(Tarea tarea) {
        System.out.println("La tarea ya está en progreso");
    }

    /**
     * Metodo que completa una tarea, la vuelve completada
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void completar(Tarea tarea) {
        System.out.println("La tarea ahora está completada");
        tarea.setEstado(new TareaCompletada());
    }

    /**
     * Metodo que vuelve una tarea a estado pendiente
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void volverPendiente(Tarea tarea) {
        System.out.println("La tarea ha vuelto a estado pendiente");
        tarea.setEstado(new TareaPendiente());
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
        } else if (estado.equalsIgnoreCase("Tarea Completada")) {
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