import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TareaSimpleCopia implements Tarea {
    String nombreArchivo = "tareas.txt";
    private String tipo = "simple";
    private String titulo;
    private String descripcion;
    private String etiquetas;
    private LocalDate fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private boolean completada;

    /**
     * Constructor de la clase TareaSimple, con los atributos que una tarea simple
     * debería tener,
     * es decir, todos menos una fecha de vencimiento.
     */
    public TareaSimpleCopia(String tipo, String titulo, String descripcion, String etiquetas,
            LocalDate fechaCreacion, boolean completada) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.fechaCreacion = LocalDate.now();
        this.completada = completada;

    }

    /**
     * Método que se encarga de construir una tarea simple, pidiendo al usuario los
     * detalles
     * de la misma, ademas que la guarda en un archivo de texto.
     */
    @Override
    public void construyeTarea(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los detalles de la tarea:");
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();
        System.out.print("Etiquetas: ");
        Etiqueta agregaEtiqueta = new Etiqueta();
        Tarea tareaTemp;
        tareaTemp = VistaTareas.ventanaConfirmacionEtiquetas(tipo);
        etiquetas = tareaTemp.getEtiquetas();
        LocalDate fechaCreacion = tareaTemp.getFechaCreacion();
        tareaTemp.setFechaCreacion(fechaCreacion);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaString = fechaCreacion.format(formateador);
        System.out.print("¿Completada? (si/no): ");
        String completada = scanner.next();

        boolean completadaB = false;
        if (completada.equals("si")) {
            System.out.println("Tarea completada");
            completadaB = true;

        } else if (completada.equals("no")) {
            System.out.println("Tarea no completada");
            completadaB = false;
        }

        System.out.println("\nTarea creada:");
        String tarea = "Tipo: " + "simple" + "\nTítulo: " + titulo + "\nDescripción: " + descripcion
                + "\nEtiquetas: " + etiquetas + "\nFecha de creación: " + fechaString + "\nCompletada: " + completadaB
                + "\n";
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

    }

    /*
     * Getters de los atributos de la clase TareaSimple.
     */
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
    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
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
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public void setEtiquetas(String etiquetas) {
        this.etiquetas = null;
    }

    @Override
    public void setEstado(TareaEstado estado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }

    @Override
    public TareaEstado getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }

    @Override
    public void iniciar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iniciar'");
    }

    @Override
    public void completar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completar'");
    }

    @Override
    public void volverPendiente() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'volverPendiente'");
    }

    @Override
    public String estadoToString(TareaEstado estado) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estadoToString'");
    }

    @Override
    public int getPrioridad() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrioridad'");
    }

    @Override
    public void setPrioridad(int prioridad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPrioridad'");
    }
}
