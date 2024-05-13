package main.java;
/**
 * Clase que representa el estado de una tarea completada, implementa la
 * interfaz TareaEstado
 */
public class TareaCompletada implements TareaEstado {

    /**
     * Metodo que inicia una tarea, la vuelve en progreso, en este caso no
     * pues ya está completada
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void iniciar(Tarea tarea) {
        System.out.println("La tarea está completada y no puede ser iniciada.");
    }

    /**
     * Metodo que completa una tarea, la vuelve completada, en este caso no
     * pues ya lo está
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void completar(Tarea tarea) {
        System.out.println("La tarea ya está completada.");
    }

    /**
     * Metodo que vuelve una tarea a estado pendiente, en este caso no pues
     * ya está completada
     * 
     * @param tarea tarea a la que se le cambiara el estado
     */
    @Override
    public void volverPendiente(Tarea tarea) {
        System.out.println("La tarea no puede volver a estado pendiente porque está completada.");
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