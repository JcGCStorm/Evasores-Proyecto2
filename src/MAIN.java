import javax.swing.SwingUtilities;
public class MAIN {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VistaTareas vistaTareas =new VistaTareas();
                vistaTareas.mostrarInicioSesion();
                
            }
        });
    }
}             