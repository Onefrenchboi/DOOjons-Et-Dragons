package game;

import game.entities.Character;
import game.entities.Entity;
import game.items.Equipment;
import game.spells.*;
import game.utils.GameUtils;
import game.utils.Display;

import java.util.*;

import static game.utils.GameUtils.parsePosition;
import static game.utils.GameUtils.scanner;


public class Dungeon {
    private String[][] _map;
    private int _height;
    private int _width;
    private Positions _positions;
    private int _number;

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



    /**
     * Checks if the given position (x, y) is valid in the dungeon.
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


    /** Methods to add entities, equipments, and obstacles to the map.
     *  Also includes methods to randomly set entities, equipments, and obstacles.
     * <br>
     * Note : I won't go into details, but its just checking is the pos is valid
     * and then adding the entity/equipment/obstacle to the positions list.
     */
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




    /**
     * Methods to set the obstacles, entities, and equipments on the map.
     * Just add each entity/equipment/obstacle to the map at the correct position.
     * */
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



    /**
     * Refresh map by callin above methods.
     */
    public void updateMap() {
        setObstacles();
        setEntities();
        setEquipments();
        displayGrid();
    }

    /**
     * Displays the current state of the dungeon map.
     * Also gives a legend
     *
     * */
    private void displayGrid() {
        for (String[] strings : _map) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
        Display.display( GameUtils.WHITE_BG + "   " + GameUtils.RESET + " : Obstacles ||" + GameUtils.BLUE + " [⌘]" + GameUtils.RESET + " : Equipements || " + GameUtils.PURPLE + " [*]" + GameUtils.RESET + " : Entities ||" + GameUtils.RED + " [#]" + GameUtils.RESET + " : Monsters");
    }






    /**
     * Methods to handle actions in the dungeon.
     * These methods are called by the game loop to perform actions

     * <br>
     * Note : I won't go into details here either, but they almost all
     * take an Entity and a position as parameters.
     * They check if the position is valid, if the entity can perform the action,
     * and then perform the action.
     */
    public void attack(Entity attacker, String pos) {
        int[] position = parsePosition(pos);
        int x = position[0];
        int y = position[1];

        Entity target = getEntityAtPosition(x, y);
        if (target == null) {
            Display.displayError("No entity at this position.");
            return;
        }

        if (!attacker.canAttack(target)) {
            Display.displayError("You cannot attack this entity.");
            return;
        }
        // Check if the attacker is too far from the target
        if (_positions.distanceBetween(attacker, position) > attacker.getEquippedWeapon().getRange()) {
            Display.displayError("You are too far from " + target.getName() + " to attack it.");
            return;
        }
        int attackRoll = GameUtils.roll(1, 20)+attacker.getEquippedWeapon().getBonus();
        Display.display("You rolled a " + attackRoll);
        if (attacker.getEquippedWeapon().getRange() == 1) {
            attackRoll += attacker.getStats().getStrength();
            Display.display("Your attack roll is " + attackRoll + " (Strength bonus applied [+"+ attacker.getStats().getStrength() +"])");
        } else {
            attackRoll += attacker.getStats().getDexterity();
            Display.display("Your attack roll is " + attackRoll + " (Dexterity bonus applied [+"+ attacker.getStats().getDexterity() +"])");
        }

        if (attackRoll > target.getAC()){
            Display.display("You hit " + target.getName() + "!");
            int damage = attacker.getEquippedWeapon().damage();
            Display.display("You did " + damage + " damage !");
            target.removeHp(damage);
            if (!target.isAlive()) {
                Display.display(target.getName() + " has been defeated!");
                target.setHp(target.getHp());
                _positions.removeEntity(target);
                _map[x][y] = " . ";
                return;
            }
            Display.display(target.getName() + " has " + target.getHp() + " HP left.");
        } else {
            Display.displayError("You missed " + target.getName() + "!");
        }



    }
    public void equip(Entity entity) {
        if (entity.isPlayer()){
            Display.display("Choose an item to equip from your inventory : ");
            String inventory = entity.displayInventory();
            if (inventory.equals("Inventory is empty.")) {
                Display.displayError("You have no items to equip.");
                return;
            }
            Display.display(inventory);
            int choice = scanner.nextInt();
            while (choice < 0 || choice >= entity.getInventory().size()) {
                Display.displayError("Invalid choice. Please choose a valid item number.");
                choice = scanner.nextInt();
            }
            Equipment equipment = entity.getInventory().get(choice);
            if (equipment.isArmor()) {
                entity.equipArmor(equipment);
                Display.display("You equipped " + equipment.getName() + ".");
            } else if (equipment.isWeapon()) {
                entity.equipWeapon(equipment);
                Display.display("You equipped " + equipment.getName() + ".");
            }
        }
    }
    public void move(Entity entity, String pos) {
        int[] position = parsePosition(pos);
        int x = position[0];
        int y = position[1];

        if (!isValidPosition(x, y)) {
            Display.displayError("Invalid position. Please enter a new position : ");
            return;
        }

        if (getEntityAtPosition(x, y) != null) {
            Display.displayError("There is already an entity at this position.");
            return;
        }
        if (_positions.distanceBetween(entity, position) > entity.getStats().getSpeed()/3) {
            Display.displayError("Too far.");
            return;
        }
        moveEntity(entity, x, y);
        Display.display(entity.getPseudo() + " moved to " + pos + ".");
    }
    public void pickUp(Entity entity,String pos) {
        int[] position = parsePosition(pos);
        int x = position[0];
        int y = position[1];

        Equipment equipment = getEquipmentAtPosition(x, y);
        if (equipment == null) {
            Display.displayError("No equipment at this position.");
            return;
        }

        if (entity.isMonster()) {
            Display.displayError("You cannot pick up this equipment.");
            return;
        }

        if (_positions.distanceBetween(entity, position) > entity.getStats().getSpeed()/3) {
            Display.displayError("Too far.");
            return;
        }

        ((game.entities.Character) entity).addToInventory(equipment);
        _positions.removeEquipment(equipment);
        _map[x][y] = " . ";
        Display.display(entity.getPseudo() + " picked up " + equipment.getName() + ".");
    }
    public void comment(Entity entity, String text){
        Display.display(entity.toString() + " : " + text);
    }
    /**
     * The only "different" method
     * It does another switch case, but for the spells
     *
     * */
    public void castSpell(Entity entity) {
        if (entity.isMonster()) {
            Display.displayError("Monsters cannot cast spells.");
            return;
        }
        Display.displaySpellsMenu(entity);
        game.entities.Character character = (game.entities.Character) entity;
        String choice = scanner.next();
        switch(choice) {
            case "heal" -> {
                Heal healSpell = (Heal) character.getSpellByName("Heal");
                if (healSpell == null) {
                    Display.displayError("You don't have the Heal spell.");
                    return;
                }
                String pos = scanner.next();
                int[] position = parsePosition(pos);
                Entity target= getEntityAtPosition(position[0], position[1]);
                if (target == null) {
                    Display.displayError("No target at this position.");
                    return;
                }
                healSpell.cast(target);
            }
            case "boogiewoogie" -> {
                BoogieWoogie boogieWoogie = (BoogieWoogie) character.getSpellByName("BoogieWoogie");
                if (boogieWoogie == null) {
                    Display.displayError("You don't have the Boogie Woogie spell.");
                    return;
                }
                String pos1 = scanner.next();
                String pos2 = scanner.next();
                int[] position1 = parsePosition(pos1);
                int[] position2 = parsePosition(pos2);
                Entity target1 = getEntityAtPosition(position1[0], position1[1]);
                Entity target2 = getEntityAtPosition(position2[0], position2[1]);
                boogieWoogie.cast(target1, target2, this);
                
            }
            case "magicweapon" -> {
                MagicWeapon magicWeapon = (MagicWeapon) character.getSpellByName("MagicWeapon");
                if (magicWeapon == null) {
                    Display.displayError("You don't have the Magic Weapon spell.");
                    return;
                }
                String pos = scanner.next();
                int[] position = parsePosition(pos);
                Entity targetEntity = getEntityAtPosition(position[0], position[1]);
                if (targetEntity == null) {
                    Display.displayError("No target at this position.");
                    return;
                }
                Equipment target = magicWeapon.selectEquipmentToEnchant((Character) targetEntity);
                if (target != null) {
                    character.getSpellByName("MagicWeapon").cast(target);
                    Display.display("You enchanted " + target.getName() + " with Magic Weapon!");
                }
            }
            default -> Display.displayError("Invalid choice. Please try again.");
        }
    }


