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

    protected HashMap<Tablero, ArrayList<HashMap<ArrayList<Jugada>, ArrayList<Jugada>>>> listaProblemas = new HashMap<>();
    protected ArrayList<String> IDsResueltos = new ArrayList<>();

    public HashMap<Tablero, ArrayList<HashMap<ArrayList<Jugada>, ArrayList<Jugada>>>> getListaProblemas() {
        return listaProblemas;
    }

    public ArrayList<String> getIDsResueltos() {
        return IDsResueltos;
    }

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

    public Pieza introducirPieza(String nomPieza, String color, int fila, int columna) {
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

    public void introduciendoDatos(Tablero tablero, Ventana ventana) {
        Scanner sc = new Scanner(System.in);
        String nomPieza = " ";
        String color = " ";
        String posicion = " ";
        boolean salir;
        while (!nomPieza.equalsIgnoreCase("fin")) {
            do {
                System.out.println("Introduzca el nombre de la pieza que quiere añadir al tablero, quitar para vaciar una casilla o fin para terminar.\nPor ejemplo: Caballo");
                nomPieza = sc.nextLine();
            } while (!nomPieza.equalsIgnoreCase("quitar") && !nomPieza.equalsIgnoreCase("caballo") && !nomPieza.equalsIgnoreCase("alfil") && !nomPieza.equalsIgnoreCase("peon") && !nomPieza.equalsIgnoreCase("dama") && !nomPieza.equalsIgnoreCase("rey") && !nomPieza.equalsIgnoreCase("torre") && !nomPieza.equalsIgnoreCase("fin"));
            if (!nomPieza.equalsIgnoreCase("fin")) {
                if (!nomPieza.equalsIgnoreCase("quitar")) {
                    do {
                        System.out.println("Introduzca el color de la pieza que quiere añadir al tablero.\nPor ejemplo: Blanco");
                        color = sc.nextLine();
                    } while (!color.equalsIgnoreCase("blanco") && !color.equalsIgnoreCase("blanca") && !color.equalsIgnoreCase("negro") && !color.equalsIgnoreCase("negra"));
                }
                do {
                    do {
                        System.out.println("Introduzca la casilla de la pieza que quiere añadir al tablero o la casilla que quiera vaciar.\nPor ejemplo: C5");
                        posicion = sc.nextLine();
                    } while (posicion.length() != 2);
                    salir = !(Juego.devolverColumna(posicion.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(posicion.toUpperCase().charAt(1)) == 8);
                } while (!salir);
                if (!nomPieza.equalsIgnoreCase("quitar")) {
                    tablero.colocaPieza(introducirPieza(nomPieza.toUpperCase(), color, Juego.devolverFila(posicion.toUpperCase().charAt(1)), Juego.devolverColumna(posicion.toUpperCase().charAt(0))));
                } else {
                    tablero.quitaPieza(Juego.devolverFila(posicion.toUpperCase().charAt(1)), Juego.devolverColumna(posicion.toUpperCase().charAt(0)));
                }
                ventana.board.pintartablero(tablero);
                Juego.pintar(tablero.getTablero());
                ventana.actualizarpantalla();
            }
        }
    }

    public void quienComienzaJugando(Tablero tablero) {
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

    public int introduciendoNumSoluciones() {
        Scanner sc = new Scanner(System.in);
        int numSoluciones = 0;
        while (numSoluciones < 1) {
            System.out.println("Introduzca el numero de soluciones total que posee el problema");
            try {
                numSoluciones = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Por favor introduzca un número mayor de 0");
            }
        }
        return numSoluciones;
    }

    public int introduciendoNumMovimientos() {
        Scanner sc = new Scanner(System.in);
        int numMovimientos = 0;
        while (numMovimientos < 1) {
            System.out.println("Introduzca el numero de movimientos que componen la solucion");
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

    public int introduciendoNumSubMovs() {
        Scanner sc = new Scanner(System.in);
        int numSubMovs = 0;
        while (numSubMovs < 1) {
            System.out.println("Introduzca el numero de variantes que puede haber en este movimiento");
            try {
                numSubMovs = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Por favor introduzca un número mayor de 0");
            }
        }
        return numSubMovs;
    }

    public void introduciendoJugadas(Tablero tablero, Ventana ventana) {
        String movimiento = " ", anular = " ";
        boolean salir, repetirMov;
        Scanner sc = new Scanner(System.in);
        ArrayList<HashMap<ArrayList<Jugada>, ArrayList<Jugada>>> listaSoluciones = new ArrayList<>();
        int numSoluciones = introduciendoNumSoluciones(), numMovs = 0, numSubMovs = 0;
        for (int i = 0; i < numSoluciones; i++) {
            numMovs = introduciendoNumMovimientos();
            for (int j = 0; j < numMovs; j++) {
                HashMap<ArrayList<Jugada>, ArrayList<Jugada>> listamovimientos = new HashMap<>();
                numSubMovs = introduciendoNumSubMovs();
                listamovimientos.re
                for (int l = 0; l < numSubMovs; l++) {
                    do {
                        try {
                            do {
                                do {
                                    System.out.println("Introduzca el submovimiento correspondiente añadiéndo la letra pertinente si es promoción del peón. Por ejemplo: E2E4 o D7D8D");
                                    System.out.println("Si desea anular el movimiento anterior escriba anular");
                                    movimiento = sc.nextLine();
                                } while (movimiento.length() != 4 && movimiento.length() != 5 && !movimiento.equalsIgnoreCase("anular"));
                                if (movimiento.length() == 4 && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(1)) == 8) && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(2)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(3)) == 8)) {
                                    salir = true;
                                } else if (movimiento.length() == 5 && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(1)) == 8) && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(2)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(3)) == 8) && !(Juego.devolverPromocion(movimiento.toUpperCase().charAt(4)) == 'n')) {
                                    salir = true;
                                } else if (movimiento.equalsIgnoreCase("anular")) {
                                    if (l==0) { 
                                        tablero.anularUltimoMovimiento();
                                        if (numMovs % 2 == 0) {
                                            aux.remove(aux.size() - 1);
                                        } else {
                                            aux2.remove(aux2.size() - 1);
                                        }
                                    }
                                    ventana.board.pintartablero(tablero);
                                    ventana.actualizarpantalla();
                                    salir = false;
                                } else {
                                    salir = false;
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
                    if (numMovs % 2 == 0) {
                        aux.add(new Jugada(numMovs, movimiento));
                    } else {
                        aux2.add(new Jugada(numMovs, movimiento));
                    }
                    ventana.board.pintartablero(tablero);
                    ventana.actualizarpantalla();
                    if (j == numMovs - 1) {
                        do {
                            System.out.println("¿Desea anular el último movimiento de la variante, antes de pasar a otra?");
                            anular = sc.nextLine();
                        } while (!anular.equalsIgnoreCase("si") && !anular.equalsIgnoreCase("no") && !anular.equalsIgnoreCase("anular"));
                        if (anular.equalsIgnoreCase("si") || anular.equalsIgnoreCase("anular")) {
                            if (numMovs % 2 == 0) {
                                aux.remove(aux.size() - 1);
                            } else {
                                aux2.remove(aux2.size() - 1);
                            }
                            tablero.anularUltimoMovimiento();
                            j--;
                            ventana.board.pintartablero(tablero);
                            ventana.actualizarpantalla();
                        } else {
                            for (int k = 0; k < numMovs; k++) {
                                tablero.anularUltimoMovimiento();
                            }
                            ventana.board.pintartablero(tablero);
                            ventana.actualizarpantalla();
                            listaSoluciones.put(aux, aux2);
                        }
                    }
                }
            }
        }
        listaProblemas.put(tablero, listaSoluciones);
    }

    public void moverProblema(Movimiento mov, Tablero tablero) throws MovIncorrectoException {
        if (tablero.hayPieza(mov.getPosInicial()) == true && ((mov.getPosInicial().getFila() == 1 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == true) || (mov.getPosInicial().getFila() == 6 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == false))) {
            throw new MovIncorrectoException("Para promocionar el peon introduzca una letra mayúscula más indicando la pieza que quiere.");
        } else if (tablero.hayPieza(mov.getPosInicial()) == true && tablero.getTurno() == tablero.DevuelvePieza(mov.getPosInicial()).getColor() && (tablero.DevuelvePieza(mov.getPosFinal()) == null || (tablero.DevuelvePieza(mov.getPosFinal()) != null && tablero.DevuelvePieza(mov.getPosFinal()).getColor() != tablero.DevuelvePieza(mov.getPosInicial()).getColor())) && tablero.hayPiezasEntre(mov) == false) {
            tablero.DevuelvePieza(mov.getPosInicial()).moverPieza(mov, tablero);
        } else {
            throw new MovIncorrectoException("Movimiento invalido-Problema");
        }
    }

    public Movimiento stringToMovimiento(String s) {
        Posicion posInicial = new Posicion(Juego.devolverFila(s.charAt(1)), Juego.devolverColumna(s.charAt(0)));
        Posicion posFinal = new Posicion(Juego.devolverFila(s.charAt(3)), Juego.devolverColumna(s.charAt(2)));
        return new Movimiento(0, posInicial, posFinal);
    }
}
