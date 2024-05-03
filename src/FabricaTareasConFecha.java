public class FabricaTareasConFecha extends FabricaTareas {
    @Override
    public Tarea construyeTarea() {
        return new TareaConFecha("con fecha", null, null, null, null, false);
    }
}
