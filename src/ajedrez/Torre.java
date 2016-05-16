/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

public class Torre extends Pieza {

    public Torre(boolean color, int fila, int columna) {
        super(color, fila, columna);
    }       
    
    @Override
    public boolean movimientoValido(Movimiento mov) {
        boolean movimientoValido = false;
        if (mov.esHorizontal() == true || mov.esVertical() == true) {
            movimientoValido = true;
        }
        return movimientoValido;
    }

    @Override
    public void pintarPieza() {
        if (color == true) {
            System.out.print("[Tb]");
        } else {
            System.out.print("[Tn]");
        }
    }

    @Override
    public void moverPieza(Movimiento mov, Tablero tablero) throws MovIncorrectoException{
        if (movimientoValido(mov) == true) {
            tablero.Mover(mov);
        }  else {
            throw new MovIncorrectoException("La torre no puede mover así");
        }        
    }
    public String toString() {
        return super.toString();
    }
}
