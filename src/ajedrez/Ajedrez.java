/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.util.Scanner;

/**
 *
 * @author dam1
 */
public class Ajedrez {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner lc = new Scanner(System.in);
        System.out.println("Bienvenido al Ajedrez de Llorenç Pagès: ");
        System.out.println("Para mover introduzca primero su posicion inicial y luego la final, en mayúsculas y sin separación. \nPor ejemplo: E2E4");
        switch (Menus.menuPrincipal()) {
            case 1:
                switch (Menus.menuJugar()) {
                    case 1:
                        Juego partida = new Juego();
                        Tablero tablero = new Tablero();
                        partida.inicializar(tablero.getTablero());
                        Ventana ventana = new Ventana(tablero);
                        ventana.setBounds(0, 0, 505, 530);
                        ventana.setVisible(true);
                        ventana.board.pintartablero(tablero);
                        for (int j = 1; j > 0; j++) {
                            System.out.println("Introduzca una jugada valida (A-H,1-8,A-H,1-8)");
                            String jugada = lc.next();
                            partida.jugada(jugada, tablero);
                            ventana.board.pintartablero(tablero);
                            ventana.actualizarpantalla();
                        }
                        break;
                    default:
                }
                break;
            case 2:
                switch (Menus.menuResolver()) {
                    case 1:
                        break;
                    default:
                }
                break;
            default:
                switch (Menus.menuAdministrar()) {
                    case 1:
                        break;
                    case 2:
                        break;
                    default:

                }
        }

    }
}
