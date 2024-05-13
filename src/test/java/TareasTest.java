package src.test.java;
import src.TareasAlmacen; // Import the TareasAlmacen class
import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TareasTest {
    @Test
    public void testGetTareas() {
      TareasAlmacen almacen = new TareasAlmacen();

        // Asegúrate de que la lista de tareas esté inicialmente vacía
        assertTrue(almacen.getTareas().isEmpty());

        // Agrega una tarea y verifica que se agregó correctamente
        Tarea tarea = new Tarea();
        almacen.addTarea(tarea);
       assertEquals(1, almacen.getTareas().size());
       assertTrue(almacen.getTareas().contains(tarea));
    }
}