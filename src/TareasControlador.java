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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas para mostrar");
            return;
        }

        JFrame frame = new JFrame("Eliminar Tarea de " + usuario.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder tareasTexto = new StringBuilder("Tareas:\n");
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);
            tareasTexto.append(i).append(". ")
                    .append("Titulo: ").append(tarea.getTitulo()).append("\n")
                    .append("Descripción: ").append(tarea.getDescripcion()).append("\n")
                    .append("Prioridad: ").append(tarea.getPrioridad()).append("\n\n");

        }
        textArea.setText(tareasTexto.toString());

       // frame.add(scrollPane);
       // frame.setVisible(true);
       JScrollPane scrollPane2 = new JScrollPane(textArea);

        // Establecer el tamaño preferido del JScrollPane (ancho, alto)
        scrollPane2.setPreferredSize(new java.awt.Dimension(600, 500));

        // Mostrar el JOptionPane con el JScrollPane como su contenido
        JOptionPane.showMessageDialog(null, scrollPane2);

        String input = JOptionPane.showInputDialog(frame, "¿Qué tarea desea eliminar?");
        if (input == null) {
            frame.dispose();
            return;
        }

        int tareaUsuario;
        try {
            tareaUsuario = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Entrada no válida");
            frame.dispose();
            return;
        }

        if (tareaUsuario >= tareas.size() || tareaUsuario < 0) {
            JOptionPane.showMessageDialog(frame, "Tarea no válida");
            frame.dispose();
            return;
        }

        Tarea tarea = tareas.get(tareaUsuario);
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

            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).equals(eliminaTitulo) && lineas.get(i - 1).equals("Tipo: con fecha")) {
                    for (int j = 0; j < 8; j++)
                        lineas.remove(i - 1);
                    tareas.remove(tarea);
                    break;
                } else if (lineas.get(i).equals(eliminaTitulo) && lineas.get(i - 1).equals("Tipo: simple")) {
                    for (int j = 0; j < 7; j++)
                        lineas.remove(i - 1);
                    tareas.remove(tarea);
                    break;
                }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            for (String line : lineas) {
                bw.write(line + "\n");
            }
            bw.close();

            JOptionPane.showMessageDialog(frame, "Archivo modificado exitosamente.");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error al manipular el archivo: " + e.getMessage());
        }
        frame.dispose();
    }

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
}
