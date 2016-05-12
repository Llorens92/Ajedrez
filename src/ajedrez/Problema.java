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
public class Problema {

    public static void crearProblema(Tablero tablero) {

    }

    public static Pieza introducirPieza(String nomPieza, String color, int fila, int columna) {
        boolean colour = false;
        Pieza pieza;
        if (color.equalsIgnoreCase("blanco") || color.equalsIgnoreCase("blanca")) {
            colour = true;
        }
        switch (nomPieza) {
            case "ALFIL":
                pieza = new Alfil(colour, fila, columna);
                break;

            case "CABALLO":
                pieza = new Caballo(colour, fila, columna);
                break;

            case "DAMA":
                pieza = new Dama(colour, fila, columna);
                break;

            case "PEON":
                pieza = new Peon(colour, fila, columna);
                break;

            case "REY":
                pieza = new Rey(colour, fila, columna);
                break;

            case "TORRE":
                pieza = new Torre(colour, fila, columna);
                break;
            default:
                pieza = null;
        }
        return pieza;
    }

    public static void introduciendoDatos() {
        Scanner sc = new Scanner(System.in);
        String nomPieza = " ";
        String color;
        String posicion;
        while (!nomPieza.equalsIgnoreCase("fin")) {
            do {
                System.out.println("Introduzca el nombre de la pieza que quiere añadir al tablero o fin para terminar.\nPor ejemplo: Caballo");
                nomPieza = sc.nextLine();
            } while (!nomPieza.equalsIgnoreCase("caballo") && !nomPieza.equalsIgnoreCase("alfil") && !nomPieza.equalsIgnoreCase("peon") && !nomPieza.equalsIgnoreCase("dama") && !nomPieza.equalsIgnoreCase("rey") && !nomPieza.equalsIgnoreCase("torre") && !nomPieza.equalsIgnoreCase("fin"));
            if (!nomPieza.equalsIgnoreCase("fin")) {
                do {
                    System.out.println("Introduzca el color de la pieza que quiere añadir al tablero.\nPor ejemplo: Blanco");
                    color = sc.nextLine();
                } while (!color.equalsIgnoreCase("blanco") && !color.equalsIgnoreCase("blanca") && !color.equalsIgnoreCase("negro") && !color.equalsIgnoreCase("negra"));
                do {
                    System.out.println("Introduzca la casilla de la pieza que quiere añadir al tablero.\nPor ejemplo: C5");
                    posicion = sc.nextLine();
                } while (posicion.length() != 2);
            }
        }
        introducirPieza(nomPieza,, 6, 8);
    }
}
