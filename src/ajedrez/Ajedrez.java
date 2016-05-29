/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.io.*;
import java.util.ArrayList;
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
        Scanner lc = new Scanner(System.in);
        IA ia = new IA();
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
        int numjugada=0;
        int numVariantes = problema.getListaProblemas().get(tablero).size();
        for (int i = 0; i < numVariantes; i++) {
            boolean inicio=true;
            String jugada;
            int jugadasaRetroceder = 0;
            if (i == 0) {
                System.out.println("Introduzca una jugada para empezar a resolver el Problema");
                jugada = lc.nextLine();
            } else {
                System.out.println("Introduzca una jugada para comenzar a resolver la siguiente variante");
                jugada = lc.nextLine();
            }
            for (int j = numjugada; j > -1; j = j + 2) {
                if (!inicio) {
                    System.out.println("Introduzca otra jugada");
                    jugada = lc.nextLine();
                }
                try {
                    if (acertoJugada(j, jugada, tablero, problema)) {
                        try {
                            if (jugada.length() == 4) {
                                problema.moverProblema(problema.stringToMovimiento(jugada.toUpperCase()), tablero);
                                ventana.board.pintartablero(tablero);
                                ventana.actualizarpantalla();
                                System.out.println("Bien hecho.");
                                esperar(3);
                                if (ia.moverMáquina(problema, tablero, ventana, j, jugada) != null) {
                                    jugadasaRetroceder = jugadasaRetroceder + 2;
                                }
                            } else {
                                tablero.promociondelPeon(problema.stringToMovimiento(jugada.toUpperCase()), jugada.toUpperCase().charAt(4));
                                ventana.board.pintartablero(tablero);
                                ventana.actualizarpantalla();
                                System.out.println("Bien hecho.");
                                esperar(3);
                                if (ia.moverMáquina(problema, tablero, ventana, j, jugada) != null) {
                                    jugadasaRetroceder = jugadasaRetroceder + 2;
                                }
                            }
                        } catch (MovIncorrectoException ex) {
                        }
                    } else {
                        System.out.println("Esa no era la jugada.");
                        j = j - 2;
                        inicio=false;
                    }
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("Enhorabuena, ha resuelto una variante del problema.\n");
                    numjugada=j-jugadasaRetroceder+2;
                    j = -3;
                }
            }
            if (i == numVariantes - 1) {
                System.out.println("Felicidades ha resuelto el Problema al completo.\n");
                problema.getIDsResueltos().add(tablero.id);
            } else {
                ia.cambiarVariante(jugadasaRetroceder, problema, tablero, ventana);
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
                    switch (menuResolver()) {
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
