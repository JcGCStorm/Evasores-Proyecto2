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
                // Dividir la línea en partes utilizando el delimitador ": "
                String[] partes = linea.split(": ", 2);
                // Verificar si hay al menos dos partes (nombre del campo y valor)
                if (partes.length == 2) {
                    String valor = partes[1];
                    valores.add(valor);
                    System.out.println("Comienzo");
                    System.out.println(valores.get(0));
                    System.out.println(contadorSimple);
                    System.out.println(contadorFecha);
                    if (valores.get(0).equals("simple") && contadorSimple == 4) {
                        TareaSimple tarea = new TareaSimple("simple", valores.get(1), valores.get(2),
                                valores.get(3), Boolean.parseBoolean(valores.get(4)));
                        tareas.add(tarea);
                        System.out.println(tarea.getDescripcion());
                        System.out.println(tareas);
                        contadorSimple = 0;
                        System.out.println(valores);
                        valores.clear();
                    } else if (valores.get(0).equals("simple")) {
                        contadorSimple++;
                    } else if (valores.get(0).equals("con fecha") && contadorFecha == 5) {
                        TareaConFecha tarea = new TareaConFecha("con fecha", valores.get(1), valores.get(2),
                                valores.get(3), valores.get(4), Boolean.parseBoolean(valores.get(5)));
                        tareas.add(tarea);
                        System.out.println(tarea.getDescripcion());
                        System.out.println(tarea);
                        contadorFecha = 0;
                        System.out.println(valores);
                        valores.clear();
                    } else if (valores.get(0).equals("con fecha")) {
                        System.out.println(valores.get(0));
                        contadorFecha++;
                        System.out.println(valores);
                        System.out.println(contadorFecha + "fecha");
                    }
                    System.out.println(tareas);
                }
            }
        } catch (IOException e) {
            System.err.println("Aún no tienes tareas.");
        }
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
