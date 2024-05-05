import java.util.Scanner;
public class TareasControlador {
    
    /**
     * Este metodo es el que se encarga de crear las tareas, primero pregunta si
     * se desea agregar una tarea, si la respuesta es no, se sale del ciclo, si
     * la respuesta es si, pregunta que tipo de tarea se desea agregar, si la
     * respuesta es simple, se crea una tarea simple mandando a llamar a la fabrica
     * de tareas simples, si la respuesta es con fecha se crea una tarea con fecha
     * mandando a llamar a la fabrica de tareas con fecha, si la respuesta no es 
     * ninguna de las anteriores imprime un mensaje diciendo que la opción no es válida.
     */
    public static void crearTarea(){
        Scanner scanner = new Scanner(System.in);
        FabricaTareas tareaSimple = new FabricaTareaSimple();
        FabricaTareas tareaConFecha = new FabricaTareasConFecha();
        boolean agregarTareas = true;
        while (agregarTareas == true) {
            System.out.println("¿Desea agregar una tarea? (si/no)");
            String decision = scanner.nextLine().trim();
            if (decision.equalsIgnoreCase("no")) {
                agregarTareas = false;
            } else if (decision.equalsIgnoreCase("si")) {
                System.out.println("¿Qué tipo de tarea desea agregar? (simple/con fecha)");
                String tipoTarea = scanner.nextLine().trim();
                if (tipoTarea.equalsIgnoreCase("con fecha")) {
                    tareaConFecha.crear();
                } else if (tipoTarea.equalsIgnoreCase("simple")) {
                    tareaSimple.crear();
                } else {
                    System.out.println("Opción no válida");
                }
      //          TareasAlmacen.muestraTareas(); este metodo sirve para ver todas las tareas conforme las agregamos
                agregarTareas = true;
            }          
            else {
                System.out.println("Opción no válida");
            }
        }
    }
}
