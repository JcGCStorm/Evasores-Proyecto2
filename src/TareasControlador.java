import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TareasControlador {
    Scanner scanner = new Scanner(System.in);

    /**
     * Este metodo es el que se encarga de crear las tareas, primero pregunta si
     * se desea agregar una tarea, si la respuesta es no, se sale del ciclo, si
     * la respuesta es si, pregunta que tipo de tarea se desea agregar, si la
     * respuesta es simple, se crea una tarea simple mandando a llamar a la fabrica
     * de tareas simples, si la respuesta es con fecha se crea una tarea con fecha
     * mandando a llamar a la fabrica de tareas con fecha, si la respuesta no es
     * ninguna de las anteriores imprime un mensaje diciendo que la opción no es
     * válida.
     */

    public static void crearTarea() {
    Scanner scanner = new Scanner(System.in);
    FabricaTareas tareaSimple = new FabricaTareaSimple();
    FabricaTareas tareaConFecha = new FabricaTareasConFecha();
    boolean agregarTareas = true;

    while (agregarTareas) {
        System.out.println("¿Desea agregar una tarea nueva? (Si/No)");
        String decision = scanner.nextLine().trim();

        if (decision.equalsIgnoreCase("no")) {
            agregarTareas = false;
        } else if (decision.equalsIgnoreCase("si")) {
            System.out.println("¿Qué tipo de tarea desea agregar? (Simple/Con fecha)");
            String tipoTarea = scanner.nextLine().trim();

            Tarea nuevaTarea = null; // Crear una variable para almacenar la nueva tarea

            if (tipoTarea.equalsIgnoreCase("con fecha")) {
                nuevaTarea = tareaConFecha.crear(); // Crear tarea con fecha
            } else if (tipoTarea.equalsIgnoreCase("simple")) {
                nuevaTarea = tareaSimple.crear(); // Crear tarea simple
            } else {
                System.out.println("Opción no válida");
            }

            if (nuevaTarea != null) {
                TareasAlmacen.guardaTarea(nuevaTarea); // Agregar la tarea al almacén
            }

        } else {
            System.out.println("Opción no válida");
        }
    }
}

    public static void crearTarea2() {
        Scanner scanner = new Scanner(System.in);
        FabricaTareas tareaSimple = new FabricaTareaSimple();
        FabricaTareas tareaConFecha = new FabricaTareasConFecha();
        boolean agregarTareas = true;
        while (agregarTareas == true) {
            System.out.println("¿Desea agregar una tarea nueva? (Si/No)");
            String decision = scanner.nextLine().trim();
            if (decision.equalsIgnoreCase("no")) {
                agregarTareas = false;
            } else if (decision.equalsIgnoreCase("si")) {
                System.out.println("¿Qué tipo de tarea desea agregar? (Simple/Con fecha)");
                String tipoTarea = scanner.nextLine().trim();
                if (tipoTarea.equalsIgnoreCase("con fecha")) {
                    tareaConFecha.crear();
                } else if (tipoTarea.equalsIgnoreCase("simple")) {
                    tareaSimple.crear();
                } else {
                    System.out.println("Opción no válida");
                }
                // TareasAlmacen.muestraTareas(); este metodo sirve para ver todas las tareas
                // conforme las agregamos
                agregarTareas = true;
            } else {
                System.out.println("Opción no válida");
            }
        }
    }

    public void modifica() {
        VistaTareas.muestraTareas();
        System.out.println("¿Qué tarea desea modificar?");
        List<Tarea> tareas = TareasAlmacen.obtenArreglo();
        Scanner sc = new Scanner(System.in);
        int tareaUsuario = sc.nextInt();
        Tarea tarea = TareasAlmacen.obtenArreglo().get(tareaUsuario);
        if (tareaUsuario > tareas.size() || tareaUsuario < 0) {
            System.out.println("Tarea no válida");
        } else {
            if (tarea.getTipo().equals("simple")) {
                modificaTareaSimple(tarea);
            } else if (tarea.getTipo().equals("con fecha")) {
                modificaTareaConFecha(tarea);
            }
        }
    }

    public void modificaTareaConFecha(Tarea tarea) {
        System.out.println("\n¿Qué desea modificar? \n 1. Titulo. \n 2. Descripcion." +
                "\n 3. Etiquetas.\n 4. Estado. \n 5. Fecha de Vencimiento" + "\n 0. Salir.");
        modifica(tarea);
    }

    public void modificaTareaSimple(Tarea tarea) {
        System.out.println("\n¿Qué desea modificar? \n 1. Titulo. \n 2. Descripcion." +
                "\n 3. Etiquetas.\n 4. Estado" + "\n 0. Salir.");
        modifica(tarea);
    }

    public void modifica(Tarea tarea) {
        String parametro = scanner.nextLine().trim();
        String parametroNuevo = "";
        String parametroViejo = "";
        String archivo = "tareas.txt";
        String mensaje = "";
        String paramTarea = "";
        if (parametro.equals("1")) {
            parametroViejo = "Titulo: ";
            mensaje = "Ingrese el nuevo valor para el " + parametroViejo;
            paramTarea = "Titulo: " + tarea.getTitulo();
            System.out.println(mensaje);
            System.out.println(paramTarea);
            parametroNuevo = scanner.nextLine().trim();
            tarea.setTitulo(parametroNuevo);
        } else if (parametro.equals("2")) {
            parametroViejo = "Descripcion: ";
            mensaje = "Ingrese el nuevo valor para la " + parametroViejo;
            paramTarea = "Descripcion: " + tarea.getDescripcion();
            System.out.println(mensaje);
            System.out.println(paramTarea);
            parametroNuevo = scanner.nextLine().trim();
            tarea.setDescripcion(parametroNuevo);
        } else if (parametro.equals("3")) {
            parametroViejo = "Etiquetas: ";
            mensaje = "Ingrese el nuevo valor para las " + parametroViejo;
            String etiquetasTemp = tarea.getEtiquetas();
            paramTarea = "Etiquetas: " + etiquetasTemp;
            System.out.println(paramTarea);
            System.out.println(mensaje);
            Etiqueta etiqueta = new Etiqueta();
            Tarea tareaTemp = etiqueta.etiquetaTarea(tarea.getTitulo(), tarea.getDescripcion(), tarea.getTipo());
            parametroNuevo = etiquetasTemp + tareaTemp.getEtiquetas();
            tarea.setEtiquetas(parametroNuevo);
        } else if (parametro.equals("4")) {
            parametroViejo = "Estado: ";
            mensaje = "Ingrese el nuevo valor para el " + parametroViejo;
            paramTarea = "Estado: " + tarea.isCompletada();
            System.out.println(mensaje);
            System.out.println(paramTarea);
            parametroNuevo = scanner.nextLine().trim();
            tarea.setEstado(null);
            // MODIFICA ESTADO
            modificarEstado(tarea);
        } else if (tarea.getTipo().equals("con fecha") && parametro.equals("5")) {
            parametroViejo = "Fecha de Vencimiento: ";
            mensaje = "Ingrese el nuevo valor para la " + parametroViejo;
            paramTarea = "Fecha de Vencimiento: " + tarea.isCompletada();
            tarea.setCompletada(Boolean.parseBoolean(scanner.nextLine().trim()));
        } else {
            System.out.println("Quancha");
        }

        try {
            // Lee el contenido del archivo
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            // Buscar y modificar la línea deseada
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(paramTarea)) {
                    lineas.set(i, parametroViejo + parametroNuevo);
                    break; // Terminamos de buscar una vez que encontramos la tarea
                }
            }

            // Escribir el contenido modificado de vuelta al archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            System.out.println("Archivo modificado exitosamente.");

        } catch (IOException e) {
            System.out.println("Error al manipular el archivo: " + e.getMessage());
        }
       TareasAlmacen.getTareas();
    }

    public void modificarEstado(Tarea tarea) {
        if (tarea.getEstado() == null) {
            tarea.setEstado(new TareaPendiente()); 
        }
        
        System.out.println("Seleccione el nuevo estado:");
        System.out.println("1. En Progreso");
        System.out.println("2. Completada");
        System.out.println("3. Pendiente");
        Scanner sc = new Scanner(System.in);
        int nuevaOpcion = sc.nextInt();
        switch (nuevaOpcion) {
            case 1:
                tarea.iniciar();
                break;
            case 2:
                tarea.completar();
                break;
            case 3:
                tarea.volverPendiente();
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
}
