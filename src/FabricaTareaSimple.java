public class FabricaTareaSimple extends FabricaTareas {

    @Override
    protected Tarea construyeTarea() {
     return new TareaSimple("simple", null,null, null, null, false);
       }
    }    
    
