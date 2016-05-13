/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dam1
 */
public class Ajedrez {

    public static void guardarysalir(Juego partida, Problema problema) {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(new File("partidas.dat")));
            writer.writeObject(partida);
            ObjectOutputStream writer2 = new ObjectOutputStream(new FileOutputStream(new File("problemas.dat")));
            writer2.writeObject(problema);
            writer.close();
            writer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ajedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ajedrez.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Juego cargarPartidas() {
        Juego partida = null;
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File("partidas.dat")));
            if (reader.available() != 0) {
                partida = (Juego) reader.readObject();
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Ajedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ajedrez.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partida;
    }

    public static Problema cargarProblemas() {
        Problema problema = null;
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File("problemas.dat")));
            if (reader.available() != 0) {
                problema = (Problema) reader.readObject();
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Ajedrez.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ajedrez.class.getName()).log(Level.SEVERE, null, ex);
        }
        return problema;
    }

    public static void main(String[] args) {
        boolean salir = false;
        Juego partida;
        Problema problema;
        Scanner lc = new Scanner(System.in);
        System.out.println("Bienvenido al Ajedrez de Llorenç Pagès: ");
        System.out.println("Para mover introduzca primero su posicion inicial y luego la final, sin separación. \nPor ejemplo: E2E4");
        if (cargarPartidas() != null) {
            partida = cargarPartidas();
        } else {
            partida = new Juego();
        }
        if (cargarProblemas() != null) {
            problema = cargarProblemas();
        } else {
            problema = new Problema();
        }
        while (!salir) {
            switch (Menus.menuPrincipal()) {
                case 1:
                    switch (Menus.menuJugar()) {
                        case 1:
                            Tablero tablero = new Tablero(partida.fijandoIDPartida());
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
                            Tablero tablero = new Tablero(problema.fijandoIDProblema());
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
                    guardarysalir(partida, problema);
                    salir = true;
            }
        }
    }
}
