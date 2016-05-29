/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import static ajedrez.Ajedrez.esperar;
import java.util.ArrayList;

/**
 *
 * @author Llorenç
 */
public class IA {

    protected ArrayList<ArrayList<String>> variantes = new ArrayList<>();

    public String moverMáquina(Problema problema, Tablero tablero, Ventana ventana, int numJugada, String jugada) throws MovIncorrectoException {
        ArrayList<String> siguienteMovimiento = new ArrayList<>();
        for (int i = 0; i < problema.getListaProblemas().get(tablero).size(); i++) {
            if (jugada.equalsIgnoreCase(problema.getListaProblemas().get(tablero).get(i).get(numJugada))) {
                siguienteMovimiento.add(problema.getListaProblemas().get(tablero).get(i).get(numJugada + 1));
            }
        }
        String movimiento = siguienteMovimiento.remove(0);
        if (siguienteMovimiento.get(0) != null) {
            variantes.add(siguienteMovimiento);
        }
        if (movimiento.length() == 4) {
            problema.moverProblema(problema.stringToMovimiento(movimiento.toUpperCase()), tablero);
        } else {
            tablero.promociondelPeon(problema.stringToMovimiento(movimiento.toUpperCase()), movimiento.toUpperCase().charAt(4));
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        return siguienteMovimiento.get(0);
    }
    
    public void moverCambioVariante(Problema problema, Tablero tablero, Ventana ventana)throws MovIncorrectoException{
        if (variantes.get(0).get(0).length() == 4) {
            problema.moverProblema(problema.stringToMovimiento(variantes.get(0).get(0).toUpperCase()), tablero);
        } else {
            tablero.promociondelPeon(problema.stringToMovimiento(variantes.get(0).get(0).toUpperCase()), variantes.get(0).get(0).toUpperCase().charAt(4));
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        variantes.get(0).remove(0);
    }
    
    public void cambiarVariante(int jugadasaRetroceder, Problema problema, Tablero tablero, Ventana ventana) {
        for (int i =0;i<jugadasaRetroceder;i++){
            tablero.anularUltimoMovimiento();
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
        esperar(3);
        try {
            moverCambioVariante(problema, tablero, ventana);
        } catch (MovIncorrectoException ex) {
            System.out.println("La IA no pudo comenzar la siguiente variante.");
        }
    }
}
