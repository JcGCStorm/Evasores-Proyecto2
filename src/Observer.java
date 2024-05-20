import java.util.List;

/**
 * Interfaz que define el comportamiento de un objeto que puede observar a otros objetos.
 */
public interface Observer {
    
    /**
     * Método que actualiza al observador con las tareas vencidas y próximas a vencer.
     * @param tareasVencidas Lista de tareas vencidas.
     * @param tareasProximas Lista de tareas próximas a vencer.
     */
    void update(List<Tarea> tareasVencidas, List<Tarea> tareasProximas);

}