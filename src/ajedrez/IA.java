/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import static ajedrez.Ajedrez.esperar;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Llorenç
 */
public class IA {

    /**
     * Conjunto de movimientos a efectuar por la máquina cuando se intenta
     * resolver un problema. *
     */
    protected ArrayList<ArrayList<String>> variantes = new ArrayList<>();

    /**
     * Este método recibe una serie de parámetros que le permiten efectuar el
     * movimiento siguiente al que efectua el jugador que está resolviendo el
     * problema.
     *
     * @param problema Objeto de la clase base donde se encuentran todos los
     * datos relacionados con los problemas.
     * @param tablero Tablero donde se está resolviendo el problema.
     * @param ventana Ventana donde se está resolviendo el problema.
     * @param numJugada Número de jugada correspondiente al al movimiento
     * efectuado por el jugador que resuelve el problema.
     * @param jugada String con la última jugada efectuada por el jugador que
     * resuelve el problema.
     * @return Este método devuelve null si solo hay una variante en el
     * problema.
     * @throws MovIncorrectoException Excepción que indica si no se pudo
     * realizar el movimiento ,y el motivo de ello.
     */
    public String moverMáquina(Problema problema, Tablero tablero, Ventana ventana, int numJugada, String jugada) throws MovIncorrectoException {
        ArrayList<String> siguienteMovimiento = new ArrayList<>();
        for (int i = 0; i < problema.getListaProblemas().get(tablero).size(); i++) {
            if (jugada.equalsIgnoreCase(problema.getListaProblemas().get(tablero).get(i).get(numJugada))) {
                siguienteMovimiento.add(problema.getListaProblemas().get(tablero).get(i).get(numJugada + 1));
            }
        }
        String movimiento = siguienteMovimiento.remove(0);
        if (siguienteMovimiento.get(0) != null) {
            variantes.add(siguienteMovimiento);
        }
        if (movimiento.length() == 4) {
            problema.moverProblema(problema.stringToMovimiento(movimiento.toUpperCase()), tablero);
        } else {
            tablero.promociondelPeon(problema.stringToMovimiento(movimiento.toUpperCase()), movimiento.toUpperCase().charAt(4));
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        return siguienteMovimiento.get(0);
    }

    /**
     * Este método realiza el movimiento siguiente al cambiar de variante.
     *
     * @param problema Objeto de la clase base donde se encuentran todos los
     * datos relacionados con los problemas.
     * @param tablero Tablero donde se está resolviendo el problema.
     * @param ventana Ventana donde se está resolviendo el problema.
     * @throws MovIncorrectoException Excepción que indica si no se pudo
     * realizar el movimiento ,y el motivo de ello.
     */
    public void moverCambioVariante(Problema problema, Tablero tablero, Ventana ventana) throws MovIncorrectoException {
        if (variantes.get(0).get(0).length() == 4) {
            problema.moverProblema(problema.stringToMovimiento(variantes.get(0).get(0).toUpperCase()), tablero);
        } else {
            tablero.promociondelPeon(problema.stringToMovimiento(variantes.get(0).get(0).toUpperCase()), variantes.get(0).get(0).toUpperCase().charAt(4));
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        variantes.get(0).remove(0);
    }

    /**
     * Este método retrocede el problema y realiza el primer movimiento de la
     * siguiente variante llamando a moverCambioVariante().
     * @param jugadasaRetroceder Número de movimientos que debe retroceder el
     * problema para comenzar la siguiente variante.
     * @param problema Objeto de la clase base donde se encuentran todos los
     * datos relacionados con los problemas.
     * @param tablero Tablero donde se está resolviendo el problema.
     * @param ventana Ventana donde se está resolviendo el problema.
     */
    public void cambiarVariante(int jugadasaRetroceder, Problema problema, Tablero tablero, Ventana ventana) {
        for (int i = 0; i < jugadasaRetroceder; i++) {
            tablero.anularUltimoMovimiento();
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        esperar(3);
        try {
            moverCambioVariante(problema, tablero, ventana);
        } catch (MovIncorrectoException ex) {
            System.out.println("La IA no pudo comenzar la siguiente variante.");
        }
    }
    
    /**
     * Este método comprueba si el jugador acertó el movimiento que corresponde del problema.
     * @param numJugada EL número de jugada que corresponde.
     * @param jugada La jugada correspondiente.
     * @param problema Objeto de la clase base donde se encuentran todos los
     * datos relacionados con los problemas.
     * @param tablero Tablero donde se está resolviendo el problema.
     * @return Devuelve un booleano en función de si acertó o no el jugador.
     */
    public static boolean acertoJugada(int numJugada, String jugada, Tablero tablero, Problema problema) throws IndexOutOfBoundsException {
        boolean acerto = false;
        for (int i = 0; i < problema.getListaProblemas().get(tablero).size() && !acerto; i++) {
            if (jugada.equalsIgnoreCase(problema.getListaProblemas().get(tablero).get(i).get(numJugada))) {
                acerto = true;
            }
        }
        return acerto;
    }    
    /**
     * Este método permite la resolución de un problema interactuando con el jugador.
     * @param problema Objeto de la clase base donde se encuentran todos los
     * datos relacionados con los problemas. 
     */
    public static void resolverProblema(Problema problema) {
        Scanner lc = new Scanner(System.in);
        IA ia = new IA();
        Tablero tablero = problema.problemaAleatorio();
        Ventana ventana = new Ventana(tablero);
        ventana.setBounds(0, 0, 505, 530);
        ventana.setVisible(true);
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        if (tablero.getTurno()) {
            System.out.println("Mueven las blancas");
        } else {
            System.out.println("Mueven las negras");
        }
        int numjugada = 0;
        int numVariantes = problema.getListaProblemas().get(tablero).size();
        for (int i = 0; i < numVariantes; i++) {
            boolean inicio = true;
            String jugada;
            int jugadasaRetroceder = 0;
            if (i == 0) {
                System.out.println("Introduzca una jugada para empezar a resolver el Problema");
                jugada = lc.nextLine();
            } else {
                System.out.println("Introduzca una jugada para comenzar a resolver la siguiente variante");
                jugada = lc.nextLine();
            }
            for (int j = numjugada; j > -1; j = j + 2) {
                if (!inicio) {
                    System.out.println("Introduzca otra jugada");
                    jugada = lc.nextLine();
                }
                try {
                    if (IA.acertoJugada(j, jugada, tablero, problema)) {
                        try {
                            if (jugada.length() == 4) {
                                problema.moverProblema(problema.stringToMovimiento(jugada.toUpperCase()), tablero);
                                ventana.board.pintartablero(tablero);
                                ventana.actualizarpantalla();
                                System.out.println("Bien hecho.");
                                esperar(3);
                                if (ia.moverMáquina(problema, tablero, ventana, j, jugada) != null) {
                                    jugadasaRetroceder = jugadasaRetroceder + 2;
                                }
                            } else {
                                tablero.promociondelPeon(problema.stringToMovimiento(jugada.toUpperCase()), jugada.toUpperCase().charAt(4));
                                ventana.board.pintartablero(tablero);
                                ventana.actualizarpantalla();
                                System.out.println("Bien hecho.");
                                esperar(3);
                                if (ia.moverMáquina(problema, tablero, ventana, j, jugada) != null) {
                                    jugadasaRetroceder = jugadasaRetroceder + 2;
                                }
                            }
                        } catch (MovIncorrectoException ex) {
                        }
                    } else {
                        System.out.println("Esa no era la jugada.");
                        j = j - 2;
                        inicio = false;
                    }
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("Enhorabuena, ha resuelto una variante del problema.\n");
                    numjugada = j - jugadasaRetroceder + 2;
                    j = -3;
                }
            }
            if (i == numVariantes - 1) {
                System.out.println("Felicidades ha resuelto el Problema al completo.\n");
                recolocarTablero(problema, tablero);
                problema.getIDsResueltos().add(tablero.id);
            } else {
                ia.cambiarVariante(jugadasaRetroceder, problema, tablero, ventana);
            }
        }
    }
    /**
     * Este método recoloca el tablero a la posición inicial del problema.  
     * @param problema Objeto de la clase base donde se encuentran todos los
     * datos relacionados con los problemas.
     * @param tablero Tablero donde se está resolviendo el problema.
     */
    public static void recolocarTablero(Problema problema, Tablero tablero) {
        for (int i = 0; i < problema.getListaProblemas().get(tablero).get(problema.getListaProblemas().get(tablero).size() - 1).size(); i++) {
            tablero.anularUltimoMovimiento();
        }
    }

}
