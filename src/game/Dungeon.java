package game;

import game.entities.Entity;
import game.items.Equipment;
import game.utils.GameUtils;
import game.utils.Display;

import java.util.*;


public class Dungeon {
    private String[][] _map;
    private int _height;
    private int _width;
    private Positions _positions;
    private int _number;

    //?Constructeur de la classe Dungeon
    public Dungeon(int h, int w, int number) {
        if (w >= 15 && w <= 26 && h >= 15 && h <= 26) {
            _height = h;
            _width = w;
        } else {
            System.out.println("La carte doit faire entre 15 et 26 cases de large et de haut.");
            _height = 15;
            _width = 15;
        }

        _number = number;
        _map = new String[_height + 3][_width + 1];
        _positions = new Positions();

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
                    _map[i][j] = (i < 10 ? " " : "") + i + " | ";
                } else {
                    _map[i][j] = (j == _map[i].length - 1 ? " .  |" : " . ");
                }
            }
        }
    }

    public boolean isValidPosition(int x, int y) {
        //verif bounds map
        if (x < 1 || x >= _map.length - 1 || y < 1 || y >= _map[0].length) {
            return false;
        }
        //verif obstacles
        for (int[] obstacle : _positions.getObstacles()) {
            if (obstacle[0] == x && obstacle[1] == y) {
                return false;
            }
        }
        //verif entities
        for (Map.Entry<Entity, int[]> entity : _positions.getEntitiesPosition().entrySet()) {
            if (entity.getValue()[0] == x && entity.getValue()[1] == y) {
                return false;
            }
        }
        //verif equipments
        for (Map.Entry<Equipment, int[]> eqipment : _positions.getEquipmentPosition().entrySet()) {
            if (eqipment.getValue()[0] == x && eqipment.getValue()[1] == y) {
                return false;
            }
        }
        return true;
    }

    //? Methods to add everything to the lists and hashmaps, manually or randomly
    public void addEntity(int x, int y, Entity entity) {
        if (isValidPosition(x, y)) {
            _positions.addEntity(entity, new int[]{x, y});
        }
    }
    public void randomSetEntity(List<Entity> entities) {
        for (Entity entity : entities) {
            int x = GameUtils.roll(1, _height-2);
            int y = GameUtils.roll(1, _width-1);
            while (!isValidPosition(x, y)) {
                x = GameUtils.roll(1, _height-2);
                y = GameUtils.roll(1, _width-1);
            }
            addEntity(x, y, entity);
        }
    }
    public void addEquipment(int x, int y, Equipment equipment) {
        if (isValidPosition(x, y)) {
            _positions.addEquipment(equipment ,new int[]{x, y});
        }
    }
    public void randomSetEquipment(List<Equipment> equipments) {
        for (Equipment equipment : equipments) {
            int x = GameUtils.roll(1, _height-2);
            int y = GameUtils.roll(1, _width-1);
            while (!isValidPosition(x, y)) {
                x = GameUtils.roll(1, _height-2);
                y = GameUtils.roll(1, _width-1);
            }
            addEquipment(x, y, equipment);
        }
    }
    public void addObstacle(int x, int y) {
        if (isValidPosition(x, y)) {
            _positions.addObstacle(new int[]{x, y});
        }
    }
    public void randomSetObstacles() {
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



    //? Methods to set everything on the map
    private void setObstacles() {
        for (int[] coord : _positions.getObstacles()) {
            int x = coord[0];
            int y = coord[1];
            if (y == _map[0].length - 1) {
                _map[x][y] = GameUtils.WHITE_BG + "   " + GameUtils.RESET + " |";
            } else {
                _map[x][y] = GameUtils.WHITE_BG + "   " + GameUtils.RESET;
            }
        }
    }
    private void setEntities() {
        _positions.getEntitiesPosition().forEach((entity, coordinates) -> {
            int x = coordinates[0];
            int y = coordinates[1];
            if (y == _map[0].length - 1) {
                _map[x][y] = entity.getColor() + entity.getPseudo() + GameUtils.RESET + " |";
            } else {
                _map[x][y] = entity.getColor() + entity.getPseudo() + GameUtils.RESET;
            }

        });
    }
    private void setEquipments() {
        _positions.getEquipmentPosition().forEach((equipment, coordinates) -> {
            int x = coordinates[0];
            int y = coordinates[1];
            _map[x][y] = GameUtils.BLUE + "[⌘]" + GameUtils.RESET;
        });
    }


    //? Method to update the map (duh)
    public void updateMap() {
        setObstacles();
        setEntities();
        setEquipments();
        displayGrid();
    }

    private void displayGrid() {
        for (String[] strings : _map) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
        Display.display( GameUtils.WHITE_BG + "   " + GameUtils.RESET + " : Obstacles ||" + GameUtils.BLUE + " [⌘]" + GameUtils.RESET + " : Equipements || " + GameUtils.PURPLE + " [*]" + GameUtils.RESET + " : Entities ||" + GameUtils.RED + " [#]" + GameUtils.RESET + " : Monsters");
        Display.display("\n");
    }


    //? Getters
    public Positions getPosition(){
        return _positions;
    }
    public int getDungeonNumber(){
        return _number;
    }
}