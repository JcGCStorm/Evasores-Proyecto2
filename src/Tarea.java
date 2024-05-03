public interface Tarea {
    void construyeTarea();

    String getTitulo();

    String getDescripcion();

    String getFechaCreacion();

    String getFechaVencimiento();

    boolean isCompletada();

    // String getPrioridad();
    // String getEtiquetas();
}
