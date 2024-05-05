import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class TareaSimple implements Tarea {
    String nombreArchivo = "tareas.txt";
    private String tipo = "simple";
    private String titulo;
    private String descripcion;
    private String etiquetas;
    private String fechaCreacion;
    private String fechaVencimiento;
    private boolean completada;

    /**
     * Constructor de la clase TareaSimple, con los atributos que una tarea simple debería tener,
     * es decir, todos menos una fecha de vencimiento.
     */
    public TareaSimple(String tipo, String titulo, String descripcion, String etiquetas ,
    String fechaCreacion, boolean completada) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.fechaCreacion = fechaCreacion;
        this.completada = completada;
    }

    /**
     * Método que se encarga de construir una tarea simple, pidiendo al usuario los detalles
     * de la misma, ademas que la guarda en un archivo de texto.
     */
    @Override
    public void construyeTarea() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los detalles de la tarea:");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Etiquetas: ");
        Etiqueta agregaEtiqueta = new Etiqueta();
        Tarea tareaTemp;
        tareaTemp = agregaEtiqueta.etiquetaTarea(titulo, descripcion, tipo);
        etiquetas = tareaTemp.getEtiquetas();
        System.out.print("Fecha de creación (dd/MM/yyyy): ");
        String fechaCreacion = scanner.next();
        tareaTemp.setFechaCreacion(fechaCreacion);
        System.out.print("¿Completada? (si/no): ");
        String completada = scanner.next();
        
        boolean completadaB = false;
        if (completada.equals("si")) {
            System.out.println("Tarea completada");
            completadaB = true;
            tareaTemp.setCompletada(completadaB);
        } else if (completada.equals("no")) {
            System.out.println("Tarea no completada");
            completadaB = false;
            tareaTemp.setCompletada(completadaB);
        }

        System.out.println("\nTarea creada:");
        String tarea = "Tipo: " + "simple" + "\nTítulo: " + titulo + "\nDescripción: " + descripcion 
        + "\nEtiquetas: " + etiquetas + "\nFecha de creación: " + fechaCreacion + "\nCompletada: " + completadaB + "\n";
        System.out.println(tarea);
        TareasAlmacen.guardaTarea(tareaTemp);

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
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public boolean isCompletada() {
        return completada;
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
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

}
