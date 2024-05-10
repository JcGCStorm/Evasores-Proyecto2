public class TareaEnProgreso implements TareaEstado {
    @Override
    public void iniciar(Tarea tarea) {
        System.out.println("La tarea ya está en progreso");
    }

    @Override
    public void completar(Tarea tarea) {
        System.out.println("La tarea ahora está completada");
        tarea.setEstado(new TareaCompletada());
    }

    @Override
    public void volverPendiente(Tarea tarea) {
        System.out.println("La tarea ha vuelto a estado pendiente");
        tarea.setEstado(new TareaPendiente());
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