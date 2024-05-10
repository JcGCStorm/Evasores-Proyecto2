public class TareaPendiente implements TareaEstado {
    @Override
    public void iniciar(Tarea tarea) {
        System.out.println("La tarea ahora ya está en progreso :D");
        tarea.setEstado(new TareaEnProgreso());
    }

    @Override
    public void completar(Tarea tarea) {
        System.out.println("La tarea está pendiente y no puede ser completada directamente");
    }

    @Override
    public void volverPendiente(Tarea tarea) {
        System.out.println("La tarea ya está pendiente :/");
    }

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