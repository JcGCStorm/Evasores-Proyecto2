import java.util.List;
import java.util.Scanner;

public class MAIN {
    public static void main(String[] args) {

        System.out.println("Bienvenido a tu lista de tareas");
        TareasAlmacen.getTareas();
        List<Tarea> lol = TareasAlmacen.obtenArreglo();
        // lol.get(0).; // q es esto Experimentos xd

        System.out.println();
        // TareasAlmacen.muestraTareas();
        // este metodo posiblemente lo borre, ya
        // veremos xd

        Scanner scanner = new Scanner(System.in);
        boolean opcionesBool = true;
        while (opcionesBool == true) {
            System.out.println("\n¿Qué desea hacer? \n 1. Ver Tareas. \n 2. Crear tareas." +
                    "\n 3. Modificar tareas" + "\n 0. Salir.");
            String opciones = scanner.nextLine().trim();
            switch (opciones) {
                case "1":
                    VistaTareas.verTareas();
                    break;
                case "2":
                    TareasControlador.crearTarea();
                    break;
                case "3":
                    TareasControlador modificaTarea = new TareasControlador();
                    modificaTarea.modifica();
                    break;
                case "0":
                    opcionesBool = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        scanner.close();
    }
}