import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TareasAlmacen {
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

    public static List<Tarea> obtenArreglo() {
        return tareas;
    }

    /**
     * Este metodo es el más perrillo y es el que se encarga de leer el archivo
     * Del txt va recorriendo linea por linea y separa los valores por el ": "
     * es decir, en el caso de que modifiquemos los atributos de las tareas,
     * vamos a tener que modificar ligeramente este metodo, solamente en la
     * parte de la creación de la tarea, pero solo eso.
     */
    public static List<Tarea> getTareas() {
        // el nombre del archivo que vamos a leer
        String nombreArchivo = "tareas.txt";
        tareas.clear();
        try {
            // Creamos la instancia de BufferedReader para leer el archivo
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            // la linea que está leyendo
            String linea;
            // el arreglo donde vamos a meter los atributos de las tareas del txt
            List<String> valores = new ArrayList<>();

            // Contadores para cada tipo de tarea, nos sirven para leer el
            // archivo de una forma o de otra segun el tipo de tarea pues
            // las tareas simples tienen 4 atributos y las tareas con fecha 5
            int contadorSimple = 0;
            int contadorFecha = 0;
            // este while pasa por todas las lineas del archivo mientras no sean
            // nulas, cuando lo sean significa que terminamos de recorrer el archivo
            // por lo que termina el while. Actualiza la variable linea en cada iteración
            // y la va comparando con null
            while ((linea = br.readLine()) != null) {
                // esto divide la linea en dos partes, el titulo de la tarea y los atributos
                // de la tarea, metemos el titulo en la primera parte del arreglo y los
                // atributos
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
                    // verificamos si es simple o con fecha, si es simple, verificamos si el
                    // contador
                    // de tareas simples es 4, si es así, creamos la tarea y la metemos en el
                    // arreglo
                    // el contador es importante pues nos dice cuantos atributos de la tarea simple
                    // ya hemos leido, si ya leimos los 4, creamos la tarea y la metemos en el
                    // arreglo
                    if (valores.get(0).equals("simple") && contadorSimple == 6) {
                        TareaEstado estado = getTareaEstado(valores.get(6));
                        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fechaHora = LocalDate.parse(valores.get(4), formateador);
                        // creamos la tarea simple
                        TareaSimple tarea = new TareaSimple("simple", valores.get(1), valores.get(2),
                                valores.get(3), fechaHora,
                                Boolean.parseBoolean(valores.get(5)), estado);
                        // la metemos en el arreglo de tareas
                        tarea.setTitulo(valores.get(1));
                        tarea.setDescripcion(valores.get(2));
                        tarea.setEtiquetas(valores.get(3));
                        tarea.setFechaCreacion(fechaHora);
                        tarea.setEstado(estado);
                        tareas.add(tarea);
                        tarea.setEstado(estado);
                        // reiniciamos el contador de tareas simples, pues posiblemente exista más de
                        // una tarea simple en el archivo
                        contadorSimple = 0;
                        // limpiamos el arreglo de valores para que no se mezclen los atributos de las
                        // tareas
                        // y sobre todo para verificar que el atributo 0 sea el tipo de la tarea.
                        valores.clear();
                        // si no hemos leido los 4 atributos de la tarea simple, incrementamos el
                        // contador
                    } else if (valores.get(0).equals("simple")) {
                        contadorSimple++;
                        // si la tarea es con fecha, verificamos si el contador de tareas con fecha es 5
                        // si es así, creamos la tarea y la metemos en el arreglo de tareas., lo mismo
                        // que en
                        // el anterior
                    } else if (valores.get(0).equals("con fecha") && contadorFecha == 7) {
                        TareaEstado estado = getTareaEstado(valores.get(7));
                        DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fecha = LocalDate.parse(valores.get(4), formateadorCreacion);
                        DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                                .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
                        LocalDateTime fechaHora = LocalDateTime.parse(valores.get(5), formateadorVencimiento);
                        TareaConFecha tarea = new TareaConFecha("con fecha", valores.get(1), valores.get(2),
                                valores.get(3), fecha, fechaHora, Boolean.parseBoolean(valores.get(6)), estado);
                        tarea.setTitulo(valores.get(1));
                        tarea.setDescripcion(valores.get(2));
                        tarea.setEtiquetas(valores.get(3));
                        tarea.setFechaCreacion(fecha);
                        tarea.setFechaVencimiento(fechaHora);
                        tarea.setEstado(estado);
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
        // esto solo lo puse para ver si funcionaba o non, simplemente imprime el
        // arreglo
        // con las tareas, pero no las visualiza en la consola, es decir, no las imprime
        System.out.println(tareas);
        return tareas;
    }

    public static TareaEstado getTareaEstado(String estado) {
        TareaEstado tareaEstado = null;
        if (estado.equals("Tarea Pendiente")) {
            tareaEstado = new TareaPendiente();
        } else if (estado.equals("Completada")) {
            tareaEstado = new TareaCompletada();
        } else if (estado.equals("Tarea En Progreso")) {
            tareaEstado = new TareaEnProgreso();
        }
        return tareaEstado;
    }
}
