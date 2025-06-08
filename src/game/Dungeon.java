package game;

import game.entities.Character;
import game.entities.Entity;
import game.entities.EntityType;
import game.items.Equipment;
import game.spells.*;
import game.utils.ActionResult;
import game.utils.GameUtils;
import game.utils.Display;

import java.util.*;

import static game.utils.GameUtils.parsePosition;
import static game.utils.GameUtils.scanner;


public class Dungeon {
    private String[][] _map;
    private int _height;
    private int _width;
    private int _number;
    private HashMap<Entity, int[]> _entitiesPosition;
    private HashMap<Equipment, int[]> _equipmentPosition;
    private List<int[]> _obstacles;

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
        _entitiesPosition = new HashMap<>();
        _equipmentPosition = new HashMap<>();
        _obstacles = new ArrayList<>();

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



    /**
     * Checks if the given position (x, y / string like A6) is valid in the dungeon.
     * A position is valid if it is within the bounds of the map,
     * not occupied by an obstacle, entity, or equipment.
     *
     * @param x
     * @param y
     * @return true if the position is valid, false sinn.
     */
    public boolean isValidPosition(int x, int y) {
        //verif bounds map
        if (x < 1 || x >= _map.length - 1 || y < 1 || y >= _map[0].length) {
            return false;
        }
        //verif obstacles
        for (int[] obstacle : _obstacles) {
            if (obstacle[0] == x && obstacle[1] == y) {
                return false;
            }
        }
        //verif entities
        for (Map.Entry<Entity, int[]> entity : _entitiesPosition.entrySet()) {
            if (entity.getValue()[0] == x && entity.getValue()[1] == y) {
                return false;
            }
        }
        //verif equipments
        for (Map.Entry<Equipment, int[]> eqipment : _equipmentPosition.entrySet()) {
            if (eqipment.getValue()[0] == x && eqipment.getValue()[1] == y) {
                return false;
            }
        }
        return true;
    }
    public boolean isValidPosition(int[] posi) {
        int x = posi[0];
        int y = posi[1];
        return isValidPosition(x, y);
    }



    /**
     * Methods to add entities, equipments, and obstacles to the map. <br>
     * Its just checking is the pos is valid and then adding the entity/equipment/obstacle to the positions Hashmap.
     * Note : The overload for addObstacles is to avoid unneccessary conversions.
     */
    public void addEntity(Entity entity, int[] position) {
        if (isValidPosition(position)) {
            _entitiesPosition.put(entity, position);
        }
    }
    public void addEquipment(Equipment equipment, int[] position) {
        if (isValidPosition(position)) {
            _equipmentPosition.put(equipment, position);
        }
    }
    public void addObstacle(int x, int y) {
        if (isValidPosition(x, y)) {
            addObstacle(new int[]{x, y});
        }
    }
    public void addObstacle(int[] position) {
        _obstacles.add(position);
    }

