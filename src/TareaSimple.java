import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class TareaSimple implements Tarea {
    String nombreArchivo = "tareas.txt";
    private String tipo = "simple";
    private String titulo;
    private String descripcion;
    private String fechaCreacion;
    private String fechaVencimiento;
    private boolean completada;

    public TareaSimple(String tipo,String titulo, String descripcion, String fechaCreacion, 
                       boolean completada) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.completada = completada;
    }

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
        System.out.print("¿Completada? (si/no): ");
        String completada = scanner.next();
        boolean completadaB = false;
        if (completada.equals("si")) {
            System.out.println("Tarea completada");
             completadaB = true;
        } else if (completada.equals("no")){
            System.out.println("Tarea no completada");
             completadaB = false;
        }
        
        System.out.println("\nTarea creada:");
        String tarea = "Título: " + titulo + "\nDescripción: " + descripcion + 
        "\nFecha de creación: " + fechaCreacion + "\nCompletada: " + completadaB + "\n";
        System.out.println(tarea);
        TareaSimple tareaS = new TareaSimple("simple",titulo, descripcion, fechaCreacion, completadaB);
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
