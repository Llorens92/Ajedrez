/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.*;
import java.util.ArrayList;
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

    public static boolean acertoJugada(int numJugada, String jugada, Tablero tablero, Problema problema) throws IndexOutOfBoundsException {
        boolean acerto = false;
        for (int i = 0; i < problema.getListaProblemas().get(tablero).size() && !acerto; i++) {
            if (jugada.equalsIgnoreCase(problema.getListaProblemas().get(tablero).get(i).get(numJugada))) {
                acerto = true;
            }
        }
        return acerto;
    }

    public static void resolverProblema(Problema problema) {
        Scanner lc = new Scanner (System.in);
        Tablero tablero = problemaAleatorio(problema);
        Ventana ventana = new Ventana(tablero);
        ventana.setBounds(0, 0, 505, 530);
        ventana.setVisible(true);
        ventana.board.pintartablero(tablero);
        ventana.actualizarpantalla();
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
            try {
                if (acertoJugada(i, jugada, tablero, problema)) {
                    try {
                        if (jugada.length() == 4) {
                            problema.moverProblema(problema.stringToMovimiento(jugada.toUpperCase()), tablero);
                        } else {
                            tablero.promociondelPeon(problema.stringToMovimiento(jugada.toUpperCase()), jugada.toUpperCase().charAt(4));
                        }
                    } catch (MovIncorrectoException ex) {
                    }
                    System.out.println("Bien hecho, pulse intro solamente si cree haber llegado al final del problema.");
                    ventana.board.pintartablero(tablero);
                    ventana.actualizarpantalla();
                } else {
                    System.out.println("Esa no era la jugada.");
                    i--;
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Enhorabuena, ha resuelto el problema.\n");
                problema.getIDsResueltos().add(tablero.id);
                i = -2;
            }
        }
    }

    public static void main(String[] args) {
        boolean salir = false;
        Juego partida;
        Problema problema;
        Scanner lc = new Scanner(System.in);
        System.out.println("Bienvenido al Ajedrez de Llorenç Pagès: ");
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
                                resolverProblema(problema);
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
