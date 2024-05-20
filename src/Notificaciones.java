import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Notificaciones extends JFrame implements Observer {
    private JTextArea textArea;

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
