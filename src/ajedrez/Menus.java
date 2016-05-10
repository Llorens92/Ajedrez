/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author dam1
 */
public class Menus {

    public static int menuPrincipal() {
        int opcion = 0;
        Scanner lc = new Scanner(System.in);
        while (opcion != 1 && opcion != 2 && opcion != 3) {
            System.out.println("Escoja entre estas opciones:");
            System.out.println("1- Jugar Partida");
            System.out.println("2- Resolver Problema");
            System.out.println("3- Administrar sistema");
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
}
