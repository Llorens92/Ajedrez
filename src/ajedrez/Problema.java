/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author dam1
 */
public class Problema implements Serializable {

    HashMap<Tablero, ArrayList<ArrayList<String>>> listaProblemas = new HashMap<>();

    public String fijandoIDProblema() {
        boolean distinto = false;
        String ID = "";
        do {
            ID = "problema".concat(String.valueOf((int) (Math.random() * 10))).concat(String.valueOf((int) (Math.random() * 10))).concat(String.valueOf((int) (Math.random() * 10)));
            if (listaProblemas.isEmpty()) {
                distinto = true;
            } else {
                Set<Tablero> tableros = listaProblemas.keySet();
                Iterator<Tablero> it = tableros.iterator();
                while (it.hasNext() && !distinto) {
                    distinto = !it.next().getId().equalsIgnoreCase(ID);
                }
            }
        } while (!distinto);
        return ID;
    }

    public static Pieza introducirPieza(String nomPieza, String color, int fila, int columna) {
        boolean colour = false;
        Pieza pieza;
        if (color.equalsIgnoreCase("blanco") || color.equalsIgnoreCase("blanca")) {
            colour = true;
        }
        switch (nomPieza) {
            case "ALFIL":
                pieza = new Alfil(colour, fila, columna);
                break;

            case "CABALLO":
                pieza = new Caballo(colour, fila, columna);
                break;

            case "DAMA":
                pieza = new Dama(colour, fila, columna);
                break;

            case "PEON":
                pieza = new Peon(colour, fila, columna);
                break;

            case "REY":
                pieza = new Rey(colour, fila, columna);
                break;

            case "TORRE":
                pieza = new Torre(colour, fila, columna);
                break;
            default:
                pieza = null;
        }
        return pieza;
    }

