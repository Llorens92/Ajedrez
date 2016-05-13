/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

/**
 *
 * @author dam1
 */
public class Tablero {

    protected String id;

    Tablero(String id) {
        this.id = id;
    }

    /**
     * Array bidimensional de piezas con dimensiones de 8x8
     */
    private Pieza[][] tablero = new Pieza[8][8];

    /**
     * Booleano que indica a quien le toca a mover. Se iniciliaza a true
     * (blancas)
     */
    private boolean turno = true;
    /**
     * Es un entero que controla el número de jugadas válidas que se producen en
     * la partida
     */
    private int contadorjugadas;
    /**
     * Es un array de movimientos donde se van introduciendo solo los válidos
     */
    private Movimiento[] arraymov = new Movimiento[200];
    /**
     * Entero que controla el número de veces que se mueve el rey blanco (para
     * el enroque)
     */
    private int contadormovreyblanco;
    /**
     * Entero que controla el número de veces que se mueve el rey negro (para el
     * enroque)
     */
    private int contadormovreynegro;
    /**
     * Booleano que controla si se ha movido o no la torre blanca de A
     */
    private boolean movtorreblancaA = false;
    /**
     * Booleano que controla si se ha movido o no la torre blanca de H
     */
    private boolean movtorreblancaH = false;
    /**
     * Booleano que controla si se ha movido o no la torre negra de A
     */
    private boolean movtorrenegraA = false;
    /**
     * Booleano que controla si se ha movido o no la torre negra de H
     */
    private boolean movtorrenegraH = false;

    public String getId() {
        return id;
    }
        
    
    public void colocaPieza(Pieza figura) {
        tablero[figura.getPos().getFila()][figura.getPos().getColumna()] = figura;
    }

    /**
     * Método que devuelve el array de movimientos que es atributo de esta clase
     *
     * @return Array de movimientos
     */
    public Movimiento[] getArraymov() {
        return arraymov;
    }

    /**
     * Método que devuelve el turno en el que se encuentra ahora mismo la
     * partida
     *
     * @return El booleano que indica el turno
     */
    public boolean getTurno() {
        return turno;
    }

    /**
     * Método que modifica el turno de la partida
     *
     * @param turno Booleano que indica el turno
     */
    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    /**
     * Método que devuelve el array de piezas atributo de esta clase
     *
     * @return Array de piezas
     */
    public Pieza[][] getTablero() {
        return tablero;
    }

    /**
     * Método que comprueba si hay una pieza dentro de una "casilla" en concreto
     * del array bidimensional
     *
     * @param fila fila del array
     * @param columna columna del array
     * @return Un booleano según si hay o no pieza
     */
    public boolean hayPieza(int fila, int columna) {
        boolean hayPieza = false;
        if (tablero[fila][columna] != null) {
            hayPieza = true;
        }
        return hayPieza;
    }

    /**
     * Método que comprueba si hay una pieza dentro de una "casilla" en concreto
     * del array bidimensional
     *
     * @param pos Posicion del array
     * @return Un booleano según si hay pieza o no
     */
    public boolean hayPieza(Posicion pos) {
        boolean hayPieza = false;
        if (tablero[pos.getFila()][pos.getColumna()] != null) {
            hayPieza = true;
        }
        return hayPieza;
    }

    /**
     * Método que comprueba si hay piezas entre dos posiciones del array. Para
     * ello se averigua primero de que tipo es el movimiento que componen.
     *
     * @param mov Movimiento a examinar formado por posición inicial y final
     * @return Un booleano en función de si hay o no piezas entre las dos
     * posiciones
     */
    public boolean hayPiezasEntre(Movimiento mov) {
        //Inicializamos el método para que devuelva un false:
        boolean hayPiezasEntre = false;
        //Si el movimiento es horizontal:
        if (mov.esHorizontal() == true) {
            //Si el movimiento es de izquierda a derecha:
            if (mov.getPosInicial().getColumna() < mov.getPosFinal().getColumna()) {
                //Recorremos la fila desde la posición consecutiva a la pieza que se va a mover a la posicion anterior a la final:
                for (int i = mov.getPosInicial().getColumna() + 1; i < mov.getPosFinal().getColumna(); i++) {
                    //Si hay una pieza cambiamos el valor a true
                    if (DevuelvePieza(mov.getPosInicial().getFila(), i) != null) {
                        hayPiezasEntre = true;
                    }
                }
            } else {//Si el movimiento es de derecha a izquierda:
                for (int i = mov.getPosInicial().getColumna() - 1; i > mov.getPosFinal().getColumna(); i--) {
                    if (DevuelvePieza(mov.getPosInicial().getFila(), i) != null) {
                        hayPiezasEntre = true;
                    }
                }
            }
        }
        if (mov.esVertical() == true) {
            //Si el movimiento es de arriba a abajo:
            if (mov.getPosInicial().getFila() < mov.getPosFinal().getFila()) {
                for (int i = mov.getPosInicial().getFila() + 1; i < mov.getPosFinal().getFila(); i++) {
                    if (DevuelvePieza(i, mov.getPosInicial().getColumna()) != null) {
                        hayPiezasEntre = true;
                    }
                }
            } else {//Si el movimiento es de abajo a arriba:
                for (int i = mov.getPosInicial().getFila() - 1; i > mov.getPosFinal().getFila(); i--) {
                    if (DevuelvePieza(i, mov.getPosInicial().getColumna()) != null) {
                        hayPiezasEntre = true;
                    }
                }
            }

        }
        if (mov.esDiagonal() == true) {
            //Si el número de fila y el número de columna descienden o aumentan a la vez:
            int columna = mov.getPosInicial().getColumna();
            if (mov.getPosFinal().getFila() - mov.getPosInicial().getFila() == mov.getPosFinal().getColumna() - mov.getPosInicial().getColumna()) {
                //Si la diagonal es de derecha a izquierda y ascendente:
                if (mov.getPosFinal().getFila() - mov.getPosInicial().getFila() < 0) {
                    for (int i = mov.getPosInicial().getFila() - 1; i > mov.getPosFinal().getFila(); i--) {
                        columna--;
                        if (DevuelvePieza(i, columna) != null) {
                            hayPiezasEntre = true;
                        }
                    }
                } else { //Si la diagonal es de izquierda a derecha y descendente:
                    for (int i = mov.getPosInicial().getFila() + 1; i < mov.getPosFinal().getFila(); i++) {
                        columna++;
                        if (DevuelvePieza(i, columna) != null) {
                            hayPiezasEntre = true;
                        }
                    }
                }
                //Si cuando aumenta el número de fila desciende el de columna o al revés:
            } else //Si la diagonal es de izquierda a derecha y ascendente:                
            {
                if (mov.getPosFinal().getColumna() > mov.getPosInicial().getColumna()) {
                    for (int i = mov.getPosInicial().getFila() - 1; i > mov.getPosFinal().getFila(); i--) {
                        columna++;
                        if (DevuelvePieza(i, columna) != null) {
                            hayPiezasEntre = true;
                        }
                    }
                } else {//Si la diagonal es de derecha a izquierda y descendente:                    
                    for (int i = mov.getPosInicial().getFila() + 1; i < mov.getPosFinal().getFila(); i++) {
                        columna--;
                        if (DevuelvePieza(i, columna) != null) {
                            hayPiezasEntre = true;
                        }
                    }
                }
            }
        }
        return hayPiezasEntre;
    }

