import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MAIN {
    public static void main(String[] args) {
        // solo se muestra la interfaz gráfica para el registro o inicio de sesión
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MarcoInicioSesion();
            }
        });
    }

    // clase para la interfaz gráfica no se si esta bn q se quede aqui pero jala
    static class MarcoInicioSesion extends JFrame {
        private JTextField campoNombreUsuario;
        private JPasswordField campoContrasena;
        private JButton botonIniciarSesion;
        private JButton botonRegistrarse;

        public MarcoInicioSesion() {
            setTitle("Gestor de Tareas - Iniciar Sesión/Registrarse");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(4, 2));

            // componentes
            JLabel etiquetaNombreUsuario = new JLabel("Nombre de usuario:");
            campoNombreUsuario = new JTextField();
            JLabel etiquetaContrasena = new JLabel("Contraseña:");
            campoContrasena = new JPasswordField();

            botonIniciarSesion = new JButton("Iniciar Sesión");
            botonRegistrarse = new JButton("Registrarse");

            // añade componentes almarco
            add(etiquetaNombreUsuario);
            add(campoNombreUsuario);
            add(etiquetaContrasena);
            add(campoContrasena);
            add(botonIniciarSesion);
            add(botonRegistrarse);

            // acciones del botón de iniciar sesión
            botonIniciarSesion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String nombreUsuario = campoNombreUsuario.getText();
                    String contrasena = new String(campoContrasena.getPassword());

                    TareasProxy proxy = new TareasProxyImpl();
                    Usuario usuario = proxy.iniciarSesion(nombreUsuario.trim(), contrasena);

                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                        setVisible(false);
                        ejecutarEnConsola(proxy, usuario);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // accioness para el botón de registro
            botonRegistrarse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String nombreUsuario = campoNombreUsuario.getText();
                    String contrasena = new String(campoContrasena.getPassword());

                    if (nombreUsuario.length() <= 4) {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario debe tener más de 4 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (UsuarioAlmacen.obtenerUsuario(nombreUsuario) != null) {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso. Por favor, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!esContrasenaValida(contrasena)) {
                        JOptionPane.showMessageDialog(null, "La contraseña debe tener entre 5 y 16 caracteres, al menos una mayúscula y un caracter especial.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasena);
                    UsuarioAlmacen.agregarUsuario(nuevoUsuario);
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
                }
            });

            // ventana de cierre de la terminal
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas salir del programa?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        e.getWindow().dispose();
                        System.exit(0);
                    }
                }
            });

            setVisible(true);
        }

        private boolean esContrasenaValida(String contrasena) {
            if (contrasena.length() < 5 || contrasena.length() > 16) {
                return false;
            }
            boolean contieneMayuscula = false;
            boolean contieneEspecial = false;
            for (char c : contrasena.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    contieneMayuscula = true;
                }
                if (!Character.isLetterOrDigit(c)) {
                    contieneEspecial = true;
                }
            }
            return contieneMayuscula && contieneEspecial;
        }
    }

    // lo q sigue despues de elegir
    private static void ejecutarEnConsola(TareasProxy proxy, Usuario usuario) {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = JOptionPane.showInputDialog(null, "Ingrese su opción:");
            if (opcion == null) {
                continue;
            }
try {
            switch (opcion) {
                case "1":
                    System.out.println("¿Cómo deseas ver las tareas?\n 1. Por fecha de creación \n 2. Por Prioridad \n 3. Por fecha de vencimiento (solo tareas con fecha)");
                    VistaTareas.verTareas(usuario);
                    break;
                case "2":
                    TareasControlador.crearTarea(usuario);
                    break;
                case "3":
                    proxy.modificarTarea(usuario);
                    break;
                case "4":
                    TareasControlador control = new TareasControlador();
                    control.eliminaTarea(usuario);
                    break;
                case "5":
                    VistaTareas.mostrarTareasXFechaVencimiento(usuario);
                    break;
                case "6":
                    VistaTareas.mostrarTareasXPrioridad(usuario);
                    break;
                case "7":
                    VistaTareas.verCalendario(usuario);
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Inténtelo de nuevo.");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error. Inténtelo de nuevo.");
                        System.exit(0);
        }
      }
      System.exit(0);
    }
    
    // menu principal
    private static void mostrarMenuPrincipal() {
        StringBuilder menu = new StringBuilder();
        menu.append("=========================================\n");
        menu.append("             GESTOR DE TAREAS :D \n");
        menu.append("=========================================\n");
        menu.append("1. Ver Tareas \n");
        menu.append("2. Crear Tareas \n");
        menu.append("3. Modificar Tareas \n");
        menu.append("4. Borrar Tareas \n");
        menu.append("5. Ver tareas por Fecha \n");
        menu.append("6. Ver tareas por Prioridad \n");
        menu.append("7. Ver Calendario \n");
        menu.append("0. Salir \n");
        menu.append("=========================================\n");
        JOptionPane.showMessageDialog(null, menu.toString());
    }
}
                    