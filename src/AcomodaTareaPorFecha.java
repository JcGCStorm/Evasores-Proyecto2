import java.util.List;
public class AcomodaTareaPorFecha implements AcomodaTareaStrategy{

    //Metodo para ordenar tareas por fecha

   @Override
    public void acomodaTarea(Tarea tarea){
      // que de hecho aquí no entendí por que no te dejaba usar el nombre tarea
      // decia que ya lo usabas en otro lado, pero literalmente este es el único
      // metodo que tiene la clase xd
        List<Tarea> tareaPorFecha = TareasAlmacen.getTareas();

        tareaPorFecha.sort((tarea1, tarea2) -> tarea.getFechaVencimiento().compareTo(tarea.getFechaVencimiento()));
    }    
}
//pues en cuanto terminen ustedes ya me los aviento, solo son metodos de ordenacion
// Vaaaa, entonces ya nomas es checar los otros metodos. Va que va man, si quieres ya mañana
// o le intentamos otro rato?