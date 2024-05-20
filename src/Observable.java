import java.util.List;

public interface Observable {
    void agregarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notificarObservers();
}
