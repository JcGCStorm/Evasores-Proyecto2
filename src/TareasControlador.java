import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TareasControlador {
    Scanner scanner = new Scanner(System.in);

    private static String obtenerArchivoTareasUsuario(Usuario usuario) {
        return usuario.getUsername() + "_tareas.txt";
    }
    
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

     public static void crearTarea(Usuario usuario) {
        Scanner scanner = new Scanner(System.in); // Cambio aquí
        String archivoTareasUsuario = obtenerArchivoTareasUsuario(usuario);
        FabricaTareas tareaSimple = new FabricaTareaSimple();
        FabricaTareas tareaConFecha = new FabricaTareasConFecha();

        boolean agregarTareas = true;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTareasUsuario, true))) {
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
                        nuevaTarea = tareaConFecha.crear(usuario); // Crear tarea con fecha
                    } else if (tipoTarea.equalsIgnoreCase("simple")) {
                        nuevaTarea = tareaSimple.crear(usuario); // Crear tarea simple
                    } else {
                        System.out.println("Opción no válida");
                    }

                    if (nuevaTarea != null) {
                        bw.write(nuevaTarea.toString() + "\n"); // Escribir la tarea en el archivo
                    }
                } else {
                    System.out.println("Opción no válida");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de tareas: " + e.getMessage());
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
                    //tareaConFecha.crear();
                } else if (tipoTarea.equalsIgnoreCase("simple")) {
                    //tareaSimple.crear();
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

    public void modifica(Usuario usuario) {
        VistaTareas.muestraTareas(usuario);
        System.out.println("¿Qué tarea desea modificar?");
        List<Tarea> tareas = TareasAlmacen.obtenArreglo();
        Scanner sc = new Scanner(System.in);
        int tareaUsuario = sc.nextInt();
        Tarea tarea = TareasAlmacen.obtenArreglo().get(tareaUsuario);
        if (tareaUsuario > tareas.size() || tareaUsuario < 0) {
            System.out.println("Tarea no válida");
        } else {
            if (tarea.getTipo().equals("simple")) {
                modificaTareaSimple(tarea, usuario);
            } else if (tarea.getTipo().equals("con fecha")) {
                modificaTareaConFecha(tarea, usuario);
            }
        }
    }

    public void modificaTareaConFecha(Tarea tarea, Usuario usuario) {
        System.out.println("\n¿Qué desea modificar? \n 1. Titulo. \n 2. Descripcion." +
                "\n 3. Etiquetas.\n 4. Estado. \n 5. Fecha de Vencimiento" + "\n 0. Salir.");
        modifica(tarea, usuario); // Pasa también el usuario como un parámetro adicional
    }
    
    public void modificaTareaSimple(Tarea tarea, Usuario usuario) {
        System.out.println("\n¿Qué desea modificar? \n 1. Titulo. \n 2. Descripcion." +
                "\n 3. Etiquetas.\n 4. Estado" + "\n 0. Salir.");
        modifica(tarea, usuario); // Pasa también el usuario como un parámetro adicional
    }
    
    public void modifica(Tarea tarea, Usuario usuario) {
        String parametro = scanner.nextLine().trim();
        String parametroNuevo = "";
        String parametroViejo = "";
        String archivo = obtenerArchivoTareasUsuario(usuario); // Obtener el archivo de tareas del usuario
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
            mensaje = "Se reiniciarán las etiquetas antiguas.";
            String etiquetasTemp = tarea.getEtiquetas();
            paramTarea = "Etiquetas: " + etiquetasTemp;
            System.out.println(paramTarea);
            System.out.println(mensaje);
            Etiqueta etiqueta = new Etiqueta();
            Tarea tareaTemp = etiqueta.etiquetaTarea(tarea.getTitulo(), tarea.getDescripcion(), tarea.getTipo());
            parametroNuevo = tareaTemp.getEtiquetas();
            tarea.setEtiquetas(parametroNuevo);
        } else if (parametro.equals("4")) {
            parametroViejo = "Estado: ";
            mensaje = "Ingrese el nuevo valor para el " + parametroViejo;
            TareaEstado estado = tarea.getEstado();
            String estadoString = tarea.estadoToString(estado);
            paramTarea = "Estado: " + estadoString;
            System.out.println(paramTarea);
            parametroNuevo = modificarEstado(tarea);
            System.out.println(parametroNuevo);

        } else if (tarea.getTipo().equals("con fecha") && parametro.equals("5")) {
            parametroViejo = "Fecha de Vencimiento: ";
            mensaje = "Ingrese el nuevo valor para la " + parametroViejo;
            DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
            LocalDateTime fechaHora = tarea.getFechaVencimiento();
            String fechaHoraString = fechaHora.format(formateadorVencimiento);
            paramTarea = "Fecha de Vencimiento: " + fechaHoraString;
            parametroNuevo = modificaFecha(tarea);
            System.out.println(parametroNuevo);

        } else {
            System.out.println("No has modificado nada.");
            return;
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
                if (parametroViejo == "Estado: ") {
                    if (tarea instanceof TareaConFecha && lineas.get(i).equals(paramTarea)
                            && lineas.get(i - 5).equals("Descripcion: " + tarea.getDescripcion())) {
                        System.out.println(tarea.getDescripcion());
                        lineas.set(i, parametroViejo + parametroNuevo);
                        break; // Terminamos de buscar una vez que encontramos la tarea
                    } else if (tarea instanceof TareaSimple && lineas.get(i).equals(paramTarea)
                            && lineas.get(i - 4).equals("Descripcion: " + tarea.getDescripcion())) {
                        System.out.println(tarea.getDescripcion());
                        lineas.set(i, parametroViejo + parametroNuevo);
                        break;
                    }
                } else {
                    if (lineas.get(i).equals(paramTarea)) {
                        lineas.set(i, parametroViejo + parametroNuevo);
                        break;
                    }
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
        TareasAlmacen.getTareas(usuario);
    }

    public void modifica2(Tarea tarea, Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        String parametro = scanner.nextLine().trim();
        String parametroNuevo = "";
        String parametroViejo = "";
        String archivo = obtenerArchivoTareasUsuario(usuario); // Obtener el archivo de tareas del usuario
        String mensaje = "";
        String paramTarea = "";
        
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
                if (lineas.get(i).contains(tarea.getTitulo())) {
                    // Aquí debes ajustar la lógica según cómo estén almacenadas las tareas en tu archivo de texto
                    // Puedes usar un formato específico para cada tarea, por ejemplo, separando los campos por un carácter especial
                    // Por ejemplo: "Titulo:Mi Tarea|Descripcion:Descripción de la tarea|..."
                    String[] campos = lineas.get(i).split("\\|"); // Dividir la línea en campos usando el carácter '|'
                    for (String campo : campos) {
                        String[] partes = campo.split(":"); // Dividir cada campo en nombre y valor
                        if (partes[0].trim().equals(parametro)) {
                            parametroViejo = partes[1].trim();
                            mensaje = "Ingrese el nuevo valor para " + parametro;
                            System.out.println(mensaje);
                            parametroNuevo = scanner.nextLine().trim();
                            // Modificar el valor en el archivo
                            lineas.set(i, partes[0].trim() + ":" + parametroNuevo);
                            break;
                        }
                    }
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
        TareasAlmacen.getTareas(usuario);
    }

    public String modificaFecha(Tarea tareaTemp) {
        LocalDate fechaCreacion = tareaTemp.getFechaCreacion();
        tareaTemp.setFechaCreacion(fechaCreacion);
        System.out.println("Ingrese la fecha de vencimiento. ");

        System.out.println("Escoge el día (con numeros): ");
        int dia = scanner.nextInt();
        if (dia < 1 || dia > 31) {
            System.out.println("Rango de dias no válidos.");
            System.out.println("Intentalo de nuevo.");
            return "";
        }
        System.out.println("Escoge el mes (con numeros): ");
        int mes = scanner.nextInt();
        if (mes < 1 || mes > 12) {
            System.out.println("Rango de meses no válidos.");
            System.out.println("Intentalo de nuevo.");
            return "";
        }
        System.out.println("\nEscoge el año (con numeros): ");
        int año = scanner.nextInt();
        if (año < 0) {
            System.out.println("Rango de años no válidos.");
            System.out.println("Intentalo de nuevo.");
            return "";
        }
        System.out.println("Escoge la hora (con numeros): ");
        int hora = scanner.nextInt();
        if (hora < 0 || hora > 23) {
            System.out.println("Rango de horas no válidas.");
            System.out.println("Intentalo de nuevo.");
            return "";
        }
        System.out.println("Escoge los minutos (con numeros): ");
        int minutos = scanner.nextInt();
        if (minutos < 0 || minutos > 59) {
            System.out.println("Rango de minutos no válidos.");
            System.out.println("Intentalo de nuevo.");
            return "";
        }

        if (fechaCreacion.isAfter(LocalDate.of(año, mes, dia))) {
            System.out.println("La fecha de vencimiento no puede ser antes de la fecha de creación.");
            System.out.println("Intentalo de nuevo.");
            return "";
        }

        String mesFormateado = String.format("%02d", mes);
        String diaFormateado = String.format("%02d", dia);
        String horaFormateada = String.format("%02d", hora);
        String minutosFormateados = String.format("%02d", minutos);
        return diaFormateado + "-" + mesFormateado + "-" + año + " Hora: " + horaFormateada + ":"
                + minutosFormateados;
    }
    

    public String modificarEstado(Tarea tarea) {
        if (tarea.getEstado() == null) {
            tarea.setEstado(new TareaPendiente());
        }

        TareaEstado estado = tarea.getEstado();
        String estadoString = tarea.estadoToString(estado);
        System.out.println("Seleccione el nuevo estado:");
        System.out.println("1. En Progreso");
        System.out.println("2. Completada");
        System.out.println("3. Pendiente");
        Scanner sc = new Scanner(System.in);
        int nuevaOpcion = sc.nextInt();
        switch (nuevaOpcion) {
            case 1:
                tarea.iniciar();
                estadoString = "Tarea En Progreso";
                tarea.setEstado(new TareaEnProgreso());
                break;
            case 2:
                tarea.completar();
                if (tarea.getEstado() instanceof TareaCompletada) {
                    estadoString = "Completada";
                    tarea.setEstado(new TareaCompletada());
                } else {
                    estadoString = "Tarea En Progreso";
                    tarea.setEstado(new TareaEnProgreso());
                }
                break;
            case 3:
                tarea.volverPendiente();
                estadoString = "Tarea Pendiente";
                tarea.setEstado(new TareaPendiente());
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
        return estadoString;
    }
}
