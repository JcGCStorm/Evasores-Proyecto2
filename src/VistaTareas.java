import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;
import java.awt.*;

public class VistaTareas {

    /**
     * Este metodo nos ayuda a obtener el nombre del archivo de tareas de un usuario
     * en específico.
     * 
     * @param usuario el usuario del que queremos obtener el archivo de tareas.
     * @return el nombre del archivo de tareas del usuario.
     */
    private static String obtenerArchivoTareasUsuario(Usuario usuario) {
        return usuario.getUsername() + "_tareas.txt";
    }

    /**
     * Este metodo imprime las tareas del archivo txt, primero lee el archivo
     * linea por linea y las va imprimiendo, si no hay tareas, imprime un mensaje
     * diciendo que no hay tareas.
     * 
     * @param usuario el usuario del que queremos ver las tareas.
     */

    public static void verTareas(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        String nombreArchivo = obtenerArchivoTareasUsuario(usuario);

        // Cframe para mostrar las tareas
        JFrame frame = new JFrame("Tareas de " + usuario.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // leer el archivo de tareas y mostrarlas en el JTextArea
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                textArea.append(linea + "\n");
            }
        } catch (java.io.FileNotFoundException e) {
            textArea.append("Aún no tienes tareas.\n");
        } catch (IOException e) {
            textArea.append("Error de E/S al leer el archivo: " + e.getMessage() + "\n");
        }

        boolean tareaProximaVencer = false;
        boolean tareaVencida = false;
        LocalDateTime ahora = LocalDateTime.now();
        for (Tarea tarea : tareas) {
            if (tarea instanceof TareaConFecha) {
                TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                LocalDateTime fechaVencimiento = tareaConFecha.getFechaVencimiento();
                long horasHastaVencimiento = ChronoUnit.HOURS.between(ahora, fechaVencimiento);
                if (horasHastaVencimiento <= 0) { // Si las horas son <= 0, la tarea está vencida
                    tareaVencida = true;
                } else if (horasHastaVencimiento <= 72) {
                    tareaProximaVencer = true;
                }
            }
        }

        if (tareaProximaVencer) {
            textArea.append("¡URGENTE, mi estimado procrastinador! Tienes tarea/s para los próximos 3 días o menos.\n");

            textArea.append("Tareas próximas a vencer:\n");
            for (Tarea tarea : tareas) {
                if (tarea instanceof TareaConFecha) {
                    TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                    LocalDateTime fechaVencimiento = tareaConFecha.getFechaVencimiento();
                    long horasHastaVencimiento = ChronoUnit.HOURS.between(ahora, fechaVencimiento);

                    if (horasHastaVencimiento <= 72) {
                        textArea.append("- " + tareaConFecha.getTitulo() + "\n");
                    }
                }
            }
        }

        if (tareaVencida) {
            textArea.append("¡OH NO! Tienes tareas vencidas ): irresponsable:\n");

            for (Tarea tarea : tareas) {
                if (tarea instanceof TareaConFecha) {
                    TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                    LocalDateTime fechaVencimiento = tareaConFecha.getFechaVencimiento();
                    long horasDesdeVencimiento = ChronoUnit.HOURS.between(fechaVencimiento, ahora);

                    if (horasDesdeVencimiento > 0) {
                        textArea.append("- " + tareaConFecha.getTitulo() + "\n");
                    }
                }
            }
        }
        JScrollPane scrollPane2 = new JScrollPane(textArea);

        // Establecer el tamaño preferido del JScrollPane (ancho, alto)
        scrollPane2.setPreferredSize(new java.awt.Dimension(600, 500));

