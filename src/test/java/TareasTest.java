package test.java;
import main.java.*; // Import the TareasAlmacen class

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TareasTest {
    @Test
    public void testGetTareasVacias() {
        Usuario usuario = new Usuario("Ztest", "password");
        List<Tarea> vacia = new ArrayList<>();
        assertEquals(vacia, TareasAlmacen.getTareas(usuario));
    }

   // @Test
public void testRemoveTarea() {
   // TareasAlmacen almacen = new TareasAlmacen();
   // Tarea tarea = new Tarea();
//    almacen.addTarea(tarea);
  //  assertEquals(1, almacen.getTareas().size());

    //almacen.removeTarea(tarea);
  //  assertTrue(almacen.getTareas().isEmpty());
  }
}