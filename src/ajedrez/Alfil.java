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
public class Alfil extends Pieza {
   
    public Alfil(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }

    @Override
    public boolean movimientoValido(Movimiento mov) {
        boolean movimientoValido = false;
        if (mov.esDiagonal() == true) {
            movimientoValido = true;
        } else {
            System.out.println("El alfil no puede mover así");
        }
        return movimientoValido;
    }

    @Override
    public void pintarPieza() {
        if (color == true) {
            System.out.print("[Ab]");
        } else {
            System.out.print("[An]");
        }
    }

    @Override
    public void moverPieza(Movimiento mov, Tablero tablero) {
        if (movimientoValido(mov) == true) {
            tablero.Mover(mov);
        }
    }
}