        // Mostrar el JOptionPane con el JScrollPane como su contenido
        JOptionPane.showMessageDialog(null, scrollPane2);
        // mostrar el frame
      //  frame.setVisible(true);
    }

    /**
     * Muestra las tareas ordenadas por prioridad.
     * 
     * @param usuario el usuario del que queremos ver las tareas.
     */
    public static void mostrarTareasXPrioridad(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas para mostrar");
            return;
        }
        Collections.sort(tareas, Comparator.comparingInt(Tarea::getPrioridad));

        JFrame frame = new JFrame("Tareas ordenadas por prioridad de " + usuario.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        textArea.append("Tareas ordenadas por prioridad:\n");
        for (Tarea tarea : tareas) {
            textArea.append("- " + tarea.getTitulo() + " (Prioridad: " + tarea.getPrioridad() + ")\n");
        }
        JScrollPane scrollPane2 = new JScrollPane(textArea);

        // Establecer el tamaño preferido del JScrollPane (ancho, alto)
        scrollPane2.setPreferredSize(new java.awt.Dimension(400, 300));

        // Mostrar el JOptionPane con el JScrollPane como su contenido
        JOptionPane.showMessageDialog(null, scrollPane2);

     //   frame.setVisible(true);
     //   frame.toFront();
    }

  /**
     * Muestra las tareas ordenadas por fecha de vencimiento.
     * 
     * @param usuario el usuario del que queremos ver las tareas.
     */
    public static void mostrarTareasXFechaVencimiento(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas para mostrar");
            return;
        }
        
        List<TareaConFecha> tareasConFecha = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea instanceof TareaConFecha) {
                tareasConFecha.add((TareaConFecha) tarea);
            }
        }

        Collections.sort(tareasConFecha, Comparator.comparing(TareaConFecha::getFechaVencimiento));

        JFrame frame = new JFrame("Tareas ordenadas por fecha de vencimiento de " + usuario.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        textArea.append("Tareas ordenadas por fecha de vencimiento:\n");
        for (TareaConFecha tarea : tareasConFecha) {
            textArea.append(
                    "- " + tarea.getTitulo() + " (Fecha de vencimiento: " + tarea.getFechaVencimiento() + ")\n");
        }
        JScrollPane scrollPane2 = new JScrollPane(textArea);

        // Establecer el tamaño preferido del JScrollPane (ancho, alto)
        scrollPane2.setPreferredSize(new java.awt.Dimension(500, 400));

        // Mostrar el JOptionPane con el JScrollPane como su contenido
        JOptionPane.showMessageDialog(null, scrollPane2);
      //  frame.setVisible(true);
      //  frame.toFront();
    }

    /**
     * Del arrayList de tareas, simplemente lo recorre y va mostrando
     * los atributos de cada tarea.
     * 
     * @param usuario
     */
    public static void muestraTareas(Usuario usuario) {
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        if (tareas == null || tareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar");
            return;
        }

        // Usar un índice explícito
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);

            System.out.println("Índice: " + i); // Imprimir el índice del elemento actual
            System.out.println("Titulo: " + tarea.getTitulo());
            System.out.println("Descripcion: " + tarea.getDescripcion());
            System.out.println("Etiquetas: " + tarea.getEtiquetas());
            DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaCreacion = tarea.getFechaCreacion().format(formateadorCreacion);
            System.out.println("Fecha de Creacion: " + fechaCreacion);

            if (tarea instanceof TareaConFecha) {
                DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                        .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
                LocalDateTime fechaHora = tarea.getFechaVencimiento();
                String fechaHoraString = fechaHora.format(formateadorVencimiento);
                System.out.println("Fecha de Vencimiento: " + fechaHoraString);
            }

            System.out.println("Prioridad: " + tarea.getPrioridad());

            // Imprimir el nombre del estado3 actual
            TareaEstado estado = tarea.getEstado();
            System.out.println("Estado: " + tarea.estadoToString(estado));

            System.out.println("\n");
        }
    }

    private static final Color COLOR_TAREAS = Color.RED;
    private static final Color COLOR_HOY = Color.YELLOW;

    // este metodo muestra un calendario (del mes) de tareas
    public static void verCalendario(Usuario usuario) {
        JFrame frame = new JFrame("Calendario de Tareas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // panel superior para mostrar el mes y el año
        JPanel panelSuperior = new JPanel(new FlowLayout());
        JLabel labelMesAno = new JLabel();
        panelSuperior.add(labelMesAno);
        frame.add(panelSuperior, BorderLayout.NORTH);

        // panel central para mostrar el calendario
        JPanel panelCentral = new JPanel(new GridLayout(0, 7));
        frame.add(panelCentral, BorderLayout.CENTER);

        // tareas del usuario
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        System.out.println("tareas recuperadas: " + tareas.size());

        // primer día del mes actual
        LocalDate fechaActual = LocalDate.now();
        LocalDate primerDiaDelMes = fechaActual.withDayOfMonth(1);

        // mes y el año actual
        DateTimeFormatter formatterMesAno = DateTimeFormatter.ofPattern("MMMM yyyy");
        labelMesAno.setText(primerDiaDelMes.format(formatterMesAno));

        // nombre de los días de la semana
        String[] diasSemana = { "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom" };
        // etiquetas para los nombres de los días de la semana
        for (String dia : diasSemana) {
            JLabel labelDiaSemana = new JLabel(dia, SwingConstants.CENTER);
            panelCentral.add(labelDiaSemana);
        }

        // etiquetas en blanco para los días antes del primer día del mes
        for (int i = 1; i < primerDiaDelMes.getDayOfWeek().getValue(); i++) {
            panelCentral.add(new JLabel());
        }

        // último día del mes actual
        LocalDate ultimoDiaDelMes = fechaActual.withDayOfMonth(fechaActual.lengthOfMonth());

        // mapa para almacenar los días con tareas
        Map<LocalDate, List<TareaConFecha>> diasConTareas = new HashMap<>();
        for (Tarea tarea : tareas) {
            if (tarea instanceof TareaConFecha) {
                TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                LocalDate fechaVencimiento = tareaConFecha.getFechaVencimiento().toLocalDate();
                // lista de tareas asociadas con la fecha de vencimiento
                List<TareaConFecha> tareasDia = diasConTareas.getOrDefault(fechaVencimiento, new ArrayList<>());
                // agrega la tarea actual a la lista de tareas del día
                tareasDia.add(tareaConFecha);
                // actualiza el mapa con la lista de tareas del día
                diasConTareas.put(fechaVencimiento, tareasDia);
            }
        }

        // botones para cada día del mes
        for (LocalDate fechaBoton = primerDiaDelMes; fechaBoton
                .isBefore(ultimoDiaDelMes.plusDays(1)); fechaBoton = fechaBoton.plusDays(1)) {
            JButton botonDia = new JButton(Integer.toString(fechaBoton.getDayOfMonth()));
            botonDia.setOpaque(true);
            botonDia.setBorderPainted(false);
            botonDia.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // lista de tareas para esta fecha
            List<TareaConFecha> tareasDelDia = diasConTareas.getOrDefault(fechaBoton, new ArrayList<>());

            // marca el día si tiene tareas asignadas
            if (!tareasDelDia.isEmpty()) {
                // agrega un punto en el botón para indicar tareas
                JLabel punto = new JLabel("\u2022");
                punto.setFont(new Font("Arial", Font.BOLD, 20)); // tamaño del punto
                punto.setForeground(COLOR_TAREAS); // color del punto
                botonDia.setLayout(new BorderLayout());
                botonDia.add(punto, BorderLayout.NORTH); // punto encima del número del día
            } else if (fechaBoton.isEqual(LocalDate.now())) { // si es hoy, cambiar el color de fondo
                botonDia.setBackground(COLOR_HOY);
            }

            // copia final de la fecha para usar dentro dellambda
            final LocalDate fechaFinal = fechaBoton;

            // ActionListener al botón para mostrar las tareas del día
            botonDia.addActionListener(e -> mostrarTareasDelDia(usuario, fechaFinal, tareasDelDia));

            panelCentral.add(botonDia);
        }

        // etiquetas en blanco para los días después del último día del mes
        for (int i = 1; i < 7 - ultimoDiaDelMes.getDayOfWeek().getValue() + 1; i++) {
            panelCentral.add(new JLabel());
        }

        frame.setVisible(true);
    }

    private static void mostrarTareasDelDia(Usuario usuario, LocalDate fecha, List<TareaConFecha> tareas) {
        JFrame frame = new JFrame("Tareas del día " + fecha);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        if (tareas.isEmpty()) {
            JLabel label = new JLabel("No hay tareas para este día");
            panel.add(label);
        } else {
            for (TareaConFecha tarea : tareas) {
                JLabel titulo = new JLabel("Titulo: " + tarea.getTitulo());
                panel.add(titulo);
            }
        }

        frame.add(panel);
        frame.setVisible(true);
    }

}