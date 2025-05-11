package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Dungeon {
    private String[][] _map;
    private int _height;
    private int _width;
    private List<int[]> _obstacles = new ArrayList<>();

    public Dungeon(Integer h, Integer w) {
        if (w >= 15 && w <= 26 && h >= 15 && h <= 26) {
            _height = h;
            _width = w;
        } else {
            System.out.println("La carte doit faire entre 15 et 26 cases de large et de haut.");
            _height = 15;
            _width = 15;
        }
        _map = new String[_height+3][_width+1];
        //+4 to height pour les lignes de tirets et les lettres et la legende



        //je deviens fou ça fait 2HEURES que je suis bloqué dessus parce que mon cerveau
        //voulait juste pas comprendre comment ça marchait.
        //Si un prof (ou elisabeth, c'est plus probable) voit ça, sachez que j'ai perdu ma sanité mentale en faisant ça

        //dernière ligne = alphabet
        for (int j = 0; j < _map[0].length; j++) {
            if (j >= 1) {
                _map[0][j] = (char) ('A' + j - 1) + "  ";
            } else {
                _map[0][j] = "      ";
            }
        }
        //deuxième et avant-dernière lignes
        for (int j = 0; j < _map[0].length; j++) {
            _map[1][j] = "   *";
            _map[_map.length-1][j] = "   *";
            for (int k = 1; k < _map[0].length; k++) {
                _map[1][k] = "---"; //on met des tirets (3 par lettres, oui j'ai compté)
                _map[_map.length-1][k] = "---";
            }
            _map[1][_map[0].length-1] = "-----*";
            _map[_map.length-1][_map[0].length-1] = "-----*";

        }
        //Double boucle pour itérer sur chaque case de la carte
        for (int i = 2; i < _map.length-1; i++) {
            for (int j = 0; j < _map[i].length; j++) {
                if (j == 0) {
                    //num de ligne
                    _map[i][j] = (i - 1 < 10 ? " " : "") + Integer.toString(i - 1) + " | ";
                    //ecriture ternaire muehehehe j'adore faire ça c'est trop marrant
                } else {
                    // Contenu de la carte
                    _map[i][j] = (j == _map[i].length-1 ? " .  |" : " . ");
                }
            }
        }
    }

    public void addObstacle(int x, int y) {
        if (x >= 2 && x < _map.length - 2 && y >= 1 && y < _map[0].length - 1) {
            _obstacles.add(new int[]{x, y});
        } else {
            System.out.println("Coordonnées invalides pour ajouter un obstacle.");
        }
    }

    public void setDefaultObstacles() {
        for (int group = 0; group < 2; group++) { //Nombre de groupes d'obstacles, ici deux
            //TODO : maybe rajouter un moyen de choisir ? idk
            int x = (int) (Math.random() * (_map.length - 2)) + 2; // Coordonnée x aléatoire
            int y = (int) (Math.random() * (_map[0].length - 1)) + 1; // Coordonnée y aléatoire

            for (int i = 0; i < 5; i++) { // 5 obstacles par groupe
                addObstacle(x, y);

                // Choisir une direction aléatoire pour le prochain obstacle
                int direction = (int) (Math.random() * 4);
                switch (direction) {
                    case 0: // Haut
                        if (x > 1) x--;
                        break;
                    case 1: // Bas
                        if (x < _map.length - 2) x++;
                        break;
                    case 2: // Gauche
                        if (y > 1) y--;
                        break;
                    case 3: // Droite
                        if (y < _map[0].length - 1) y++;
                        break;
                }
            }
        }
    }

    public void displayGrid() {
        for (int i = 0; i < _map.length; i++) {
            for (int j = 0; j < _map[i].length; j++) {
                System.out.print(_map[i][j]);
                }
            System.out.println();
            }
    }

    public void displayObstacles() {
        for (int i = 0; i < _obstacles.size(); i++) {
            int[] coord = _obstacles.get(i);
            int x = coord[0];
            int y = coord[1];
            _map[x][y] = " Ø "; // Emoji obstacle
        }
    }

    public void displayMap() {
        displayObstacles();
        displayGrid();
    }
}