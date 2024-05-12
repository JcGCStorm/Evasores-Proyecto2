import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TareaConFecha implements Tarea {

    private TareaEstado estado; // Estado actual de la tarea
    String nombreArchivo = "tareas.txt";
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
     * es decir, todos los atributos posibles A MENOS QUE LO CAMBIEMOS EN UN FUTURO.
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

    @Override
    public void iniciar() {
        estado.iniciar(this);
    }

    @Override
    public void completar() {
        estado.completar(this);
    }

    @Override
    public void volverPendiente() {
        estado.volverPendiente(this);
    }

    @Override
    public void setEstado(TareaEstado estado) {
        this.estado = estado;
    }

    @Override
    public TareaEstado getEstado() {
        return estado;
    }

    /**
     * Método que se encarga de construir una tarea con fecha, pidiendo al usuario
     * los detalles
     * de la misma, ademas que la guarda en un archivo de texto.
     */
    @Override
    public void construyeTarea() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los detalles de la tarea:");
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();
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
            FileWriter salida = new FileWriter(nombreArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(salida);
            bufferedWriter.write(tarea);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo");
        }
        List<Tarea> tareas = TareasAlmacen.getTareas();
    }

    // Getters de los atributos de las tareas con fecha.
    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public int getPrioridad() {
        return prioridad;
    }

    @Override
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String getEtiquetas() {
        return etiquetas;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public String estadoToString(TareaEstado estado) {
        return estado.estadoToString(estado);
    }

}
