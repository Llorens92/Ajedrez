/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author dam1
 */
public class Ajedrez {

    public static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (Exception e) {
            System.out.println("No se pudo esperar");
        }
    }

    public static int menuPrincipal() {
        int opcion = 0;
        Scanner lc = new Scanner(System.in);
        while (opcion != 1 && opcion != 2 && opcion != 3 && opcion != 4) {
            System.out.println("Escoja entre estas opciones:");
            System.out.println("1- Jugar Partida");
            System.out.println("2- Resolver Problema");
            System.out.println("3- Administrar sistema");
            System.out.println("4- Salir");
            try {
                opcion = lc.nextInt();
            } catch (InputMismatchException e) {
                lc.next();
                System.out.println("Por favor, introduzca un número entre 1 y 4");
                opcion = 0;
            }
        }
        return opcion;
    }

    public static int menuJugar() {
        int opcion = 0;
        Scanner lc = new Scanner(System.in);
        while (opcion != 1 && opcion != 2) {
            System.out.println("Escoja entre estas opciones:");
            System.out.println("1- Nueva Partida");
            System.out.println("2- Cargar Partida");
            try {
                opcion = lc.nextInt();
            } catch (InputMismatchException e) {
                lc.next();
                System.out.println("Por favor, introduzca un número entre 1 y 2");
                opcion = 0;
            }
        }
        return opcion;
    }

    public static int menuResolver() {
        int opcion = 0;
        Scanner lc = new Scanner(System.in);
        while (opcion != 1 && opcion != 2) {
            System.out.println("Escoja entre estas opciones:");
            System.out.println("1- Resolver Nuevo");
            System.out.println("2- Ver Resueltos");
            try {
                opcion = lc.nextInt();
            } catch (InputMismatchException e) {
                lc.next();
                System.out.println("Por favor, introduzca un número entre 1 y 2");
                opcion = 0;
            }
        }
        return opcion;
    }

    public static int menuAdministrar() {
        int opcion = 0;
        Scanner lc = new Scanner(System.in);
        while (opcion != 1 && opcion != 2 && opcion != 3) {
            System.out.println("Escoja entre estas opciones:");
            System.out.println("1- Crear Problema");
            System.out.println("2- Borrar Problema");
            System.out.println("3- Borrar Partida");
            try {
                opcion = lc.nextInt();
            } catch (InputMismatchException e) {
                lc.next();
                System.out.println("Por favor, introduzca un número entre 1 y 3");
                opcion = 0;
            }
        }
        return opcion;
    }

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

    public static void main(String[] args) {
        Scanner lc = new Scanner(System.in);
        boolean salir = false;
        String contraseña;
        Juego partida;
        Problema problema;
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
            switch (menuPrincipal()) {
                case 1:
                    switch (menuJugar()) {
                        case 1:
                            Tablero tablero = new Tablero(partida.fijandoIDPartida());
                            partida.inicializar(tablero.getTablero());
                            Ventana ventana = new Ventana(tablero);
                            ventana.setBounds(0, 0, 505, 530);
                            ventana.setVisible(true);
                            ventana.board.pintartablero(tablero);
                            partida.jugarPartida(tablero, ventana);
                            break;
                        default:
                            if (!partida.getListapartidas().isEmpty()) {
                                partida.pintarPartidas();
                            } else {
                                System.out.println("No hay partidas guardadas.");
                            }
                    }
                    break;
                case 2:
                    switch (menuResolver()) {
                        case 1:
                            if (problema.problemaAleatorio() != null) {
                                IA.resolverProblema(problema);
                            } else {
                                System.out.println("No hay problemas por resolver.");
                            }
                            break;

                        default:
                            if (!problema.getIDsResueltos().isEmpty()) {
                                problema.pintarProblemasResueltos();
                            } else {
                                System.out.println("No hay problemas resueltos.");
                            }
                    }
                    break;
                case 3:
                    do {
                        System.out.println("Introduzca la contraseña o salir para volver al menu: ");
                        contraseña = lc.nextLine();
                    } while (!contraseña.equals("1234qwer") && !contraseña.equals("salir"));
                    if (!contraseña.equalsIgnoreCase("salir")) {
                        switch (menuAdministrar()) {
                            case 1:
                                Tablero tablero = new Tablero(problema.fijandoIDProblema());
                                Ventana ventana = new Ventana(tablero);
                                ventana.setBounds(0, 0, 505, 530);
                                ventana.setVisible(true);
                                ventana.board.pintartablero(tablero);
                                problema.introduciendoDatos(tablero, ventana);
                                problema.quienComienzaJugando(tablero);
                                problema.introduciendoJugadas(tablero, ventana);
                                problema.introduciendoDescripción(tablero);
                                break;
                            case 2:
                                if (!problema.getListaProblemas().isEmpty()) {
                                    problema.borrarProblemas();
                                } else {
                                    System.out.println("No hay problemas que borrar.");
                                }
                                break;
                            default:
                                if (!partida.getListapartidas().isEmpty()) {
                                    partida.borrarPartidas();
                                } else {
                                    System.out.println("No hay partidas que borrar.");
                                }
                        }
                    }
                    break;
                default:
                    guardarysalir(partida, problema);
                    salir = true;
            }
        }
    }
}
