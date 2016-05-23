/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

/**
 *
 * @author Llorenç
 */
public class Jugada {
    private int ID;
    private String jugada;

    public Jugada(int ID, String jugada) {
        this.ID = ID;
        this.jugada = jugada;
    }

    public int getID() {
        return ID;
    }

    public String getJugada() {
        return jugada;
    }
    
    
}
