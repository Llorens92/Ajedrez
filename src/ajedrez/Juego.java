/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author dam1
 */
public class Juego implements Serializable {

    protected ArrayList<Tablero> listaPartidas = new ArrayList<>();

    public ArrayList<Tablero> getListapartidas() {
        return listaPartidas;
    }

    public String fijandoIDPartida() {
        boolean distinto = false;
        String ID = "";
        do {
            ID = "partida".concat(String.valueOf((int) (Math.random() * 10))).concat(String.valueOf((int) (Math.random() * 10))).concat(String.valueOf((int) (Math.random() * 10)));
            if (listaPartidas.isEmpty()) {
                distinto = true;
            } else {
                for (int i = 0; i < listaPartidas.size() && !distinto; i++) {
                    distinto = !listaPartidas.get(i).getId().equalsIgnoreCase(ID);
                }
            }
        } while (!distinto);
        return ID;
    }

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
        Scanner lc = new Scanner (System.in);
        System.out.println("Introduzca una descripción para identificar la partida guardada posteriormente.");
        String descripcion = lc.nextLine();
        tablero.setDescripcion(descripcion);
        listaPartidas.add(tablero);
        System.out.println("Partida guardada");
    }

    public static char devolverPromocion(char caracter) {
        char nuevaPieza;
        switch (caracter) {
            case 'A':
                nuevaPieza = 'A';
                break;
            case 'C':
                nuevaPieza = 'C';
                break;
            case 'D':
                nuevaPieza = 'D';
                break;
            case 'T':
                nuevaPieza = 'T';
                break;
            default:
                nuevaPieza = 'n';

        }
        return nuevaPieza;
    }

    public static int devolverFila(char caracter) {
        int fila;
        switch (caracter) {
            case '1':
                fila = 7;
                break;
            case '2':
                fila = 6;
                break;
            case '3':
                fila = 5;
                break;
            case '4':
                fila = 4;
                break;
            case '5':
                fila = 3;
                break;
            case '6':
                fila = 2;
                break;
            case '7':
                fila = 1;
                break;
            case '8':
                fila = 0;
                break;
            default:
                fila = 8;

        }
        return fila;
    }

    public static int devolverColumna(char caracter) {
        int columna;
        switch (caracter) {
            case 'A':
                columna = 0;
                break;
            case 'B':
                columna = 1;
                break;
            case 'C':
                columna = 2;
                break;
            case 'D':
                columna = 3;
                break;
            case 'E':
                columna = 4;
                break;
            case 'F':
                columna = 5;
                break;
            case 'G':
                columna = 6;
                break;
            case 'H':
                columna = 7;
                break;
            default:
                columna = 8;

        }
        return columna;
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
    public static void pintar(Pieza[][] tablero) {
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
            if (devolverColumna(jugada.charAt(0)) == 8 || devolverFila(jugada.charAt(1)) == 8 || devolverColumna(jugada.charAt(2)) == 8 || devolverFila(jugada.charAt(3)) == 8) {
                System.out.println("Jugada fuera de rango-> Introduzca una jugada valida (A-H,1-8,A-H,1-8)");
            } else {
                Posicion PosInicial = new Posicion(devolverFila(jugada.charAt(1)), devolverColumna(jugada.charAt(0)));
                Posicion PosFinal = new Posicion(devolverFila(jugada.charAt(3)), devolverColumna(jugada.charAt(2)));
                Movimiento mov = new Movimiento(0,PosInicial, PosFinal);
                try {
                    moverJuego(mov, tablero);
                } catch (MovIncorrectoException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else if (jugada.length() == 5) {
            Posicion PosInicial = new Posicion(devolverFila(jugada.charAt(1)), devolverColumna(jugada.charAt(0)));
            Posicion PosFinal = new Posicion(devolverFila(jugada.charAt(3)), devolverColumna(jugada.charAt(2)));
            Movimiento mov = new Movimiento(0,PosInicial, PosFinal);
            try {
            tablero.promociondelPeon(mov, devolverPromocion(jugada.charAt(4)));
            } catch (MovIncorrectoException ex) {
                System.out.println(ex.getMessage());
            }            
        } else {
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
     * @throws MovIncorrectoException
     */
    public void moverJuego(Movimiento mov, Tablero tablero) throws MovIncorrectoException {
        if (tablero.hayPieza(mov.getPosInicial()) == true && ((mov.getPosInicial().getFila() == 1 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == true) || (mov.getPosInicial().getFila() == 6 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == false))) {
            throw new MovIncorrectoException("Para promocionar el peon introduzca una letra mayúscula más indicando la pieza que quiere.");
        } else if (tablero.hayPieza(mov.getPosInicial()) == true && tablero.getTurno() == tablero.DevuelvePieza(mov.getPosInicial()).getColor() && (tablero.DevuelvePieza(mov.getPosFinal()) == null || (tablero.DevuelvePieza(mov.getPosFinal()) != null && tablero.DevuelvePieza(mov.getPosFinal()).getColor() != tablero.DevuelvePieza(mov.getPosInicial()).getColor())) && tablero.hayPiezasEntre(mov) == false) {
            tablero.DevuelvePieza(mov.getPosInicial()).moverPieza(mov, tablero);
        } else {
            throw new MovIncorrectoException("Movimiento invalido-JUEGO");
        }
    }
}
