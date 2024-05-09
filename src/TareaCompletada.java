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
}