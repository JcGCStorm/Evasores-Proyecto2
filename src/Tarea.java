public interface Tarea {
    void construyeTarea();

    String getTipo();

    String getTitulo();

    String getDescripcion();

    String getFechaCreacion();

    void setFechaCreacion(String fechaCreacion);

    String getFechaVencimiento();

    boolean isCompletada();

    void setCompletada(boolean completada);

    // String getPrioridad();
     String getEtiquetas();
}
