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

    /*
     * Este metodo creo que de momento no lo uso para nada
     */
    static List<Tarea> tareas = new ArrayList<>();

    public TareasAlmacen(Tarea tarea) {
        tareas.add(tarea);
    }

    /**
     * Lo mismo que el anterior, creo que no lo uso
     */
    public static void guardaTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    /**
     * Del arrayList de tareas, simplemente lo recorre y va mostrando
     * los atributos de cada tarea.
     */
    public static void muestraTareas() {
        for (Tarea tarea : tareas) {
            System.out.println(tarea.getTitulo());
            System.out.println(tarea.getDescripcion());
            System.out.println(tarea.getFechaCreacion());
            System.out.println(tarea.getFechaVencimiento());
            System.out.println(tarea.isCompletada());
        }
    }

    /**
     * Este metodo imprime  las tareas del archivo txt, primero lee el archivo 
     * linea por linea y las va imprimiendo, si no hay tareas, imprime un mensaje
     * diciendo que no hay tareas.
     */
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


    /**
     * Este metodo es el más perrillo y es el que se encarga de leer el archivo
     * Del txt va recorriendo linea por linea y separa los valores por el ": "
     * es decir, en el caso de que modifiquemos los atributos de las tareas,
     * vamos a tener que modificar ligeramente este metodo, solamente en la
     * parte de la creación de la tarea, pero solo eso. 
     */
    public static void getTareas() {
        // el  nombre del archivo que vamos a leer
        String nombreArchivo = "tareas.txt";
        try {
            // Creamos la instancia de BufferedReader para leer el archivo
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            // la linea que está leyendo
            String linea;
            // el arreglo donde vamos  a meter los atributos de las tareas del txt
            List<String> valores = new ArrayList<>();

            // Contadores para cada tipo de tarea, nos sirven para leer el
            // archivo de una forma o de otra segun el tipo de tarea pues
            // las tareas simples tienen 4 atributos y las tareas con fecha 5
            int contadorSimple = 0;
            int contadorFecha = 0;
            // este while pasa por todas las lineas del archivo mientras no sean
            // nulas, cuando lo sean significa que terminamos de recorrer el archivo
            //por lo que termina el while. Actualiza la variable linea en cada iteración
            // y la va comparando con null
            while ((linea = br.readLine()) != null) {
                // esto divide la linea en dos partes, el titulo de la tarea y los atributos
                // de la tarea, metemos el titulo en la primera parte del arreglo y los atributos
                // en la segunda parte
                String[] partes = linea.split(": ", 2);
                // Verificamos que tenga dos partes solo para evitar fallos,
                // si no tiene dos partes no es una tarea válida
                if (partes.length == 2) {
                    // sacamos el valor del arreglo creado anteriormente
                    String valor = partes[1];
                    // lo metemos en el arreglo de valores
                    valores.add(valor);
                    // Aquí siempre el primer atributo de una tarea será su tipo, por lo que
                    // verificamos si es simple o con fecha, si es simple, verificamos si el contador
                    // de tareas simples es 4, si es así, creamos la tarea y la metemos en el arreglo
                    // el contador es importante pues nos dice cuantos atributos de la tarea simple
                    // ya hemos leido, si ya leimos los 4, creamos la tarea y la metemos en el arreglo
                    if (valores.get(0).equals("simple") && contadorSimple == 4) {
                        // creamos la tarea simple
                        TareaSimple tarea = new TareaSimple("simple", valores.get(1), valores.get(2),
                                valores.get(3), Boolean.parseBoolean(valores.get(4)));
                        // la metemos en el arreglo de tareas
                        tareas.add(tarea);
                        // reiniciamos el contador de tareas simples, pues posiblemente exista más de 
                        // una tarea simple en el archivo
                        contadorSimple = 0;
                        // limpiamos el arreglo de valores para que no se mezclen los atributos de las tareas
                        // y sobre todo para verificar que el atributo 0 sea el tipo de la tarea.
                        valores.clear();
                    // si no hemos leido los 4 atributos de la tarea simple, incrementamos el contador
                    } else if (valores.get(0).equals("simple")) {
                        contadorSimple++;
                    // si la tarea es con fecha, verificamos si el contador de tareas con fecha es 5
                    // si es así, creamos la tarea y la metemos en el arreglo de tareas., lo mismo que en 
                    // el anterior
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
            // en caso de que no existan tareas en el archiiivo mandamos el mensaje.
        } catch (IOException e) {
            System.err.println("Aún no tienes tareas.");
        }
        // esto solo lo puse para ver si funcionaba o non, simplemente imprime el arreglo
        // con las tareas, pero no las visualiza en la consola, es decir, no las imprime
        System.out.println(tareas);
    }

    /**
     * El clasico getter y setter
     */
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
