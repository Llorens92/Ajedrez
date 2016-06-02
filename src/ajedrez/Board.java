/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.*;

/**
 * Clase a partir de la cual se crea el tablero que irá dentro de la clase
 * Ventana
 * @author Propietario
 */
public class Board extends JPanel {

    /**
     * Imagen de fondo del tablero
     */
    private Image img;
    /**
     * Array bidimensional de JPanel que formará el tablero en sí
     */
    private JPanel[][] arraytablero = new JPanel[8][8];
    /**
     * Array de iconos construidos a partir del array de imágenes
     */
    private ImageIcon[] arrayiconos = new ImageIcon[12];
    /**
     * Array de imágenes con las que cambiamos la imagen de los iconos
     */
    private Image[] arrayimagenes = new Image[12];

    /**
     * Constructor que inicializa la imagen de fondo, inicializa el arraytablero para que en cada "casilla" haya un JPanel,
     * inicializa el arrayiconos con las distintas imágenes posibles y las reescala para que ocupen el tamaño deseado.
     * @param img Imagen de fondo del tablero.
     * @param tablero Instancia de la clase Tableor a partir de la cual se pinta el mismo gráficamente.
     */
    public Board(Image img, Tablero tablero) {
        this.img = img;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                arraytablero[i][j] = new JPanel();
            }
        }
        for (int i = 0; i < 12; i++) {
            switch (i) {
                case 0:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/torrenegra.JPG"));
                    break;
                case 1:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/caballonegro.JPG"));
                    break;
                case 2:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/alfilnegro.JPG"));
                    break;
                case 3:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/damanegra.JPG"));
                    break;
                case 4:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/reynegro.JPG"));
                    break;
                case 5:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/peonnegro.JPG"));
                    break;
                case 6:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/peonblanco.JPG"));
                    break;
                case 7:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/torreblanca.JPG"));
                    break;
                case 8:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/caballoblanco.JPG"));
                    break;
                case 9:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/alfilblanco.JPG"));
                    break;
                case 10:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/damablanca.JPG"));
                    break;
                default:
                    arrayiconos[i] = new ImageIcon(getClass().getResource("Imagenes/reyblanco.JPG"));
                    break;
            }
        }
        for (int i = 0; i < 12; i++) {
            arrayimagenes[i] = arrayiconos[i].getImage();
            arrayiconos[i] = new ImageIcon(arrayimagenes[i].getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        }
    }
    /**
     * Método que, en función de las piezas que haya en el Tablero que se le pasa como argumento, va situando los correspondientes
     * iconos dentro del array de JPanel.
     * Para ello, una vez se comprueba que en tal posición hay tal pieza se crea un JLabel al que se le añade al correspondiente
     * icono y se agrega el JLabel al JPanel pertinente.
     * Luego se comprueba sucesivamente si hay dos JLabel en cada JPanel, o si hay uno pero no debería haberlo, y en esos
     * casos se borra el primero que "entro".
     * Finalmente se añade el JPanel (vacío o con el icono oportuno) al propio Board que no es más que otro JPanel, se le cambia el
     * Layout para que se centre el icono y se le hace visible.
     * @param tablero Tablero que contiene el array bidimensional de piezas que se usará para "pintar" la posición de las mismas 
     * en el Board.
     */
    public void pintartablero(Tablero tablero) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Torre") == 0 && tablero.getTablero()[i][j].getColor() == false) {
                    JLabel jtorreN = new JLabel();
                    jtorreN.setIcon(arrayiconos[0]);
                    arraytablero[i][j].add(jtorreN);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero.getTablero()[i][j].getColor() == false) {
                    JLabel jcaballoN = new JLabel();
                    jcaballoN.setIcon(arrayiconos[1]);
                    arraytablero[i][j].add(jcaballoN);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Alfil") == 0 && tablero.getTablero()[i][j].getColor() == false) {
                    JLabel jalfilN = new JLabel();
                    jalfilN.setIcon(arrayiconos[2]);
                    arraytablero[i][j].add(jalfilN);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Dama") == 0 && tablero.getTablero()[i][j].getColor() == false) {
                    JLabel jdamaN = new JLabel();
                    jdamaN.setIcon(arrayiconos[3]);
                    arraytablero[i][j].add(jdamaN);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero.getTablero()[i][j].getColor() == false) {
                    JLabel jreyN = new JLabel();
                    jreyN.setIcon(arrayiconos[4]);
                    arraytablero[i][j].add(jreyN);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Torre") == 0 && tablero.getTablero()[i][j].getColor() == true) {
                    JLabel jtorreB = new JLabel();
                    jtorreB.setIcon(arrayiconos[7]);
                    arraytablero[i][j].add(jtorreB);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Caballo") == 0 && tablero.getTablero()[i][j].getColor() == true) {
                    JLabel jcaballoB = new JLabel();
                    jcaballoB.setIcon(arrayiconos[8]);
                    arraytablero[i][j].add(jcaballoB);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Alfil") == 0 && tablero.getTablero()[i][j].getColor() == true) {
                    JLabel jalfilB = new JLabel();
                    jalfilB.setIcon(arrayiconos[9]);
                    arraytablero[i][j].add(jalfilB);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Dama") == 0 && tablero.getTablero()[i][j].getColor() == true) {
                    JLabel jdamaB = new JLabel();
                    jdamaB.setIcon(arrayiconos[10]);
                    arraytablero[i][j].add(jdamaB);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Rey") == 0 && tablero.getTablero()[i][j].getColor() == true) {
                    JLabel jreyB = new JLabel();
                    jreyB.setIcon(arrayiconos[11]);
                    arraytablero[i][j].add(jreyB);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.getTablero()[i][j].getColor() == false) {
                    JLabel jpeonN = new JLabel();
                    jpeonN.setIcon(arrayiconos[5]);
                    arraytablero[i][j].add(jpeonN);
                } else if (tablero.getTablero()[i][j] != null && tablero.getTablero()[i][j].getClass().getName().compareTo("ajedrez.Peon") == 0 && tablero.getTablero()[i][j].getColor() == true) {
                    JLabel jpeonB = new JLabel();
                    jpeonB.setIcon(arrayiconos[6]);
                    arraytablero[i][j].add(jpeonB);
                }
                if (arraytablero[i][j].getComponentCount() > 1) {
                    arraytablero[i][j].remove(0);
                }
                if (tablero.getTablero()[i][j] == null && arraytablero[i][j].getComponentCount() == 1) {
                    arraytablero[i][j].remove(0);
                }
                this.add(arraytablero[i][j]);
                arraytablero[i][j].setLayout(new GridBagLayout());
                arraytablero[i][j].setOpaque(false);
            }
        }
    }
    /**
     * Permite visualizar la imagen de fondo del tablero.     *
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

}
