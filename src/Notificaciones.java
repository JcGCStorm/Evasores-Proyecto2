import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que representa una ventana de notificaciones de tareas vencidas y próximas a vencer
 * en nuestra interfaz gráfica. Implementa la interfaz Observer para ser notificada de los cambios
 * en el estado de las tareas.
 */
public class Notificaciones extends JFrame implements Observer {
    private JTextArea textArea;

    /**
     * Constructor de la clase Notificaciones. El cual inicializa la ventana de notificaciones
     * con una altura y anchura predeterminada, y se agrega un WindowListener para cerrar la ventana.
     */
    public Notificaciones() {
        setTitle("Notificaciones de Tareas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // se agrega un WindowListener para  cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); 
            }
        });
    }


    /**
     * Método que actualiza la ventana de notificaciones con las tareas vencidas y próximas a vencer.
     * Muestra un mensaje con las tareas vencidas y próximas a vencer, o un mensaje indicando que no hay tareas
     * vencidas o próximas a vencer.
     * @param tareasVencidas Lista de tareas vencidas.
     * @param tareasProximas Lista de tareas próximas a vencer.
     */
    @Override
    public void update(List<Tarea> tareasVencidas, List<Tarea> tareasProximas) {
        StringBuilder mensaje = new StringBuilder();

        if (!tareasVencidas.isEmpty()) {
            mensaje.append("Tareas Vencidas:\n");
            for (Tarea tarea : tareasVencidas) {
                mensaje.append("- ").append(tarea.getTitulo()).append("\n");
            }
        } else {
            mensaje.append("No hay tareas vencidas.\n");
        }

        mensaje.append("\n");

        if (!tareasProximas.isEmpty()) {
            mensaje.append("Tareas Próximas a Vencer:\n");
            for (Tarea tarea : tareasProximas) {
                mensaje.append("- ").append(tarea.getTitulo()).append("\n");
            }
        } else {
            mensaje.append("No hay tareas próximas a vencer.\n");
        }

        textArea.setText(mensaje.toString());
        setVisible(true);
        toFront(); 
    }
}
