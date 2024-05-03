import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class TareaConFecha implements Tarea {
    String nombreArchivo = "tareas.txt";
    private String tipo = "con fecha";
    private String titulo;
    private String descripcion;
    private String fechaCreacion;
    private String fechaVencimiento;
    private boolean completada;

    /**
     * Constructor de la clase TareaConFecha, con los atributos que una tarea con fecha debería tener,
     * es decir, todos los atributos posibles A MENOS QUE LO CAMBIEMOS EN UN FUTURO.
     */
    public TareaConFecha(String tipo, String titulo, String descripcion, String fechaCreacion,
            String fechaVencimiento, boolean completada) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
        this.completada = completada;
    }

    /**
     * Método que se encarga de construir una tarea con fecha, pidiendo al usuario los detalles
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
        System.out.print("Fecha de creación (dd/MM/yyyy): ");
        String fechaCreacion = scanner.next();
        System.out.print("Fecha de vencimiento (dd/MM/yyyy): ");
        String fechaVencimiento = scanner.next();
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
        String tarea = "Tipo: " + "con fecha" + "\nTítulo: " + titulo + "\nDescripción: " + descripcion +
                "\nFecha de creación: " + fechaCreacion + "\nFecha de Vencimiento: " + fechaVencimiento
                + "\nCompletada: " + completadaB + "\n";
        System.out.println(tarea);
        TareaConFecha tareaS = new TareaConFecha("con fecha", titulo, descripcion, fechaCreacion, fechaVencimiento,
                completadaB);
        TareasAlmacen.guardaTarea(tareaS);

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

    // Getters de los atributos de las tareas con fecha.
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

}
