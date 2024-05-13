import java.util.Scanner;

public class MAIN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TareasProxy proxy = new TareasProxyImpl();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    crearUsuario();
                    break;
                case "2":
                    Usuario usuario = iniciarSesion(proxy, scanner);
                    if (usuario != null) {
                        menuTareas(proxy, usuario, scanner);
                    } else {
                        System.out.println("Inicio de sesión fallido. Verifique sus credenciales.");
                    }
                    break;

                case "3":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
                    break;
            }
        }

        scanner.close();
    }

    private static void crearUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creación de nuevo usuario...");
        String username;
        do {
            System.out.print("Ingrese un nombre de usuario (debe tener más de 4 caracteres): ");
            username = scanner.nextLine().trim();

            if (username.length() <= 4) {
                System.out.println("El nombre de usuario debe tener más de 4 caracteres.");
            }
        } while (username.length() <= 4);

        if (UsuarioAlmacen.obtenerUsuario(username) != null) {
            System.out.println("El nombre de usuario ya está en uso. Por favor, elija otro.");
            return;
        }
        String password;
        boolean passwordValida = false;
        do {
            System.out
                    .print("Ingrese una contraseña (5-16 caracteres, al menos una mayúscula y un caracter especial): ");
            password = scanner.nextLine().trim();

            // longitud
            if (password.length() < 5 || password.length() > 16) {
                System.out.println("La contraseña debe tener entre 5 y 16 caracteres.");
                continue;
            }
            // mayuscula y un caracter especial
            boolean contieneMayuscula = false;
            boolean contieneEspecial = false;
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    contieneMayuscula = true;
                }
                if (!Character.isLetterOrDigit(c)) {
                    contieneEspecial = true;
                }
            }
            if (!contieneMayuscula) {
                System.out.println("La contraseña debe contener al menos una mayúscula.");
                continue;
            }
            if (!contieneEspecial) {
                System.out.println("La contraseña debe contener al menos un caracter especial.");
                continue;
            }
            passwordValida = true;
        } while (!passwordValida);

        // Crear y almacenar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(username, password);
        UsuarioAlmacen.agregarUsuario(nuevoUsuario);
        System.out.println("Usuario creado correctamente.");
    }

    private static Usuario iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inicio de sesión:");
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();

        // Verificar las credenciales
        if (UsuarioAlmacen.verificarCredenciales(username, password)) {
            System.out.println("Inicio de sesión exitoso.");
            return UsuarioAlmacen.obtenerUsuario(username);
        } else {
            System.out.println("Credenciales incorrectas.");
            return null;
        }
    }

    private static Usuario iniciarSesion(TareasProxy proxy, Scanner scanner) {
        System.out.println("Inicio de sesión:");
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();

        // Verificar las credenciales utilizando el proxy
        return proxy.iniciarSesion(username, password);
    }

    private static void menuTareas(Usuario usuario, Scanner scanner) {
        boolean opcionesBool = true;
        while (opcionesBool) {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Ver Tareas");
            System.out.println("2. Crear Tareas");
            System.out.println("3. Modificar Tareas");
            System.out.println("4.  Compartir Tareas");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    VistaTareas.verTareas(usuario);
                    break;
                case "2":
                    TareasControlador.crearTarea(usuario);
                    break;
                case "3":
                    TareasControlador modificaTarea = new TareasControlador();
                    modificaTarea.modifica(usuario);
                    break;

                case "0":
                    opcionesBool = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    private static void menuTareas(TareasProxy proxy, Usuario usuario, Scanner scanner) {
        TareasControlador controlador = new TareasControlador();
        boolean opcionesBool = true;
        while (opcionesBool) {
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("1. Ver Tareas");
            System.out.println("2. Crear Tareas");
            System.out.println("3. Modificar Tareas");
            System.out.println("4. Borrar Tareas");
            System.out.println("5. Ver tareas por Fecha");
            System.out.println("6. Ver tareas por Prioridad");

            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    VistaTareas.verTareas(usuario);//
                    break;
                case "2":
                    TareasControlador.crearTarea(usuario); // Utilizando el proxy para crear una tarea
                    break;
                case "3":
                    proxy.modificarTarea(usuario); // Utilizando el proxy para modificar una tarea
                    break;
                case "4":
                    TareasControlador control = new TareasControlador();
                    control.eliminaTarea(usuario); // Utilizando el proxy para compartir una tarea
                    break;
                // case "5":
                // AcomodaTareaPorFecha.acomodaTarea(usuario);

                // case "6":
                // AcomodaTareaPorPrioridad.acomodaTarea(usuario);

                case "0":
                    opcionesBool = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

}