import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class VistaTareas {

    /**
     * Este metodo imprime las tareas del archivo txt, primero lee el archivo
     * linea por linea y las va imprimiendo, si no hay tareas, imprime un mensaje
     * diciendo que no hay tareas.
     */
    public static void verTareas() {
        String nombreArchivo = "tareas.txt";

        try {
            String linea;

            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Aún no tienes tareas.");
        }
    }

    /**
     * Del arrayList de tareas, simplemente lo recorre y va mostrando
     * los atributos de cada tarea.
     */
    public static void muestraTareas() {
        List<Tarea> tareas = TareasAlmacen.obtenArreglo();
        for (Tarea tarea : tareas) {
            System.out.println(tareas.indexOf(tarea));
            System.out.println("Titulo: " + tarea.getTitulo());
            System.out.println("Descripcion: " + tarea.getDescripcion());
            System.out.println("Etiquetas: " + tarea.getEtiquetas());
            System.out.println("Fecha de Creacion: " + tarea.getFechaCreacion());
            if (tarea instanceof TareaConFecha) {
                System.out.println("Fecha de Vencimiento: " + ((TareaConFecha) tarea).getFechaVencimiento());
            }
            System.out.println("Completada: " + tarea.isCompletada());
            System.out.println("\n");
        }
    }

}
