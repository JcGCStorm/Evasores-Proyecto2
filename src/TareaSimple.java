
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TareaSimple implements Tarea {

    private TareaEstado estado; // Estado actual de la tarea
    private String tipo = "simple";
    private String titulo;
    private String descripcion;
    private String etiquetas;
    private LocalDate fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private int prioridad;

    public TareaSimple(String tipo, String titulo, String descripcion, String etiquetas,
            LocalDate fechaCreacion, int prioridad, TareaEstado estado) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.fechaCreacion = LocalDate.now();
        this.prioridad = prioridad;
        this.estado = new TareaPendiente(); // inicialmente pendiente
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

    @Override
    public String estadoToString(TareaEstado estado) {
        return estado.estadoToString(estado);
    }

    @Override
    public void construyeTarea(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese detalles de la tarea simple:");
        System.out.print("Titulo: ");
        this.titulo = scanner.nextLine();
        System.out.print("Descripcion: ");
        this.descripcion = scanner.nextLine();
        System.out.print("Etiquetas: ");
        Etiqueta agregaEtiqueta = new Etiqueta();

        this.estado = new TareaPendiente();

        Tarea tareaTemp;
        tareaTemp = agregaEtiqueta.etiquetaTarea(titulo, descripcion, tipo);
        etiquetas = tareaTemp.getEtiquetas();
        LocalDate fechaCreacion = tareaTemp.getFechaCreacion();
        tareaTemp.setFechaCreacion(fechaCreacion);
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaString = fechaCreacion.format(formateador);
        System.out.print("Ingresa el nivel de prioridad (0-10): ");
        int prioridadImput = scanner.nextInt();
        if (prioridadImput < 0 || prioridadImput > 10) {
            System.out.println("Prioridad invalida, se asignara prioridad 0");
            prioridadImput = 0;
        }
        this.prioridad = prioridadImput;

        // Guardar la tarea en un archivo de texto
        try {
            String nombreArchivo = usuario.getUsername() + "_tareas.txt";
            FileWriter salida = new FileWriter(nombreArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(salida);
            String tareaString = "Tipo: " + "simple" + "\nTitulo: " + titulo + "\nDescripcion: " + descripcion
                    + "\nEtiquetas: " + etiquetas + "\nFecha de creación: " + fechaString + "\nPrioridad: "
                    + prioridadImput + "\nEstado: " + tareaTemp.estadoToString(tareaTemp.getEstado()) + "\n";
            bufferedWriter.write(tareaString);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error al guardar la tarea: " + e.getMessage());
        }
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
    }

    /*
     * Getters de los atributos de la clase TareaSimple.
     */
    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
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
    public void setEtiquetas(String etiquetas) {
        this.etiquetas = null;
    }

    @Override
    public int getPrioridad() {
        return prioridad;
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
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaCreacionString = fechaCreacion.format(formatter);
        String estadoString = estado.estadoToString(estado);
        return "Tipo: " + tipo + "\n" +
                "Titulo: " + titulo + "\n" +
                "Descripcion: " + descripcion + "\n" +
                "Etiquetas: " + etiquetas + "\n" +
                "Fecha de creación: " + fechaCreacionString + "\n" +
                "Prioridad: " + prioridad + "\n" +
                "Estado: " + estadoString + "\n";
    }
}
