import java.util.Scanner;

public class MAIN {
    public static void main(String[] args) {

        TareasAlmacen.getTareas();
        TareasAlmacen.muestraTareas(); // este metodo posiblemente lo borre, ya veremos xd

        Scanner scanner = new Scanner(System.in);
        boolean opcionesBool = true;
        while (opcionesBool == true) {
            System.out.println("¿Qué desea hacer? \n 1. Ver Tareas. \n 2. Crear tareas." +
                    "\n 0. Salir.");
            String opciones = scanner.nextLine().trim();
            switch (opciones) {
                case "1":
                    TareasAlmacen.verTareas();
                    break;
                case "2":
                    TareasControlador.crearTarea();
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