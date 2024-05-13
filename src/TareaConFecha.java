import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que representa una tarea con fecha, que implementa la interfaz Tarea
 */
public class TareaConFecha implements Tarea {

    // Atributos de la clase TareaConFecha
    private TareaEstado estado; // Estado actual de la tarea
    private String tipo = "con fecha";
    private String titulo;
    private String descripcion;
    private String etiquetas;
    private LocalDate fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private int prioridad;

    /**
     * Constructor de la clase TareaConFecha, con los atributos que una tarea con
     * fecha debería tener,
     * es decir, todos los atributos posibles.
     * 
     * @param titulo
     * @param descripcion
     * @param etiquetas
     * @param fechaCreacion
     * @param fechaVencimiento
     * @param prioridad
     * @param estado
     * @param tipo
     */
    public TareaConFecha(String tipo, String titulo, String descripcion, String etiquetas,
            LocalDate fechaCreacion, LocalDateTime fechaVencimiento, int prioridad, TareaEstado estado) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.fechaCreacion = LocalDate.now();
        this.fechaVencimiento = LocalDateTime.now();
        this.prioridad = prioridad;
        this.estado = new TareaPendiente();

    }

    /**
     * Método que se encarga de iniciar una tarea, cambiando su estado a "En
     * progreso".
     */
    @Override
    public void iniciar() {
        estado.iniciar(this);
    }

    /**
     * Método que se encarga de completar una tarea, cambiando su estado a
     * "Completada", solo si la tarea está en progreso, en caso de ser una
     * tarea pendiente no cambia el estado.
     */
    @Override
    public void completar() {
        estado.completar(this);
    }

    /**
     * Método que se encarga de posponer una tarea, cambiando su estado a
     * "Pendiente", solo si la tarea está en progreso, en caso de ser una
     * tarea completada no cambia el estado.
     */
    @Override
    public void volverPendiente() {
        estado.volverPendiente(this);
    }

    /**
     * Método que se encarga de poner un estado a una tarea.
     * 
     * @param estado el estado nuevo de la tarea.
     */
    @Override
    public void setEstado(TareaEstado estado) {
        this.estado = estado;
    }

    /**
     * Método que se encarga de obtener el estado de una tarea.
     * 
     * @return el estado de la tarea.
     */
    @Override
    public TareaEstado getEstado() {
        return estado;
    }

    /**
     * Método que se encarga de contruir una tarea. Pide el usuario para saber
     * donde guardará la tarea creada.
     */
    @Override
    public void construyeTarea(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los detalles de la tarea:");
        String titulo;
        do {
            System.out.print("Titulo: ");
            titulo = scanner.nextLine();
            if (titulo.trim().isEmpty()) {
                // verificamos que la tarea tenga titulo
                System.out.println("No puedes dejar el título vacío.");
            }
        } while (titulo.trim().isEmpty());

        System.out.print("¿Desea agregar una descripción? (S/N): ");
        String respuesta = scanner.nextLine();
        String descripcion = "";
        if (respuesta.equalsIgnoreCase("S")) {
            do {
                System.out.print("Descripcion: ");
                descripcion = scanner.nextLine();
                if (descripcion.trim().isEmpty()) {
                    System.out.println("No puedes dejar la descripción vacía si has decidido agregarla.");
                }
            } while (descripcion.trim().isEmpty());
        }

        System.out.print("Etiquetas: ");
        Etiqueta agregaEtiqueta = new Etiqueta();
        Tarea tareaTemp;
        tareaTemp = agregaEtiqueta.etiquetaTarea(titulo, descripcion, tipo);
        etiquetas = tareaTemp.getEtiquetas();
        LocalDate fechaCreacion = tareaTemp.getFechaCreacion();
        tareaTemp.setFechaCreacion(fechaCreacion);
        // damos formato a la fecha de creacion para mostrarla en el archivo
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaCreacionString = fechaCreacion.format(formateador);

        int dia, mes, año, hora, minutos;

        boolean fechaValida = false;
        do {
            System.out.println("Ingrese la fecha de vencimiento.");

            System.out.println("Escoge el día (con numeros): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido para el día.");
                scanner.next();
            }
            dia = scanner.nextInt();

            System.out.println("Escoge el mes (con numeros): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido para el mes.");
                scanner.next();
            }
            mes = scanner.nextInt();

            System.out.println("Escoge el año (con numeros): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido para el año.");
                scanner.next();
            }
            año = scanner.nextInt();

            System.out.println("Escoge la hora (con numeros): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido para la hora.");
                scanner.next();
            }
            hora = scanner.nextInt();

            System.out.println("Escoge los minutos (con numeros): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido para los minutos.");
                scanner.next();
            }
            minutos = scanner.nextInt();

            try {
                LocalDate.of(año, mes, dia);
                LocalTime.of(hora, minutos);
                fechaValida = true;
            } catch (DateTimeException e) {
                System.out.println("Fecha no válida. Inténtalo de nuevo.");
            }

            if (fechaCreacion.isAfter(LocalDate.of(año, mes, dia))) {
                System.out.println(
                        "La fecha de vencimiento no puede ser antes de la fecha de creación. Inténtalo de nuevo.");
            } else {
                fechaValida = true;
            }
        } while (!fechaValida);
        // Damos el formato correcto a la fecha y la hora
        String mesFormateado = String.format("%02d", mes);
        String diaFormateado = String.format("%02d", dia);
        String horaFormateada = String.format("%02d", hora);
        String minutosFormateados = String.format("%02d", minutos);

        String fechaString = diaFormateado + "-" + mesFormateado + "-" + año +
                " Hora: " + horaFormateada + ":" + minutosFormateados;
        System.out.print("Ingresa el nivel de prioridad (0-10): ");
        int prioridadImput = scanner.nextInt();
        if (prioridadImput < 0 || prioridadImput > 10) {
            System.out.println("Prioridad invalida, se asignara prioridad 0");
            prioridadImput = 0;
        }
        this.prioridad = prioridadImput;

        System.out.println("\nTarea creada:");
        String tarea = "Tipo: " + "con fecha" + "\nTitulo: " + titulo + "\nDescripcion: " + descripcion +
                "\nEtiquetas: " + etiquetas + "\nFecha de creación: " + fechaCreacionString +
                "\nFecha de Vencimiento: " + fechaString + "\nPrioridad: " + prioridadImput + "\n" +
                "Estado: " + tareaTemp.estadoToString(tareaTemp.getEstado()) + "\n";
        System.out.println(tarea);
        // escribimos la tarea creada en el archivo
        try {
            // obtenemos el archivo txt dependiendo del usuario que creó la tarea.
            String nombreArchivo = usuario.getUsername() + "_tareas.txt";
            FileWriter salida = new FileWriter(nombreArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(salida);
            bufferedWriter.write(tarea);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo");
        }
        // Esto lo hacemos para verificar que la tarea se haya guardado correctamente.
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
    }

    /**
     * Método que se encarga de construir una tarea con fecha, pidiendo al usuario
     * los detalles
     * de la misma, ademas que la guarda en un archivo de texto.
     * 
     * @param usuario el usuario que crea la tarea.
     */
    public void construyeTarea2(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los detalles de la tarea:");
        String titulo;
        do {
            System.out.print("Titulo: ");
            titulo = scanner.nextLine();
            if (titulo.trim().isEmpty()) {
                System.out.println("No puedes dejar el título vacío.");
            }
        } while (titulo.trim().isEmpty());

        System.out.print("¿Desea agregar una descripción? (S/N): ");
        String respuesta = scanner.nextLine();
        String descripcion = "";
        if (respuesta.equalsIgnoreCase("S")) {
            do {
                System.out.print("Descripcion: ");
                descripcion = scanner.nextLine();
                if (descripcion.trim().isEmpty()) {
                    System.out.println("No puedes dejar la descripción vacía si has decidido agregarla.");
                }
            } while (descripcion.trim().isEmpty());
        }

        System.out.print("Etiquetas: ");
        Etiqueta agregaEtiqueta = new Etiqueta();
        Tarea tareaTemp;
        tareaTemp = agregaEtiqueta.etiquetaTarea(titulo, descripcion, tipo);
        etiquetas = tareaTemp.getEtiquetas();
        LocalDate fechaCreacion = tareaTemp.getFechaCreacion();
        tareaTemp.setFechaCreacion(fechaCreacion);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaCreacionString = fechaCreacion.format(formateador);
        System.out.println("Ingrese la fecha de vencimiento. ");

        System.out.println("Escoge el día (con numeros): ");
        int dia = scanner.nextInt();
        if (dia < 1 || dia > 31) {
            System.out.println("Rango de dias no válidos.");
            System.out.println("Intentalo de nuevo.");
            return;
        }
        System.out.println("Escoge el mes (con numeros): ");
        int mes = scanner.nextInt();
        if (mes < 1 || mes > 12) {
            System.out.println("Rango de meses no válidos.");
            System.out.println("Intentalo de nuevo.");
            return;
        }
        System.out.println("\nEscoge el año (con numeros): ");
        int año = scanner.nextInt();
        if (año < 0) {
            System.out.println("Rango de años no válidos.");
            System.out.println("Intentalo de nuevo.");
            return;
        }
        System.out.println("Escoge la hora (con numeros): ");
        int hora = scanner.nextInt();
        if (hora < 0 || hora > 23) {
            System.out.println("Rango de horas no válidas.");
            System.out.println("Intentalo de nuevo.");
            return;
        }
        System.out.println("Escoge los minutos (con numeros): ");
        int minutos = scanner.nextInt();
        if (minutos < 0 || minutos > 59) {
            System.out.println("Rango de minutos no válidos.");
            System.out.println("Intentalo de nuevo.");
            return;
        }

        if (fechaCreacion.isAfter(LocalDate.of(año, mes, dia))) {
            System.out.println("La fecha de vencimiento no puede ser antes de la fecha de creación.");
            System.out.println("Intentalo de nuevo.");
            return;
        }

        String mesFormateado = String.format("%02d", mes);
        String diaFormateado = String.format("%02d", dia);
        String horaFormateada = String.format("%02d", hora);
        String minutosFormateados = String.format("%02d", minutos);

        String fechaString = diaFormateado + "-" + mesFormateado + "-" + año +
                " Hora: " + horaFormateada + ":" + minutosFormateados;
        System.out.print("Ingresa el nivel de prioridad (0-10): ");
        int prioridadImput = scanner.nextInt();
        if (prioridadImput < 0 || prioridadImput > 10) {
            System.out.println("Prioridad invalida, se asignara prioridad 0");
            prioridadImput = 0;
        }
        this.prioridad = prioridadImput;

        System.out.println("\nTarea creada:");
        String tarea = "Tipo: " + "con fecha" + "\nTitulo: " + titulo + "\nDescripcion: " + descripcion +
                "\nEtiquetas: " + etiquetas + "\nFecha de creación: " + fechaCreacionString +
                "\nFecha de Vencimiento: " + fechaString + "\nPrioridad: " + prioridadImput + "\n" +
                "Estado: " + tareaTemp.estadoToString(tareaTemp.getEstado()) + "\n";
        System.out.println(tarea);

        try {
            String nombreArchivo = usuario.getUsername() + "_tareas.txt";
            FileWriter salida = new FileWriter(nombreArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(salida);
            bufferedWriter.write(tarea);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo");
        }
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
    }

    /**
     * Este metodo regresa el titulo de la tarea.
     */
    @Override
    public String getTitulo() {
        return titulo;
    }

    /**
     * Este metodo asigna un titulo a la tarea.
     */
    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Este metodo regresa la descripcion de la tarea.
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Este metodo asigna una descripcion a la tarea.
     */
    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Este metodo regresa la fecha de creacion de la tarea.
     */
    @Override
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Este metodo asigna una fecha de creacion a la tarea.
     */
    @Override
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Este metodo regresa la fecha de vencimiento de la tarea.
     */
    @Override
    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Este metodo regresa la prioridad de la tarea.
     */
    @Override
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * Este metodo asigna una prioridad a la tarea.
     */
    @Override
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    /*
     * Este metodo regresa las etiquetas de la tarea
     */
    @Override
    public String getEtiquetas() {
        return etiquetas;
    }

    /**
     * Este metodo regresa el tipo de la tarea.
     */
    @Override
    public String getTipo() {
        return tipo;
    }

    /**
     * Este metodo asigna una fecha de vencimiento a la tarea.
     */
    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Este metodo asigna etiquetas a la tarea. Solo lo ocupamos para recuperar
     * las etiquetas del txts
     */
    @Override
    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    /**
     * Este metodo regresa el estado de la tarea en forma de string.
     */
    @Override
    public String estadoToString(TareaEstado estado) {
        return estado.estadoToString(estado);
    }

}
