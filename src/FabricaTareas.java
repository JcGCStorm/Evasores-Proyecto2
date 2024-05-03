public abstract class FabricaTareas {
   public Tarea crear(){
    Tarea tarea = construyeTarea();
    tarea.construyeTarea();
    return tarea;
   }

   protected abstract Tarea construyeTarea();
}