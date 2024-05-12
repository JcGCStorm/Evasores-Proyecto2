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
        TareasAlmacen.getTareas();
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

    public void eliminaTarea() {
        VistaTareas.muestraTareas();
        System.out.println("¿Qué tarea desea eliminar?");
        List<Tarea> tareas = TareasAlmacen.getTareas();
        Scanner sc = new Scanner(System.in);
        int tareaUsuario = sc.nextInt();
        Tarea tarea = TareasAlmacen.getTareas().get(tareaUsuario);
        if (tareaUsuario > tareas.size() || tareaUsuario < 0) {
            System.out.println("Tarea no válida");
        }
        String archivo = "tareas.txt";
        // Texto que deseas eliminar
        String eliminaTipo = "";
        String eliminaTitulo = "";
        String eliminaDescripcion = "";
        String eliminaEtiquetas = "";
        String eliminaEstado = "";
        String eliminaFechaVencimiento = "";
        String eliminaFechaCreacion = "";
        String eliminaPrioridad = "";

        if (tarea instanceof TareaConFecha) {
            DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
            LocalDateTime fechaHora = tarea.getFechaVencimiento();
            String fechaHoraString = fechaHora.format(formateadorVencimiento);
            eliminaTipo = "Tipo: " + tarea.getTipo();
            eliminaTitulo = "Titulo: " + tarea.getTitulo();
            eliminaDescripcion = "Descripcion: " + tarea.getDescripcion();
            eliminaEtiquetas = "Etiquetas: " + tarea.getEtiquetas();
            eliminaFechaVencimiento = "Fecha de Vencimiento: " + fechaHoraString;
            DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaCreacion = tarea.getFechaCreacion().format(formateadorCreacion);
            eliminaFechaCreacion = "Fecha de Creacion: " + fechaCreacion;
            eliminaEstado = "Estado: " + tarea.estadoToString(tarea.getEstado());
        } else if (tarea instanceof TareaSimple) {
            eliminaTipo = "Tipo: " + tarea.getTipo();
            eliminaTitulo = "Titulo: " + tarea.getTitulo();
            eliminaDescripcion = "Descripcion: " + tarea.getDescripcion();
            eliminaEtiquetas = "Etiquetas: " + tarea.getEtiquetas();
            DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaCreacion = tarea.getFechaCreacion().format(formateadorCreacion);
            eliminaFechaCreacion = "Fecha de Creacion: " + fechaCreacion;
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
                if (lineas.get(i).equals(eliminaTitulo) && lineas.get(i - 1).equals("Tipo: con fecha")) {
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i - 1);
                    lineas.remove(i - 1);
                    tareas.remove(tarea);
                    break;
                } else if (lineas.get(i).equals(eliminaTitulo) && lineas.get(i - 1).equals("Tipo: simple")) {
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i);
                    lineas.remove(i - 1);
                    lineas.remove(i - 1);
                    lineas.remove(i - 1);
                    tareas.remove(tarea);
                    break;
                }
            }
            // Escribir el contenido modificado de vuelta al archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            System.out.println("Archivo modificado exitosamente.");

        } catch (

        IOException e) {
            System.out.println("Error al manipular el archivo: " + e.getMessage());
        }
        TareasAlmacen.getTareas();
    }

    public void eliminaTareas() {
        String nombreArchivo = "tareas.txt";
        VistaTareas.muestraTareas();
        System.out.println("¿Qué tarea desea eliminar?");
        List<Tarea> tareas = TareasAlmacen.getTareas();
        Scanner sc = new Scanner(System.in);
        int tareaUsuario = sc.nextInt();
        Tarea tarea = TareasAlmacen.getTareas().get(tareaUsuario);
        String identificadorTarea = "";
        // Texto del bloque a eliminar
        if (tarea instanceof TareaConFecha) {
            DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaCreacion = tarea.getFechaCreacion().format(formateadorCreacion);
            DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
            LocalDateTime fechaHora = tarea.getFechaVencimiento();
            String fechaHoraString = fechaHora.format(formateadorVencimiento);
            identificadorTarea = "Tipo: " + tarea.getTipo() + "\nTitulo: " + tarea.getTitulo() +
                    "\nDescripcion: " + tarea.getDescripcion() + "\nEtiquetas: " + tarea.getEtiquetas()
                    + "\nFecha de Creacion: " + fechaCreacion + "\nFecha de Vencimiento: " +
                    fechaHoraString + "\nPrioridad: " + tarea.getPrioridad() + "\nEstado: " +
                    tarea.estadoToString(tarea.getEstado()) + "\n";
        } else if (tarea instanceof TareaSimple) {
            DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaCreacion = tarea.getFechaCreacion().format(formateadorCreacion);
            identificadorTarea = "Tipo: " + tarea.getTipo() + "\nTitulo: " + tarea.getTitulo() +
                    "\nDescripcion: " + tarea.getDescripcion() + "\nEtiquetas: " + tarea.getEtiquetas()
                    + "\nFecha de Creacion: " + fechaCreacion + "\n" + "Prioridad: " + tarea.getPrioridad() + "\n" +
                    "Estado: " + tarea.estadoToString(tarea.getEstado()) + "\n";
        }

        // Crear un StringBuilder para almacenar el contenido del archivo
        StringBuilder contenido = new StringBuilder();

        // Bandera para indicar si estamos dentro del bloque a eliminar
        boolean encontrada = false;

        // Leer el contenido del archivo y eliminar el bloque deseado
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Si encontramos la tarea a eliminar, activamos la bandera
                if (linea.equals(identificadorTarea)) {
                    encontrada = true;
                    continue;
                }

                // Si encontramos la tarea y ahora estamos en el siguiente bloque, desactivamos
                // la bandera
                if (encontrada && linea.startsWith("Tipo: ")) {
                    encontrada = false;
                }

                // Si no encontramos la tarea, añadimos la línea al StringBuilder
                if (!encontrada) {
                    contenido.append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Escribir el contenido actualizado de vuelta al archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write(contenido.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Texto eliminado exitosamente del archivo.");
    }

}
