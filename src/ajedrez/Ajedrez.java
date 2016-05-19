/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        } catch (IOException ex) {
        }
    }

    public static Juego cargarPartidas() {
        Juego partida = null;
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File("partidas.dat")));
            partida = (Juego) reader.readObject();
            reader.close();
        } catch (ClassNotFoundException | IOException ex) {
        }
        return partida;
    }

    public static Problema cargarProblemas() {
        Problema problema = null;
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File("problemas.dat")));
            problema = (Problema) reader.readObject();
            reader.close();
        } catch (ClassNotFoundException | IOException ex) {
        }
        return problema;
    }

    public static Tablero problemaAleatorio(Problema problema) {
        boolean salir = false;
        Tablero tablero = null;
        ArrayList<Tablero> listaTableros = new ArrayList<>(problema.getListaProblemas().keySet());
        for (int i = 0; i < listaTableros.size() && !salir; i++) {
            if (problema.getIDsResueltos().size() > 0) {
                for (int j = 0; j < problema.getIDsResueltos().size() && !salir; j++) {
                    if (!listaTableros.get(i).getId().equalsIgnoreCase(problema.getIDsResueltos().get(j))) {
                        tablero = listaTableros.get(i);
                        salir = true;
                    }
                }
            } else {
                tablero = listaTableros.get(i);
                salir = true;
            }
        }
        return tablero;
    }

    public static boolean acertoJugada(int numJugada, String jugada, Tablero tablero, Problema problema) {
        boolean acerto = false;
        for (int i = 0; i < problema.getListaProblemas().get(tablero).size() && !acerto; i++) {
            if (jugada.equalsIgnoreCase(problema.getListaProblemas().get(tablero).get(i).get(numJugada))) {
                acerto = true;
            }
        }
        return acerto;
    }

    public static void moverProblema(Ventana ventana, Problema problema, Tablero tablero, String jugada) {
        try {
            problema.moverProblema(problema.stringToMovimiento(jugada), tablero);
        } catch (MovIncorrectoException ex) {
            System.out.println(ex.getMessage());
        }
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
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
            System.out.println("CARGADO");
        } else {
            partida = new Juego();
            System.out.println("nuevo");
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
                                System.out.println("Si desea anular el último movimiento escriba anular.");
                                String jugada = lc.nextLine();
                                if (jugada.equalsIgnoreCase("anular")) {
                                    tablero.anularUltimoMovimiento();
                                    ventana.board.pintartablero(tablero);
                                    ventana.actualizarpantalla();
                                } else if (!partida.finPartida(jugada, tablero)) {
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
                            if (problemaAleatorio(problema) != null) {
                                Tablero tablero = problemaAleatorio(problema);
                                Ventana ventana = new Ventana(tablero);
                                ventana.setBounds(0, 0, 505, 530);
                                ventana.setVisible(true);
                                ventana.board.pintartablero(tablero);
                                if (tablero.getTurno()) {
                                    System.out.println("Mueven las blancas");
                                } else {
                                    System.out.println("Mueven las negras");
                                }
                                for (int i = 0; i > -1; i++) {
                                    String jugada;
                                    if (i == 0) {
                                        System.out.println("Introduzca una jugada para empezar a resolver el Problema");
                                        jugada = lc.nextLine();
                                    } else {
                                        System.out.println("Introduzca otra jugada");
                                        jugada = lc.nextLine();
                                    }
                                    if (acertoJugada(i, jugada, tablero, problema)) {
                                        System.out.println("Bien hecho");
                                    } else {
                                        System.out.println("Esa no era la jugada.");
                                        i--;
                                    }
                                }
                            } else {
                                System.out.println("No hay problemas por resolver.");
                            }
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
                            problema.introduciendoDatos(tablero, ventana);
                            problema.quienComienzaJugando(tablero);
                            problema.introduciendoJugadas(tablero, ventana);
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
