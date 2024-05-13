public abstract class FabricaTareas {

   /**
    * Este metodo se encarga de crear una tarea, simplemente manda a llamar
    * al metodo construyeTarea() que es el que se encarga de crear la tarea
    * y que es abstracto y se implementa en las clases que heredan a esta, es decur
    * las clases hijas van a implementar el método construyeTarea según se
    * requiera.
    * y finalmente regresar la tarea creada.
    * 
    * @param usuario
    * @return
    */
   public Tarea crear(Usuario usuario) {
      Tarea tarea = construyeTarea();
      tarea.construyeTarea(usuario);
      return tarea;
   }

   protected abstract Tarea construyeTarea();
}