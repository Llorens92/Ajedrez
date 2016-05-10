/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.ImageIcon;

/**
 *  Clase que implementa la ventana donde se mostrara el tablero y las piezas
 * @author Propietario
 */
public class Ventana extends JFrame {
    /**
     * JPanel que contiene el tablero y las piezas
     */
    protected Board board;
    /**
     * Constructor que inicializa el board, le cambia el layout a un grid de 8x8 y lo añade a la ventana
     * @param tablero 
     */
    public Ventana(Tablero tablero) {
        ImageIcon icon = new ImageIcon(getClass().getResource("Imagenes/tablero.jpg"));
        board = new Board(icon.getImage(), tablero);
        board.setBounds(0, 0, 500, 500);
        board.setLayout(new GridLayout(8, 8));
        this.getContentPane().add(board);
    }
    /**
     * Método que refresca automáticamente la ventana
     */
    public void actualizarpantalla() {
        SwingUtilities.updateComponentTreeUI(this);
        this.validate();
    }
}
