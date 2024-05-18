import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TareasControlador {
    Scanner scanner = new Scanner(System.in);

    /*
     * Método para mostrar las tareas de un usuario específico. Recupera el txt con
     * las tareas del usuario especifico.
     * 
     * @param usuario El usuario del que se desea mostrar las tareas.
     */
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
        String archivoTareasUsuario = obtenerArchivoTareasUsuario(usuario);
        FabricaTareas tareaSimple = new FabricaTareaSimple();
        FabricaTareas tareaConFecha = new FabricaTareasConFecha();

        boolean agregarTareas = true;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTareasUsuario, true))) {
            while (agregarTareas) {
                if (!VistaTareas.deseaAgregarTarea()) {
                    agregarTareas = false;
                    continue;
                }

                String tipoTarea = VistaTareas.obtenerTipoTarea();
                if (tipoTarea == null) {
                    VistaTareas.mostrarMensaje("¿No que deseabas agregar una tarea?");
                    continue;
                }

                Tarea nuevaTarea = null; 

                if (tipoTarea.equalsIgnoreCase("con fecha")) {
                    nuevaTarea = tareaConFecha.crear(usuario); 
                } else if (tipoTarea.equalsIgnoreCase("simple")) {
                    nuevaTarea = tareaSimple.crear(usuario); 
                } else {
                    VistaTareas.mostrarMensaje("Opción no válida");
                }

                if (nuevaTarea != null) {
                    TareasAlmacen.guardaTarea(nuevaTarea); // Agregar la tarea al almacén
                }
            }
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al escribir en el archivo de tareas: " + e.getMessage());
        }
    }

    // me parece que lo vamos a borrar
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
                    // tareaConFecha.crear();
                } else if (tipoTarea.equalsIgnoreCase("simple")) {
                    // tareaSimple.crear();
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

    /**
     * Método para eliminar una tarea de un usuario específico.
     * 
     * @param usuario El usuario del que se desea eliminar una tarea.
     */
    public void eliminaTarea(Usuario usuario) {
        //  tareas del usuario
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas == null || tareas.isEmpty()) {
            VistaTareas.mostrarMensaje("No hay tareas para mostrar");
            return;
        }
        VistaTareas.muestraTareas(usuario);

        // tarea a eliminar
        String input = VistaTareas.obtenerEntrada("¿Qué tarea desea eliminar?");
        if (input == null) {
            VistaTareas.mostrarMensaje("No se ha seleccionado ninguna tarea.");
            return;
        }
        int tareaUsuario;
        try {
            tareaUsuario = Integer.parseInt(input);
            if (tareaUsuario < 0 || tareaUsuario >= tareas.size()) {
                VistaTareas.mostrarMensaje("Índice de tarea no válido.");
                return;
            }
        } catch (NumberFormatException e) {
            VistaTareas.mostrarMensaje("Entrada no válida. Debe ingresar un número de tarea válido.");
            return;
        }


        Tarea tarea = tareas.get(tareaUsuario);

        //  elimina la tarea y mueve el txt
        tareas.remove(tarea);
        String archivo = obtenerArchivoTareasUsuario(usuario);
        String eliminaTitulo = "Titulo: " + tarea.getTitulo();

        try {
        
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(eliminaTitulo) && lineas.get(i - 1).equals("Tipo: con fecha")) {
                    for (int j = 0; j < 8; j++)
                        lineas.remove(i - 1);
                    break;
                } else if (lineas.get(i).equals(eliminaTitulo) && lineas.get(i - 1).equals("Tipo: simple")) {
                    for (int j = 0; j < 7; j++)
                        lineas.remove(i - 1);
                    break;
                }
            }

            //  líneas actualizadas en el archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            VistaTareas.mostrarMensaje("Tarea eliminada exitosamente.");

        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al manipular el archivo: " + e.getMessage());
        }
    }

    /**
     * Método para modificar una tarea de un usuario específico.
     * Lo unico que hace es buscar la tarea que se desea modificar y la linea que desea modificar
     * es decir el parametro, este se sobreescribe con el nuevo valor que se desea.
     * 
     * @param usuario El usuario del que se desea modificar una tarea.
     */
    public void modifica(Usuario usuario) {
        // Obtener la lista de tareas del usuario
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas == null || tareas.isEmpty()) {
            VistaTareas.mostrarMensaje("No hay tareas para mostrar");
            return;
        }

        // Mostrar las tareas al usuario
        VistaTareas.muestraTareas(usuario);

        // Solicitar al usuario el índice de la tarea que desea modificar
        String input = VistaTareas.obtenerEntrada("¿Qué tarea desea modificar?");
        if (input == null) {
            VistaTareas.mostrarMensaje("No se ha seleccionado ninguna tarea.");
            return;
        }

        // Validar la entrada del usuario para asegurar que sea un número válido
        int tareaUsuario;
        try {
            tareaUsuario = Integer.parseInt(input);
            if (tareaUsuario < 0 || tareaUsuario >= tareas.size()) {
                VistaTareas.mostrarMensaje("Índice de tarea no válido.");
                return;
            }
        } catch (NumberFormatException e) {
            VistaTareas.mostrarMensaje("Entrada no válida. Debe ingresar un número de tarea válido.");
            return;
        }

        // Obtener la tarea seleccionada por el usuario
        Tarea tarea = tareas.get(tareaUsuario);

        // Mostrar información de depuración sobre la tarea seleccionada
        System.out.println("Tarea seleccionada: " + tarea.getTitulo() + " - Tipo: " + tarea.getTipo());

        // Modificar la tarea según su tipo
        if (tarea instanceof TareaSimple) {
            modificaTareaSimple((TareaSimple) tarea, usuario);
        } else if (tarea instanceof TareaConFecha) {
            modificaTareaConFecha((TareaConFecha) tarea, usuario);
        } else {
            VistaTareas.mostrarMensaje("Tipo de tarea no reconocido: " + tarea.getClass().getSimpleName());
        }
    }

    // Métodos para modificar tareas simples y tareas con fecha...

    /*
     * Debido a que las tareas simples y las tareas con fecha tienen diferentes
     * atributos, se deben implementar métodos específicos para mostrar en la interfaz que cosa 
     * se desea modificar.
     * 
     * @param tarea La tarea simple que se desea modificar.
     * @param usuario El usuario al que pertenece la tarea.
     */
    public void modificaTareaConFecha(Tarea tarea, Usuario usuario) {
        String[] opciones = { "Titulo", "Descripcion", "Etiquetas", "Fecha de Vencimiento", "Prioridad", "Estado", "Salir" };
        String opcion = (String) JOptionPane.showInputDialog(null, "¿Qué desea modificar?", "Modificar Tarea",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (opcion == null || opcion.equals("Salir")) {
            return;
        }

        switch (opcion) {
            case "Titulo":
                modificarParametro(tarea, usuario, "Titulo", tarea.getTitulo());
                break;
            case "Descripcion":
                modificarParametro(tarea, usuario, "Descripcion", tarea.getDescripcion());
                break;
            case "Etiquetas":
                modificarEtiquetas(tarea, usuario);
                break;
            case "Fecha de Vencimiento":
                modificarFecha(tarea, usuario);
                break;
            case "Prioridad":
                modificarPrioridad(tarea, usuario, tarea.getPrioridad());
                break;
            case "Estado":
                modificarEstado(tarea, usuario);
                break;
        }
    }

    /*
     * Debido a que las tareas simples y las tareas con fecha tienen diferentes
     * atributos, se deben implementar métodos específicos para mostrar en la interfaz que cosa 
     * se desea modificar.
     * 
     * @param tarea La tarea simple que se desea modificar.
     * @param usuario El usuario al que pertenece la tarea.
     */
    public void modificaTareaSimple(Tarea tarea, Usuario usuario) {
        String[] opciones = { "Titulo", "Descripcion", "Etiquetas", "Prioridad", "Estado", "Salir" };
        String opcion = (String) JOptionPane.showInputDialog(null, "¿Qué desea modificar?", "Modificar Tarea",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (opcion == null || opcion.equals("Salir")) {
            return;
        }

        switch (opcion) {
            case "Titulo":
                modificarParametro(tarea, usuario, "Titulo", tarea.getTitulo());
                break;
            case "Descripcion":
                modificarParametro(tarea, usuario, "Descripcion", tarea.getDescripcion());
                break;
            case "Etiquetas":
                modificarEtiquetas(tarea, usuario);
                break;
            case "Prioridad":
                modificarPrioridad(tarea, usuario, tarea.getPrioridad());
                break;
            case "Estado":
                modificarEstado(tarea, usuario);
                break;
        }
    }

    /*
     * Este metodo encapsula la lógica para modificar un parámetro de una tarea.
     * Ya que dependiendo el parametro a modificar se manda a llamar a un metodo
     * u a otro.
     * 
     * @param tarea La tarea que se desea modificar.
     * 
     * @param usuario El usuario al que pertenece la tarea.
     * 
     * @param parametro El parámetro que se desea modificar.
     * 
     * @param valorActual El valor actual del parámetro.
     */
    private void modificarParametro(Tarea tarea, Usuario usuario, String parametro, String valorActual) {
        String nuevoValor = VistaTareas
                .obtenerEntrada("Ingrese el nuevo valor para " + parametro + " (Actual: " + valorActual + ")");
        if (nuevoValor == null || nuevoValor.trim().isEmpty()) {
            return;
        }

        String archivo = obtenerArchivoTareasUsuario(usuario);
        try {
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(parametro + ": " + valorActual)) {
                    lineas.set(i, parametro + ": " + nuevoValor);
                    break;
                }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            switch (parametro) {
                case "Titulo":
                    tarea.setTitulo(nuevoValor);
                    break;
                case "Descripcion":
                    tarea.setDescripcion(nuevoValor);
                    break;
                case "Etiquetas":
                    tarea.setEtiquetas(nuevoValor);
                    break;
                case "Fecha de Vencimiento":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    LocalDateTime nuevaFecha = LocalDateTime.parse(nuevoValor, formatter);
                    tarea.setFechaVencimiento(nuevaFecha);
                    break;
            }

            VistaTareas.mostrarMensaje("Archivo modificado exitosamente.");
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al manipular el archivo: " + e.getMessage());
        }
    }

    /*
     * Este metodo se encarga de modificar las etiquetas de una tarea. Simplemente
     * manda a llamar al metodo que agrega etiwuetas a una tarea y luego sobreescribe
     * el archivo de texto con las nuevas etiquetas.
     * 
     * @param tarea La tarea que se desea modificar.
     * 
     * @param usuario El usuario al que pertenece la tarea.
     */
    private void modificarEtiquetas(Tarea tarea, Usuario usuario) {
        VistaTareas.mostrarMensaje("Se reiniciarán las etiquetas antiguas.");
        Tarea tareaTemp = VistaTareas.ventanaConfirmacionEtiquetas(tarea.getTipo());
        tarea.setEtiquetas(tareaTemp.getEtiquetas());
        String archivo = obtenerArchivoTareasUsuario(usuario);
        try {
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).contains("Etiquetas: " ) && lineas.get(i-2).contains("Titulo: " + tarea.getTitulo())){
                    lineas.set(i, "Etiquetas: " + tarea.getEtiquetas());
                    break;
                }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close(); 
         VistaTareas.mostrarMensaje("Archivo modificado exitosamente.");
         } catch (IOException e) {
       VistaTareas.mostrarMensaje("Error al manipular el archivo: " + e.getMessage());
       }
    }

    /*
     * Este metodo se encarga de modificar la prioridad de una tarea. Simplemente
     * manda a llamar al metodo que recupera una entrada en la interfaz y luego
     * sobreescribe el archivo de texto con la nueva prioridad.
     * 
     * @param tarea La tarea que se desea modificar.
     * 
     * @param usuario El usuario al que pertenece la tarea.
     * 
     * @param prioridad La prioridad actual de la tarea.
     */
    private void modificarPrioridad(Tarea tarea, Usuario usuario, int prioridad){
        try {
        String nuevoValor = VistaTareas
                .obtenerEntrada("Ingrese el nuevo valor para la prioridad. (Actual: " + tarea.getPrioridad() + ")");
        if (nuevoValor == null || nuevoValor.trim().isEmpty()) {
            throw new IllegalArgumentException("La prioridad no puede estar vacía.");
        }
        tarea.setPrioridad(Integer.parseInt(nuevoValor));
        String archivo = obtenerArchivoTareasUsuario(usuario);
        try {
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).contains("Prioridad: " + tarea.getPrioridad())) {
                    lineas.set(i, "Prioridad: " + nuevoValor);
                    break;
                }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();
            VistaTareas.mostrarMensaje("Archivo modificado exitosamente.");
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al manipular el archivo: " + e.getMessage());
        }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Este metodo se encarga de modificar el estado de una tarea. Simplemente
     * recuperamos el nuevo estado de la eleccion que se haga en la interfaz y luego
     * sobreescribe el archivo de texto con el nuevo estado.
     * 
     * @param tarea La tarea que se desea modificar.
     * 
     * @param usuario El usuario al que pertenece la tarea.
     */
    private void modificarEstado(Tarea tarea, Usuario usuario) {
        String estadoViejo = "Estado: " + tarea.estadoToString(tarea.getEstado());
        String[] opciones = { "Tarea En Progreso", "Completada", "Tarea Pendiente" };
        String nuevoEstado = (String) JOptionPane.showInputDialog(null, "Seleccione el nuevo estado:",
                "Modificar Estado",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (nuevoEstado == null) {
            return;
        }

        System.out.println("Nuevo estado seleccionado: " + nuevoEstado);
String mensajeModifica = "";

        switch (nuevoEstado) {
            case "Tarea En Progreso":
             if (estadoViejo.equals("Estado: Tarea En Progreso")) {
                VistaTareas.mostrarMensaje("La tarea ya estaba en progreso. \n" + //
                                        "No se ha modificado el archivo");
                return;
            } else if (estadoViejo.equals("Estado: Completada")) {
                VistaTareas.mostrarMensaje("La tarea ya habia sido completada. \n" + //
                                        "No se ha modificado el archivo");
                return;
            } else {
                tarea.iniciar();
                tarea.setEstado(new TareaEnProgreso());
                mensajeModifica = "La tarea ahora está en progreso. \n ¡Tú puedes! \n" + 
                                "Recuerda que puedes completarla o volverla pendiente." + 
                                "\n Archivo modificado exitosamente.";
            }
                break;
            case "Completada":
            if (estadoViejo.equals("Estado: Completada")) {
                VistaTareas.mostrarMensaje("La tarea ya estaba completada.\n" 
                                        + "No se ha modificado el archivo");
                return;
            } else if (estadoViejo.equals("Estado: Tarea En Progreso")) {
                tarea.completar();
                tarea.setEstado(new TareaCompletada());
                mensajeModifica = "¡Has completado la tarea! ¡¡Felicidades!!. \n" + 
                                   "Archivo modificado exitosamente.";
            } else if (estadoViejo.equals("Estado: Tarea Pendiente")) {
                VistaTareas.mostrarMensaje("La tarea no puede pasar de pendiente a completada. \n" + 
                                           "No se ha modificado el archivo");
                return;
            }
                break;
            case "Tarea Pendiente":
            if (estadoViejo.equals("Estado: Tarea Pendiente")) {
                VistaTareas.mostrarMensaje("La tarea ya estaba pendiente. \n" + 
                                        "No se ha modificado el archivo");
                return;
            } else if (estadoViejo.equals("Estado: Tarea En Progreso")) {
                tarea.volverPendiente();
                tarea.setEstado(new TareaPendiente());
                mensajeModifica = "La tarea ahora está pendiente. \n" + 
                                "Espero que no procrastines mucho" + 
                                "\n Archivo modificado exitosamente.";
            } else if (estadoViejo.equals("Estado: Completada")) {
                VistaTareas.mostrarMensaje("La tarea no puede pasar de completada a pendiente. \n" + 
                                        "No se ha modificado el archivo");
                return;
            }
                break;
            default:
                VistaTareas.mostrarMensaje("Opción no válida.");
                return;
        }

        String archivo = obtenerArchivoTareasUsuario(usuario);
        try {
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            String titulo = "Titulo: " + tarea.getTitulo();
            String estadoNuevo = "Estado: " + tarea.estadoToString(tarea.getEstado());
            String nuevaLinea = "Estado: " + nuevoEstado;
            String tipo = "Tipo: " + tarea.getTipo();
            System.out.println(tipo);
            System.out.println(estadoViejo);
            System.out.println(estadoNuevo);
            for (int i = 0; i < lineas.size(); i++) {
                // Busca la línea que contiene el título y el estado actual de la tarea
                    if (lineas.get(i).equals(estadoViejo) && lineas.get(i-5).equals(titulo) 
                    && lineas.get(i-6).equals("Tipo: simple")) {
                        System.out.println(lineas.get(i) + " " + lineas.get(i - 5));
                        lineas.set(i, nuevaLinea);
                        break;
                    }
                    if (lineas.get(i).equals(estadoViejo) && lineas.get(i-6).equals(titulo) 
                    && lineas.get(i-7).equals("Tipo: con fecha")) {
                        System.out.println(lineas.get(i) + " " + lineas.get(i - 5));
                        lineas.set(i, estadoNuevo);
                        break;
                    }
            }
            System.out.println(tarea.getEstado().toString());

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            VistaTareas.mostrarMensaje(mensajeModifica);
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al manipular el archivo: " + e.getMessage());
        }
    }

    /*
     * Este metodo se encarga de modificar la fecha de vencimiento de una tarea.
     * Simplemente recuperamos la nueva fecha de la eleccion que se haga en la interfaz 
     * y luego sobreescribe el archivo de texto con la nueva fecha.
     * 
     * @param tarea La tarea que se desea modificar.
     * 
     * @param usuario El usuario al que pertenece la tarea.
     */
    private void modificarFecha(Tarea tarea, Usuario usuario) {
        String nuevoValor = VistaTareas.obtenerEntrada("Ingrese la nueva fecha de vencimiento (dd-MM-yyyy HH:mm):");
        if (nuevoValor == null || nuevoValor.trim().isEmpty()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            LocalDateTime nuevaFecha = LocalDateTime.parse(nuevoValor, formatter);
            tarea.setFechaVencimiento(nuevaFecha);

            String archivo = obtenerArchivoTareasUsuario(usuario);
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).startsWith("Fecha de Vencimiento: ")) {
                    lineas.set(i, "Fecha de Vencimiento: " + nuevaFecha.format(formatter));
                    break;
                }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            VistaTareas.mostrarMensaje("Fecha de vencimiento modificada exitosamente.");
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al manipular el archivo: " + e.getMessage());
        } catch (Exception e) {
            VistaTareas.mostrarMensaje("Formato de fecha no válido.");
        }
    }

    // creo qeu esto lo vamos a borrar
    public void eliminaTarea2(Usuario usuario) {
        VistaTareas.muestraTareas(usuario);
        System.out.println("¿Qué tarea desea eliminar?");
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        Scanner sc = new Scanner(System.in);
        int tareaUsuario = sc.nextInt();
        if (tareaUsuario > tareas.size() || tareaUsuario < 0) {
            System.out.println("Tarea no válida");
            return;
        }
        Tarea tarea = TareasAlmacen.getTareas(usuario).get(tareaUsuario);
        String archivo = obtenerArchivoTareasUsuario(usuario);
        String eliminaTitulo = "Titulo: " + tarea.getTitulo();

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
        TareasAlmacen.getTareas(usuario);
        VistaTareas.muestraTareas(usuario);
    }

    /**
     * Método para compartir una tarea con otro usuario. Se le pide al usuario que
     * seleccione una tarea de su lista de tareas y luego se le pide el nombre de
     * usuario al que se desea compartir la tarea. Se crea un archivo con el nombre
     * del usuario receptor y se guarda la tarea compartida en ese archivo.
     * 
     * @param usuario
     */
    public void compartirTarea(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas == null || tareas.isEmpty()) {
            VistaTareas.mostrarMensaje("No hay tareas para mostrar");
            return;
        }

        VistaTareas.muestraTareas(usuario);

        String input = VistaTareas.obtenerEntrada("¿Qué tarea deseas compartir?");
        if (input == null) {
            VistaTareas.mostrarMensaje("No se ha seleccionado ninguna tarea.");
            return;
        }

        int tareaIndex;
        try {
            tareaIndex = Integer.parseInt(input);
            if (tareaIndex < 0 || tareaIndex >= tareas.size()) {
                VistaTareas.mostrarMensaje("Índice de tarea no válido.");
                return;
            }
        } catch (NumberFormatException e) {
            VistaTareas.mostrarMensaje("Entrada no válida. Debe ingresar un número de tarea válido.");
            return;
        }

        Tarea tarea = tareas.get(tareaIndex);

        String nombreUsuarioReceptor = VistaTareas.obtenerEntrada("Ingrese el username al que le llegará la tarea:");
        if (nombreUsuarioReceptor == null || nombreUsuarioReceptor.trim().isEmpty()) {
            VistaTareas.mostrarMensaje("Username no válido.");
            return;
        }
        if (UsuarioAlmacen.obtenerUsuario(nombreUsuarioReceptor) == null) {
            JOptionPane.showMessageDialog(null, "Ese username no existe. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            String archivoReceptor = nombreUsuarioReceptor + "_compartidas.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoReceptor, true))) {
            bw.write("Usuario Emisor: " + usuario.getUsername() + "\n");
            bw.write("Tipo: " + tarea.getTipo() + "\n");
            bw.write("Titulo: " + tarea.getTitulo() + "\n");
            bw.write("Etiquetas: " + tarea.getEtiquetas() + "\n");
            bw.write("Descripcion: " + tarea.getDescripcion() + "\n");
            bw.write("Prioridad: " + tarea.getPrioridad() + "\n");
            bw.write("Estado: " + tarea.estadoToString(tarea.getEstado()) + "\n");
            if (tarea instanceof TareaConFecha) {
                TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                bw.write("Fecha de Vencimiento: " + tareaConFecha.getFechaVencimiento().toString() + "\n");
            }
            bw.write("\n");
            VistaTareas.mostrarMensaje("Tarea compartida exitosamente.");
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al compartir la tarea: " + e.getMessage());
        }

        }

        
    }

    /**
     * Método para recibir tareas compartidas. Se verifica si el usuario tiene
     * tareas compartidas y se le pregunta si desea verlas. Si el usuario desea ver
     * las tareas compartidas, se muestran las tareas y se guardan en un archivo de
     * tareas compartidas guardadas. Si el usuario no desea ver las tareas se eliminan 
     * del archivo de tareas compartidas.
     * @param usuario
     */
    public void recibirTareas(Usuario usuario) {
        //  archivo de tareas compartidas del usuario
        String archivoCompartidas = usuario.getUsername() + "_compartidas.txt";
        String archivoCompartidasGuardadas = usuario.getUsername() + "_compartidas_guardadas.txt";
    
        // si existen tareas compartidas
        if (!existeArchivoCompartidas(usuario)) {
            // muestra las tareas compartidas guardadas 
            VistaTareas.mostrarTareasCompartidas(usuario, archivoCompartidasGuardadas);
            return;
        }
    
        VistaTareas.mostrarMensaje("¡Tienes nuevas tareas compartidas!! :0");
    
        boolean deseaVerTareas = VistaTareas.confirmar("¿Deseas ver y guardar las nuevas tareas compartidas?");
    
        if (deseaVerTareas) {
            // muestra las tareas compartidas al usuario
            VistaTareas.mostrarTareasCompartidas(usuario, archivoCompartidas);
    
            // guarda las tareas compartidas en el archivo de tareas compartidas guardadas
            guardarTareasCompartidas(usuario, archivoCompartidas, archivoCompartidasGuardadas);
    
            // elimina el archivo de tareas compartidas
            eliminarArchivoCompartidas(usuario, archivoCompartidas);
        } else {
            // eliminar las tareas compartidas 
            eliminarArchivoCompartidas(usuario, archivoCompartidas);
        }
    }
    

    /**
     * Método para verificar si el usuario tiene tareas compartidas.
     * 
     * @param usuario el usuario que se desea verificar si tiene tareas compartidas.
     * @return
     */
    private boolean existeArchivoCompartidas(Usuario usuario) {
        String archivoCompartidas = usuario.getUsername() + "_compartidas.txt";
        File file = new File(archivoCompartidas);
        return file.exists() && !file.isDirectory();
    }
    
    /**
     * Método para guardar las tareas compartidas en el archivo de tareas compartidas guardadas.
     * 
     * @param usuario el usuario al que pertenecen las tareas compartidas.
     * @param archivoCompartidas el archivo de tareas compartidas.
     * @param archivoCompartidasGuardadas el archivo de tareas compartidas guardadas.
     */
    private void guardarTareasCompartidas(Usuario usuario, String archivoCompartidas, String archivoCompartidasGuardadas) {
        // Obtener el archivo de tareas compartidas guardadas
        File fileCompartidasGuardadas = new File(archivoCompartidasGuardadas);
    
        // Verificar si el archivo de tareas compartidas guardadas ya existe
        if (!fileCompartidasGuardadas.exists()) {
            try {
                fileCompartidasGuardadas.createNewFile();
            } catch (IOException e) {
                VistaTareas.mostrarMensaje("Error al crear el archivo de tareas compartidas guardadas: " + e.getMessage());
                return;
            }
        }
    
        // guarda las tareas compartidas en el archivo de tareas compartidas guardadas
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCompartidas));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoCompartidasGuardadas, true))) {
    
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea + "\n");
            }
    
            VistaTareas.mostrarMensaje("Tareas compartidas guardadas.");
    
        } catch (IOException e) {
            VistaTareas.mostrarMensaje("Error al guardar las tareas compartidas: " + e.getMessage());
        }
    }
    
    /**
     * Método para eliminar el archivo de tareas compartidas.
     * 
     * @param usuario el usuario al que pertenece el archivo de tareas compartidas.
     * @param archivoCompartidas el archivo de tareas compartidas.
     */
    private void eliminarArchivoCompartidas(Usuario usuario, String archivoCompartidas) {
        // elimina el archivo de tareas compartidas
        File fileCompartidas = new File(archivoCompartidas);
        fileCompartidas.delete();
    }
}
