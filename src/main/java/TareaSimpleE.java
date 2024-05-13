package main.java;
/// CLASE DE PRUEBA DE TAREA SIMPLE CON ESTADOSSSSS
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TareaSimpleE implements TareaE {


   private TareaEstado estado; // Estado actual de la tarea
    private String tipo = "simple";
    private String titulo;
    private String descripcion;
    private String etiquetas;
    private LocalDate fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private boolean completada;

    public TareaSimpleE(String tipo, String titulo, String descripcion, String etiquetas,
                       LocalDate fechaCreacion, boolean completada) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.fechaCreacion = fechaCreacion;
        this.completada = completada;
        this.estado = new TareaPendiente(); // inicialmente pendiente
    }


    @Override
    public void iniciar() {
       // estado.iniciar(this); 
    }

    @Override
    public void completar() {
      //  estado.completar(this); 
    }

    @Override
    public void volverPendiente() {
      //  estado.volverPendiente(this); 
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
    public void construyeTarea() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese detalles de la tarea simple:");
        System.out.print("Título: ");
        this.titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        this.descripcion = scanner.nextLine();

        System.out.print("¿Completada? (si/no): ");
        String completadaInput = scanner.nextLine().trim().toLowerCase();
        this.completada = completadaInput.equals("si");

        if (this.completada) {
            this.estado = new TareaCompletada();
        }

        // Guardar la tarea en un archivo de texto
        try {
            FileWriter salida = new FileWriter("tareas.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(salida);

            String tareaString = "Tipo: " + tipo + "\nTítulo: " + titulo + "\nDescripción: " + descripcion +
                                 "\nFecha de creación: " + fechaCreacion +
                                 "\nCompletada: " + completada + "\n";

            bufferedWriter.write(tareaString);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error al guardar la tarea: " + e.getMessage());
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
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public LocalDateTime getFechaVencimiento() {
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
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    @Override
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}


