package main.java;
import java.util.List;

public class AcomodaTareaPorPrioridad implements AcomodaTareaStrategy {

    // Metodo para ordenar tareas por prioridad

    @Override
    public void acomodaTarea(Tarea tarea) {

        List<Tarea> tare = TareasAlmacen.getTareas(null);

        tare.sort((t1, t2) -> Integer.compare(t2.getPrioridad(), t1.getPrioridad()));
    }
}