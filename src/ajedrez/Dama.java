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
public class Dama extends Pieza {

    public Dama(boolean color, int fila, int columna) {
        super(color, fila, columna);
    } 
    
    @Override
    public boolean movimientoValido(Movimiento mov) {
        boolean movimientoValido = false;
        if (mov.esHorizontal() == true || mov.esVertical() == true || mov.esDiagonal() == true) {
            movimientoValido = true;
        } else {
            System.out.println("La Dama no puede mover así");
        }
        return movimientoValido;
    }

    @Override
    public void pintarPieza() {
        if (color == true) {
            System.out.print("[Db]");
        } else {
            System.out.print("[Dn]");
        }
    }

    @Override
    public void moverPieza(Movimiento mov, Tablero tablero) {
        if (movimientoValido(mov) == true) {
            tablero.Mover(mov);
        }
    }       
}
