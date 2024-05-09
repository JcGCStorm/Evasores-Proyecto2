public class TareaCompletada implements TareaEstado {
    @Override
    public void iniciar(Tarea tarea) {
        System.out.println("La tarea está completada y no puede ser iniciada.");
    }

    @Override
    public void completar(Tarea tarea) {
        System.out.println("La tarea ya está completada.");
    }

    @Override
    public void volverPendiente(Tarea tarea) {
        System.out.println("La tarea no puede volver a estado pendiente porque está completada.");
    }

    @Override
    public TareaEstado getEstado(String estado) {
        TareaEstado estadoTarea = null;
        if (estado.equalsIgnoreCase("TareaPendiente")) {
            estadoTarea = new TareaPendiente();
        } else if (estado.equalsIgnoreCase("TareaCompletada")) {
            estadoTarea = new TareaCompletada();
        } else if (estado.equalsIgnoreCase("TareaEnProgreso")) {
            estadoTarea = new TareaEnProgreso();
        }
        return estadoTarea;
    }
}