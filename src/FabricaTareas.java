public abstract class FabricaTareas {

   /**
    * Este metodo se encarga de crear una tarea, simplemente manda a llamar
    * al metodo construyeTarea() que es el que se encarga de crear la tarea
    * y que es abstracto y se implementa en las clases que heredan a esta, es decur
    * las clases hijas van a implementar el método construyeTarea según se requiera.    
    * y finalmente regresar la tarea creada.
    */
   public Tarea crear(){
    Tarea tarea = construyeTarea();
    tarea.construyeTarea();
    return tarea;
   }

   protected abstract Tarea construyeTarea();
}