
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

    /*
     * Constructor de la clase TareaSimple, inicializa los atributos de la tarea
     * simple.
     * 
     * @param tipo, el tipo de tarea
     * 
     * @param titulo, el titulo de la tarea
     * 
     * @param descripcion, la descripcion de la tarea
     * 
     * @param etiquetas, las etiquetas de la tarea
     * 
     * @param fechaCreacion, la fecha de creacion de la tarea
     * 
     * @param prioridad, la prioridad de la tarea
     * 
     * @param estado, el estado de la tarea
     */
    public TareaSimple(String tipo, String titulo, String descripcion, String etiquetas,
            LocalDate fechaCreacion, int prioridad, TareaEstado estado) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.fechaCreacion = fechaCreacion;
        this.prioridad = prioridad;
        this.estado = new TareaPendiente(); // inicialmente pendiente
    }

    /*
     * Este metodo cambia el estado de la tarea a iniciada
     */
    @Override
    public void iniciar() {
        estado.iniciar(this);
    }

    /*
     * Este metodo cambia el estado de la tarea a completada
     */
    @Override
    public void completar() {
        estado.completar(this);
    }

    /*
     * Este metodo cambia el estado de la tarea a pendiente
     */
    @Override
    public void volverPendiente() {
        estado.volverPendiente(this);
    }

    /*
     * Este metodo cambia el estado de la tarea, lo ocupamos para
     * recuperar las tareas del txt, los estados los asignamos en el State de cada
     * uno, este solo es un auxiliar del Almacen
     * 
     * @param estado, el estado de la tarea
     */
    @Override
    public void setEstado(TareaEstado estado) {
        this.estado = estado;
    }

    /*
     * Este metodo regresa el estado de la tarea
     */
    @Override
    public TareaEstado getEstado() {
        return estado;
    }

    /*
     * Este metodo regresa el estado de la tarea
     */
    @Override
    public String estadoToString(TareaEstado estado) {
        return estado.estadoToString(estado);
    }

    /*
     * Este metodo construye una tarea simple, pidiendo al usuario que ingrese los
     * detalles de la tarea.
     * 
     * @param usuario, el usuario que esta creando la tarea
     */
    @Override
    public void construyeTarea(Usuario usuario) {
        VistaTareas vista = new VistaTareas();
        
        this.titulo = vista.obtenerTitulo();
        this.descripcion = vista.obtenerDescripcion();
        this.etiquetas = vista.obtenerEtiquetas();
        
        // falta gui de etiquetas
        Etiqueta agregaEtiqueta = new Etiqueta();
        Tarea tareaTemp = agregaEtiqueta.etiquetaTarea(titulo, descripcion, tipo);
        etiquetas = tareaTemp.getEtiquetas();
        LocalDate fechaCreacion = tareaTemp.getFechaCreacion();
        tareaTemp.setFechaCreacion(fechaCreacion);

        this.prioridad = vista.obtenerPrioridad();
        this.estado = new TareaPendiente();
        this.fechaCreacion = LocalDate.now(); // O según lo que necesites
        
        
        try {
            String nombreArchivo = usuario.getUsername() + "_tareas.txt";
            FileWriter salida = new FileWriter(nombreArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(salida);
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaString = fechaCreacion.format(formateador);
            String tareaString = "Tipo: " + "simple" + "\nTitulo: " + titulo + "\nDescripcion: " + descripcion
                    + "\nEtiquetas: " + etiquetas + "\nFecha de creación: " + fechaString + "\nPrioridad: "
                    + prioridad + "\nEstado: " + tareaTemp.estadoToString(tareaTemp.getEstado()) + "\n";
            bufferedWriter.write(tareaString);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error al guardar la tarea: " + e.getMessage());
        }
        List<Tarea> tareas = TareasAlmacen.getTareas(usuario);
    }

    /*
     * Getter del título de la tarea, simplemente regresa el título de la tarea.
     * 
     * @return el título de la tarea
     */
    @Override
    public String getTitulo() {
        return titulo;
    }

    /*
     * Getter de la descripción de la tarea, simplemente regresa la descripción de
     * la tarea.
     * 
     * @return la descripción de la tarea
     */
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    /*
     * Getter de la fecha de creación de la tarea, simplemente regresa la fecha de
     * creación de la tarea.
     * 
     * @return la fecha de creación de la tarea
     */
    @Override
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /*
     * Getter de la fecha de vencimiento de la tarea, simplemente regresa la fecha
     * de
     * vencimiento de la tarea.
     * 
     * @return la fecha de vencimiento de la tarea
     */
    @Override
    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    /*
     * Getter de la prioridad de la tarea, simplemente regresa la prioridad de la
     * tarea.
     * 
     * @return la prioridad de la tarea
     */
    @Override
    public int getPrioridad() {
        return prioridad;
    }

    /*
     * Getter de las etiquetas de la tarea, simplemente regresa las etiquetas de la
     * tarea.
     * 
     * @return las etiquetas de la tarea
     */
    @Override
    public String getEtiquetas() {
        return etiquetas;
    }

    /*
     * Setter de las etiquetas de la tarea, simplemente sirve para asignarle
     * etiquetas a la tarea. Solo lo usamos para recuperarlas del txt, no para
     * el decorator
     * 
     * @param etiquetas
     */
    @Override
    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    /*
     * Getter del tipo de tarea, simplemente regresa el tipo de tarea, en este caso
     * simple
     * 
     * @return el tipo de tarea
     */
    @Override
    public String getTipo() {
        return tipo;
    }

    /*
     * Setter de la fecha de creación de la tarea, simplemente sirve para asignarle
     * una fecha de creación a la tarea.
     * 
     * @param fechaCreacion
     */
    @Override
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Setter de la prioridad de la tarea, simplemente sirve para asignarle una
     * prioridad a la tarea.
     * 
     * @param prioridad
     */
    @Override
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Setter de la fecha de vencimiento de la tarea, simplemente sirve para asignar
     * una fecha de vencimiento a la tarea. Aunque nunca lo usemos en esta clase ya
     * que es una
     * tarea simple, debemos implementarlo por la interfaz.
     * 
     * @param fechaVencimiento
     */
    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Setter del título de la tarea, simplemente sirve para asignarle un título a
     * la
     * tarea.
     * 
     * @param titulo
     */
    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Setter de la descripción de la tarea, simplemente sirve para asignarle una
     * descripción a la tarea.
     * 
     * @param descripcion
     */
    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
