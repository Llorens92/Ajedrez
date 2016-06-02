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
public class Peon extends Pieza {

    public Peon(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public boolean movimientoValido(Movimiento mov) {
        boolean movimientoValido = false;
        if (color == false && mov.movimientoPeonNegro() == true) {
            movimientoValido = true;
        } else if (color == true && mov.movimientoPeonBlanco() == true) {
            movimientoValido = true;
        }
        return movimientoValido;
    }

    /**
     * Método que comprueba que el movimiento coincida con el de un peon que
     * come.
     *
     * @param mov Movimiento a validar
     * @return Booleano en función de si se pudo comer o no.
     */
    public boolean comerValido(Movimiento mov) {
        boolean comerValido = false;
        if (color == false && mov.comerPeonNegro() == true) {
            comerValido = true;
        } else if (color == true && mov.comerPeonBlanco() == true) {
            comerValido = true;
        }
        return comerValido;
    }

    @Override
    public void pintarPieza() {
        if (color == true) {
            System.out.print("[pb]");
        } else {
            System.out.print("[pn]");
        }
    }

    @Override
    public void moverPieza(Movimiento mov, Tablero tablero) throws MovIncorrectoException {
        if (movimientoValido(mov) == true && tablero.hayPieza(mov.getPosFinal()) == false) {
            tablero.Mover(mov);
        } else if (comerValido(mov) == true && tablero.hayPieza(mov.getPosFinal()) == true) {
            tablero.Mover(mov);
        } else if (comerValido(mov) == true && tablero.hayPieza(mov.getPosFinal()) == false) {            
            tablero.Mover(mov);
            if (tablero.comerAlPaso(mov) == true) {
                if (tablero.DevuelvePieza(mov.getPosFinal()).getColor() == false) {
                    tablero.quitaPieza(mov.getPosFinal().getFila() - 1, mov.getPosFinal().getColumna());
                } else {
                    tablero.quitaPieza(mov.getPosFinal().getFila() + 1, mov.getPosFinal().getColumna());
                }
            } else {
                tablero.anularUltimoMovimiento();
                throw new MovIncorrectoException("El peon no puede mover así");
            }
        } else {
            throw new MovIncorrectoException("El peon no puede mover así");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
