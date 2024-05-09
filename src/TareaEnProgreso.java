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
}