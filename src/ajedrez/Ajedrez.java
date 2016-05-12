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
        boolean salir = false;
        Scanner lc = new Scanner(System.in);
        System.out.println("Bienvenido al Ajedrez de Llorenç Pagès: ");
        System.out.println("Para mover introduzca primero su posicion inicial y luego la final, sin separación. \nPor ejemplo: E2E4");
        while (!salir) {
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
                                System.out.println("Para guardar y salir escriba Guardar. Si desea rendirse escriba Fin durante su turno.");
                                String jugada = lc.nextLine();
                                if (!partida.finPartida(jugada, tablero)) {
                                    partida.jugada(jugada.toUpperCase(), tablero);
                                    ventana.board.pintartablero(tablero);
                                    ventana.actualizarpantalla();
                                } else {
                                    j = -1;
                                    System.out.println("Espero que hayan disfrutado de la partida.");
                                    System.out.println("Hasta Pronto");
                                }
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
                case 3:
                    switch (Menus.menuAdministrar()) {
                        case 1:
                            Tablero tablero = new Tablero();
                            Ventana ventana = new Ventana(tablero);
                            ventana.setBounds(0, 0, 505, 530);
                            ventana.setVisible(true);
                            ventana.board.pintartablero(tablero);
                            Problema.introduciendoDatos(tablero, ventana);
                            ;
                            break;
                        case 2:
                            break;
                        default:
                    }
                    break;
                default:
                    salir = true;
            }
        }
    }
}
