/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import static ajedrez.Ajedrez.esperar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author dam1
 */
public class Problema implements Serializable {

    /**
     * Hashmap con los tableros de cada problema y las correspondientes
     * soluciones.
     */
    protected HashMap<Tablero, ArrayList<ArrayList<String>>> listaProblemas = new HashMap<>();
    /**
     * ArrayList con los IDs de los problemas resueltos.
     */
    protected ArrayList<String> IDsResueltos = new ArrayList<>();

    /**
     * Método que devuelve el HashMap con los problemas.
     *
     * @return HashMap de tableros y soluciones
     */
    public HashMap<Tablero, ArrayList<ArrayList<String>>> getListaProblemas() {
        return listaProblemas;
    }

    /**
     * Método que devuelve el ArrayList con los IDs de los problemas resueltos.
     *
     * @return ArrayList con los IDs de los problemas resueltos.
     */
    public ArrayList<String> getIDsResueltos() {
        return IDsResueltos;
    }

    /**
     * Este método permite inicializar el ID de cada problema.
     *
     * @return String con el ID del problema.
     */
    public String fijandoIDProblema() {
        boolean distinto = false;
        String ID = "";
        do {
            ID = "problema".concat(String.valueOf((int) (Math.random() * 10))).concat(String.valueOf((int) (Math.random() * 10))).concat(String.valueOf((int) (Math.random() * 10)));
            if (listaProblemas.isEmpty()) {
                distinto = true;
            } else {
                Set<Tablero> tableros = listaProblemas.keySet();
                Iterator<Tablero> it = tableros.iterator();
                while (it.hasNext() && !distinto) {
                    distinto = !it.next().getId().equalsIgnoreCase(ID);
                }
            }
        } while (!distinto);
        return ID;
    }

    /**
     * Este método encuentra el problema (si lo hay), que corresponde al ID
     * introducido como parámtero, devolviendo el tablero que corresponde.
     *
     * @param posibleID ID a comprobar.
     * @return Instancia de la clase Tablero corresponidente al ID introducido o
     * null si no hay ninguna coincidencia
     */
    public Tablero encontrarProblema(String posibleID) {
        boolean salir = false;
        Tablero tablero = null;
        Iterator<Tablero> it = listaProblemas.keySet().iterator();
        while (it.hasNext() && !salir) {
            Tablero t = it.next();
            if (posibleID.equalsIgnoreCase(t.getId())) {
                tablero = t;
                salir = true;
            }
        }
        return tablero;
    }

    /**
     * Este método pinta la lista de problemas resueltos permitiendo luego
     * recuperar y volver a visualizar uno de ellos, pues acaba llamando al
     * método verProblemaResuelto.
     */
    public void pintarProblemasResueltos() {
        Scanner lc = new Scanner(System.in);
        String id;
        Iterator<Tablero> it = listaProblemas.keySet().iterator();
        while (it.hasNext()) {
            Tablero t = it.next();
            for (String s : IDsResueltos) {
                if (s.equalsIgnoreCase(t.getId())) {
                    System.out.println(t.getId().concat("-->").concat(t.toString()));
                }
            }
        }
        do {
            System.out.println("Introduzca el ID del problema que quiere ver resuelto.");
            id = lc.nextLine();
        } while (encontrarProblema(id) == null);
        Tablero tablero = encontrarProblema(id);
        Ventana ventana = new Ventana(tablero);
        ventana.setBounds(0, 0, 505, 530);
        ventana.setVisible(true);
        ventana.board.pintartablero(tablero);
        esperar(3);
        try {
            verProblemaResuelto(ventana, tablero, listaProblemas.get(tablero));
        } catch (MovIncorrectoException ex) {
            System.out.println("No se pudo visualizar el problema");
        }
    }

    /**
     * Este método muestra una lista con los IDs y las descripciones de cada
     * problema y luego borra el problema cuyo ID se solicita en la ejecución
     * del mismo.
     */
    public void borrarProblemas() {
        Scanner lc = new Scanner(System.in);
        String id;
        Iterator<Tablero> it = listaProblemas.keySet().iterator();
        while (it.hasNext()) {
            Tablero t = it.next();
            System.out.println(t.getId().concat("-->").concat(t.toString()));
        }
        do {
            System.out.println("Introduzca el ID del problema que quiere borrar.");
            id = lc.nextLine();
        } while (encontrarProblema(id) == null);
        Tablero t = encontrarProblema(id);
        listaProblemas.remove(t);
        System.out.println("Problema Borrado");
    }

