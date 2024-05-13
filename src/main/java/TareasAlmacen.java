package main.java;
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
     * Arreglo que guarda las tareas.
     */
    static List<Tarea> tareas = new ArrayList<>();

    /**
     * Simplemente nos ayuda a guardar una tarea en el arreglo de tareas.
     * 
     * @param tarea la tarea que queremos guardar.
     */
    public static void guardaTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    /**
     * Este metodo simplemente nos ayuda a obtener el arreglo de tareas.
     * 
     * @return el arreglo de tareas.
     */
    public static List<Tarea> obtenArreglo() {
        return tareas;
    }

    /**
     * Este metodo nos ayuda a obtener el nombre del archivo de tareas de un usuario
     * en específico.
     * 
     * @param usuario el usuario del que queremos obtener el archivo de tareas.
     * @return el nombre del archivo de tareas del usuario.
     */
    private static String obtenerArchivoTareasUsuario(Usuario usuario) {
        return usuario.getUsername() + "_tareas.txt";
    }

    /**
     * Este metodo es de los más importantes ya que es el que se encarga de leer
     * el archivo de texto de cada usuario y transforma ese texto a una tarea
     * va recorriendo el archivo de un usuario especifico linea por linea y separa
     * los valores por el ": "es decir, cada que encuentra dos puntos separa la
     * linea
     * en 2 partes y guarda los valores
     * 
     * @param usuario el usuario del que queremos obtener las tareas.
     * @return el arreglo de tareas del usuario.
     */
    public static List<Tarea> getTareas(Usuario usuario) {
        // el nombre del archivo que vamos a leer según el usuario
        String nombreArchivo = obtenerArchivoTareasUsuario(usuario);
        // limpiamos el arreglo de tareas para que no se mezclen las tareas de un
        // usuario con otro
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
            // las tareas simples tienen 6 atributos y las tareas con fecha 7
            int contadorSimple = 0;
            int contadorFecha = 0;
            // este while pasa por todas las lineas del archivo mientras no sean
            // nulas, cuando lo sean significa que terminamos de recorrer el archivo
            // por lo que termina el while. Actualiza la variable linea en cada iteración
            // y la va comparando con null
            while ((linea = br.readLine()) != null) {
                // esto divide la linea en dos partes, los campos de la tarea y los atributos
                // de la tarea, metemos el titulo en la primera parte del arreglo y los
                // atributos
                // en la segunda parte
                String[] partes = linea.split(": ", 2);
                // Verificamos que tenga dos partes para evitar fallos,
                // si no tiene dos partes no es una tarea válida
                if (partes.length == 2) {
                    // sacamos el valor del arreglo creado anteriormente
                    String valor = partes[1];
                    // lo metemos en el arreglo de valores
                    valores.add(valor);
                    // Aquí siempre el primer atributo de una tarea será su tipo, por lo que
                    // verificamos si es simple o con fecha, si es simple, verificamos si el
                    // contador de tareas simples es 4, si es así, creamos la tarea y la metemos
                    // en el arreglo el contador es importante pues nos dice cuantos atributos
                    // de la tarea simple ya hemos leido, si ya leimos los 4, creamos la tarea
                    // y la metemos en el arreglo
                    if (valores.get(0).equals("simple") && contadorSimple == 6) {
                        // le damos formato a la fecha
                        TareaEstado estado = getTareaEstado(valores.get(6));
                        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fechaHora = LocalDate.parse(valores.get(4), formateador);
                        // creamos la tarea simple
                        TareaSimple tarea = new TareaSimple("simple", valores.get(1), valores.get(2),
                                valores.get(3), fechaHora,
                                Integer.parseInt(valores.get(5)), estado);
                        // le asignamos los valores a la tarea
                        tarea.setTitulo(valores.get(1));
                        tarea.setDescripcion(valores.get(2));
                        tarea.setEtiquetas(valores.get(3));
                        tarea.setFechaCreacion(fechaHora);
                        tarea.setPrioridad(Integer.parseInt(valores.get(5)));
                        tarea.setEstado(estado);
                        tarea.setEstado(estado);
                        // la metemos en el arreglo de tareas
                        tareas.add(tarea);
                        // reiniciamos el contador de tareas simples, pues posiblemente exista más de
                        // una tarea simple en el archivo
                        contadorSimple = 0;
                        // limpiamos el arreglo de valores para que no se mezclen los atributos de las
                        // tareas y sobre todo para verificar que el atributo 0 sea el tipo de la tarea
                        // que sigue.
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
                        // le damos formato a las fechasS
                        TareaEstado estado = getTareaEstado(valores.get(7));
                        DateTimeFormatter formateadorCreacion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate fecha = LocalDate.parse(valores.get(4), formateadorCreacion);
                        DateTimeFormatter formateadorVencimiento = DateTimeFormatter
                                .ofPattern("dd-MM-yyyy 'Hora:' HH:mm");
                        LocalDateTime fechaHora = LocalDateTime.parse(valores.get(5), formateadorVencimiento);
                        TareaConFecha tarea = new TareaConFecha("con fecha", valores.get(1), valores.get(2),
                                valores.get(3), fecha, fechaHora, Integer.parseInt(valores.get(6)), estado);
                        // le asignamos los valores a la tarea
                        tarea.setTitulo(valores.get(1));
                        tarea.setDescripcion(valores.get(2));
                        tarea.setEtiquetas(valores.get(3));
                        tarea.setFechaCreacion(fecha);
                        tarea.setFechaVencimiento(fechaHora);
                        tarea.setPrioridad(Integer.parseInt(valores.get(6)));
                        tarea.setEstado(estado);
                        // la metemos en el arreglo de tareas
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
            System.err.println("Aún no tienes tareas." + e.getMessage());
        }
        return tareas;
    }

    /**
     * Este metodo nos ayuda a obtener el estado de la tarea, es decir, pasamos de
     * una string
     * que representa el estado de la tarea a una instancia de la clase TareaEstado,
     * con la cuál
     * vamos a poder cambiar el estado de la tarea.
     * 
     * @param estado el estado de la tarea en forma de string.
     * @return la instancia de la clase TareaEstado.
     */
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
