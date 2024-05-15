import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;

public class VistaTareas  {

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
            JOptionPane.showMessageDialog(null, "No hay tareas para mostrar");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tareas.size(); i++) {
            Tarea tarea = tareas.get(i);
            sb.append("Índice: ").append(i).append("\n");
            sb.append("Título: ").append(tarea.getTitulo()).append("\n");
            sb.append("Descripción: ").append(tarea.getDescripcion()).append("\n");
            sb.append("Etiquetas: ").append(tarea.getEtiquetas()).append("\n");
            sb.append("Fecha de Creación: ").append(tarea.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
            if (tarea instanceof TareaConFecha) {
                LocalDateTime fechaHora = tarea.getFechaVencimiento();
                sb.append("Fecha de Vencimiento: ").append(fechaHora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Hora:' HH:mm"))).append("\n");
            }
            sb.append("Prioridad: ").append(tarea.getPrioridad()).append("\n");
            sb.append("Estado: ").append(tarea.estadoToString(tarea.getEstado())).append("\n\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Tareas de " + usuario.getUsername(), JOptionPane.INFORMATION_MESSAGE);
    }

    private static final Color COLOR_TAREAS = Color.RED;
    private static final Color COLOR_HOY = Color.YELLOW;
    private static LocalDate mesActual;
    private static JDialog dialog;

    public static void verCalendario(Usuario usuario) {
        // inicializa el mes actual
        mesActual = LocalDate.now().withDayOfMonth(1);

        // crea y muestra el calendario en un JOptionPane
        mostrarCalendario(usuario);
    }

    private static void mostrarCalendario(Usuario usuario) {

        // esta cosa del dialog ayuda a que solo se muestre una ventana x mes
        if (dialog != null) {
            dialog.dispose();
        }

        JPanel panelCalendario = crearPanelCalendario(usuario);
        JScrollPane scrollPane = new JScrollPane(panelCalendario);
        scrollPane.setPreferredSize(new Dimension(800, 600));

        dialog = new JOptionPane(scrollPane, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION).createDialog("Calendario de Tareas");
        dialog.setVisible(true);
    }

    private static JPanel crearPanelCalendario(Usuario usuario) {
        JPanel panelCalendario = new JPanel(new BorderLayout());

        // panel superior para mostrar el mes y el año con botones 
        JPanel panelSuperior = new JPanel(new FlowLayout());
        JButton botonAnterior = new JButton("<");
        JButton botonSiguiente = new JButton(">");
        JLabel labelMesAno = new JLabel();
        panelSuperior.add(botonAnterior);
        panelSuperior.add(labelMesAno);
        panelSuperior.add(botonSiguiente);
        panelCalendario.add(panelSuperior, BorderLayout.NORTH);

        // panel central para mostrar el calendario
        JPanel panelCentral = new JPanel(new GridLayout(0, 7));
        panelCalendario.add(panelCentral, BorderLayout.CENTER);

        actualizarCalendario(usuario, labelMesAno, panelCentral);

        botonAnterior.addActionListener(e -> {
            mesActual = mesActual.minusMonths(1);
            mostrarCalendario(usuario);
        });

        botonSiguiente.addActionListener(e -> {
            mesActual = mesActual.plusMonths(1);
            mostrarCalendario(usuario);
        });

        return panelCalendario;
    }

    private static void actualizarCalendario(Usuario usuario, JLabel labelMesAno, JPanel panelCentral) {
        panelCentral.removeAll();

        // mes y año actual
        DateTimeFormatter formatterMesAno = DateTimeFormatter.ofPattern("MMMM yyyy");
        labelMesAno.setText(mesActual.format(formatterMesAno));

        //  días de la semana
        String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (String dia : diasSemana) {
            JLabel labelDiaSemana = new JLabel(dia, SwingConstants.CENTER);
            panelCentral.add(labelDiaSemana);
        }

        // primer día del mes 
        LocalDate primerDiaDelMes = mesActual;

        // etiquetas en blanco pa los días antes del primer día del mes
        for (int i = 1; i < primerDiaDelMes.getDayOfWeek().getValue(); i++) {
            panelCentral.add(new JLabel());
        }

        // ultimo día del mes actual
        LocalDate ultimoDiaDelMes = mesActual.withDayOfMonth(mesActual.lengthOfMonth());

        // Tareas del usuario
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
        Map<LocalDate, List<TareaConFecha>> diasConTareas = new HashMap<>();
        for (Tarea tarea : tareas) {
            if (tarea instanceof TareaConFecha) {
                TareaConFecha tareaConFecha = (TareaConFecha) tarea;
                LocalDate fechaVencimiento = tareaConFecha.getFechaVencimiento().toLocalDate();
                List<TareaConFecha> tareasDia = diasConTareas.getOrDefault(fechaVencimiento, new ArrayList<>());
                tareasDia.add(tareaConFecha);
                diasConTareas.put(fechaVencimiento, tareasDia);
            }
        }

        // botones pa cada día del mes
        for (LocalDate fechaBoton = primerDiaDelMes; !fechaBoton.isAfter(ultimoDiaDelMes); fechaBoton = fechaBoton.plusDays(1)) {
            JButton botonDia = new JButton(Integer.toString(fechaBoton.getDayOfMonth()));
            botonDia.setOpaque(true);
            botonDia.setBorderPainted(false);
            botonDia.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Lista de tareas para esta fecha
            List<TareaConFecha> tareasDelDia = diasConTareas.getOrDefault(fechaBoton, new ArrayList<>());

            if (!tareasDelDia.isEmpty()) {
                JLabel punto = new JLabel("\u2022");
                punto.setFont(new Font("Arial", Font.BOLD, 20));
                punto.setForeground(COLOR_TAREAS);
                botonDia.setLayout(new BorderLayout());
                botonDia.add(punto, BorderLayout.NORTH);
            } else if (fechaBoton.isEqual(LocalDate.now())) {
                botonDia.setBackground(COLOR_HOY);
            }

            final LocalDate fechaFinal = fechaBoton;
            botonDia.addActionListener(e -> mostrarTareasDelDia(usuario, fechaFinal, tareasDelDia));

            panelCentral.add(botonDia);
        }

        // etiquetas en blanco para los días después del último día del mes
        for (int i = 1; i < 7 - ultimoDiaDelMes.getDayOfWeek().getValue() + 1; i++) {
            panelCentral.add(new JLabel());
        }

        panelCentral.revalidate();
        panelCentral.repaint();
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

    public static boolean deseaAgregarTarea() {
        int response = JOptionPane.showConfirmDialog(null, "¿Desea agregar una tarea nueva?", "Agregar Tarea",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return response == JOptionPane.YES_OPTION;
    }

    public static String obtenerTipoTarea() {
        String[] options = { "Simple", "Con fecha" };
        return (String) JOptionPane.showInputDialog(null, "¿Qué tipo de tarea desea agregar?", "Tipo de Tarea",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    //

    /**
     * Método para obtener una entrada de texto del usuario.
     * 
     * @param mensaje el mensaje a mostrar al usuario.
     * @return la entrada del usuario.
     */
    public static String obtenerEntrada(String mensaje) {
        String entrada;
        do {
            entrada = JOptionPane.showInputDialog(null, mensaje);
            if (entrada == null || entrada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puedes dejar el campo vacío.");
            }
        } while (entrada == null || entrada.trim().isEmpty());
        return entrada;
    }

    public String obtenerTitulo() {
        return obtenerEntrada("Ingrese el título de la tarea:");
    }

    public String obtenerDescripcion() {
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar una descripción?", "Descripción",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            return obtenerEntrada("Ingrese la descripción de la tarea:");
        }
        return "";
    }

    public static Tarea ventanaConfirmacionEtiquetas(String tipo) {
        Tarea tarea = null;
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar etiquetas?", "Etiquetas",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            String etiqueta = "pña";
            Etiqueta etiqueta2 = new Etiqueta();
             tarea = etiqueta2.etiquetaTarea(tipo, etiqueta);
        }
            if (respuesta == JOptionPane.NO_OPTION && tipo.equals("simple")){
                tarea = new TareaSimple("simple", "", "", "", LocalDate.now(), 0, null);
            } else if (respuesta == JOptionPane.NO_OPTION && tipo.equals("con fecha")){
                tarea = new TareaConFecha("con fecha", "", "", "", LocalDate.now(), LocalDateTime.now(), 5, new TareaPendiente());
            }
        return tarea;
    }

    public int obtenerPrioridad() {
        int prioridad;
        do {
            String input = obtenerEntrada("Ingresa el nivel de prioridad (0-10):");
            try {
                prioridad = Integer.parseInt(input);
                if (prioridad < 0 || prioridad > 10) {
                    JOptionPane.showMessageDialog(null, "Prioridad inválida, ingrese un número entre 0 y 10.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida, ingrese un número.");
            }
        } while (true);
        return prioridad;
    }

    public LocalDateTime obtenerFechaVencimiento() {
        while (true) {
            JTextField diaField = new JTextField();
            JTextField mesField = new JTextField();
            JTextField añoField = new JTextField();
            JTextField horaField = new JTextField();
            JTextField minutosField = new JTextField();

            Object[] fields = {
                    "Día de vencimiento (1-31):", diaField,
                    "Mes de vencimiento (1-12):", mesField,
                    "Año de vencimiento:", añoField,
                    "Hora de vencimiento (0-23):", horaField,
                    "Minutos de vencimiento (0-59):", minutosField
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Fecha de Vencimiento",
                    JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int dia = Integer.parseInt(diaField.getText().trim());
                    int mes = Integer.parseInt(mesField.getText().trim());
                    int año = Integer.parseInt(añoField.getText().trim());
                    int hora = Integer.parseInt(horaField.getText().trim());
                    int minutos = Integer.parseInt(minutosField.getText().trim());

                    // Validaciones de rango
                    if (dia < 1 || dia > 31) {
                        throw new IllegalArgumentException("Día fuera de rango");
                    }
                    if (mes < 1 || mes > 12) {
                        throw new IllegalArgumentException("Mes fuera de rango");
                    }
                    if (año < 0) {
                        throw new IllegalArgumentException("Año fuera de rango");
                    }
                    if (hora < 0 || hora > 23) {
                        throw new IllegalArgumentException("Hora fuera de rango");
                    }
                    if (minutos < 0 || minutos > 59) {
                        throw new IllegalArgumentException("Minutos fuera de rango");
                    }

                    // Construir la fecha
                    LocalDate fecha = LocalDate.of(año, mes, dia);

                    // Construir la hora
                    LocalTime hora1 = LocalTime.of(hora, minutos);

                    // Combinar fecha y hora en LocalDateTime
                    LocalDateTime fechaVencimiento = LocalDateTime.of(fecha, hora1);

                    if (fechaVencimiento.isBefore(LocalDateTime.now())) {
                        JOptionPane.showMessageDialog(null,
                                "La fecha de vencimiento no puede ser antes de la fecha actual. Inténtelo de nuevo.");
                        continue;
                    }

                    return fechaVencimiento;

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida. Asegúrese de ingresar números válidos.");
                } catch (DateTimeException e) {
                    JOptionPane.showMessageDialog(null, "Fecha o hora no válidas. Inténtelo de nuevo.");
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + ". Inténtelo de nuevo.");
                }
            } else {
                return null; // Si se cancela, retorna null.
            }
        }
    }

    private JComboBox<String> etiquetasComboBox;
    private JButton agregarButton;
    private JTextArea tareaTextArea;

    public void etiquetasGUI() {
        JPanel etiquetasPanel = new JPanel();
        etiquetasPanel.setLayout(new FlowLayout());
        JLabel etiquetasLabel = new JLabel("¿Qué etiqueta deseas agregar a tu tarea?");
        etiquetasComboBox = new JComboBox<>(new String[]{"Estudio", "Trabajo", "Personal", "Deportes", "Comida", "Salud", "Entretenimiento", "Hogar", "Viaje", "Compras", "Social", "Asambleas", "Otro"});
        etiquetasPanel.add(etiquetasLabel);
        etiquetasPanel.add(etiquetasComboBox);

        agregarButton = new JButton("Agregar");
        //agregarButton.addActionListener(this);

        tareaTextArea = new JTextArea();
        JScrollPane tareaScrollPane = new JScrollPane(tareaTextArea);
        JOptionPane.showMessageDialog(null, tareaScrollPane);

       // add(etiquetasPanel, BorderLayout.NORTH);
       // add(agregarButton, BorderLayout.CENTER);
       // add(tareaScrollPane, BorderLayout.SOUTH);
    }
}