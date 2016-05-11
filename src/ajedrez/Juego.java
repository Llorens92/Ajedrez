/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.util.ArrayList;

/**
 *
 * @author dam1
 */
public class Juego {

    ArrayList<Tablero> listapartidas = new ArrayList<>();

    public boolean finPartida(String mensaje, Tablero tablero) {
        boolean salir = false;
        if (mensaje.equalsIgnoreCase("guardar")) {
            guardarPartida(tablero);
            salir = true;
        } else if (mensaje.equalsIgnoreCase("fin")) {
            salir = true;
        }
        return salir;
    }

    public void guardarPartida(Tablero tablero) {
        listapartidas.add(tablero);
    }

    /**
     * Método que carga el array de piezas colocando cada una en su posición
     * inicial.
     *
     * @param tablero Que es un array bidimensional de piezas
     */
    public void inicializar(Pieza[][] tablero) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0) {
                    boolean color = false;
                    switch (j) {
                        case 0:
                            tablero[i][j] = new Torre(color, i, j);
                            break;
                        case 1:
                            tablero[i][j] = new Caballo(color, i, j);
                            break;
                        case 2:
                            tablero[i][j] = new Alfil(color, i, j);
                            break;
                        case 3:
                            tablero[i][j] = new Dama(color, i, j);
                            break;
                        case 4:
                            tablero[i][j] = new Rey(color, i, j);
                            break;
                        case 5:
                            tablero[i][j] = new Alfil(color, i, j);
                            break;
                        case 6:
                            tablero[i][j] = new Caballo(color, i, j);
                            break;
                        case 7:
                            tablero[i][j] = new Torre(color, i, j);
                            break;
                    }
                } else if (i == 1) {
                    boolean color = false;
                    tablero[i][j] = new Peon(color, i, j);
                } else if (i == 7) {
                    boolean color = true;
                    switch (j) {
                        case 0:
                            tablero[i][j] = new Torre(color, i, j);
                            break;
                        case 1:
                            tablero[i][j] = new Caballo(color, i, j);
                            break;
                        case 2:
                            tablero[i][j] = new Alfil(color, i, j);
                            break;
                        case 3:
                            tablero[i][j] = new Dama(color, i, j);
                            break;
                        case 4:
                            tablero[i][j] = new Rey(color, i, j);
                            break;
                        case 5:
                            tablero[i][j] = new Alfil(color, i, j);
                            break;
                        case 6:
                            tablero[i][j] = new Caballo(color, i, j);
                            break;
                        case 7:
                            tablero[i][j] = new Torre(color, i, j);
                            break;
                    }
                } else if (i == 6) {
                    boolean color = true;
                    tablero[i][j] = new Peon(color, i, j);
                }
            }
        }
    }

    /**
     * Método que pinta cada pieza según la posición que ocupen en el array
     * bidimensional.
     *
     * @param tablero Que es un array bidimensional de piezas
     */
    public void pintar(Pieza[][] tablero) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] != null) {
                    tablero[i][j].pintarPieza();
                } else {
                    System.out.print("[  ]");
                }
            }
            System.out.println();
        }

    }

    /**
     * Método que carga las jugadas en el tablero convirtiendo convirtiendo el
     * string que recibe en un objeto de la clase Movimiento.
     *
     * @param jugada Que debe ser un string de 4 o 5 caracteres con el formato
     * (A-H 1-8 A-H 1-8)
     * @param tablero Es un objeto de la clase tablero al que se cargan las
     * jugadas
     */
    public void jugada(String jugada, Tablero tablero) {
        if (jugada.length() == 4) {
            int filaInicial, filaFinal, columnaFinal, columnaInicial;
            switch (jugada.charAt(0)) {
                case 'A':
                    columnaInicial = 0;
                    break;
                case 'B':
                    columnaInicial = 1;
                    break;
                case 'C':
                    columnaInicial = 2;
                    break;
                case 'D':
                    columnaInicial = 3;
                    break;
                case 'E':
                    columnaInicial = 4;
                    break;
                case 'F':
                    columnaInicial = 5;
                    break;
                case 'G':
                    columnaInicial = 6;
                    break;
                case 'H':
                    columnaInicial = 7;
                    break;
                default:
                    columnaInicial = 8;

            }
            switch (jugada.charAt(1)) {
                case '1':
                    filaInicial = 7;
                    break;
                case '2':
                    filaInicial = 6;
                    break;
                case '3':
                    filaInicial = 5;
                    break;
                case '4':
                    filaInicial = 4;
                    break;
                case '5':
                    filaInicial = 3;
                    break;
                case '6':
                    filaInicial = 2;
                    break;
                case '7':
                    filaInicial = 1;
                    break;
                case '8':
                    filaInicial = 0;
                    break;
                default:
                    filaInicial = 8;
            }
            switch (jugada.charAt(2)) {
                case 'A':
                    columnaFinal = 0;
                    break;
                case 'B':
                    columnaFinal = 1;
                    break;
                case 'C':
                    columnaFinal = 2;
                    break;
                case 'D':
                    columnaFinal = 3;
                    break;
                case 'E':
                    columnaFinal = 4;
                    break;
                case 'F':
                    columnaFinal = 5;
                    break;
                case 'G':
                    columnaFinal = 6;
                    break;
                case 'H':
                    columnaFinal = 7;
                    break;
                default:
                    columnaFinal = 8;

            }
            switch (jugada.charAt(3)) {
                case '1':
                    filaFinal = 7;
                    break;
                case '2':
                    filaFinal = 6;
                    break;
                case '3':
                    filaFinal = 5;
                    break;
                case '4':
                    filaFinal = 4;
                    break;
                case '5':
                    filaFinal = 3;
                    break;
                case '6':
                    filaFinal = 2;
                    break;
                case '7':
                    filaFinal = 1;
                    break;
                case '8':
                    filaFinal = 0;
                    break;
                default:
                    filaFinal = 8;

            }
            if (filaInicial == 8 || columnaInicial == 8 || filaFinal == 8 || columnaFinal == 8) {
                System.out.println("Jugada fuera de rango-> Introduzca una jugada valida (A-H,1-8,A-H,1-8)");
            } else {
                Posicion PosInicial = new Posicion(filaInicial, columnaInicial);
                Posicion PosFinal = new Posicion(filaFinal, columnaFinal);
                Movimiento mov = new Movimiento(PosInicial, PosFinal);
                moverJuego(mov, tablero);
            }
        } else if (jugada.length() == 5) {
            int filaInicial, filaFinal, columnaFinal, columnaInicial;
            char nuevaPieza;
            switch (jugada.charAt(0)) {
                case 'A':
                    columnaInicial = 0;
                    break;
                case 'B':
                    columnaInicial = 1;
                    break;
                case 'C':
                    columnaInicial = 2;
                    break;
                case 'D':
                    columnaInicial = 3;
                    break;
                case 'E':
                    columnaInicial = 4;
                    break;
                case 'F':
                    columnaInicial = 5;
                    break;
                case 'G':
                    columnaInicial = 6;
                    break;
                case 'H':
                    columnaInicial = 7;
                    break;
                default:
                    columnaInicial = 0;

            }
            switch (jugada.charAt(1)) {
                case '1':
                    filaInicial = 7;
                    break;
                case '2':
                    filaInicial = 6;
                    break;
                case '3':
                    filaInicial = 5;
                    break;
                case '4':
                    filaInicial = 4;
                    break;
                case '5':
                    filaInicial = 3;
                    break;
                case '6':
                    filaInicial = 2;
                    break;
                case '7':
                    filaInicial = 1;
                    break;
                case '8':
                    filaInicial = 0;
                    break;
                default:
                    filaInicial = 0;
            }
            switch (jugada.charAt(2)) {
                case 'A':
                    columnaFinal = 0;
                    break;
                case 'B':
                    columnaFinal = 1;
                    break;
                case 'C':
                    columnaFinal = 2;
                    break;
                case 'D':
                    columnaFinal = 3;
                    break;
                case 'E':
                    columnaFinal = 4;
                    break;
                case 'F':
                    columnaFinal = 5;
                    break;
                case 'G':
                    columnaFinal = 6;
                    break;
                case 'H':
                    columnaFinal = 7;
                    break;
                default:
                    columnaFinal = 6;

            }
            switch (jugada.charAt(3)) {
                case '1':
                    filaFinal = 7;
                    break;
                case '2':
                    filaFinal = 6;
                    break;
                case '3':
                    filaFinal = 5;
                    break;
                case '4':
                    filaFinal = 4;
                    break;
                case '5':
                    filaFinal = 3;
                    break;
                case '6':
                    filaFinal = 2;
                    break;
                case '7':
                    filaFinal = 1;
                    break;
                case '8':
                    filaFinal = 0;
                    break;
                default:
                    filaFinal = 5;

            }
            switch (jugada.charAt(4)) {
                case 'A':
                    nuevaPieza = 'a';
                    break;
                case 'C':
                    nuevaPieza = 'c';
                    break;
                case 'D':
                    nuevaPieza = 'd';
                    break;
                case 'T':
                    nuevaPieza = 't';
                    break;
                default:
                    nuevaPieza = 'n';

            }
            Posicion PosInicial = new Posicion(filaInicial, columnaInicial);
            Posicion PosFinal = new Posicion(filaFinal, columnaFinal);
            Movimiento mov = new Movimiento(PosInicial, PosFinal);
            tablero.promociondelPeon(mov, nuevaPieza);
        } else{
            System.out.println("Movimiento invalido-JUGADA");
        }
    }

    /**
     * Este Método filtra los movimientos recibidos del método jugada en función
     * de ciertos requisitos. Si pasa el filtro el movimiento es enviado a los
     * métodos propios de la clase Pieza y sus hijos.
     *
     * @param mov Es el objeto de clase Movimiento creado a partir del String
     * que recibe el método jugada.
     * @param tablero Es el mismo objeto de clase Tablero utilizado en jugada.
     */
    public void moverJuego(Movimiento mov, Tablero tablero) {
        if (tablero.hayPieza(mov.getPosInicial()) == true && ((mov.getPosInicial().getFila() == 1 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == true) || (mov.getPosInicial().getFila() == 6 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == false))) {
            System.out.println("Para promocionar el peon introduzca una letra mayúscula más indicando la pieza que quiere.");
        } else if (tablero.hayPieza(mov.getPosInicial()) == true && tablero.getTurno() == tablero.DevuelvePieza(mov.getPosInicial()).getColor() && (tablero.DevuelvePieza(mov.getPosFinal()) == null || (tablero.DevuelvePieza(mov.getPosFinal()) != null && tablero.DevuelvePieza(mov.getPosFinal()).getColor() != tablero.DevuelvePieza(mov.getPosInicial()).getColor())) && tablero.hayPiezasEntre(mov) == false) {
            tablero.DevuelvePieza(mov.getPosInicial()).moverPieza(mov, tablero);
        } else {
            System.out.println("Movimiento invalido-JUEGO");
        }
    }
}
