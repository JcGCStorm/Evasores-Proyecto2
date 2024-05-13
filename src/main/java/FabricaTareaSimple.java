package main.java;
public class FabricaTareaSimple extends FabricaTareas {

  /**
   * Este metodo heredado de la fabrica de tareas se encarga de crear
   * una tarea simple, simplemente regresa la tarea pero todo el trabajo
   * lo hace la clase TareaSimple.
   * 
   * @return la tarea simple
   */
  @Override
  protected Tarea construyeTarea() {
    return new TareaSimple("simple", null, null, null, null, 0, null);
  }
}
