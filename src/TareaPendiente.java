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
}