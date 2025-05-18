package game;

import game.entities.Entity;

import java.util.*;


public class Dungeon {
    private String[][] _map;
    private int _height;
    private int _width;
    private List<int[]> _obstacles = new ArrayList<>();

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";

    public static final String WHITE_BG = "\u001B[47m";





    public Dungeon(Integer h, Integer w) {
        if (w >= 15 && w <= 26 && h >= 15 && h <= 26) {
            _height = h;
            _width = w;
        } else {
            System.out.println("La carte doit faire entre 15 et 26 cases de large et de haut.");
            _height = 15;
            _width = 15;
        }

        _map = new String[_height + 3][_width + 1];

        for (int j = 0; j < _map[0].length; j++) {
            if (j >= 1) {
                _map[_map.length - 1][j] = (char) ('A' + j - 1) + "  ";
            } else {
                _map[_map.length - 1][j] = "      ";
            }
        }

        for (int j = 0; j < _map[0].length; j++) {
            _map[0][j] = "   *";
            _map[_map.length - 2][j] = "   *";
            for (int k = 1; k < _map[0].length; k++) {
                _map[0][k] = "---";
                _map[_map.length - 2][k] = "---";
            }
            _map[0][_map[0].length - 1] = "-----*";
            _map[_map.length - 2][_map[0].length - 1] = "-----*";
        }

        for (int i = 1; i < _map.length - 2; i++) {
            for (int j = 0; j < _map[i].length; j++) {
                if (j == 0) {
                    _map[i][j] = (i < 10 ? " " : "") + Integer.toString(i) + " | ";
                } else {
                    _map[i][j] = (j == _map[i].length - 1 ? " .  |" : " . ");
                }
            }
        }
    }



    public boolean isValidPosition(int x, int y) {
        if (x < 1 || x >= _map.length - 1 || y < 1 || y >= _map[0].length) {
            return false;
        }
        for (int[] obstacle : _obstacles) {
            if (obstacle[0] == x && obstacle[1] == y) {
                return false;
            }
        }
        if (_map[x][y] != null && !_map[x][y].trim().equals(".")) { //on verif si c'est autre chose qu'un point, i.e. une entité
            return false;
        }
        return true;
    }

    public void addObstacle(int x, int y) {
        if (isValidPosition(x, y)) {
            _obstacles.add(new int[]{x, y});
        }
    }

    public void CreateDefaultObstacles() {
        for (int group = 0; group < 5; group++) {
            Random r = new Random();
            int x = r.nextInt(2, _map.length - 4);
            int y = r.nextInt(2, _map[0].length-3);

            for (int i = 0; i < 5; i++) {
                addObstacle(x, y);

                int direction = (int) (Math.random() * 4);
                switch (direction) {
                    case 0:
                        if (x > 2) x--;
                        break;
                    case 1:
                        if (x < _map.length - 4) x++;
                        break;
                    case 2:
                        if (y > 2) y--;
                        break;
                    case 3:
                        if (y < _map[0].length - 1) y++;
                        break;
                }
            }
        }
    }

    public void setObstacles() {
        for (int[] coord : _obstacles) {
            int x = coord[0];
            int y = coord[1];
            _map[x][y] = WHITE_BG + "   " + RESET;
        }
    }

    public void setEntities(HashMap<Entity, int[]> entities) {
        entities.forEach((entity, coordinates) -> {
            int x = coordinates[0];
            int y = coordinates[1];
            _map[x][y] = entity.getColor() + entity.getPseudo() + RESET;
        });
    }

    public void displayGrid() {
        for (int i = 0; i < _map.length; i++) {
            for (int j = 0; j < _map[i].length; j++) {
                System.out.print(_map[i][j]);
            }
            System.out.println();
        }
    }

    public void displayMap(HashMap<Entity, int[]> entities) {
        setObstacles();
        setEntities(entities);
        displayGrid();
    }

    public int[] getSize() {
        return new int[]{_height, _width};
    }
}