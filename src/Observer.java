import java.util.List;

public interface Observer {

    void update(List<Tarea> tareasVencidas, List<Tarea> tareasProximas);

}