    /**
     * Moves an entity to a new position (x, y) in the dungeon.
     * Hurts an entity
     * <br>
     * Note : Mainly used by the DM
     */
    public void moveEntity(Entity entity, int x, int y) {
        if (isValidPosition(x, y)) {
            int[] oldPosition = _positions.getEntitiesPosition().get(entity);
            if (oldPosition != null) {
                _positions.getEntitiesPosition().remove(entity);
                _positions.addEntity(entity, new int[]{x, y});
                _map[oldPosition[0]][oldPosition[1]] = " . ";
            }
        } else {
            Display.displayError("Invalid position for entity movement.");
        }
        Display.display("Moved " + entity.toString() + " successfully.");
    }
    public void hurtEntity(String pos, int dices, int faces){
        int[] position = parsePosition(pos);
        int x = position[0];
        int y = position[1];

        Entity target = getEntityAtPosition(x, y);
        if (target == null) {
            Display.displayError("No entity at this position.");
            return;
        }
        int damage = GameUtils.roll(dices, faces); // Example damage calculation
        target.removeHp(damage);
        Display.display(target.getName() + " has been hurt for " + damage + " damage.");

        if (!target.isAlive()) {
            Display.display(target.getName() + " has been defeated!");
            target.setHp(-target.getHp());
            _positions.removeEntity(target);
            _map[x][y] = " . ";
        }


    }


    /**
     * Switches the positions of two entities in the dungeon.
     * <br>
     * Note : Used for the Boogie Woogie spell
     */
    public void switchEntities(Entity target1, Entity target2) {
        int[] pos1 = _positions.getEntityPosition(target1);
        int[] pos2 = _positions.getEntityPosition(target2);

        if (pos1 == null || pos2 == null) {
            Display.displayError("One of the targets is not in the dungeon.");
            return;
        }
        _positions.removeEntity(target1);
        _positions.removeEntity(target2);

        _positions.addEntity(target1, new int[]{pos2[0], pos2[1]});
        _positions.addEntity(target2, new int[]{pos1[0], pos1[1]});


        Display.display("You switched " + target1.getPseudo() + " and " + target2.getPseudo() + ".");
    }


    //? Getters
    public int getDungeonNumber(){
        return _number;
    }
    public Entity getEntityAtPosition(int x, int y) {
        for (Map.Entry<Entity, int[]> entry : _positions.getEntitiesPosition().entrySet()) {
            int[] position = entry.getValue();
            if (position[0] == x && position[1] == y) {
                return entry.getKey();
            }
        }
        return null;
    }
    public Equipment getEquipmentAtPosition(int x, int y) {
        for (Map.Entry<Equipment, int[]> entry : _positions.getEquipmentPosition().entrySet()) {
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



}

