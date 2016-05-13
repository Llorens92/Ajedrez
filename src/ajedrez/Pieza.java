/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.Serializable;

/**
 *
 * @author dam1
 */
public abstract class Pieza implements Serializable{

    /**
     * Booleano que indica el color de la pieza (blancas true, negras false)
     */
    protected boolean color;
    /**
     * Entero que indica la fila en que se encuentra la pieza
     */
    protected int fila;
    /**
     * Entero que indica la columna en que se encuentra la pieza
     */
    protected int columna;
    /**
     * Objeto de clase Posición que indica la posición en que se encuentra la
     * pieza
     */
    protected Posicion pos;

    /**
     * Constructor que inicializa el color y la posición de la pieza
     *
     * @param color Booleano que indica el color de la pieza (blancas true,
     * negras false)
     * @param fila Entero que indica la fila en que se encuentra la pieza
     * @param columna Entero que indica la columna en que se encuentra la pieza
     */
    public Pieza(boolean color, int fila, int columna) {
        this.color = color;
        this.fila = fila;
        this.columna = columna;
        pos =  new Posicion(fila, columna);
    }

    /**
     * Método que devuelve la posición de la pieza
     *
     * @return Posición de la pieza
     */
    public Posicion getPos() {
        return pos;
    }

    /**
     * Método que modifica la fila de la pieza
     *
     * @param fila Nueva fila
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * Método que modifica la columna de la pieza
     *
     * @param columna Nueva columna
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * Mñetodo que devuelve el color de la pieza
     *
     * @return Color de la pieza
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Método que define si el movimiento es valido para cierta pieza
     *
     * @param mov Movimiento a validar
     * @return Booleano según si es valido o no
     */
    public abstract boolean movimientoValido(Movimiento mov);

    /**
     * Método que pinta cada pieza en la consola.
     */
    public abstract void pintarPieza();

    /**
     * Método que mueve la pieza dentro del array en función de unos criterios
     * (generalmente que el método movimiento valido sea true).
     *
     * @param mov Movimiento a realizar
     * @param tablero Objeto de tipo tablero que llama a mover de tablero
     */
    public abstract void moverPieza(Movimiento mov, Tablero tablero);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
