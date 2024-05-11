import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Etiqueta {
    Scanner scanner = new Scanner(System.in);

    /**
     * Realiza una compra de productos según la opción seleccionada por el usuario.
     * Este es parte de nuestro decorator ya que imprime los objetos que compramos
     * junto al catalogo.
     * 
     * @param opcionUsuario La opción seleccionada por el usuario.
     * @return true si la compra se realizó con éxito, false si la compra fue
     *         cancelada o no se pudo completar.
     */
    public Tarea etiquetaTarea(String titulo, String descripcion, String tipo) {
        System.out.println();
        Tarea tarea;
        switch (tipo) {
            case "simple":
                tarea = new TareaSimple("simple", titulo, descripcion, "", LocalDate.now(), false,
                        new TareaPendiente());
                break;
            case "con fecha":

                tarea = new TareaConFecha("con fecha", titulo, descripcion, "", LocalDate.now(), LocalDateTime.now(),
                        false, new TareaPendiente());
                break;
            default:
                System.out.println("No has seleccionado una opción válida, vuélvelo a intentar :D");
                tarea = null;
        }
        if (tarea != null) {
            boolean ponmeMas = true;
            while (ponmeMas) {
                try {
                    System.out.println("¿Deseas agregar etiquetas a tu tarea?\nSi/No");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        ponmeMas = false;
                    } else {
                        System.out.println("¿Qué etiqueta deseas agregar a tu tarea?");
                        System.out.println("1. Estudio.");
                        System.out.println("2. Trabajo.");
                        System.out.println("3. Personal.");
                        System.out.println("4. Deportes.");
                        System.out.println("5. Comida.");
                        System.out.println("6. Salud.");
                        System.out.println("7. Entretenimiento.");
                        System.out.println("8. Hogar.");
                        System.out.println("9. Viaje.");
                        System.err.println("10. Compras.");
                        System.out.println("11. Social");
                        System.out.println("12. Asambleas");
                        System.out.println("13. Otro.");
                        System.out.println("0. No agregar más etiquetas");
                        int productoEleccion = scanner.nextInt();
                        if (productoEleccion == 0) {
                            ponmeMas = false;
                        } else {
                            switch (productoEleccion) {
                                case 1:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta estudio a tu tarea.");
                                    break;
                                case 2:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta trabajo a tu tarea.");
                                    break;
                                case 3:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Personal a tu tarea.");
                                    break;
                                case 4:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Deportes a tu tarea.");
                                    break;
                                case 5:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Comida a tu tarea.");
                                    break;
                                case 6:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Salud a tu tarea.");
                                    break;
                                case 7:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Entretenimiento a tu tarea.");
                                    break;
                                case 8:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Hogar a tu tarea.");
                                    break;
                                case 9:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Viaje a tu tarea.");
                                    break;
                                case 10:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Compras a tu tarea.");
                                    break;
                                case 11:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Social a tu tarea.");
                                    break;
                                case 12:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Asambleas a tu tarea.");
                                    break;
                                case 13:
                                    tarea = agregarEtiquetas(productoEleccion, tarea);
                                    System.out.println("Se ha agregado la etiqueta Otro a tu tarea.");
                                    break;
                                case 0:
                                    ponmeMas = false;
                                default:
                                    System.out.println("No seleccionaste una opción válida ):");
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Debes ingresar el número, no debes ingresar letras.");
                    ponmeMas = false;
                }
            }
        }
        return tarea;
    }

    /**
     * Metodo agregarProductos que nos ayuda en el Decorator para poder
     * añadir elementos al carrito, recibe un int y un producto, y agrega
     * un producto al carrito.
     * 
     * @param tipoProducto un int que nos ayuda a controlar el numero de
     *                     productos.
     * @param producto     el carrito que vamos a "envolver" con un producto.
     * @return el producto con el elemento agregado.
     */
    private Tarea agregarEtiquetas(int tipoProducto, Tarea tarea) {
        switch (tipoProducto) {
            case 1:
                return new EtiquetaEstudio(tarea);
            case 2:
                return new EtiquetaTrabajo(tarea);
            case 3:
                return new EtiquetaPersonal(tarea);
            case 4:
                return new EtiquetaDeportes(tarea);
            case 5:
                return new EtiquetaComida(tarea);
            case 6:
                return new EtiquetaSalud(tarea);
            case 7:
                return new EtiquetaEntretenimiento(tarea);
            case 8:
                return new EtiquetaHogar(tarea);
            case 9:
                return new EtiquetaViaje(tarea);
            case 10:
                return new EtiquetaCompras(tarea);
            case 11:
                return new EtiquetaSocial(tarea);
            case 12:
                return new EtiquetaAsambleas(tarea);
            case 13:
                return new EtiquetaOtro(tarea);
            default:
                return tarea;
        }
    }

    public String etiquetasString(Tarea tarea) {
        return tarea.getEtiquetas();
    }
}