    /**
     * Methods to randomly add entities, equipments, and obstacles.
     * Uses GameUtils.roll to generate random positions.
     * Checks if the position is valid and adds it if so.
     * */
    public void randomlyAddEntity(List<Entity> entities) {
        for (Entity entity : entities) {
            int x = GameUtils.roll(1, _height-2);
            int y = GameUtils.roll(1, _width-1);
            while (!isValidPosition(x, y)) {
                x = GameUtils.roll(1, _height-2);
                y = GameUtils.roll(1, _width-1);
            }
            addEntity(entity, new int[]{x, y});
        }
    }
    public void randomlyAddEquipment(List<Equipment> equipments) {
        for (Equipment equipment : equipments) {
            int x = GameUtils.roll(1, _height-2);
            int y = GameUtils.roll(1, _width-1);
            while (!isValidPosition(x, y)) {
                x = GameUtils.roll(1, _height-2);
                y = GameUtils.roll(1, _width-1);
            }
            addEquipment( equipment, new int[]{x, y});
        }
    }
    public void randomlyAddObstacles() {
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




    /**
     * Methods to set the obstacles, entities, and equipments on the map.
     * Just add each entity/equipment/obstacle to the map display at the correct position.
     * */
    private void setObstacles() {
        for (int[] coord : _obstacles) {
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
       _entitiesPosition.forEach((entity, coordinates) -> {
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
        _equipmentPosition.forEach((equipment, coordinates) -> {
            int x = coordinates[0];
            int y = coordinates[1];
            _map[x][y] = GameUtils.BLUE + "[⌘]" + GameUtils.RESET;
        });
    }



    /**
     * Refresh map by calling above methods.
     */
    public void updateMap() {
        setObstacles();
        setEntities();
        setEquipments();
    }




    /**
     * Methods to handle actions in the dungeon.
     * These methods are called by the game loop to perform actions

     * <br>
     * Note : I won't go into details here either, but they almost all
     * take an Entity and a position as parameters.
     * They check if the position is valid, if the entity can perform the action,
     * and then perform the action.
     * They return an ActionResult, which is an enum that indicates the result of the action. (duh)
     * This is used to display the result in DM, and not in here
     */
    public ActionResult attack(Entity attacker, int x, int y) {
        Entity target = getEntityAtPosition(x,y);
        if (target == null) {
            return ActionResult.NO_TARGET;
        }

        int dist = distanceBetween(attacker, new int[]{x, y});
        ActionResult isDead = attacker.attack(target, dist);
        if (isDead==ActionResult.TARGET_KILLED) {
            removeEntity(target);
            _map[x][y] = " . ";
        }
        return isDead;
    }
    public ActionResult move(Entity entity, int x, int y) {
        if (!isValidPosition(x, y)) {
            return ActionResult.POSITION_BLOCKED;
        }
        if (getEntityAtPosition(x, y) != null) {
            return ActionResult.POSITION_BLOCKED;
        }
        if (distanceBetween(entity, new int[] {x, y}) > entity.getSpeed()/3) {
            return ActionResult.POSITION_TOO_FAR;
        }
        return moveEntity(entity, x, y);
    }
    public ActionResult pickUp(Entity entity,int x, int y) {
        Equipment equipment = getEquipmentAtPosition(x, y);
        if (equipment == null) {
            return ActionResult.POSITION_EMPTY;
        }
        if (entity.getType()==EntityType.MONSTER) {
            return ActionResult.WRONG_TYPE;
        }

        if (distanceBetween(entity, new int[]{x,y}) > entity.getStats().getSpeed()/3) {
            return ActionResult.POSITION_TOO_FAR;
        }

        ((game.entities.Character) entity).addToInventory(equipment);
        removeEquipment(equipment);
        _map[x][y] = " . ";
        Display.display(entity.getPseudo() + " picked up " + equipment.getName() + ".");
        return ActionResult.SUCCESS;
    }
    public String comment(Entity entity, String text){
        return (entity.toString() + " : " + text);
    }
    public ActionResult castSpell(Entity entity) {
        if (entity.getType() == EntityType.MONSTER) {
            return ActionResult.WRONG_TYPE;
        }
        Character character = (Character) entity;
        List<Spell> spells = character.getSpells();
        if (character.getSpells().isEmpty()) {
            return ActionResult.FAILURE;
        }
        Display.displaySpellsMenu(entity);
        String choice = scanner.next();
        switch(choice) {
            case "heal" -> {
                for (Spell spell : spells) {
                    if (spell.getName().equalsIgnoreCase("Heal")) {
                        String pos = scanner.next();
                        int[] position = parsePosition(pos);
                        Entity target= getEntityAtPosition(position[0], position[1]);
                        if (target == null) {
                            return ActionResult.FAILURE;
                        }
                        spell.cast(_entitiesPosition, target);
                        return ActionResult.SUCCESS;
                    }
                    return ActionResult.UNKNOWN_SPELL;
                }
            }
            case "boogiewoogie" -> {
                for (Spell spell : spells) {
                    if (spell.getName().equalsIgnoreCase("BoogieWoogie")) {
                        String pos = scanner.next();
                        int[] position = parsePosition(pos);
                        Entity target= getEntityAtPosition(position[0], position[1]);
                        if (target == null) {
                            return ActionResult.FAILURE;
                        }
                        spell.cast(_entitiesPosition, target);
                        return ActionResult.SUCCESS;
                    }
                    return ActionResult.UNKNOWN_SPELL;
                }
            }
            case "magicweapon" -> {
                for (Spell spell : spells) {
                    if (spell.getName().equalsIgnoreCase("Magicweapon")) {
                        String pos = scanner.next();
                        int[] position = parsePosition(pos);
                        Entity target= getEntityAtPosition(position[0], position[1]);
                        if (target == null) {
                            return ActionResult.FAILURE;
                        }
                        spell.cast(_entitiesPosition, target);
                        return ActionResult.SUCCESS;
                    }
                    return ActionResult.UNKNOWN_SPELL;
                }
            }
            case "stop" -> {
                return ActionResult.STOP;
            }
            default -> Display.displayError("Invalid choice. Please try again.");
        }
        return ActionResult.FAILURE;
    }


    /**
     * Moves an entity to a new position (x, y) in the dungeon.
     *
     * @param entity the entity to move
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @return ActionResult the result of the action
     */
    public ActionResult moveEntity(Entity entity, int x, int y) {
        if (isValidPosition(x, y)) {
            int[] oldPosition = _entitiesPosition.get(entity);
            if (oldPosition != null) {
                _entitiesPosition.remove(entity);
                addEntity(entity, new int[]{x, y});
                _map[oldPosition[0]][oldPosition[1]] = " . ";
            }
        } else {
            return ActionResult.FAILURE;
        }
        return ActionResult.SUCCESS;
    }

    /**
     * Hurts an entity for "dices"d"faces" dmg.
     *
     * @param pos the position of the entity to hurt
     * @param dices the number of dice to roll for damage
     * @param faces the number of faces of the die to roll
     * @return ActionResult the result of the action
     */
    public ActionResult hurtEntity(String pos, int dices, int faces){
        int[] position = parsePosition(pos);
        int x = position[0];
        int y = position[1];

        Entity target = getEntityAtPosition(x, y);
        if (target == null) {
            return ActionResult.NO_TARGET;
        }
        int damage = GameUtils.roll(dices, faces);
        target.removeHp(damage);
        Display.display(target.getName() + " has been hurt for " + damage + " damage.");

        if (!target.isAlive()) {
            target.setHp(-target.getHp());
            removeEntity(target);
            _map[x][y] = " . ";
            return ActionResult.TARGET_KILLED;
        }
        return ActionResult.TARGET_HIT;
    }


    /**
     * Methods to add an obstacle, or remove entities or equipment
     * Note : No need to remove obstacles
     *
     *
     */
    public void removeEntity(Entity target) {
        _entitiesPosition.remove(target);
    }
    public void removeEquipment(Equipment target) {
        _equipmentPosition.remove(target);
    }



    /**
     * give the distance between an entity and a target position.
     * uses une formule dont je me souviens plus le nom mais qui permets de calculer la diag
     *
     * @param entity the entity to check the distance from
     * @param targetPosition the target position to check the distance to
     * @return the distance between the entity and the target position
     */
    public int distanceBetween(Entity entity, int[] targetPosition) {
        int[] entityPosition = _entitiesPosition.get(entity);

        int dx = Math.abs(entityPosition[0] - targetPosition[0]);
        int dy = Math.abs(entityPosition[1] - targetPosition[1]);

        return Math.max(dx, dy);
    }


    //? Getters
    public int getDungeonNumber(){
        return _number;
    }
    public Entity getEntityAtPosition(int x, int y) {
        for (Map.Entry<Entity, int[]> entry : _entitiesPosition.entrySet()) {
            int[] position = entry.getValue();
            if (position[0] == x && position[1] == y) {
                return entry.getKey();
            }
        }
        return null;
    }
    public Equipment getEquipmentAtPosition(int x, int y) {
        for (Map.Entry<Equipment, int[]> entry : _equipmentPosition.entrySet()) {
            int[] position = entry.getValue();
            if (position[0] == x && position[1] == y) {
                return entry.getKey();
            }
        }
        return null;
    }
    public int getNumber() {
        return _number;
    }
    public int getHeight(){
        return _height;
    }
    public int getWidth(){
        return _width;}
    public String[][] getMap() {
        return _map;
    }
}