    public static void introduciendoDatos(Tablero tablero, Ventana ventana) {
        Scanner sc = new Scanner(System.in);
        String nomPieza = " ";
        String color = " ";
        String posicion = " ";
        boolean salir = false;
        while (!nomPieza.equalsIgnoreCase("fin")) {
            do {
                System.out.println("Introduzca el nombre de la pieza que quiere añadir al tablero o fin para terminar.\nPor ejemplo: Caballo");
                nomPieza = sc.nextLine();
            } while (!nomPieza.equalsIgnoreCase("caballo") && !nomPieza.equalsIgnoreCase("alfil") && !nomPieza.equalsIgnoreCase("peon") && !nomPieza.equalsIgnoreCase("dama") && !nomPieza.equalsIgnoreCase("rey") && !nomPieza.equalsIgnoreCase("torre") && !nomPieza.equalsIgnoreCase("fin"));
            if (!nomPieza.equalsIgnoreCase("fin")) {
                do {
                    System.out.println("Introduzca el color de la pieza que quiere añadir al tablero.\nPor ejemplo: Blanco");
                    color = sc.nextLine();
                } while (!color.equalsIgnoreCase("blanco") && !color.equalsIgnoreCase("blanca") && !color.equalsIgnoreCase("negro") && !color.equalsIgnoreCase("negra"));
                do {
                    do {
                        System.out.println("Introduzca la casilla de la pieza que quiere añadir al tablero.\nPor ejemplo: C5");
                        posicion = sc.nextLine();
                    } while (posicion.length() != 2);
                    if (!(Juego.devolverColumna(posicion.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(posicion.toUpperCase().charAt(1)) == 8)) {
                        salir = true;
                    }
                } while (!salir);
                tablero.colocaPieza(introducirPieza(nomPieza.toUpperCase(), color, Juego.devolverFila(posicion.toUpperCase().charAt(1)), Juego.devolverColumna(posicion.toUpperCase().charAt(0))));
                ventana.board.pintartablero(tablero);
                Juego.pintar(tablero.getTablero());
                ventana.actualizarpantalla();
            }
        }
    }

    public static void quienComienzaJugando(Tablero tablero) {
        Scanner sc = new Scanner(System.in);
        String color;
        do {
            System.out.println("Indique quien empieza en el problema, si blancas o negras");
            color = sc.nextLine();
        } while (!color.equalsIgnoreCase("negro") && !color.equalsIgnoreCase("blanco") && !color.equalsIgnoreCase("blancas") && !color.equalsIgnoreCase("negras"));
        if (color.equalsIgnoreCase("negro") || color.equalsIgnoreCase("negras")) {
            tablero.setTurno(false);
        }
    }

    public static int introduciendoNumVariantes() {
        Scanner sc = new Scanner(System.in);
        int numVariantes = 0;
        while (numVariantes < 1) {
            System.out.println("Introduzca el numero de variantes total que posee el problema");
            try {
                numVariantes = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Por favor introduzca un número mayor de 0");
            }
        }
        return numVariantes;
    }

    public static int introduciendoNumMovimientos() {
        Scanner sc = new Scanner(System.in);
        int numMovimientos = 0;
        while (numMovimientos < 1) {
            System.out.println("Introduzca el numero de movimientos que componen la variante");
            try {
                numMovimientos = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Por favor introduzca un número mayor de 0");
            }
        }
        return numMovimientos;
    }

    public static void introduciendoJugadas(Tablero tablero, Ventana ventana) {
        String movimiento = " ";
        boolean salir = false, repetirMov;
        Scanner sc = new Scanner(System.in);
        ArrayList< ArrayList<String>> listaSoluciones = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        for (int i = 0; i < introduciendoNumVariantes(); i++) {
            for (int j = 0; j < introduciendoNumMovimientos(); j++) {
                do {
                    try {
                        do {
                            do {
                                System.out.println("Introduzca el movimiento correspondiente añadiéndo la letra pertinente si es promoción del peón. Por ejemplo: E2E4 o D7D8D");
                                System.out.println("Si desea anular el movimiento anterior escriba anular");
                                movimiento = sc.nextLine();
                            } while (movimiento.length() != 4 && movimiento.length() != 5 && !movimiento.equalsIgnoreCase("anular"));
                            if (movimiento.length() == 4 && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(1)) == 8) && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(2)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(3)) == 8)) {
                                salir = true;
                            } else if (movimiento.length() == 5 && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(1)) == 8) && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(2)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(3)) == 8) && !(Juego.devolverPromocion(movimiento.toUpperCase().charAt(4)) == 'n')) {
                                salir = true;
                            } else if (movimiento.equalsIgnoreCase("anular")) {
                            }
                        } while (!salir);
                        if (movimiento.length() == 4) {
                            moverProblema(stringToMovimiento(movimiento.toUpperCase()), tablero);
                            repetirMov = false;
                        } else {
                            tablero.promociondelPeon(stringToMovimiento(movimiento.toUpperCase()), movimiento.toUpperCase().charAt(4));
                            repetirMov = false;
                        }
                    } catch (MovIncorrectoException ex) {
                        repetirMov = true;
                        System.out.println(ex.getMessage());
                    }
                } while (repetirMov);
                aux.add(movimiento);
                ventana.board.pintartablero(tablero);
                Juego.pintar(tablero.getTablero());
                ventana.actualizarpantalla();
            }
        }
    }

    public static void moverProblema(Movimiento mov, Tablero tablero) throws MovIncorrectoException {
        if (tablero.hayPieza(mov.getPosInicial()) == true && ((mov.getPosInicial().getFila() == 1 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == true) || (mov.getPosInicial().getFila() == 6 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == false))) {
            throw new MovIncorrectoException("Para promocionar el peon introduzca una letra mayúscula más indicando la pieza que quiere.");
        } else if (tablero.hayPieza(mov.getPosInicial()) == true && tablero.getTurno() == tablero.DevuelvePieza(mov.getPosInicial()).getColor() && (tablero.DevuelvePieza(mov.getPosFinal()) == null || (tablero.DevuelvePieza(mov.getPosFinal()) != null && tablero.DevuelvePieza(mov.getPosFinal()).getColor() != tablero.DevuelvePieza(mov.getPosInicial()).getColor())) && tablero.hayPiezasEntre(mov) == false) {
            tablero.DevuelvePieza(mov.getPosInicial()).moverPieza(mov, tablero);
        } else {
            throw new MovIncorrectoException("Movimiento invalido-Problema");
        }
    }

    public static Movimiento stringToMovimiento(String s) {
        Posicion posInicial = new Posicion(Juego.devolverFila(s.charAt(1)), Juego.devolverColumna(s.charAt(0)));
        Posicion posFinal = new Posicion(Juego.devolverFila(s.charAt(3)), Juego.devolverColumna(s.charAt(2)));
        return new Movimiento(posInicial, posFinal);
    }
}