    /**
     * Este Método averigua si un movimiento es valido en función de si al
     * producirse habría o no jaque al rey.
     *
     * @param mov Movimiento a examinar formado por posición inicial y final
     * @return Un booleano según si hay jaque o no al final del movimiento
     */
    public boolean Jaque(Movimiento mov) {
        boolean jaque = false;
        //Comprobamos que el que mueve no es el rey:
        if (tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()] != null && tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()].getClass().getName().compareTo("ajedrez.Rey") == 0) {
            jaque = false;
            //Y si lo es comprobamos que no está en jaque:
        } else if (tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()] != null && tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()].getClass().getName().compareTo("ajedrez.Rey") == 0 && Jaque(mov.getPosInicial().getFila(), mov.getPosInicial().getColumna()) == true) {
            jaque = true;
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {//Recorremos el tablero buscando el rey a cuyo bando le toca mover:
                    if (tablero[i][j] != null && tablero[i][j].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[i][j].getColor() == turno) {
                        //Inicializamos la posicion inicial de un movimiento "imaginario" para comprobar si hay piezas entre la pieza que puede hacer jaque y el rey:
                        Posicion posInicial = new Posicion(i, j);
                        Posicion posFinal = new Posicion(i, j);
                        Movimiento movimag = new Movimiento(posInicial, posFinal);
                        //Recorremos la vertical de arriba a abajo desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        for (int k = i; k < 8 && jaque == false; k++) {
                            if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                                jaque = true;
                                //Inicializamos la posición final del movimiento imaginario anteriormente mencionado a la posición de la pieza que supuestamente hace jaque:
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(j);
                                //Comprobamos que no hay piezas entre la pieza que supuestamente hace jaque y el rey:
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }//Comprobamos que nadie se come la pieza que hace jaque o bloquea el jaque:
                                if (jaque == true && mov.getPosFinal().getColumna() == j && mov.getPosFinal().getFila() > i && mov.getPosFinal().getFila() <= k) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la vertical de abajo a arriba desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        for (int k = i; k > -1 && jaque == false; k--) {
                            if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(j);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getColumna() == j && mov.getPosFinal().getFila() < i && mov.getPosFinal().getFila() >= k) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la horizontal de derecha a izquierda desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        for (int k = j; k > -1 && jaque == false; k--) {
                            if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(i);
                                movimag.getPosFinal().setColumna(k);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getFila() == i && mov.getPosFinal().getColumna() < j && mov.getPosFinal().getColumna() >= k) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la horizontal de izquierda a derecha desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        for (int k = j; k < 8 && jaque == false; k++) {
                            if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(i);
                                movimag.getPosFinal().setColumna(k);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getFila() == i && mov.getPosFinal().getColumna() > j && mov.getPosFinal().getColumna() <= k) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la diagonal descendente de izquierda a derecha desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        int columna = j - 1;
                        for (int k = i; k < 8 && jaque == false && columna < 7; k++) {
                            columna++;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getFila() - mov.getPosFinal().getColumna() == i - j && mov.getPosFinal().getFila() + mov.getPosFinal().getColumna() > i + j && mov.getPosFinal().getFila() + mov.getPosFinal().getColumna() <= k + columna) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la diagonal ascendente de derecha a izquierda desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        columna = j + 1;
                        for (int k = i; k > -1 && jaque == false && columna > 0; k--) {
                            columna--;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getFila() - mov.getPosFinal().getColumna() == i - j && mov.getPosFinal().getFila() + mov.getPosFinal().getColumna() < i + j && mov.getPosFinal().getFila() + mov.getPosFinal().getColumna() >= k + columna) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la diagonal descendente de derecha a izquierda desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        columna = j + 1;
                        for (int k = i; k < 8 && jaque == false && columna > 0; k++) {
                            columna--;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getFila() - j == i - mov.getPosFinal().getColumna() && mov.getPosFinal().getColumna() < j && mov.getPosFinal().getColumna() >= columna) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos la diagonal ascendente de izquierda a derecha desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque:
                        columna = j - 1;
                        for (int k = i; k > -1 && jaque == false && columna < 7; k--) {
                            columna++;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                jaque = true;
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == true) {
                                    jaque = false;
                                }
                                if (jaque == true && mov.getPosFinal().getFila() - j == i - mov.getPosFinal().getColumna() && mov.getPosFinal().getColumna() > j && mov.getPosFinal().getColumna() <= columna) {
                                    jaque = false;
                                }
                            }
                        }//Recorremos las posiciones desde las que podría hacer jaque un caballo rival:
                        int aux;
                        int cambiofila = -2;
                        int cambiocolumna = -1;
                        int fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        for (int k = 0; k < 8; k++) {
                            if (k == 0 && fila > -1 && columna > -1) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno && mov.getPosFinal() != tablero[fila][columna].getPos()) {
                                    jaque = true;
                                }
                            } else if (k % 2 != 0) {
                                aux = cambiofila;
                                cambiofila = cambiocolumna;
                                cambiocolumna = aux;
                                fila = i + cambiofila;
                                columna = j + cambiocolumna;
                                if (fila > -1 && fila < 8 && columna > -1 && columna < 8 && tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno && mov.getPosFinal() != tablero[fila][columna].getPos()) {
                                    jaque = true;
                                }

                            } else {
                                switch (k) {
                                    case 2:
                                        cambiofila = cambiofila + 2;
                                        fila = i + cambiofila;
                                        columna = j + cambiocolumna;
                                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno && mov.getPosFinal() != tablero[fila][columna].getPos()) {
                                                jaque = true;
                                            }
                                        }
                                        break;
                                    case 4:
                                        cambiofila = cambiofila + 4;
                                        fila = i + cambiofila;
                                        columna = j + cambiocolumna;
                                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno && mov.getPosFinal() != tablero[fila][columna].getPos()) {
                                                jaque = true;
                                            }
                                        }
                                        break;
                                    case 6:
                                        cambiofila = cambiofila - 2;
                                        fila = i + cambiofila;
                                        columna = j + cambiocolumna;
                                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno && mov.getPosFinal() != tablero[fila][columna].getPos()) {
                                                jaque = true;
                                            }
                                        }
                                        break;
                                }

                            }
                        }//Recorremos las posiciones en las que podría hacer jaque un peon tanto al rey blanco como al negro:
                        if (i > 0 && j < 7 && j > 0 && tablero[i][j].getColor() == true && ((tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j - 1].getColor() != turno && mov.getPosFinal() != tablero[i - 1][j - 1].getPos()) || (tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j + 1].getColor() != turno && mov.getPosFinal() != tablero[i - 1][j + 1].getPos()))) {
                            jaque = true;
                        }
                        if (i < 7 && j < 7 && j > 0 && tablero[i][j].getColor() == false && ((tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j - 1].getColor() != turno && mov.getPosFinal() != tablero[i + 1][j - 1].getPos()) || (tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j + 1].getColor() != turno && mov.getPosFinal() != tablero[i + 1][j + 1].getPos()))) {
                            jaque = true;
                        }
                        if (i > 0 && j == 7 && tablero[i][j].getColor() == true && tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j - 1].getColor() != turno && mov.getPosFinal() != tablero[i - 1][j - 1].getPos()) {
                            jaque = true;
                        }
                        if (i > 0 && j == 0 && tablero[i][j].getColor() == true && tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j + 1].getColor() != turno && mov.getPosFinal() != tablero[i - 1][j + 1].getPos()) {
                            jaque = true;
                        }
                        if (i < 7 && j == 7 && tablero[i][j].getColor() == false && tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j - 1].getColor() != turno && mov.getPosFinal() != tablero[i + 1][j - 1].getPos()) {
                            jaque = true;
                        }
                        if (i < 7 && j == 0 && tablero[i][j].getColor() == false && tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j + 1].getColor() != turno && mov.getPosFinal() != tablero[i + 1][j + 1].getPos()) {
                            jaque = true;
                        }
                    }
                }
            }
        }
        return jaque;
    }

    /**
     * Con este Método comprobamos si una casilla concreta del array está siendo
     * atacada.
     *
     * @param fila1 Fila de la casilla supuestamente atacada
     * @param columna1 Columna de la casilla supuestamente atacada
     * @return Un booleano según si está siendo atacada o no
     */
    public boolean Jaque(int fila1, int columna1) {
        boolean jaque = false;
        int i = fila1;
        int j = columna1;
        //Inicializamos la posicion inicial de un movimiento "imaginario" para comprobar si hay piezas entre la pieza que puede hacer jaque o atacar esa casilla y el rey (o la casilla supuestamente atacada):
        Posicion posInicial = new Posicion(i, j);
        Posicion posFinal = new Posicion(i, j);
        Movimiento movimag = new Movimiento(posInicial, posFinal);
        //Recorremos la vertical de arriba a abajo desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:        
        for (int k = i; k < 8 && jaque == false; k++) {
            if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                jaque = true;
                //Inicializamos la posición final del movimiento imaginario anteriormente mencionado a la posición de la pieza que supuestamente hace jaque:
                movimag.getPosFinal().setFila(k);
                movimag.getPosFinal().setColumna(j);
                //Comprobamos que no hay piezas entre la pieza que supuestamente hace jaque y el rey:
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la vertical de abajo a arriba desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        for (int k = i; k > -1 && jaque == false; k--) {
            if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(k);
                movimag.getPosFinal().setColumna(j);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la horizontal de derecha a izquierda desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        for (int k = j; k > -1 && jaque == false; k--) {
            if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(i);
                movimag.getPosFinal().setColumna(k);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la horizontal de izquierda a derecha desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        for (int k = j; k < 8 && jaque == false; k++) {
            if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(i);
                movimag.getPosFinal().setColumna(k);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la diagonal descendente de izquierda a derecha desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        int columna = j - 1;
        for (int k = i; k < 8 && jaque == false && columna < 7; k++) {
            columna++;
            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(k);
                movimag.getPosFinal().setColumna(columna);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la diagonal ascendente de derecha a izquierda desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        columna = j + 1;
        for (int k = i; k > -1 && jaque == false && columna > 0; k--) {
            columna--;
            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(k);
                movimag.getPosFinal().setColumna(columna);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la diagonal descendente de derecha a izquierda desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        columna = j + 1;
        for (int k = i; k < 8 && jaque == false && columna > 0; k++) {
            columna--;
            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(k);
                movimag.getPosFinal().setColumna(columna);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos la diagonal ascendente de izquierda a derecha desde la posicion en la que está el rey hasta encontrar una pieza que haga jaque o ataque esa casilla:
        columna = j - 1;
        for (int k = i; k > -1 && jaque == false && columna < 7; k--) {
            columna++;
            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                jaque = true;
                movimag.getPosFinal().setFila(k);
                movimag.getPosFinal().setColumna(columna);
                if (hayPiezasEntre(movimag) == true) {
                    jaque = false;
                }
            }
        }//Recorremos las posiciones desde las que podría hacer jaque un caballo rival o atacar esa casilla:
        int aux;
        int cambiofila = -2;
        int cambiocolumna = -1;
        int fila = i + cambiofila;
        columna = j + cambiocolumna;
        for (int k = 0; k < 8; k++) {
            if (k == 0 && fila > -1 && columna > -1) {
                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno) {
                    jaque = true;
                }
            } else if (k % 2 != 0) {
                aux = cambiofila;
                cambiofila = cambiocolumna;
                cambiocolumna = aux;
                fila = i + cambiofila;
                columna = j + cambiocolumna;
                if (fila > -1 && fila < 8 && columna > -1 && columna < 8 && tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno) {
                    jaque = true;
                }

            } else {
                switch (k) {
                    case 2:
                        cambiofila = cambiofila + 2;
                        fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno) {
                                jaque = true;
                            }
                        }
                        break;
                    case 4:
                        cambiofila = cambiofila + 4;
                        fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno) {
                                jaque = true;
                            }
                        }
                        break;
                    case 6:
                        cambiofila = cambiofila - 2;
                        fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() != turno) {
                                jaque = true;
                            }
                        }
                        break;
                }

            }
        }//Recorremos las posiciones en las que un peon podría atacar esa casilla o hacer jaque tanto al rey blanco como al negro:
        if (i > 0 && j < 7 && j > 0 && (tablero[i][j] == null || (tablero[i][j] != null && tablero[i][j].getColor() == false)) && ((tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j - 1].getColor() != turno) || (tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j + 1].getColor() != turno))) {
            jaque = true;
        }
        if (i < 7 && j < 7 && j > 0 && (tablero[i][j] == null || (tablero[i][j] != null && tablero[i][j].getColor() == true)) && ((tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j - 1].getColor() != turno) || (tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j + 1].getColor() != turno))) {
            jaque = true;
        }
        if (i > 0 && j == 7 && (tablero[i][j] == null || (tablero[i][j] != null && tablero[i][j].getColor() == false)) && tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j - 1].getColor() != turno) {
            jaque = true;
        }
        if (i > 0 && j == 0 && (tablero[i][j] == null || (tablero[i][j] != null && tablero[i][j].getColor() == false)) && tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][j + 1].getColor() != turno) {
            jaque = true;
        }
        if (i < 7 && j == 7 && (tablero[i][j] == null || (tablero[i][j] != null && tablero[i][j].getColor() == true)) && tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j - 1].getColor() != turno) {
            jaque = true;
        }
        if (i < 7 && j == 0 && (tablero[i][j] == null || (tablero[i][j] != null && tablero[i][j].getColor() == true)) && tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][j + 1].getColor() != turno) {
            jaque = true;
        }
        //Recorremos las posiciones desde las que podría estar atacando una casilla el rey enemigo:
        cambiofila = -1;
        cambiocolumna = -1;
        fila = i + cambiofila;
        columna = j + cambiocolumna;
        for (int k = 0; k < 8; k++) {
            if (k == 0 && fila > -1 && columna > -1) {
                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[fila][columna].getColor() != turno) {
                    jaque = true;
                }
            } else if (k % 2 != 0) {
                cambiofila = -cambiofila;
                cambiocolumna = -cambiocolumna;
                fila = i + cambiofila;
                columna = j + cambiocolumna;
                if (fila > -1 && fila < 8 && columna > -1 && columna < 8 && tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[fila][columna].getColor() != turno) {
                    jaque = true;
                }

            } else {
                switch (k) {
                    case 2:
                        cambiofila = cambiofila - 2;
                        cambiocolumna--;
                        fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[fila][columna].getColor() != turno) {
                                jaque = true;
                            }
                        }
                        break;
                    case 4:
                        cambiofila = cambiofila - 2;
                        cambiocolumna++;
                        fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[fila][columna].getColor() != turno) {
                                jaque = true;
                            }
                        }
                        break;
                    case 6:
                        cambiofila--;
                        cambiocolumna = cambiocolumna + 2;
                        fila = i + cambiofila;
                        columna = j + cambiocolumna;
                        if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                            if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[fila][columna].getColor() != turno) {
                                jaque = true;
                            }
                        }
                        break;
                }
            }
        }

        return jaque;
    }

    /**
     * Con este Método averiguamos si las casillas alrededor del rey están
     * siendo atacadas o no. Para ello tenemos que distinguir entre posiciones
     * interiores y los bordes y las esquinas pues sino correriamos el peligro
     * de caer fuera del array.
     *
     * @return Un booleano según si están siendo atacadas todas o no
     */
    public boolean comprobarPosibleJaque() {
        boolean comprobarPosibleJaque = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {//Ubicamos a nuestro rey y comprobamos si hay jaque en las posiciones vacías u ocupadas por otra pieza enemiga:
                if (tablero[i][j] != null && tablero[i][j].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[i][j].getColor() == turno) {
                    //Comprobamos si el rey está en una casilla interior:
                    if (i < 7 && i > 0 && j < 7 && j > 0) {
                        int contadorPosJaques = 8;
                        int contadorjaques = 8;
                        if (tablero[i - 1][j - 1] == null || (tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j] == null || (tablero[i - 1][j] != null && tablero[i - 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j + 1] == null || (tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j + 1] == null || (tablero[i][j + 1] != null && tablero[i][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j + 1] == null || (tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j] == null || (tablero[i + 1][j] != null && tablero[i + 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j - 1] == null || (tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j - 1] == null || (tablero[i][j - 1] != null && tablero[i][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 8) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está entre A2 y A7:
                    if (i < 7 && i > 0 && j == 0) {
                        int contadorPosJaques = 5;
                        int contadorjaques = 5;
                        if (tablero[i - 1][j] == null || (tablero[i - 1][j] != null && tablero[i - 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j + 1] == null || (tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j + 1] == null || (tablero[i][j + 1] != null && tablero[i][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j + 1] == null || (tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j] == null || (tablero[i + 1][j] != null && tablero[i + 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 5) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está entre H2 y H7:
                    if (i < 7 && i > 0 && j == 7) {
                        int contadorPosJaques = 5;
                        int contadorjaques = 5;
                        if (tablero[i - 1][j] == null || (tablero[i - 1][j] != null && tablero[i - 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j - 1] == null || (tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j - 1] == null || (tablero[i][j - 1] != null && tablero[i][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j - 1] == null || (tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j] == null || (tablero[i + 1][j] != null && tablero[i + 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 5) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está entre B8 Y G8:
                    if (i == 0 && j < 7 && j > 0) {
                        int contadorPosJaques = 5;
                        int contadorjaques = 5;
                        if (tablero[i][j + 1] == null || (tablero[i][j + 1] != null && tablero[i][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j + 1] == null || (tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j] == null || (tablero[i + 1][j] != null && tablero[i + 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j - 1] == null || (tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j - 1] == null || (tablero[i][j - 1] != null && tablero[i][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 5) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está entre B1 Y G1:
                    if (i == 7 && j < 7 && j > 0) {
                        int contadorPosJaques = 5;
                        int contadorjaques = 5;
                        if (tablero[i][j - 1] == null || (tablero[i][j - 1] != null && tablero[i][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j - 1] == null || (tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j] == null || (tablero[i - 1][j] != null && tablero[i - 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j + 1] == null || (tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j + 1] == null || (tablero[i][j + 1] != null && tablero[i][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 5) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está en A8:
                    if (i == 0 && j == 0) {
                        int contadorPosJaques = 3;
                        int contadorjaques = 3;
                        if (tablero[i][j + 1] == null || (tablero[i][j + 1] != null && tablero[i][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j + 1] == null || (tablero[i + 1][j + 1] != null && tablero[i + 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j] == null || (tablero[i + 1][j] != null && tablero[i + 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 3) {
                            comprobarPosibleJaque = true;
                        }
                    }
                    //Comprobamos si el rey está en H8
                    if (i == 0 && j == 7) {
                        int contadorPosJaques = 3;
                        int contadorjaques = 3;
                        if (tablero[i + 1][j] == null || (tablero[i + 1][j] != null && tablero[i + 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i + 1][j - 1] == null || (tablero[i + 1][j - 1] != null && tablero[i + 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i + 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j - 1] == null || (tablero[i][j - 1] != null && tablero[i][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 3) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está en H1
                    if (i == 7 && j == 7) {
                        int contadorPosJaques = 3;
                        int contadorjaques = 3;
                        if (tablero[i][j - 1] == null || (tablero[i][j - 1] != null && tablero[i][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j - 1] == null || (tablero[i - 1][j - 1] != null && tablero[i - 1][j - 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j - 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j] == null || (tablero[i - 1][j] != null && tablero[i - 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 3) {
                            comprobarPosibleJaque = true;
                        }
                    }//Comprobamos si el rey está en A1
                    if (i == 7 && j == 0) {
                        int contadorPosJaques = 3;
                        int contadorjaques = 3;
                        if (tablero[i - 1][j] == null || (tablero[i - 1][j] != null && tablero[i - 1][j].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i - 1][j + 1] == null || (tablero[i - 1][j + 1] != null && tablero[i - 1][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i - 1, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (tablero[i][j + 1] == null || (tablero[i][j + 1] != null && tablero[i][j + 1].getColor() != turno)) {
                            contadorPosJaques--;
                            if (Jaque(i, j + 1) == true) {
                                contadorjaques--;
                            }
                        }
                        if (contadorPosJaques == contadorjaques && contadorjaques != 3) {
                            comprobarPosibleJaque = true;
                        }
                    }
                }
            }
        }
        return comprobarPosibleJaque;
    }

    /**
     * Este Método comprueba si el movimiento del rey es valido o no
     *
     * @param mov El movimiento que pretende realizar el rey
     * @return Un booleano según si es valido o no
     */
    public boolean comprobarPosibleJaque(Movimiento mov) {
        boolean comprobarPosibleJaque = false;
        if (tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()] != null && tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()].getClass().getName().compareTo("ajedrez.Rey") == 0 && Jaque(mov.getPosFinal().getFila(), mov.getPosFinal().getColumna()) == true) {
            comprobarPosibleJaque = true;
        }
        return comprobarPosibleJaque;
    }

    /**
     * Este Método averigua si una pieza puede hacer un movimiento o no en
     * función de si esta clavada.
     *
     * @param mov El movimiento que se pretende realizar
     * @return Un booleano según si es valido o no
     */
    public boolean piezaClavada(Movimiento mov) {
        boolean piezaClavada;
        //Solo comprobamos si esta clavada si no es el rey:
        if (tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()] != null && tablero[mov.getPosInicial().getFila()][mov.getPosInicial().getColumna()].getClass().getName().compareTo("ajedrez.Rey") == 0) {
            piezaClavada = false;
        } else {
            piezaClavada = false;
            boolean ataque = false;
            //Comprobamos si la pieza está siendo atacada:        
            int i = mov.getPosInicial().getFila();
            int j = mov.getPosInicial().getColumna();
            //Inicializamos la posicion inicial de un movimiento "imaginario" para comprobar si hay piezas entre la pieza que puede estar clavando y la nuestra:
            Posicion posInicial = new Posicion(i, j);
            Posicion posFinal = new Posicion(i, j);
            Movimiento movimag = new Movimiento(posInicial, posFinal);
            //Recorremos la vertical de arriba a abajo desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            for (int k = i; k < 8 && ataque == false; k++) {
                if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                    ataque = true;
                    //Inicializamos la posición final del movimiento imaginario anteriormente mencionado a la posición de la pieza que supuestamente ataca nuestra pieza:
                    movimag.getPosFinal().setFila(k);
                    movimag.getPosFinal().setColumna(j);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente ataca la nuestra y ésta:
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                for (int k = i; k > -1 && reyclavado == false; k--) {
                    if (tablero[k][j] == null || (tablero[k][j] != null && tablero[k][j].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[k][j].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosFinal().getColumna() != mov.getPosInicial().getColumna()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la vertical de abajo a arriba desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            for (int k = i; k > -1 && ataque == false; k--) {
                if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(k);
                    movimag.getPosFinal().setColumna(j);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                for (int k = i; k < 8 && reyclavado == false; k++) {
                    if (tablero[k][j] == null || (tablero[k][j] != null && tablero[k][j].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[k][j].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosFinal().getColumna() != mov.getPosInicial().getColumna()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la horizontal de derecha a izquierda desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            for (int k = j; k > -1 && ataque == false; k--) {
                if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(i);
                    movimag.getPosFinal().setColumna(k);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                for (int k = j; k < 8 && reyclavado == false; k++) {
                    if (tablero[i][k] == null || (tablero[i][k] != null && tablero[i][k].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[i][k].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosFinal().getFila() != mov.getPosInicial().getFila()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la horizontal de izquierda a derecha desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            for (int k = j; k < 8 && ataque == false; k++) {
                if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(i);
                    movimag.getPosFinal().setColumna(k);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                for (int k = j; k > -1 && reyclavado == false; k--) {
                    if (tablero[i][k] == null || (tablero[i][k] != null && tablero[i][k].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[i][k].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosFinal().getFila() != mov.getPosInicial().getFila()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la diagonal descendente de izquierda a derecha desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            int columna = j - 1;
            for (int k = i; k < 8 && ataque == false && columna < 7; k++) {
                columna++;
                if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(k);
                    movimag.getPosFinal().setColumna(columna);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                columna = j + 1;
                for (int k = i; k > -1 && reyclavado == false && columna > 0; k--) {
                    columna--;
                    if (tablero[k][columna] == null || (tablero[k][columna] != null && tablero[k][columna].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[k][columna].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosFinal().getFila() - mov.getPosFinal().getColumna() != mov.getPosInicial().getFila() - mov.getPosInicial().getColumna()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la diagonal ascendente de derecha a izquierda desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            columna = j + 1;
            for (int k = i; k > -1 && ataque == false && columna > 0; k--) {
                columna--;
                if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(k);
                    movimag.getPosFinal().setColumna(columna);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                columna = j - 1;
                for (int k = i; k < 8 && reyclavado == false && columna < 7; k++) {
                    columna++;
                    if (tablero[k][columna] == null || (tablero[k][columna] != null && tablero[k][columna].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[k][columna].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosFinal().getFila() - mov.getPosFinal().getColumna() != mov.getPosInicial().getFila() - mov.getPosInicial().getColumna()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la diagonal descendente de derecha a izquierda desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            columna = j + 1;
            for (int k = i; k < 8 && ataque == false && columna > 0; k++) {
                columna--;
                if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(k);
                    movimag.getPosFinal().setColumna(columna);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                columna = j - 1;
                for (int k = i; k > -1 && reyclavado == false && columna < 7; k--) {
                    columna++;
                    if (tablero[k][columna] == null || (tablero[k][columna] != null && tablero[k][columna].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[k][columna].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosInicial().getFila() - mov.getPosFinal().getColumna() != mov.getPosFinal().getFila() - mov.getPosInicial().getColumna()) {
                    piezaClavada = true;
                }
                ataque = false;
            }
            //Recorremos la diagonal ascendente de izquierda a derecha desde la posicion en la que está la pieza que supuestamente está clavada hasta encontrar una pieza que la ataque:
            columna = j - 1;
            for (int k = i; k > -1 && ataque == false && columna < 7; k--) {
                columna++;
                if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                    ataque = true;
                    movimag.getPosFinal().setFila(k);
                    movimag.getPosFinal().setColumna(columna);
                    if (hayPiezasEntre(movimag) == true) {
                        ataque = false;
                    }
                }
            }
            if (ataque == true) {
                //Si la pieza esta siendo atacada comprobamos que el rey propio está detrás
                boolean reyclavado = false;
                columna = j + 1;
                for (int k = i; k < 8 && reyclavado == false && columna > 0; k++) {
                    columna--;
                    if (tablero[k][columna] == null || (tablero[k][columna] != null && tablero[k][columna].getClass().getName().compareTo("ajedrez.Rey") != 0)) {
                        reyclavado = false;
                    } else if (tablero[k][columna].getColor() == turno) {
                        reyclavado = true;
                    }
                }//Si la pieza esta clavada comprobamos que no se intenta mover dentro de la dirección del ataque:
                if (reyclavado == true && mov.getPosInicial().getFila() - mov.getPosFinal().getColumna() != mov.getPosFinal().getFila() - mov.getPosInicial().getColumna()) {
                    piezaClavada = true;
                }
            }
        }
        return piezaClavada;
    }

    /**
     * Este Método localiza al rey, averigua si está siendo atacado y en ese
     * caso te dice si puedes abortar el ataque.
     *
     * @return Un booleano según si te puedes cubrir o no
     */
    public boolean cubrirJaque() {
        boolean cubrirJaque = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {//Recorremos el tablero buscando el rey a cuyo bando le toca mover:
                if (tablero[i][j] != null && tablero[i][j].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero[i][j].getColor() == turno) {
                    //Comprobamos si le están haciendo jaque:
                    if (Jaque(i, j) == true) {
                        Posicion posInicial = new Posicion(i, j);
                        Posicion posFinal = new Posicion(i, j);
                        Movimiento movimag = new Movimiento(posInicial, posFinal);
                        //Comprobamos la vertical de arriba a abajo:
                        for (int k = i; k < 8; k++) {
                            if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(j);
                                //Comprobamos que no hay piezas entre la pieza que supuestamente hace jaque y el rey:
                                if (hayPiezasEntre(movimag) == false) {
                                    //Comprobamos las casillas entre el rey y la pieza que hace jaque:
                                    if (cubrirjaquevertical(i, k, j) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }//Comprobamos la vertical de abajo a arriba:
                        for (int k = i; k > -1; k--) {
                            if (tablero[k][j] != null && (tablero[k][j].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[k][j].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][j].getColor() != turno) {
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(j);
                                //Comprobamos que no hay piezas entre la pieza que supuestamente hace jaque y el rey:
                                if (hayPiezasEntre(movimag) == false) {
                                    //Comprobamos las casillas entre el rey y la pieza que hace jaque:
                                    if (cubrirjaquevertical(k, i, j) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }//Comprobamos la horizontal de derecha a izquierda:
                        for (int k = j; k > -1; k--) {
                            if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                                movimag.getPosFinal().setFila(i);
                                movimag.getPosFinal().setColumna(k);
                                //Comprobamos que no hay piezas entre la pieza que supuestamente hace jaque y el rey:
                                if (hayPiezasEntre(movimag) == false) {
                                    //Comprobamos las casillas entre el rey y la pieza que hace jaque:
                                    if (cubrirjaquehorizontal(i, j, k) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }//Comprobamos la horizontal de izquierda a derecha:
                        for (int k = j; k < 8; k++) {
                            if (tablero[i][k] != null && (tablero[i][k].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[i][k].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[i][k].getColor() != turno) {
                                movimag.getPosFinal().setFila(i);
                                movimag.getPosFinal().setColumna(k);
                                //Comprobamos que no hay piezas entre la pieza que supuestamente hace jaque y el rey:
                                if (hayPiezasEntre(movimag) == false) {
                                    //Comprobamos las casillas entre el rey y la pieza que hace jaque:
                                    if (cubrirjaquehorizontal(i, k, j) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }//Comprobamos la diagonal descendente de izquierda a derecha:
                        int columna = j - 1;
                        for (int k = i; k < 8 && columna < 7; k++) {
                            columna++;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == false) {
                                    if (cubrirjaquediagonaldescendente(i, j, k, columna) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }//Comprobamos la diagonal ascendente de derecha a izquierda:
                        columna = j + 1;
                        for (int k = i; k > -1 && columna > 0; k--) {
                            columna--;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == false) {
                                    if (cubrirjaquediagonaldescendente(k, columna, i, j) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }
                        //Comprobamos la diagonal ascendente de izquierda a derecha:
                        columna = j + 1;
                        for (int k = i; k < 8 && columna > 0; k++) {
                            columna--;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == false) {
                                    if (cubrirjaquediagonalascendente(k, columna, i, j) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }//Comprobamos la diagonal descendente de derecha a izquierda:
                        columna = j + 1;
                        for (int k = i; k > -1 && columna > 0; k--) {
                            columna--;
                            if (tablero[k][columna] != null && (tablero[k][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[k][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[k][columna].getColor() != turno) {
                                movimag.getPosFinal().setFila(k);
                                movimag.getPosFinal().setColumna(columna);
                                if (hayPiezasEntre(movimag) == false) {
                                    if (cubrirjaquediagonalascendente(i, j, k, columna) == true) {
                                        cubrirJaque = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return cubrirJaque;
    }

    /**
     * Este Método te permite saber si habiendo un jaque en una de las
     * diagonales ascendentes puedes abortarlo.
     *
     * @param i Fila de la primera de las casillas que se van a comprobar si se
     * pueden cubrir
     * @param j Columna de la primera de las casillas que se van a comprobar si
     * se pueden cubrir
     * @param k Fila de la última de las casillas que se van a comprobar si se
     * pueden cubrir
     * @param columna Columna de la última de las casillas que se van a
     * comprobar si se pueden cubrir
     * @return Un booleano según si se puede cubrir o no
     */
    public boolean cubrirjaquediagonalascendente(int i, int j, int k, int columna) {
        boolean cubrirjaquediagonalascendente = false;
        int fila = i;
        for (int l = j + 1; l <= columna && fila > k; l++) {
            fila--;
            Posicion posInicial = new Posicion(fila, l);
            Posicion posFinal = new Posicion(fila, l);
            Movimiento movimag = new Movimiento(posInicial, posFinal);
            //Comprobamos las posiciones desde las que podría cubrir un peon:
            if (fila > 1 && (tablero[fila - 1][l] != null && tablero[fila - 1][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila - 1][l].getColor() == false && turno == false)) {
                cubrirjaquediagonalascendente = true;
                Posicion posInicial2 = new Posicion(fila - 1, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonalascendente = false;
                }
            }
            if (fila > 1 && tablero[fila - 2][l] != null && tablero[fila - 2][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila - 2][l].getColor() == false && turno == false && fila == 3 && tablero[fila - 1][l] == null) {
                cubrirjaquediagonalascendente = true;
                Posicion posInicial2 = new Posicion(fila - 2, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonalascendente = false;
                }
            }
            if (fila < 6 && (tablero[fila + 1][l] != null && tablero[fila + 1][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila + 1][l].getColor() == true && turno == true)) {
                cubrirjaquediagonalascendente = true;
                Posicion posInicial2 = new Posicion(fila + 1, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonalascendente = false;
                }
            }
            if (tablero[fila + 2][l] != null && tablero[fila + 2][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila + 2][l].getColor() == true && turno == true && fila == 4 && tablero[fila + 1][l] == null) {
                cubrirjaquediagonalascendente = true;
                Posicion posInicial2 = new Posicion(fila + 2, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonalascendente = false;
                }
            }
            //Comprobamos la vertical de izquierda a derecha:
            for (int n = fila + 1; n < 8 && cubrirjaquediagonalascendente == false; n++) {
                if (tablero[n][l] != null && (tablero[n][l].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[n][l].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][l].getColor() == turno) {
                    cubrirjaquediagonalascendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(l);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonalascendente = false;
                    }
                }
            }//Comprobamos la vertical de derecha a izquierda:
            for (int n = fila - 1; n > 0 && cubrirjaquediagonalascendente == false; n--) {
                if (tablero[n][l] != null && (tablero[n][l].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[n][l].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][l].getColor() == turno) {
                    cubrirjaquediagonalascendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(l);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonalascendente = false;
                    }
                }
            }
            //Comprobamos la horizontal de izquierda a derecha:
            for (int n = l + 1; n < 8 && cubrirjaquediagonalascendente == false; n++) {
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquediagonalascendente = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonalascendente = false;
                    }
                }
            }//Comprobamos la horizontal de derecha a izquierda:
            for (int n = l - 1; n > 0 && cubrirjaquediagonalascendente == false; n--) {
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquediagonalascendente = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonalascendente = false;
                    }
                }
            }//Comprobamos las diagonales:
            int columna2 = l;
            for (int n = fila + 1; n < 8 && cubrirjaquediagonalascendente == false && columna2 < 7; n++) {
                columna2++;
                if (tablero[n][columna2] != null && (tablero[n][columna2].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna2].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna2].getColor() == turno) {
                    cubrirjaquediagonalascendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna2);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonalascendente = false;
                    }
                }
            }
            columna2 = l;
            for (int n = fila - 1; n > 0 && cubrirjaquediagonalascendente == false && columna2 > 0; n--) {
                columna2--;
                if (tablero[n][columna2] != null && (tablero[n][columna2].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna2].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna2].getColor() == turno) {
                    cubrirjaquediagonalascendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna2);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonalascendente = false;
                    }
                }
            }
            int aux;
            int cambiofila = -2;
            int cambiocolumna = -1;
            int fila2 = fila + cambiofila;
            columna2 = l + cambiocolumna;
            //Comprobamos las posiciones desde las que podría cubrir un caballo:
            for (int n = 0; n < 8; n++) {
                if (n == 0 && fila2 > -1 && columna2 > -1) {
                    if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                        cubrirjaquediagonalascendente = true;
                    }
                } else if (n % 2 != 0) {
                    aux = cambiofila;
                    cambiofila = cambiocolumna;
                    cambiocolumna = aux;
                    fila2 = l + cambiofila;
                    columna2 = j + cambiocolumna;
                    if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8 && tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                        cubrirjaquediagonalascendente = true;
                    }

                } else {
                    switch (k) {
                        case 2:
                            cambiofila = cambiofila + 2;
                            fila2 = l + cambiofila;
                            columna2 = j + cambiocolumna;
                            if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8) {
                                if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                                    cubrirjaquediagonalascendente = true;
                                }
                            }
                            break;
                        case 4:
                            cambiofila = cambiofila + 4;
                            fila2 = l + cambiofila;
                            columna2 = j + cambiocolumna;
                            if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8) {
                                if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                                    cubrirjaquediagonalascendente = true;
                                }
                            }
                            break;
                        case 6:
                            cambiofila = cambiofila - 2;
                            fila2 = l + cambiofila;
                            columna2 = j + cambiocolumna;
                            if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8) {
                                if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                                    cubrirjaquediagonalascendente = true;
                                }
                            }
                            break;
                    }
                }
            }
        }
        return cubrirjaquediagonalascendente;
    }

    /**
     * Este Método te permite saber si habiendo un jaque en una de las
     * diagonales descendentes puedes abortarlo.
     *
     * @param i Fila de la primera de las casillas que se van a comprobar si se
     * pueden cubrir
     * @param j Columna de la primera de las casillas que se van a comprobar si
     * se pueden cubrir
     * @param k Fila de la última de las casillas que se van a comprobar si se
     * pueden cubrir
     * @param columna Columna de la última de las casillas que se van a
     * comprobar si se pueden cubrir
     * @return Un booleano según si se puede cubrir o no
     */
    public boolean cubrirjaquediagonaldescendente(int i, int j, int k, int columna) {
        boolean cubrirjaquediagonaldescendente = false;
        int fila = i;
        for (int l = j + 1; l <= columna && fila < k; l++) {
            fila++;
            Posicion posInicial = new Posicion(fila, l);
            Posicion posFinal = new Posicion(fila, l);
            Movimiento movimag = new Movimiento(posInicial, posFinal);
            //Comprobamos las posiciones desde las que podría cubrir un peon:
            if (fila > 1 && (tablero[fila - 1][l] != null && tablero[fila - 1][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila - 1][l].getColor() == false && turno == false)) {
                cubrirjaquediagonaldescendente = true;
                Posicion posInicial2 = new Posicion(fila - 1, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonaldescendente = false;
                }
            }
            if (fila > 1 && tablero[fila - 2][l] != null && tablero[fila - 2][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila - 2][l].getColor() == false && turno == false && fila == 3 && tablero[fila - 1][l] == null) {
                cubrirjaquediagonaldescendente = true;
                Posicion posInicial2 = new Posicion(fila - 2, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonaldescendente = false;
                }
            }
            if (fila < 6 && (tablero[fila + 1][l] != null && tablero[fila + 1][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila + 1][l].getColor() == true && turno == true)) {
                cubrirjaquediagonaldescendente = true;
                Posicion posInicial2 = new Posicion(fila + 1, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonaldescendente = false;
                }
            }
            if (tablero[fila + 2][l] != null && tablero[fila + 2][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[fila + 2][l].getColor() == true && turno == true && fila == 4 && tablero[fila + 1][l] == null) {
                cubrirjaquediagonaldescendente = true;
                Posicion posInicial2 = new Posicion(fila + 2, l);
                Posicion posFinal2 = new Posicion(fila, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquediagonaldescendente = false;
                }
            }
            //Comprobamos la vertical de izquierda a derecha:
            for (int n = fila + 1; n < 8 && cubrirjaquediagonaldescendente == false; n++) {
                if (tablero[n][l] != null && (tablero[n][l].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[n][l].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][l].getColor() == turno) {
                    cubrirjaquediagonaldescendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(l);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonaldescendente = false;
                    }
                }
            }//Comprobamos la vertical de derecha a izquierda:
            for (int n = fila - 1; n > 0 && cubrirjaquediagonaldescendente == false; n--) {
                if (tablero[n][l] != null && (tablero[n][l].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[n][l].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][l].getColor() == turno) {
                    cubrirjaquediagonaldescendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(l);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonaldescendente = false;
                    }
                }
            }
            //Comprobamos la horizontal de izquierda a derecha:
            for (int n = l + 1; n < 8 && cubrirjaquediagonaldescendente == false; n++) {
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquediagonaldescendente = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonaldescendente = false;
                    }
                }
            }//Comprobamos la horizontal de derecha a izquierda:
            for (int n = l - 1; n > 0 && cubrirjaquediagonaldescendente == false; n--) {
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquediagonaldescendente = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonaldescendente = false;
                    }
                }
            }//Comprobamos las diagonales:
            int columna2 = l;
            for (int n = fila + 1; n < 8 && cubrirjaquediagonaldescendente == false && columna2 > 0; n++) {
                columna2--;
                if (tablero[n][columna2] != null && (tablero[n][columna2].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna2].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna2].getColor() == turno) {
                    cubrirjaquediagonaldescendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna2);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonaldescendente = false;
                    }
                }
            }
            columna2 = l;
            for (int n = fila - 1; n > 0 && cubrirjaquediagonaldescendente == false && columna2 > 0; n--) {
                columna2++;
                if (tablero[n][columna2] != null && (tablero[n][columna2].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna2].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna2].getColor() == turno) {
                    cubrirjaquediagonaldescendente = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna2);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquediagonaldescendente = false;
                    }
                }
            }
            int aux;
            int cambiofila = -2;
            int cambiocolumna = -1;
            int fila2 = fila + cambiofila;
            columna2 = l + cambiocolumna;
            //Comprobamos las posiciones desde las que podría cubrir un caballo:
            for (int n = 0; n < 8; n++) {
                if (n == 0 && fila2 > -1 && columna2 > -1) {
                    if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                        cubrirjaquediagonaldescendente = true;
                    }
                } else if (n % 2 != 0) {
                    aux = cambiofila;
                    cambiofila = cambiocolumna;
                    cambiocolumna = aux;
                    fila2 = l + cambiofila;
                    columna2 = j + cambiocolumna;
                    if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8 && tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                        cubrirjaquediagonaldescendente = true;
                    }

                } else {
                    switch (k) {
                        case 2:
                            cambiofila = cambiofila + 2;
                            fila2 = l + cambiofila;
                            columna2 = j + cambiocolumna;
                            if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8) {
                                if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                                    cubrirjaquediagonaldescendente = true;
                                }
                            }
                            break;
                        case 4:
                            cambiofila = cambiofila + 4;
                            fila2 = l + cambiofila;
                            columna2 = j + cambiocolumna;
                            if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8) {
                                if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                                    cubrirjaquediagonaldescendente = true;
                                }
                            }
                            break;
                        case 6:
                            cambiofila = cambiofila - 2;
                            fila2 = l + cambiofila;
                            columna2 = j + cambiocolumna;
                            if (fila2 > -1 && fila2 < 8 && columna2 > -1 && columna2 < 8) {
                                if (tablero[fila2][columna2] != null && tablero[fila2][columna2].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila2][columna2].getColor() == turno) {
                                    cubrirjaquediagonaldescendente = true;
                                }
                            }
                            break;
                    }
                }
            }
        }
        return cubrirjaquediagonaldescendente;
    }

    /**
     * Este Método te permite saber si habiendo un jaque en una de las filas
     * puedes abortarlo.
     *
     * @param i Fila de las casillas que se van a comprobar si se pueden cubrir
     * @param j Columna de la primera de las casillas que se van a comprobar si
     * se pueden cubrir
     * @param k Columna de la última de las casillas que se van a comprobar si
     * se pueden cubrir
     * @return Un booleano según si se puede cubrir o no
     */
    public boolean cubrirjaquehorizontal(int i, int k, int j) {
        boolean cubrirjaquehorizontal = false;
        for (int l = j + 1; l <= k; l++) {
            Posicion posInicial = new Posicion(i, l);
            Posicion posFinal = new Posicion(i, l);
            Movimiento movimag = new Movimiento(posInicial, posFinal);
            //Comprobamos las posiciones desde las que podría cubrir un peon:
            if (i > 1 && (tablero[i - 1][l] != null && tablero[i - 1][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 1][l].getColor() == false && turno == false)) {
                cubrirjaquehorizontal = true;
                Posicion posInicial2 = new Posicion(i - 1, l);
                Posicion posFinal2 = new Posicion(i, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquehorizontal = false;
                }
            }
            if (tablero[i - 2][l] != null && tablero[i - 2][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i - 2][l].getColor() == false && turno == false && i == 3 && tablero[i - 1][l] == null) {
                cubrirjaquehorizontal = true;
                Posicion posInicial2 = new Posicion(i - 2, l);
                Posicion posFinal2 = new Posicion(i, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquehorizontal = false;
                }
            }
            if (i < 6 && (tablero[i + 1][l] != null && tablero[i + 1][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 1][l].getColor() == true && turno == true)) {
                cubrirjaquehorizontal = true;
                Posicion posInicial2 = new Posicion(i + 1, l);
                Posicion posFinal2 = new Posicion(i, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquehorizontal = false;
                }
            }
            if (tablero[i + 2][l] != null && tablero[i + 2][l].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero[i + 2][l].getColor() == true && turno == true && i == 4 && tablero[i + 1][l] == null) {
                cubrirjaquehorizontal = true;
                Posicion posInicial2 = new Posicion(i + 2, l);
                Posicion posFinal2 = new Posicion(i, l);
                Movimiento movimag2 = new Movimiento(posInicial2, posFinal2);
                if (piezaClavada(movimag2) == true) {
                    cubrirjaquehorizontal = false;
                }
            }
            //Comprobamos la vertical de izquierda a derecha:
            for (int n = i + 1; n < 8 && cubrirjaquehorizontal == false; n++) {
                if (tablero[n][l] != null && (tablero[n][l].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[n][l].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][l].getColor() == turno) {
                    cubrirjaquehorizontal = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(l);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquehorizontal = false;
                    }
                }
            }//Comprobamos la vertical de derecha a izquierda:
            for (int n = i - 1; n > 0 && cubrirjaquehorizontal == false; n--) {
                if (tablero[n][l] != null && (tablero[n][l].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[n][l].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][l].getColor() == turno) {
                    cubrirjaquehorizontal = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(l);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquehorizontal = false;
                    }
                }
            }
            int columna = l;
            //Comprobamos las diagonales:
            for (int n = i + 1; n < 8 && cubrirjaquehorizontal == false && columna < 7; n++) {
                columna++;
                if (tablero[n][columna] != null && (tablero[n][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna].getColor() == turno) {
                    cubrirjaquehorizontal = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquehorizontal = false;
                    }
                }
            }
            columna = l;
            for (int n = i + 1; n < 8 && cubrirjaquehorizontal == false && columna > 0; n++) {
                columna--;
                if (tablero[n][columna] != null && (tablero[n][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna].getColor() == turno) {
                    cubrirjaquehorizontal = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquehorizontal = false;
                    }
                }
            }
            columna = l;
            for (int n = i - 1; n > 0 && cubrirjaquehorizontal == false && columna > 0; n--) {
                columna--;
                if (tablero[n][columna] != null && (tablero[n][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna].getColor() == turno) {
                    cubrirjaquehorizontal = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquehorizontal = false;
                    }
                }
            }
            columna = l;
            for (int n = i - 1; n > 0 && cubrirjaquehorizontal == false && columna < 7; n--) {
                columna++;
                if (tablero[n][columna] != null && (tablero[n][columna].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[n][columna].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[n][columna].getColor() == turno) {
                    cubrirjaquehorizontal = true;
                    movimag.getPosFinal().setFila(n);
                    movimag.getPosFinal().setColumna(columna);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquehorizontal = false;
                    }
                }
            }
            int aux;
            int cambiofila = -2;
            int cambiocolumna = -1;
            int fila = i + cambiofila;
            columna = l + cambiocolumna;
            //Comprobamos las posiciones desde las que podría cubir un caballo:
            for (int n = 0; n < 8; n++) {
                if (n == 0 && fila > -1 && columna > -1) {
                    if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                        cubrirjaquehorizontal = true;
                    }
                } else if (n % 2 != 0) {
                    aux = cambiofila;
                    cambiofila = cambiocolumna;
                    cambiocolumna = aux;
                    fila = l + cambiofila;
                    columna = j + cambiocolumna;
                    if (fila > -1 && fila < 8 && columna > -1 && columna < 8 && tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                        cubrirjaquehorizontal = true;
                    }

                } else {
                    switch (k) {
                        case 2:
                            cambiofila = cambiofila + 2;
                            fila = l + cambiofila;
                            columna = j + cambiocolumna;
                            if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                                    cubrirjaquehorizontal = true;
                                }
                            }
                            break;
                        case 4:
                            cambiofila = cambiofila + 4;
                            fila = l + cambiofila;
                            columna = j + cambiocolumna;
                            if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                                    cubrirjaquehorizontal = true;
                                }
                            }
                            break;
                        case 6:
                            cambiofila = cambiofila - 2;
                            fila = l + cambiofila;
                            columna = j + cambiocolumna;
                            if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                                    cubrirjaquehorizontal = true;
                                }
                            }
                            break;
                    }
                }
            }
        }
        return cubrirjaquehorizontal;
    }

    /**
     * Este Método te permite saber si habiendo un jaque en una de las columnas
     * puedes abortarlo.
     *
     * @param i Fila de la primera de las casillas que se van a comprobar si se
     * pueden cubrir
     * @param j Columna de las casillas que se van a comprobar si se pueden
     * cubrir
     * @param k Fila de la última de las casillas que se van a comprobar si se
     * pueden cubrir
     * @return Un booleano según si se puede cubrir o no
     */
    public boolean cubrirjaquevertical(int i, int k, int j) {
        boolean cubrirjaquevertical = false;
        for (int l = i + 1; l <= k; l++) {
            Posicion posInicial = new Posicion(l, j);
            Posicion posFinal = new Posicion(l, j);
            Movimiento movimag = new Movimiento(posInicial, posFinal);
            //Comprobamos la horizontal de izquierda a derecha:
            for (int n = j + 1; n < 8 && cubrirjaquevertical == false; n++) {
                if (tablero[l][n] != null && (tablero[l][n].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[l][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[l][n].getColor() == turno) {
                    cubrirjaquevertical = true;
                    movimag.getPosFinal().setFila(l);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquevertical = false;
                    }
                }
            }//Comprobamos la horizontal de derecha a izquierda:
            for (int n = j - 1; n > 0 && cubrirjaquevertical == false; n--) {
                if (tablero[l][n] != null && (tablero[l][n].getClass().getName().compareTo("ajedrez.Torre") == 0 || tablero[l][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[l][n].getColor() == turno) {
                    cubrirjaquevertical = true;
                    movimag.getPosFinal().setFila(l);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquevertical = false;
                    }
                }
            }
            int fila = l;
            //Comprobamos las diagonales:
            for (int n = j + 1; n < 8 && cubrirjaquevertical == false && fila < 7; n++) {
                fila++;
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquevertical = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquevertical = false;
                    }
                }
            }
            fila = l;
            for (int n = j + 1; n < 8 && cubrirjaquevertical == false && fila > 0; n++) {
                fila--;
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquevertical = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquevertical = false;
                    }
                }
            }
            fila = l;
            for (int n = j - 1; n > 0 && cubrirjaquevertical == false && fila > 0; n--) {
                fila--;
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquevertical = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquevertical = false;
                    }
                }
            }
            fila = l;
            for (int n = j - 1; n > 0 && cubrirjaquevertical == false && fila < 7; n--) {
                fila++;
                if (tablero[fila][n] != null && (tablero[fila][n].getClass().getName().compareTo("ajedrez.Alfil") == 0 || tablero[fila][n].getClass().getName().compareTo("ajedrez.Dama") == 0) && tablero[fila][n].getColor() == turno) {
                    cubrirjaquevertical = true;
                    movimag.getPosFinal().setFila(fila);
                    movimag.getPosFinal().setColumna(n);
                    //Comprobamos que no hay piezas entre la pieza que supuestamente puede cubrir y la casilla a cubrir:
                    if (hayPiezasEntre(movimag) == true) {
                        cubrirjaquevertical = false;
                    }
                }
            }
            int aux;
            int cambiofila = -2;
            int cambiocolumna = -1;
            fila = l + cambiofila;
            int columna = j + cambiocolumna;
            //Comprobamos las posiciones desde las que podría cubir un caballo:
            for (int n = 0; n < 8; n++) {
                if (n == 0 && fila > -1 && columna > -1) {
                    if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                        cubrirjaquevertical = true;
                    }
                } else if (n % 2 != 0) {
                    aux = cambiofila;
                    cambiofila = cambiocolumna;
                    cambiocolumna = aux;
                    fila = l + cambiofila;
                    columna = j + cambiocolumna;
                    if (fila > -1 && fila < 8 && columna > -1 && columna < 8 && tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                        cubrirjaquevertical = true;
                    }

                } else {
                    switch (k) {
                        case 2:
                            cambiofila = cambiofila + 2;
                            fila = l + cambiofila;
                            columna = j + cambiocolumna;
                            if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                                    cubrirjaquevertical = true;
                                }
                            }
                            break;
                        case 4:
                            cambiofila = cambiofila + 4;
                            fila = l + cambiofila;
                            columna = j + cambiocolumna;
                            if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                                    cubrirjaquevertical = true;
                                }
                            }
                            break;
                        case 6:
                            cambiofila = cambiofila - 2;
                            fila = l + cambiofila;
                            columna = j + cambiocolumna;
                            if (fila > -1 && fila < 8 && columna > -1 && columna < 8) {
                                if (tablero[fila][columna] != null && tablero[fila][columna].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero[fila][columna].getColor() == turno) {
                                    cubrirjaquevertical = true;
                                }
                            }
                            break;
                    }
                }
            }
        }
        return cubrirjaquevertical;
    }

    /**
     * Método que comprueba si el movimiento del enroque es valido.
     *
     * @param mov Movimiento del rey distinto a uno valido normalmente
     * @return Un booleano si te puedes enrocar o no
     */
    public boolean enroqueValido(Movimiento mov) {
        boolean enroqueValido = false; //Comprobamos si alguien intenta hacer el enroque corto con las blancas:
        if (DevuelvePieza(mov.getPosInicial()) != null && DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Rey") == 0 && DevuelvePieza(mov.getPosInicial()).getColor() == true && mov.getPosInicial().getFila() == 7 && mov.getPosInicial().getColumna() == 4 && mov.getPosFinal().getFila() == 7 && mov.getPosFinal().getColumna() == 6 && Jaque(7, 4) == false && Jaque(7, 5) == false && Jaque(7, 6) == false) {
            //Llamamos 160 veces al método mover para distinguir entre las veces que lo hemos llamado para mover cualquiera de los reyes o las torres y la que lo llamamos para saber si ya se han movido los susodichos reyes o torres:
            for (int i = 0; i < 20; i++) {
                //Hacemos el enroque siempre que el entero que nos devuelve el método mover coincida con el valor que queremos en la última llamada: 
                if (Mover(mov) == 0 && i == 19) {
                    enroqueValido = true;
                    //Borramos la torre correspondiente y la colocamos en su nuevo lugar:
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 2 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 3 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 5 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 6 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 7 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 8 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 13 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][7], 7, 5);
                    quitaPieza(7, 7);
                    //Al final de la comprobación borramos el movimiento producido por la llamada al método mover llamado para saber si se podía o no enrocar:
                } else if (DevuelvePieza(mov.getPosFinal()) != null && i == 19) {
                    ponPieza(DevuelvePieza(mov.getPosFinal()), mov.getPosInicial());
                    quitaPieza(mov.getPosFinal());
                }
            }
        } //Comprobamos si alguien intenta hacer el enroque largo con las blancas:
        if (DevuelvePieza(mov.getPosInicial()) != null && DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Rey") == 0 && DevuelvePieza(mov.getPosInicial()).getColor() == true && mov.getPosInicial().getFila() == 7 && mov.getPosInicial().getColumna() == 4 && mov.getPosFinal().getFila() == 7 && mov.getPosFinal().getColumna() == 2 && Jaque(7, 4) == false && Jaque(7, 3) == false && Jaque(7, 2) == false) {
            //Llamamos 160 veces al método mover para distinguir entre las veces que lo hemos llamado para mover cualquiera de los reyes o las torres y la que lo llamamos para saber si ya se han movido los susodichos reyes o torres:
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 0 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 2 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 4 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 5 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 6 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 9 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 10 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 14 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[7][0], 7, 3);
                    quitaPieza(7, 0);
                    //Al final de la comprobación borramos el movimiento producido por la llamada al método mover llamado para saber si se podía o no enrocar:
                } else if (DevuelvePieza(mov.getPosFinal()) != null && i == 19) {
                    ponPieza(DevuelvePieza(mov.getPosFinal()), mov.getPosInicial());
                    quitaPieza(mov.getPosFinal());
                }
            }
        }//Comprobamos si alguien intenta hacer el enroque corto con las negras:
        if (DevuelvePieza(mov.getPosInicial()) != null && DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Rey") == 0 && DevuelvePieza(mov.getPosInicial()).getColor() == false && mov.getPosInicial().getFila() == 0 && mov.getPosInicial().getColumna() == 4 && mov.getPosFinal().getFila() == 0 && mov.getPosFinal().getColumna() == 6 && Jaque(0, 4) == false && Jaque(0, 5) == false && Jaque(0, 6) == false) {
            //Llamamos 160 veces al método mover para distinguir entre las veces que lo hemos llamado para mover cualquiera de los reyes o las torres y la que lo llamamos para saber si ya se han movido los susodichos reyes o torres:
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 0 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 1 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 3 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 4 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 5 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 7 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 10 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 11 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][7], 0, 5);
                    quitaPieza(0, 7);
                    //Al final de la comprobación borramos el movimiento producido por la llamada al método mover llamado para saber si se podía o no enrocar:
                } else if (DevuelvePieza(mov.getPosFinal()) != null && i == 19) {
                    ponPieza(DevuelvePieza(mov.getPosFinal()), mov.getPosInicial());
                    quitaPieza(mov.getPosFinal());
                }
            }
        }//Comprobamos si alguien intenta hacer el enroque largo con las negras:
        if (DevuelvePieza(mov.getPosInicial()) != null && DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Rey") == 0 && DevuelvePieza(mov.getPosInicial()).getColor() == false && mov.getPosInicial().getFila() == 0 && mov.getPosInicial().getColumna() == 4 && mov.getPosFinal().getFila() == 0 && mov.getPosFinal().getColumna() == 2 && Jaque(0, 4) == false && Jaque(0, 3) == false && Jaque(0, 2) == false) {
            //Llamamos 160 veces al método mover para distinguir entre las veces que lo hemos llamado para mover cualquiera de los reyes o las torres y la que lo llamamos para saber si ya se han movido los susodichos reyes o torres:
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 0 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 1 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 3 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 4 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 6 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 8 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 9 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                }
            }
            for (int i = 0; i < 20; i++) {
                if (Mover(mov) == 12 && i == 19) {
                    enroqueValido = true;
                    ponPieza(tablero[0][0], 0, 3);
                    quitaPieza(0, 0);
                    //Al final de la comprobación borramos el movimiento producido por la llamada al método mover llamado para saber si se podía o no enrocar:
                } else if (DevuelvePieza(mov.getPosFinal()) != null && i == 19) {
                    ponPieza(DevuelvePieza(mov.getPosFinal()), mov.getPosInicial());
                    quitaPieza(mov.getPosFinal());
                }
            }
        }
        return enroqueValido;
    }

    /**
     * Este Método te dice si puedes comer al paso o no en función de los dos
     * últimos movimientos cargados en el array.
     *
     * @param arraymov Array de movimientos atributo de esta clase
     * @return Un booleano según si se puede o no
     */
    public boolean comerAlPaso(Movimiento[] arraymov) {
        boolean comerAlPaso = false;//Comprobamos si el movimiento coincide con uno de comer al paso:        
        if (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 1 && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 0) {
            //Comprobamos si el movimiento anterior al que se quiere realizar coincide con  el del peon que debe haber movido para que el movimiento anteriormente mencionado sea valido:
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 0 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 0) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 2)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 1) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 1 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 1) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 3)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 2) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 2 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 2) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 4)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 3) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 3 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 3) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 5)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 4) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 4 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 4) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 6)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 5) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 5 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 5) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 7)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 6) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 6 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (arraymov[arraymov.length - 1].getPosInicial().getFila() == 4 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 6 && arraymov[arraymov.length - 1].getPosFinal().getFila() == 5 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 7) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == true && arraymov[arraymov.length - 3].getPosInicial().getFila() == 6 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 7 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 4) {
                comerAlPaso = true;
            }
        }
        if (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 1 && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 0) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 0 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 0) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 2)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 1) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 1 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 1) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 3)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 2) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 2 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 2) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 4)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 3) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 3 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 3) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 5)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 4) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 4 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 4) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 6)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 5) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 5 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (((arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 5) || (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 7)) && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 6) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 6 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        if (arraymov[arraymov.length - 1].getPosInicial().getFila() == 3 && arraymov[arraymov.length - 1].getPosInicial().getColumna() == 6 && arraymov[arraymov.length - 1].getPosFinal().getFila() == 2 && arraymov[arraymov.length - 1].getPosFinal().getColumna() == 7) {
            if (DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()) != null && DevuelvePieza(arraymov[arraymov.length - 3].getPosFinal()).getColor() == false && arraymov[arraymov.length - 3].getPosInicial().getFila() == 1 && arraymov[arraymov.length - 3].getPosInicial().getColumna() == 7 && arraymov[arraymov.length - 3].getPosFinal().getFila() == 3) {
                comerAlPaso = true;
            }
        }
        return comerAlPaso;

    }

    /**
     * Método que realiza la promoción (es decir, la transformación en otra
     * pieza) del peón.
     *
     * @param mov Movimiento que realiza el peon para promocionar. Puede ser
     * hacia delante o en diagonal según si hay pieza en la casilla de destino o
     * no.
     * @param nuevaPieza Caracter que indica cual es la pieza en que se va a
     * transformar el peon.
     */
    public void promociondelPeon(Movimiento mov, char nuevaPieza) {
        //Comprobamos que la pieza es un peon:
        if (DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0) {
            //Comprobamos que el peon cumple las condiciones para promocionar según su color:
            if (mov.getPosInicial().getFila() == 1 && ((mov.movimientoPeonBlanco() == true && hayPieza(mov.getPosFinal()) == false) || (mov.comerPeonBlanco() == true && hayPieza(mov.getPosFinal()) == true && DevuelvePieza(mov.getPosFinal()).getColor() != turno))) {
                switch (nuevaPieza) {
                    case 'a':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Alfil(true, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    case 'c':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Caballo(true, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    case 'd':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Dama(true, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    case 't':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Torre(true, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    default:
                        System.out.println("Caracter no valido");
                }
                //Comprobamos que el peon cumple las condiciones para promocionar según su color:
            } else if (mov.getPosInicial().getFila() == 6 && ((mov.movimientoPeonNegro() == true && hayPieza(mov.getPosFinal()) == false) || (mov.comerPeonNegro() == true && hayPieza(mov.getPosFinal()) == true && DevuelvePieza(mov.getPosFinal()).getColor() != turno))) {
                switch (nuevaPieza) {
                    case 'a':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Alfil(false, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    case 'c':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Caballo(false, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    case 'd':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Dama(false, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    case 't':
                        tablero[mov.getPosFinal().getFila()][mov.getPosFinal().getColumna()] = new Torre(false, mov.getPosFinal().getFila(), mov.getPosFinal().getColumna());
                        quitaPieza(mov.getPosInicial());
                        break;
                    default:
                        System.out.println("Caracter no valido");
                }
            } else {
                System.out.println("El peon no ha llegado a la penúltima fila");
            }
        } else {
            System.out.println("La pieza no es un peon");
        }
    }

    /**
     * Método que hace una copia de la pieza que se desee en la casilla
     * indicada.
     *
     * @param figura Pieza a colocar
     * @param fila Fila de la casilla donde se quiere colar la pieza
     * @param columna Columna de la casilla donde se quiere colar la pieza
     */
    public void ponPieza(Pieza figura, int fila, int columna) {
        tablero[fila][columna] = figura;
        tablero[fila][columna].setFila(fila);
        tablero[fila][columna].setColumna(columna);
    }

    /**
     * Método que hace una copia de la pieza que se desee en la casilla
     * indicada.
     *
     * @param figura Pieza a colocar
     * @param Pos Posición donde se quiere colar la pieza
     */
    public void ponPieza(Pieza figura, Posicion Pos) {
        tablero[Pos.getFila()][Pos.getColumna()] = figura;
        tablero[Pos.getFila()][Pos.getColumna()].setFila(Pos.getFila());
        tablero[Pos.getFila()][Pos.getColumna()].setColumna(Pos.getColumna());
    }

    /**
     * Método que borra el contenido de una casilla concreta del array
     *
     * @param fila Fila de la casilla que se quiere borrar
     * @param columna Columna de la casilla que se quiere borrar
     */
    public void quitaPieza(int fila, int columna) {
        tablero[fila][columna] = null;
    }

    /**
     * Método que borra el contenido de una casilla concreta del array
     *
     * @param Pos Posición del array que se desea borrar
     */
    public void quitaPieza(Posicion Pos) {
        tablero[Pos.getFila()][Pos.getColumna()] = null;
    }

    /**
     * Método que devuelve el contenido de una casilla del array
     *
     * @param fila Fila de la casilla que devuelve
     * @param columna Columna de la casilla que devuelve
     * @return Una pieza o null según si hay contenido o no
     */
    public Pieza DevuelvePieza(int fila, int columna) {
        return tablero[fila][columna];
    }

    /**
     * Método que devuelve el contenido de una casilla del array
     *
     * @param pos Posición de la casilla que devuelve
     * @return Una pieza o null según si hay contenido o no
     */
    public Pieza DevuelvePieza(Posicion pos) {
        return tablero[pos.getFila()][pos.getColumna()];
    }

    /**
     * Este Método recibe un movimiento y lo anula , es decir, devuelve la pieza
     * a su posición inicial, borra el movimiento del array y rectifica el
     * turno.
     *
     * @param mov Movimiento a modificar
     */
    public void anularMovimiento(Movimiento mov) {
        ponPieza(DevuelvePieza(mov.getPosFinal()), mov.getPosInicial());
        quitaPieza(mov.getPosFinal());
        for (int i = arraymov.length - 2; i > arraymov.length - contadorjugadas; i--) {
            arraymov[i] = arraymov[i - 1];

        }
        arraymov[arraymov.length - 1] = arraymov[arraymov.length - 2];
        contadorjugadas--;
        if (turno == false) {
            turno = true;
        } else {
            turno = false;
        }

    }

    /**
     * Este Metódo es el que finalmente realiza los movimientos validos que han
     * superado los filtros anteriores y los que tiene el propio método. Además
     * los carga en el array de movimientos validos. Como añadido el método
     * devuelve un entero utilizado para validar el enroque.
     *
     * @param mov Movimiento a efectuar
     * @return Entero que indica las combinaciones de enroques que se pueden
     * realizar en función de las combinaciones de movimientos de reyes y torres
     * que se hayan producido
     */
    public int Mover(Movimiento mov) {
        int enroquesValidos = 0;
        boolean movreyblanco = false;
        boolean movreynegro = false;
        if (Jaque(mov) == false && comprobarPosibleJaque(mov) == false && piezaClavada(mov) == false) {
            if (DevuelvePieza(mov.getPosInicial()) != null) {
                ponPieza(DevuelvePieza(mov.getPosInicial()), mov.getPosFinal());
                quitaPieza(mov.getPosInicial());
                arraymov[arraymov.length - 1] = mov;
                contadorjugadas++;
                for (int i = arraymov.length - contadorjugadas; i < arraymov.length; i++) {
                    if (i > arraymov.length - contadorjugadas) {
                        arraymov[i - 1] = arraymov[i];
                    }
                }
            }
            if (DevuelvePieza(mov.getPosFinal()) != null && DevuelvePieza(mov.getPosFinal()).getClass().getName().compareTo("ajedrez.Rey") == 0 && DevuelvePieza(mov.getPosFinal()).getColor() == true) {
                contadormovreyblanco++;
                //Como se indicaba en el método enroqueValido, se considerará que el rey no ha movido y por tanto puede enrocarse si el contador de movimientos indica un múltiplo de 20:
                if (contadormovreyblanco % 20 == 0) {
                    movreyblanco = false;
                } else {
                    movreyblanco = true;
                }
            }
            if (DevuelvePieza(mov.getPosFinal()) != null && DevuelvePieza(mov.getPosFinal()).getClass().getName().compareTo("ajedrez.Rey") == 0 && DevuelvePieza(mov.getPosFinal()).getColor() == false) {
                contadormovreynegro++;
                //Como se indicaba en el método enroqueValido, se considerará que el rey no ha movido y por tanto puede enrocarse si el contador de movimientos indica un múltiplo de 20:
                if (contadormovreynegro % 20 == 0) {
                    movreynegro = false;
                } else {
                    movreynegro = true;
                }
            }//Comprobamos si se mueve alguna de las torres:
            if (DevuelvePieza(mov.getPosFinal()) != null && DevuelvePieza(mov.getPosFinal()).getClass().getName().compareTo("ajedrez.Torre") == 0 && DevuelvePieza(mov.getPosFinal()).getColor() == true && mov.getPosInicial().getFila() == 7 && mov.getPosInicial().getColumna() == 0) {
                movtorreblancaA = true;
            }
            if (DevuelvePieza(mov.getPosFinal()) != null && DevuelvePieza(mov.getPosFinal()).getClass().getName().compareTo("ajedrez.Torre") == 0 && DevuelvePieza(mov.getPosFinal()).getColor() == true && mov.getPosInicial().getFila() == 7 && mov.getPosInicial().getColumna() == 7) {
                movtorreblancaH = true;
            }
            if (DevuelvePieza(mov.getPosFinal()) != null && DevuelvePieza(mov.getPosFinal()).getClass().getName().compareTo("ajedrez.Torre") == 0 && DevuelvePieza(mov.getPosFinal()).getColor() == false && mov.getPosInicial().getFila() == 0 && mov.getPosInicial().getColumna() == 0) {
                movtorrenegraA = true;
            }
            if (DevuelvePieza(mov.getPosFinal()) != null && DevuelvePieza(mov.getPosFinal()).getClass().getName().compareTo("ajedrez.Torre") == 0 && DevuelvePieza(mov.getPosFinal()).getColor() == false && mov.getPosInicial().getFila() == 0 && mov.getPosInicial().getColumna() == 7) {
                movtorrenegraH = true;
            }
            if (turno == false) {
                turno = true;
            } else {
                turno = false;
            }
        } else if (comprobarPosibleJaque() == true && Jaque(mov) == true && cubrirJaque() == false) {
            System.out.println("Jaque Mate");
        }
        if (piezaClavada(mov) == true) {
            System.out.println("La pieza esta clavada");
        }
        if (Jaque(mov) == true) {
            System.out.println("Jaque");
        }
        if (comprobarPosibleJaque(mov) == true) {
            System.out.println("La casilla de destino está siendo atacada");
        }

        //Asignamos un valor al entero que devuelve el método en función de las posibles combinaciones de enroques que se puedan dar:
        if (movreyblanco == true || (movtorreblancaA == true && movtorreblancaH == true)) {
            enroquesValidos = 1;
        }
        if (movreynegro == true || (movtorrenegraA == true && movtorrenegraH == true)) {
            enroquesValidos = 2;
        }
        if (movtorreblancaA == true) {
            enroquesValidos = 3;
        }
        if (movtorreblancaH == true) {
            enroquesValidos = 4;
        }
        if (movtorrenegraA == true) {
            enroquesValidos = 5;
        }
        if (movtorrenegraH == true) {
            enroquesValidos = 6;
        }
        if (movtorreblancaA == true && movtorrenegraA == true) {
            enroquesValidos = 7;
        }
        if (movtorreblancaA == true && movtorrenegraH == true) {
            enroquesValidos = 8;
        }
        if (movtorreblancaH == true && movtorrenegraH == true) {
            enroquesValidos = 9;
        }
        if (movtorreblancaH == true && movtorrenegraA == true) {
            enroquesValidos = 10;
        }
        if (((movtorreblancaA == true && movtorreblancaH == true) || movreyblanco == true) && movtorrenegraA == true) {
            enroquesValidos = 11;
        }
        if (((movtorreblancaA == true && movtorreblancaH == true) || movreyblanco == true) && movtorrenegraH == true) {
            enroquesValidos = 12;
        }
        if (((movtorrenegraA == true && movtorrenegraH == true) || movreynegro == true) && movtorreblancaA == true) {
            enroquesValidos = 13;
        }
        if (((movtorrenegraA == true && movtorrenegraH == true) || movreynegro == true) && movtorreblancaH == true) {
            enroquesValidos = 14;
        }
        return enroquesValidos;
    }
}
