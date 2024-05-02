public class GestorDeTareas {
private static GestorDeTareas instanciaUnica;

    private GestorDeTareas() {
       
    }

    public static GestorDeTareas getInstancia() {
        if(instanciaUnica == null){
            instanciaUnica = new GestorDeTareas();
        }
        return instanciaUnica;
    }
}
