import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class TareasAlmacen {
    private String titulo;
    private String descripcion;
    private String fechaCreacion;
    private String fechaVencimiento;
    private boolean completada;

    static List<Tarea> tareas = new ArrayList<>();

    public TareasAlmacen(Tarea tarea) {
        tareas.add(tarea);
    }

    public static void guardaTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public static void muestraTareas() {
        for (Tarea tarea : tareas) {
            System.out.println(tarea.getTitulo());
            System.out.println(tarea.getDescripcion());
            System.out.println(tarea.getFechaCreacion());
            System.out.println(tarea.getFechaVencimiento());
            System.out.println(tarea.isCompletada());
        }
    }

    public static void verTareas() {
        String nombreArchivo = "tareas.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Aún no tienes tareas.");
        }
    }

    public static void getTareas() {
        String nombreArchivo = "tareas.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            List<String> valores = new ArrayList<>();

            int contadorSimple = 0;
            int contadorFecha = 0;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(": ", 2);
                if (partes.length == 2) {
                    String valor = partes[1];
                    valores.add(valor);
                    if (valores.get(0).equals("simple") && contadorSimple == 5) {
                        TareaSimple tarea = new TareaSimple("simple", valores.get(1), valores.get(2), 
                                        valores.get(3), valores.get(4), Boolean.parseBoolean(valores.get(5)));
                        tareas.add(tarea);
                        System.out.println(contadorSimple);
                        System.out.println(valores);
                        contadorSimple = 0;
                        valores.clear();
                    } else if (valores.get(0).equals("simple")) {
                        contadorSimple++;
                    } else if (valores.get(0).equals("con fecha") && contadorFecha == 5) {
                        TareaConFecha tarea = new TareaConFecha("con fecha", valores.get(1), valores.get(2),
                                valores.get(3), valores.get(4), Boolean.parseBoolean(valores.get(5)));
                        tareas.add(tarea);
                        contadorFecha = 0;
                        valores.clear();
                    } else if (valores.get(0).equals("con fecha")) {
                        contadorFecha++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Aún no tienes tareas.");
        }
        System.out.println(tareas);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

}
