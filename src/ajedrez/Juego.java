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

    /**
     * Colección que contiene las partidas guardadas.
     */
    protected ArrayList<Tablero> listaPartidas = new ArrayList<>();

    /**
     * Este método devuelve la lista de partidas guardadas
     *
     * @return Lista de partidas guardadas.
     */
    public ArrayList<Tablero> getListapartidas() {
        return listaPartidas;
    }

    /**
     * Este método permite inicializar el ID de cada partida.
     *
     * @return String con el ID de la partida.
     */
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

    /**
     * Este método es el que interactúa con el jugador durante las partidas,
     * solicitando que introduzca los movimientos y efectuándolos en el tablero
     * y ventana correspondiente.
     *
     * @param ventana Ventana donde se está jugando la partida.
     * @param tablero Tablero donde se está jugando la partida.
     */
    public void jugarPartida(Tablero tablero, Ventana ventana) {
        Scanner lc = new Scanner(System.in);
        for (int j = 1; j > 0; j++) {
            System.out.println("Introduzca una jugada valida (A-H,1-8,A-H,1-8)");
            System.out.println("Para guardar y salir escriba Guardar. Si desea rendirse escriba Fin durante su turno.");
            System.out.println("Si desea anular el último movimiento escriba anular.");
            String jugada = lc.nextLine();
            if (jugada.equalsIgnoreCase("anular")) {
                tablero.anularUltimoMovimiento();
                ventana.board.pintartablero(tablero);
                ventana.actualizarpantalla();
            } else if (!finPartida(jugada, tablero)) {
                jugada(jugada.toUpperCase(), tablero);
                ventana.board.pintartablero(tablero);
                ventana.actualizarpantalla();
            } else {
                j = -1;
                System.out.println("Espero que hayan disfrutado de la partida.");
                System.out.println("Hasta Pronto");
            }
        }
    }

    /**
     * Este método encuentra la partida (si la hay), que corresponde al ID
     * introducido como parámtero, devolviendo el tablero que corresponde.
     *
     * @param posibleID ID a comprobar.
     * @return Instancia de la clase Tablero corresponidente al ID introducido o
     * null si no hay ninguna coincidencia
     */
    public Tablero encontrarPartida(String posibleID) {
        boolean salir = false;
        Tablero tablero = null;
        for (int i = 0; i < listaPartidas.size() && !salir; i++) {
            if (posibleID.equalsIgnoreCase(listaPartidas.get(i).getId())) {
                tablero = listaPartidas.get(i);
                salir = true;
            }
        }
        return tablero;
    }

    /**
     * Este método pinta la lista de partidas guardadas permitiendo luego
     * recuperar y volver a jugar con una de ellas, pues acaba llamando al
     * método jugarPartida.
     */
    public void pintarPartidas() {
        Scanner lc = new Scanner(System.in);
        String id;
        for (Tablero tablero1 : listaPartidas) {
            System.out.println(tablero1.getId().concat("-->").concat(tablero1.toString()));
        }
        do {
            System.out.println("Introduzca el ID de la partida que quiere recuperar.");
            id = lc.nextLine();
        } while (encontrarPartida(id) == null);
        Tablero tablero = encontrarPartida(id);
        Ventana ventana = new Ventana(tablero);
        ventana.setBounds(0, 0, 505, 530);
        ventana.setVisible(true);
        ventana.board.pintartablero(tablero);
        jugarPartida(tablero, ventana);
    }

    /**
     * Este método muestra los IDs dy las descripciones de las partidas y luego
     * borra la partida cuyo ID se solicita en la ejecución del mismo.
     */
    public void borrarPartidas() {
        Scanner lc = new Scanner(System.in);
        String id;
        for (Tablero tablero1 : listaPartidas) {
            System.out.println(tablero1.getId().concat("-->").concat(tablero1.toString()));
        }
        do {
            System.out.println("Introduzca el ID de la partida que quiere borrar.");
            id = lc.nextLine();
        } while (encontrarPartida(id) == null);
        listaPartidas.remove(encontrarPartida(id));
        System.out.println("Partida borrada");
    }

    /**
     * Este método permite poner fin a una partida ya sea guardandola o
     * saliéndose sin más, mediante el String que recibe.
     *
     * @param mensaje String que de corresponderse con fin o guardar para poner
     * fin a una partida.
     * @param tablero Tablero donde se está jugando la partida.
     * @return Booleano que indica si se acabo la partida.
     */
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

    /**
     * Este método permite guardar el estado de una partida.
     *
     * @param tablero Tablero donde se está jugando la partida.
     */
    public void guardarPartida(Tablero tablero) {
        Scanner lc = new Scanner(System.in);
        if (tablero.toString().equalsIgnoreCase("El tablero no tiene asociada ninguna descripcion")) {
            System.out.println("Introduzca una descripción para identificar la partida guardada posteriormente.");
            tablero.setDescripcion(lc.nextLine());
        }
        listaPartidas.add(tablero);
        System.out.println("Partida guardada");
    }

    /**
     * Este método permite cribar el caracter que recibe para que se corresponda
     * solo con los válidos para promocionar el peón.
     *
     * @param caracter Caracter a cribar
     * @return Caracter cribado
     */
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

    /**
     * Este método permite cribar el caracter que recibe para que se corresponda
     * solo con los válidos correspondientes a una fila de un tablero de
     * ajedrez.
     *
     * @param caracter Caracter a cribar
     * @return Entero cribado
     */
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

    /**
     * Este método permite cribar el caracter que recibe para que se corresponda
     * solo con los válidos correspondientes a una columna de un tablero de
     * ajedrez.
     *
     * @param caracter Caracter a cribar
     * @return Entero cribado
     */
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
                Movimiento mov = new Movimiento(0, PosInicial, PosFinal);
                try {
                    moverJuego(mov, tablero);
                } catch (MovIncorrectoException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else if (jugada.length() == 5) {
            Posicion PosInicial = new Posicion(devolverFila(jugada.charAt(1)), devolverColumna(jugada.charAt(0)));
            Posicion PosFinal = new Posicion(devolverFila(jugada.charAt(3)), devolverColumna(jugada.charAt(2)));
            Movimiento mov = new Movimiento(0, PosInicial, PosFinal);
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
     * @throws MovIncorrectoException Excepción que notifica si un movimiento n ose pudo efectuar.
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
