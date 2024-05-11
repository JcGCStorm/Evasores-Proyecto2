import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaTareas {

    /**
     * Este metodo imprime las tareas del archivo txt, primero lee el archivo
     * linea por linea y las va imprimiendo, si no hay tareas, imprime un mensaje
     * diciendo que no hay tareas.
     */
    public static void verTareas() {
        String nombreArchivo = "tareas.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Aún no tienes tareas.");
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Del arrayList de tareas, simplemente lo recorre y va mostrando
     * los atributos de cada tarea.
     */
    public static void muestraTareas() {
        List<Tarea> tareas = TareasAlmacen.getTareas();
        if (tareas == null || tareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar");
            return;
        }

        // Usar un índice explícito
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);

            System.out.println("Índice: " + i); // Imprimir el índice del elemento actual
            System.out.println("Titulo: " + tarea.getTitulo());
            System.out.println("Descripcion: " + tarea.getDescripcion());
            System.out.println("Etiquetas: " + tarea.getEtiquetas());
            DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaCreacion = tarea.getFechaCreacion().format(formateadorCreacion);
            System.out.println("Fecha de Creacion: " + fechaCreacion);

            if (tarea instanceof TareaConFecha) {
                DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                        .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
                LocalDateTime fechaHora = tarea.getFechaVencimiento();
                String fechaHoraString = fechaHora.format(formateadorVencimiento);
                System.out.println("Fecha de Vencimiento: " + fechaHoraString);
            }

            System.out.println("Completada: " + (tarea.isCompletada() ? "Sí" : "No"));

            // Imprimir el nombre del estado3 actual
            TareaEstado estado = tarea.getEstado();
            System.out.println("Estado: " + tarea.estadoToString(estado));

            System.out.println("\n");
        }
    }

}
