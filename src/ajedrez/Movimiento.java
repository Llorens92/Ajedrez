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
public class Movimiento {

    /**
     * Esta es la posición inicial de nuestro movimiento
     */
    private Posicion PosInicial;
    /**
     * Esta es la posición final de nuestro movimiento
     */
    private Posicion PosFinal;

    /**
     * Este Método construye los objetos de clase Movimiento en función de unos
     * parámetros de entrada que deben cumplir ciertos requisitos
     *
     * @param PosInicial Posición inicial del movimiento a construir
     * @param PosFinal Posición final del movimiento a construir
     */
    public Movimiento(Posicion PosInicial, Posicion PosFinal) {
        if (PosInicial.getColumna() > -1 && PosInicial.getColumna() < 8 && PosFinal.getColumna() > -1 && PosFinal.getColumna() < 8 && PosInicial.getFila() > -1 && PosInicial.getFila() < 8 && PosFinal.getFila() > -1 && PosFinal.getFila() < 8) {
            this.PosInicial = PosInicial;
            this.PosFinal = PosFinal;
        } else {
            System.out.println("Movimiento invalido-MOVIMIENTO");
        }
    }

    /**
     * Método que te devuelve la posicion inicial del movimiento
     *
     * @return Posicion inicial del movimiento
     */
    public Posicion getPosInicial() {
        return PosInicial;
    }

    /**
     * Método que te devuelve la posición final del movimiento
     *
     * @return Posicion final del movimiento
     */
    public Posicion getPosFinal() {
        return PosFinal;
    }

    /**
     * Método que te dice si el movimiento es vertical
     *
     * @return Booleano en función de si lo es o no
     */
    public boolean esVertical() {
        boolean vertical = false;
        if (PosInicial.getColumna() == PosFinal.getColumna()) {
            vertical = true;
        }
        return vertical;
    }

    /**
     * Método que te dice si el movimiento es horizontal
     *
     * @return Booleano en función de si lo es o no
     */
    public boolean esHorizontal() {
        boolean horizontal = false;
        if (PosInicial.getFila() == PosFinal.getFila()) {
            horizontal = true;
        }
        return horizontal;
    }

    /**
     * Método que te dice si el movimiento es diagonal
     *
     * @return Booleano en función de si lo es o no
     */
    public boolean esDiagonal() {
        boolean diagonal = false;
        if (PosInicial.getFila() - PosFinal.getFila() == PosInicial.getColumna() - PosFinal.getColumna() || PosInicial.getFila() - PosFinal.getFila() == PosFinal.getColumna() - PosInicial.getColumna()) {
            diagonal = true;
        }
        return diagonal;
    }

    /**
     * Método que te dice si el movimiento es el de un caballo saltando en
     * horizontal
     *
     * @return Booleano en función de si lo es o no
     */
    public boolean saltoHorizontal() {
        boolean saltocaballo = false;
        if ((PosInicial.getFila() == PosFinal.getFila() + 1 || PosInicial.getFila() == PosFinal.getFila() - 1) && (PosInicial.getColumna() == PosFinal.getColumna() + 2 || PosInicial.getColumna() == PosFinal.getColumna() - 2)) {
            saltocaballo = true;
        }
        return saltocaballo;
    }

    /**
     * Método que te dice si el movimiento es el de un caballo saltando en
     * vertical
     *
     * @return Booleano en función de si lo es o no
     */
    public boolean saltoVertical() {
        boolean saltocaballo = false;
        if ((PosInicial.getColumna() == PosFinal.getColumna() + 1 || PosInicial.getColumna() == PosFinal.getColumna() - 1) && (PosInicial.getFila() == PosFinal.getFila() + 2 || PosInicial.getFila() == PosFinal.getFila() - 2)) {
            saltocaballo = true;
        }
        return saltocaballo;
    }
    /**
     * Método que te dice si el movimiento es el propio de un rey 
     * @return Booleano en función de si lo es o no
     */
    public boolean movimientoRey() {
        boolean movimientoRey = false;
        if (PosFinal.getColumna() == PosInicial.getColumna() + 1 || PosFinal.getFila() == PosInicial.getFila() + 1 || PosFinal.getColumna() == PosInicial.getColumna() - 1 || PosFinal.getFila() == PosInicial.getFila() - 1 || (PosFinal.getColumna() == PosInicial.getColumna() + 1 && PosFinal.getFila() == PosInicial.getFila() + 1) || (PosFinal.getColumna() == PosInicial.getColumna() - 1 && PosFinal.getFila() == PosInicial.getFila() - 1) || (PosFinal.getColumna() == PosInicial.getColumna() + 1 && PosFinal.getFila() == PosInicial.getFila() - 1) || (PosFinal.getColumna() == PosInicial.getColumna() - 1 && PosFinal.getFila() == PosInicial.getFila() + 1)) {
            movimientoRey = true;
        }
        return movimientoRey;
    }
    /**
     * Método que te dice si el movimiento es el propio de un peon negro 
     * @return Booleano en función de si lo es o no
     */
    public boolean movimientoPeonNegro() {
        boolean movimientoPeon = false;
        if (PosFinal.getFila() == 3 && PosInicial.getFila() == 1 && PosFinal.getColumna() == PosInicial.getColumna()) {
            movimientoPeon = true;
        } else if (PosFinal.getFila() == PosInicial.getFila() + 1 && PosInicial.getColumna() == PosFinal.getColumna()) {
            movimientoPeon = true;
        }
        return movimientoPeon;
    }
    /**
     * Método que te dice si el movimiento es el propio de un peon blanco 
     * @return Booleano en función de si lo es o no
    */
    public boolean movimientoPeonBlanco() {
        boolean movimientoPeon = false;
        if (PosFinal.getFila() == 4 && PosInicial.getFila() == 6 && PosFinal.getColumna() == PosInicial.getColumna()) {
            movimientoPeon = true;
        } else if (PosFinal.getFila() == PosInicial.getFila() - 1 && PosInicial.getColumna() == PosFinal.getColumna()) {
            movimientoPeon = true;
        }
        return movimientoPeon;
    }
     /**
     * Método que te dice si el movimiento es el propio de un peon negro que come 
     * @return Booleano en función de si lo es o no
    */
    public boolean comerPeonNegro() {
        boolean comerPeonNegro = false;
        if ((PosFinal.getFila() == PosInicial.getFila() + 1 && PosFinal.getColumna() == PosInicial.getColumna() + 1) || (PosFinal.getFila() == PosInicial.getFila() + 1 && PosFinal.getColumna() == PosInicial.getColumna() - 1)) {
            comerPeonNegro = true;
        }
        return comerPeonNegro;
    }
     /**
     * Método que te dice si el movimiento es el propio de un peon blanco que come
     * @return Booleano en función de si lo es o no
    */
    public boolean comerPeonBlanco() {
        boolean comerPeonBlanco = false;
        if ((PosFinal.getFila() == PosInicial.getFila() - 1 && PosFinal.getColumna() == PosInicial.getColumna() + 1) || (PosFinal.getFila() == PosInicial.getFila() - 1 && PosFinal.getColumna() == PosInicial.getColumna() - 1)) {
            comerPeonBlanco = true;
        }
        return comerPeonBlanco;
    }
}
