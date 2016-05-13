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
public class Posicion implements Serializable{
    /**
     * Fila de la posición
     */
    private int fila;
    /**
     * Columna de la posición
     */
    private int columna;
    /**
     * Contructor que inicializa los atributos en función de los parametros de entrada.
     * @param fila Fila inicializada
     * @param columna Columna inicializada
     */
    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    /**
     * Método que devuelve la fila de la posición
     * @return Fila de la posición
     */
    public int getFila() {
        return fila;
    }
    /**
     * Método que devuelve la columna de la posición
     * @return Columna de la posición
     */
    public int getColumna() {
        return columna;
    }
    /**
     * Método que modifica la fila de la posición
     * @param fila Nueva fila
     */
    public void setFila(int fila) {
        this.fila = fila;
    }
    /**
     * Método que modifica la columna de la posición
     * @param columna Nueva columna
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

}
