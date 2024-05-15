import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Etiqueta {
    /**
     * Solo son las opciones de etiquetas que se pueden agregar a una tarea. Las
     * creo aquí por
     * que
     * después cada una actualiza su valor para que no se pueda agregar más de
     * una
     * y se pueda ver reflejado en el 'menu' de etiquetas.
     */
    Scanner scanner = new Scanner(System.in);
    String estudio = "1. Estudio.";
    String trabajo = "2. Trabajo.";
    String personal = "3. Personal.";
    String deportes = "4. Deportes.";
    String comida = "5. Comida.";
    String salud = "6. Salud.";
    String entretenimiento = "7. Entretenimiento.";
    String hogar = "8. Hogar.";
    String viaje = "9. Viaje.";
    String compras = "10. Compras.";
    String social = "11. Social.";
    String asambleas = "12. Asambleas.";
    String otro = "13. Otro.";
    int[] etiquetasContador = new int[14];

    /**
     * Agrega una etiqueta según la opción seleccionada por el usuario.
     * 
     * @param titulo      el titulo de la tarea
     * @param descripcion la descripcion de la tare
     * @param tipo        el tipo de tarea
     * @return Tarea, la tarea con la(s) etiqueta(s) agregada(s).
     */
    public Tarea etiquetaTarea(String tipo, String etiqueta) {
        Tarea tarea;
        switch (tipo) {
            case "simple":
                tarea = new TareaSimple("simple", tipo, tipo, "", LocalDate.now(), 0,
                        new TareaPendiente());
                break;
            case "con fecha":

                tarea = new TareaConFecha("con fecha", tipo, tipo, "", LocalDate.now(), LocalDateTime.now(),
                        0, new TareaPendiente());
                break;
            default:
                System.out.println("No has seleccionado una opción válida, vuélvelo a intentar :D");
                tarea = null;
        }
        if (tarea != null) {
            while (!etiqueta.equals("0. Salir")){
                try {
                    String[] options = { estudio, trabajo, personal, deportes, comida, salud, 
            entretenimiento, hogar, viaje, compras, social, asambleas, otro, "0. Salir"};
            etiqueta = (String) JOptionPane.showInputDialog(null, "¿Qué etiqueta desea agregar?", "Etiqueta.",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (etiqueta == null) {
                    etiqueta = "0. Salir";
                }
                        System.out.println("¿Qué etiqueta deseas agregar a tu tarea?");
                        System.out.println(estudio);
                        System.out.println(trabajo);
                        System.out.println(personal);
                        System.out.println(deportes);
                        System.out.println(comida);
                        System.out.println(salud);
                        System.out.println(entretenimiento);
                        System.out.println(hogar);
                        System.out.println(viaje);
                        System.out.println(compras);
                        System.out.println(social);
                        System.out.println(asambleas);
                        System.out.println(otro);
                        System.out.println("0. No agregar más etiquetas");
                        int productoEleccion = 0;
                            if (etiqueta.equals("1. Estudio.") || etiqueta.equals("1. Estudio. AGREGADA")) {
                                productoEleccion = 1;
                            } else if (etiqueta.equals("2. Trabajo.") || etiqueta.equals("2. Trabajo. AGREGADA")) {
                                productoEleccion = 2;
                            } else if (etiqueta.equals("3. Personal.") || etiqueta.equals("3. Personal. AGREGADA")) {
                                productoEleccion = 3;
                            } else if (etiqueta.equals("4. Deportes.") || etiqueta.equals("4. Deportes. AGREGADA")) {
                                productoEleccion = 4;
                            } else if (etiqueta.equals("5. Comida.") || etiqueta.equals("5. Comida. AGREGADA")) {
                                productoEleccion = 5;
                            } else if (etiqueta.equals("6. Salud.") || etiqueta.equals("6. Salud. AGREGADA")) {
                                productoEleccion = 6;
                            } else if (etiqueta.equals("7. Entretenimiento.")
                                    || etiqueta.equals("7. Entretenimiento. AGREGADA")) {
                                productoEleccion = 7;
                            } else if (etiqueta.equals("8. Hogar.") || etiqueta.equals("8. Hogar. AGREGADA")) {
                                productoEleccion = 8;
                            } else if (etiqueta.equals("9. Viaje.") || etiqueta.equals("9. Viaje. AGREGADA")) {
                                productoEleccion = 9;
                            } else if (etiqueta.equals("10. Compras.") || etiqueta.equals("10. Compras. AGREGADA")) {
                                productoEleccion = 10;
                            } else if (etiqueta.equals("11. Social.") || etiqueta.equals("11. Social. AGREGADA")) {
                                productoEleccion = 11;
                            } else if (etiqueta.equals("12. Asambleas.") || etiqueta.equals("12. Asambleas. AGREGADA")) {
                                productoEleccion = 12;
                            } else if (etiqueta.equals("13. Otro.") || etiqueta.equals("13. Otro. AGREGADA")) {
                                productoEleccion = 13;
                            }
                             
                            switch (productoEleccion) {
                                case 1:
                                    int etiquetaEstudio = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaEstudio == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta estudio a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta estudio a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        estudio = "1. Estudio. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de estudio en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 2:
                                    int etiquetaTrabajo = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaTrabajo == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta trabajo a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta trabajo a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        trabajo = "2. Trabajo. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de trabajo en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 3:
                                    int etiquetaPersonal = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaPersonal == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta personal a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta personal a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        personal = "3. Personal. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta personal en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 4:
                                    int etiquetaDeportes = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaDeportes == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta deportes a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta deportes a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        deportes = "4. Deportes. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de deportes en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 5:
                                    int etiquetaComida = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaComida == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta comida a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta comida a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        comida = "5. Comida. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de comida en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 6:
                                    int etiquetaSalud = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaSalud == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta salud a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta salud a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        salud = "6. Salud. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de salud en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 7:
                                    int etiquetaEntretenimiento = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaEntretenimiento == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta entretenimiento a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta entretenimiento a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        entretenimiento = "7. Entretenimiento. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de entretenimiento en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 8:
                                    int etiquetaHogar = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaHogar == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta hogar a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta hogar a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        hogar = "8. Hogar. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de hogar en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 9:
                                    int etiquetaViaje = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaViaje == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta viaje a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta viaje a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        viaje = "9. Viaje. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de viaje en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 10:
                                    int etiquetaCompras = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaCompras == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta compras a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta compras a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        compras = "10. Compras. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de compras en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 11:
                                    int etiquetaSocial = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaSocial == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta social a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta social a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        social = "11. Social. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta social en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 12:
                                    int etiquetaAsambleas = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaAsambleas == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta asambleas a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta asambleas a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        asambleas = "12. Asambleas. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de asambleas en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                case 13:
                                    int etiquetaOtro = etiquetasContador[productoEleccion] + 1;
                                    if (etiquetaOtro == 1) {
                                        tarea = agregarEtiquetas(productoEleccion, tarea);
                                        VistaTareas.mostrarMensaje("Se ha agregado la etiqueta otro a tu tarea.");
                                        System.out.println("Se ha agregado la etiqueta otro a tu tarea.");
                                        etiquetasContador[productoEleccion]++;
                                        otro = "13. Otro. AGREGADA";
                                    } else {
                                        System.out.println(
                                                "Ya tienes una etiqueta de otro en tu tarea, no puedes agregar más.");
                                    }
                                    break;
                                default:
                                    System.out.println("No seleccionaste una opción válida ):");
                            }
                     //       etiqueta = (String) JOptionPane.showInputDialog(null, "¿Qué etiqueta desea agregar?", "Etiqueta.",
                     //       JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                } catch (Exception e) {
                    System.out.println("Debes ingresar el número, no debes ingresar letras.");
                }
            }
        }
        return tarea;
    }

    /**
     * Metodo agregarEtiquetas que nos ayuda en el Decorator para poder
     * añadir etiquetas a una tarea, recibe un int y una etiqueta, y agrega
     * una etiqueta a la tarea.
     * 
     * @param tipoEtiqueta un int que nos ayuda a controlar el numero de
     *                     productos.
     * @param tarea        la tarea que vamos a "envolver" con una etiqueta.
     * @return la tarea con la etiqueta agregada.
     */
    private Tarea agregarEtiquetas(int tipoEtiqueta, Tarea tarea) {
        switch (tipoEtiqueta) {
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

    /*
     * Este metodo nos ayuda a obtener las etiquetas de una tarea.
     */
    public String etiquetasString(Tarea tarea) {
        return tarea.getEtiquetas();
    }
}
