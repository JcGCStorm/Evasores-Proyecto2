import java.util.List;

/**
 * Interfaz que define el comportamiento de un objeto que puede ser observado por otros objetos.
 */
public interface Observable {
    void agregarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notificarObservers();
}