    /**
     * Este método devuelve un Tablero de un problema.
     *
     * @return Tablero de un problema.
     */
    public Tablero problemaAleatorio() {
        boolean salir = false;
        Tablero tablero = null;
        ArrayList<Tablero> listaTableros = new ArrayList<>(listaProblemas.keySet());
        for (int i = 0; i < listaTableros.size() && !salir; i++) {
            if (IDsResueltos.size() > 0) {
                for (int j = 0; j < IDsResueltos.size() && !salir; j++) {
                    if (!listaTableros.get(i).getId().equalsIgnoreCase(IDsResueltos.get(j))) {
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

    /**
     * Este método permite ver la solución de un determinado problema viendo
     * cada movimiento con intervalos de 3 segundos.
     *
     * @param ventana Ventana en que se visualiza el problema.
     * @param tablero Tablero en que se producen los movimientos.
     * @param solucion Conjunto de movimientos que forman el problema.
     * @throws MovIncorrectoException Excepción que notifica si algo ha ido mal
     * al realizar un movimiento y porque.
     */
    public void verProblemaResuelto(Ventana ventana, Tablero tablero, ArrayList<ArrayList<String>> solucion) throws MovIncorrectoException {
        for (int i = 0; i < solucion.size(); i++) {
            for (int j = 0; j < solucion.get(i).size(); j++) {
                moverProblema(stringToMovimiento(solucion.get(i).get(j).toUpperCase()), tablero);
                ventana.board.pintartablero(tablero);
                ventana.actualizarpantalla();
                esperar(3);
            }
            for (int j = 0; j < solucion.get(i).size(); j++) {
                tablero.anularUltimoMovimiento();
            }
        }
    }

    /**
     * Este método permite introducir una pieza en el tablero.
     *
     * @param nomPieza Nombre de la pieza.
     * @param color Color de la pieza.
     * @param fila Fila donde irá colocada la pieza.
     * @param columna Columna donde irá colocada la pieza.
     * @return Pieza a colocar.
     */
    public Pieza introducirPieza(String nomPieza, String color, int fila, int columna) {
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

    /**
     * Este método interactua con el Administrador solicitandole los datos para
     * poder introducir las piezas en el tablero.
     *
     * @param tablero Tablero donde irán las piezas
     * @param ventana Ventana donde se visualizará el tablero.
     */
    public void introduciendoDatos(Tablero tablero, Ventana ventana) {
        Scanner sc = new Scanner(System.in);
        String nomPieza = " ";
        String color = " ";
        String posicion = " ";
        boolean salir;
        while (!nomPieza.equalsIgnoreCase("fin")) {
            do {
                System.out.println("Introduzca el nombre de la pieza que quiere añadir al tablero, quitar para vaciar una casilla o fin para terminar.\nPor ejemplo: Caballo");
                nomPieza = sc.nextLine();
            } while (!nomPieza.equalsIgnoreCase("quitar") && !nomPieza.equalsIgnoreCase("caballo") && !nomPieza.equalsIgnoreCase("alfil") && !nomPieza.equalsIgnoreCase("peon") && !nomPieza.equalsIgnoreCase("dama") && !nomPieza.equalsIgnoreCase("rey") && !nomPieza.equalsIgnoreCase("torre") && !nomPieza.equalsIgnoreCase("fin"));
            if (!nomPieza.equalsIgnoreCase("fin")) {
                if (!nomPieza.equalsIgnoreCase("quitar")) {
                    do {
                        System.out.println("Introduzca el color de la pieza que quiere añadir al tablero.\nPor ejemplo: Blanco");
                        color = sc.nextLine();
                    } while (!color.equalsIgnoreCase("blanco") && !color.equalsIgnoreCase("blanca") && !color.equalsIgnoreCase("negro") && !color.equalsIgnoreCase("negra"));
                }
                do {
                    do {
                        System.out.println("Introduzca la casilla de la pieza que quiere añadir al tablero o la casilla que quiera vaciar.\nPor ejemplo: C5");
                        posicion = sc.nextLine();
                    } while (posicion.length() != 2);
                    salir = !(Juego.devolverColumna(posicion.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(posicion.toUpperCase().charAt(1)) == 8);
                } while (!salir);
                if (!nomPieza.equalsIgnoreCase("quitar")) {
                    tablero.colocaPieza(introducirPieza(nomPieza.toUpperCase(), color, Juego.devolverFila(posicion.toUpperCase().charAt(1)), Juego.devolverColumna(posicion.toUpperCase().charAt(0))));
                } else {
                    tablero.quitaPieza(Juego.devolverFila(posicion.toUpperCase().charAt(1)), Juego.devolverColumna(posicion.toUpperCase().charAt(0)));
                }
                ventana.board.pintartablero(tablero);
                Juego.pintar(tablero.getTablero());
                ventana.actualizarpantalla();
            }
        }
    }

    /**
     * Este método solicita al Administrador el color del bando que empezará
     * jugando en el problema.
     *
     * @param tablero Tablero donde se localizará el problema.
     */
    public void quienComienzaJugando(Tablero tablero) {
        Scanner sc = new Scanner(System.in);
        String color;
        do {
            System.out.println("Indique quien empieza en el problema, si blancas o negras");
            color = sc.nextLine();
        } while (!color.equalsIgnoreCase("negro") && !color.equalsIgnoreCase("blanco") && !color.equalsIgnoreCase("blancas") && !color.equalsIgnoreCase("negras"));
        if (color.equalsIgnoreCase("negro") || color.equalsIgnoreCase("negras")) {
            tablero.setTurno(false);
        }
    }

    /**
     * Este método solicita al Administrador la cantidad de variantes de los que
     * consta el problema y la devuelve.
     *
     * @return Entero devolviendo el valor introducido por el Administrador.
     */
    public int introduciendoNumVariantes() {
        Scanner sc = new Scanner(System.in);
        int numVariantes = 0;
        while (numVariantes < 1) {
            System.out.println("Introduzca el numero de variantes total que posee el problema");
            try {
                numVariantes = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Por favor introduzca un número mayor de 0");
            }
        }
        return numVariantes;
    }

    /**
     * Este método solicita al Administrador la cantidad de movimientos de los
     * que consta una determinada variante y los devuelve.
     *
     * @return Entero devolviendo el valor introducido por el Administrador.
     */
    public int introduciendoNumMovimientos() {
        Scanner sc = new Scanner(System.in);
        int numMovimientos = 0;
        while (numMovimientos < 1) {
            System.out.println("Introduzca el numero de movimientos que componen la variante");
            try {
                numMovimientos = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Por favor introduzca un número mayor de 0");
            }
        }
        return numMovimientos;
    }

    /**
     * Este método interactua con el Administrador solicitandole las jugadas que
     * compondrán el problema.
     *
     * @param tablero Tablero donde se van ejecutando esas jugadas.
     * @param ventana Ventana donde se visualizará el tablero.
     */
    public void introduciendoJugadas(Tablero tablero, Ventana ventana) {
        String movimiento = " ", anular = " ";
        boolean salir, repetirMov;
        Scanner sc = new Scanner(System.in);
        ArrayList< ArrayList<String>> listaSoluciones = new ArrayList<>();
        int numVariantes = introduciendoNumVariantes(), numMovs;
        for (int i = 0; i < numVariantes; i++) {
            ArrayList<String> aux = new ArrayList<>();
            numMovs = introduciendoNumMovimientos();
            for (int j = 0; j < numMovs; j++) {
                do {
                    try {
                        do {
                            do {
                                System.out.println("Introduzca el movimiento correspondiente añadiéndo la letra pertinente si es promoción del peón. Por ejemplo: E2E4 o D7D8D");
                                System.out.println("Si desea anular el movimiento anterior escriba anular");
                                movimiento = sc.nextLine();
                            } while (movimiento.length() != 4 && movimiento.length() != 5 && !movimiento.equalsIgnoreCase("anular"));
                            if (movimiento.length() == 4 && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(1)) == 8) && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(2)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(3)) == 8)) {
                                salir = true;
                            } else if (movimiento.length() == 5 && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(0)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(1)) == 8) && !(Juego.devolverColumna(movimiento.toUpperCase().charAt(2)) == 8) && !(Juego.devolverFila(movimiento.toUpperCase().charAt(3)) == 8) && !(Juego.devolverPromocion(movimiento.toUpperCase().charAt(4)) == 'n')) {
                                salir = true;
                            } else if (movimiento.equalsIgnoreCase("anular")) {
                                if (tablero.anularUltimoMovimiento()) {
                                    j--;
                                    aux.remove(aux.size() - 1);
                                }
                                ventana.board.pintartablero(tablero);
                                ventana.actualizarpantalla();
                                salir = false;
                            } else {
                                salir = false;
                            }
                        } while (!salir);
                        if (movimiento.length() == 4) {
                            moverProblema(stringToMovimiento(movimiento.toUpperCase()), tablero);
                            repetirMov = false;
                        } else {
                            tablero.promociondelPeon(stringToMovimiento(movimiento.toUpperCase()), movimiento.toUpperCase().charAt(4));
                            repetirMov = false;
                        }
                    } catch (MovIncorrectoException ex) {
                        repetirMov = true;
                        System.out.println(ex.getMessage());
                    }
                } while (repetirMov);
                aux.add(movimiento);
                ventana.board.pintartablero(tablero);
                Juego.pintar(tablero.getTablero());
                ventana.actualizarpantalla();
                if (j == numMovs - 1) {
                    do {
                        System.out.println("¿Desea anular el último movimiento de la variante, antes de pasar a otra?");
                        anular = sc.nextLine();
                    } while (!anular.equalsIgnoreCase("si") && !anular.equalsIgnoreCase("no") && !anular.equalsIgnoreCase("anular"));
                    if (anular.equalsIgnoreCase("si") || anular.equalsIgnoreCase("anular")) {
                        aux.remove(aux.size() - 1);
                        tablero.anularUltimoMovimiento();
                        j--;
                        ventana.board.pintartablero(tablero);
                        ventana.actualizarpantalla();
                    } else {
                        for (int k = 0; k < numMovs; k++) {
                            tablero.anularUltimoMovimiento();
                        }
                        ventana.board.pintartablero(tablero);
                        ventana.actualizarpantalla();
                        listaSoluciones.add(aux);
                    }
                }
            }
        }
        listaProblemas.put(tablero, listaSoluciones);
    }

    /**
     * Este método interactua con el Administrador solicitandole los datos para
     * poder introducir las piezas en el tablero.
     *
     * @param tablero Tablero del Problema.
     */
    public void introduciendoDescripción(Tablero tablero) {
        Scanner lc = new Scanner(System.in);
        System.out.println("Introduzca una descripción para identificar el problema guardado.");
        System.out.println("Procure dar alguna pista sobre el mismo, por ejemplo: \"Mate en 3\".");
        String descripcion = lc.nextLine();
        tablero.setDescripcion(descripcion);
    }
    /**
     * Este método realiza la funcion de mover las piezas en el tablero llamando así mismo a otros métodos de otras clases.
     *
     * @param mov Movimiento candidato a ser realizado.
     * @param tablero Tablero donde irán las piezas     
     * @throws MovIncorrectoException  Excepción que notifica de los posibles errores al intentar ejecutar un movimiento.   
     */
    public void moverProblema(Movimiento mov, Tablero tablero) throws MovIncorrectoException {
        if (tablero.hayPieza(mov.getPosInicial()) == true && ((mov.getPosInicial().getFila() == 1 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == true) || (mov.getPosInicial().getFila() == 6 && tablero.DevuelvePieza(mov.getPosInicial()).getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.DevuelvePieza(mov.getPosInicial()).getColor() == false))) {
            throw new MovIncorrectoException("Para promocionar el peon introduzca una letra mayúscula más indicando la pieza que quiere.");
        } else if (tablero.hayPieza(mov.getPosInicial()) == true && tablero.getTurno() == tablero.DevuelvePieza(mov.getPosInicial()).getColor() && (tablero.DevuelvePieza(mov.getPosFinal()) == null || (tablero.DevuelvePieza(mov.getPosFinal()) != null && tablero.DevuelvePieza(mov.getPosFinal()).getColor() != tablero.DevuelvePieza(mov.getPosInicial()).getColor())) && tablero.hayPiezasEntre(mov) == false) {
            tablero.DevuelvePieza(mov.getPosInicial()).moverPieza(mov, tablero);
        } else {
            throw new MovIncorrectoException("Movimiento invalido-Problema");
        }
    }
    /**
     * Este método convierte los Strings previamente cribados a movimientos.
     *
     * @param s String de tamaño 4.
     * @return Movimiento correspondiente a dicho String.
     */
    public Movimiento stringToMovimiento(String s) {
        Posicion posInicial = new Posicion(Juego.devolverFila(s.charAt(1)), Juego.devolverColumna(s.charAt(0)));
        Posicion posFinal = new Posicion(Juego.devolverFila(s.charAt(3)), Juego.devolverColumna(s.charAt(2)));
        return new Movimiento(0, posInicial, posFinal);
    }
}
