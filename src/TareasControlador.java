import java.util.Scanner;
public class TareasControlador {
    

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
