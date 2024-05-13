import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VistaTareas {

    /**
     * Este metodo nos ayuda a obtener el nombre del archivo de tareas de un usuario
     * en específico.
     * 
     * @param usuario el usuario del que queremos obtener el archivo de tareas.
     * @return el nombre del archivo de tareas del usuario.
     */
    private static String obtenerArchivoTareasUsuario(Usuario usuario) {
        return usuario.getUsername() + "_tareas.txt";
    }

    /**
     * Este metodo imprime las tareas del archivo txt, primero lee el archivo
     * linea por linea y las va imprimiendo, si no hay tareas, imprime un mensaje
     * diciendo que no hay tareas.
     * 
     * @param usuario el usuario del que queremos ver las tareas.
     */

    public static void verTareas(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        String nombreArchivo = obtenerArchivoTareasUsuario(usuario);

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

        boolean tareaProximaVencer = false;
        boolean tareaVencida = false;
        LocalDateTime ahora = LocalDateTime.now();
        for (Tarea tarea : tareas) {
            if (tarea instanceof TareaConFecha) {
                TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                LocalDateTime fechaVencimiento = tareaConFecha.getFechaVencimiento();
                long horasHastaVencimiento = ChronoUnit.HOURS.between(ahora, fechaVencimiento);
                if (horasHastaVencimiento <= 0) { // Si las horas son <= 0, la tarea está vencida
                    tareaVencida = true;
                } else if (horasHastaVencimiento <= 72) {
                    tareaProximaVencer = true;
                }
            }
        }

        if (tareaProximaVencer) {
            System.out
                    .println("¡URGENTE, mi estimado procrastinador! Tienes tarea/s para los próximos 3 días o menos.");

            System.out.println("Tareas próximas a vencer:");
            for (Tarea tarea : tareas) {
                if (tarea instanceof TareaConFecha) {
                    TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                    LocalDateTime fechaVencimiento = tareaConFecha.getFechaVencimiento();
                    long horasHastaVencimiento = ChronoUnit.HOURS.between(ahora, fechaVencimiento);

                    if (horasHastaVencimiento <= 72) {
                        System.out.println("- " + tareaConFecha.getTitulo());
                    }
                }
            }
        }

        if (tareaVencida) {
            System.out.println("¡OH NO! Tienes tareas vencidas ): irresponsable:");

            for (Tarea tarea : tareas) {
                if (tarea instanceof TareaConFecha) {
                    TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                    LocalDateTime fechaVencimiento = tareaConFecha.getFechaVencimiento();
                    long horasDesdeVencimiento = ChronoUnit.HOURS.between(fechaVencimiento, ahora);

                    if (horasDesdeVencimiento > 0) {
                        System.out.println("- " + tareaConFecha.getTitulo());
                    }
                }
            }
        }
    }

    /**
     * Del arrayList de tareas, simplemente lo recorre y va mostrando
     * los atributos de cada tarea.
     * 
     * @param usuario
     */
    public static void muestraTareas(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
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

            System.out.println("Prioridad: " + tarea.getPrioridad());

            // Imprimir el nombre del estado3 actual
            TareaEstado estado = tarea.getEstado();
            System.out.println("Estado: " + tarea.estadoToString(estado));

            System.out.println("\n");
        }
    }
}