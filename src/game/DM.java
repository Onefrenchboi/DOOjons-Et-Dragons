package game;

import game.entities.*;
import game.entities.races.*;
import game.entities.classes.*;

import game.entities.Character;
import game.items.*;
import game.utils.Display;
import game.utils.Repo;
import game.utils.GameUtils;

import static game.utils.GameUtils.parsePosition;
import static game.utils.GameUtils.scanner;


import java.util.*;


public class DM {
    private Dungeon _dungeon;
    private List<Entity> _entitiesSortedByInitiative;
    private List<Equipment> _equipmentList;
    private Repo _equipmentRepo;
    private Entity _currentEntity;
    private int _turn;
    private boolean _winCondition;

    public DM() {
        _entitiesSortedByInitiative = new ArrayList<>();
        _equipmentList = new ArrayList<>();
        _equipmentRepo= new Repo();
        _turn = 1;
        _winCondition= true;
    }


    //? Functions to create everything
    public void createCharacters(){
        Display.display("How many players ? (1-4) : ");
        int nbPlayers = scanner.nextInt();
        while (nbPlayers < 1 || nbPlayers > 4) {
            Display.displayError("Invalid number of players, choose a number between 1 and 4 : ");
            nbPlayers = scanner.nextInt();
        }

        for (int i = 0; i < nbPlayers; i++) {
            Display.display("Player " + (i + 1) + ", enter your name : ");
            String name = scanner.next();

            Race heroRace = null;
            while (heroRace == null) {
                Display.display("Choose your race (1-Dwarf, 2-Elf, 3-Halfling, 4-Human) : ");
                int raceChoice = scanner.nextInt();
                switch (raceChoice) {
                    case 1 -> heroRace = new Dwarf();
                    case 2 -> heroRace = new Elf();
                    case 3 -> heroRace = new Halfling();
                    case 4 -> heroRace = new Human();
                    default -> Display.displayError("Invalid choice. Please try again.");
                }
            }

            CharacterClass heroClass = null;
            while (heroClass == null) {
                Display.display("Choose your class (1-Cleric, 2-Rogue, 3-Warrior, 4-Wizard) : ");
                int classChoice = scanner.nextInt();
                switch (classChoice) {
                    case 1 -> heroClass = new Cleric();
                    case 2 -> heroClass = new Rogue();
                    case 3 -> heroClass = new Warrior();
                    case 4 -> heroClass = new Wizard();
                    default -> Display.displayError("Invalid choice. Please try again.");
                }
            }

            Character hero = new Character(name, heroRace, heroClass);
            _entitiesSortedByInitiative.add(hero);
        }
    }
    public void createMonsters(){
        Scanner scanner = new Scanner(System.in);

        Display.display("DM, do you want to create monsters ? (if not, a predetermined selection will be applied) (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            Display.display("How many monsters ? (1-4) : ");
            int nbMonstres = scanner.nextInt();
            Map<String, Integer> monsterCount = new HashMap<>();
            while (nbMonstres < 1 || nbMonstres > 4) {
                Display.displayError("Invalid number of monsters, choose a number between 1 and 4 : ");
                nbMonstres = scanner.nextInt();
            }
            for (int i = 0; i < nbMonstres; i++) {
                Display.display("Enter the monsters race : ");
                String species = scanner.next();
                Display.display("Enter the monsters armor class : ");
                int AC = scanner.nextInt();
                Display.display("Enter the monster's attack range, number of dice, and dice value (e.g., 1 2 6 for range 1, 2d6 damage) : ");
                int range = scanner.nextInt();
                int dicenum = scanner.nextInt();
                int damageroll = scanner.nextInt();
                Weapon weapon;
                if (range == 1) {
                    weapon = new MeleeWeapon("MonsterMeleeAttack", range, dicenum ,damageroll);
                } else {
                    weapon = new RangedWeapon("MonsterRangedAttack", range, dicenum, damageroll);
                }
                int number = monsterCount.getOrDefault(species, 0) + 1;
                monsterCount.put(species, number);

                Monster monster = new Monster(species, number, weapon, AC);
                _entitiesSortedByInitiative.add(monster);
            }
        }
        else {
            System.out.println("Applying default monster selection");
            _entitiesSortedByInitiative.add(new Monster("Fragon", 1, new MeleeWeapon("MonsterMeleeAttack", 1, 1,6),10));
            _entitiesSortedByInitiative.add(new Monster("Fragon", 2, new MeleeWeapon("MonsterMeleeAttack", 1, 1,6),10));
            _entitiesSortedByInitiative.add(new Monster("Bob", 1, new RangedWeapon("MonsterRangedAttack", 12, 1,4),10));
        }
    }
    public void createEquipments(){
        Scanner scanner = new Scanner(System.in);
        Display.display("How many items ? (1-5) : ");
        int nbEquipment = scanner.nextInt();
        while (nbEquipment < 1 || nbEquipment > 5) {
            Display.displayError("Invalid number of items, choose a number between 1 and 5 : ");
            nbEquipment = scanner.nextInt();
        }

        Display.display("Here is the list of available items : ");
        for (Equipment equipment : _equipmentRepo.getEquipments()) {
            int n = _equipmentRepo.getEquipments().indexOf(equipment);
            Display.display("(" + n + ")" + equipment.toString());
        }
        for (int i = 0; i < nbEquipment; i++) {
            Display.display("Enter the number of the item to add : ");
            int equipment = scanner.nextInt();
            while (equipment < 0 || equipment > _equipmentRepo.getEquipments().size()) {
                Display.displayError("Invalid number, choose a number between 0 and " + (_equipmentRepo.getEquipments().size() - 1) + " : ");
                equipment = scanner.nextInt();
            }
            Equipment selectedEquipment = _equipmentRepo.getEquipments().get(equipment);
            _equipmentList.add(selectedEquipment);
        }
    }
    public void createDungeon(int number) {
        Scanner scanner = new Scanner(System.in);

        Display.display("DM, please enter the size of the dungeon ([W]idth [H]eight) : ");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        while (width < 15 || width > 26 || height < 15 || height > 26) {
            Display.displayError("The map must be between 15 and 26 squares wide and high");
            Display.display("Please enter the size of the dungeon ([W]idth [H}eight) : ");
            width = scanner.nextInt();
            height = scanner.nextInt();
        }
        _dungeon = new Dungeon(width, height, number);
    }

    public void setDungeon() {
        //On sort les monstres et personnages par initiative
        Map<Entity, Integer> initiativeMap = new HashMap<>();
        for (Entity entity : _entitiesSortedByInitiative) {
            initiativeMap.put(entity, entity.getInitiative() + GameUtils.roll(1, 20));
        }

        _entitiesSortedByInitiative.sort((e1, e2) -> initiativeMap.get(e2) - initiativeMap.get(e1));
        //et on les affiche
        Display.display("Here is the list of players and monsters in the game : ");
        for (Entity entity : _entitiesSortedByInitiative) {
            Display.display(entity.toString());
        }

        Display.display("DM, do you want to place the entities manually ? (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            for (Entity entity : _entitiesSortedByInitiative) {
                Display.display("Enter the position of " + entity.toString() + " ([A-Z]x) : ");
                String position = scanner.next();
                int[] pos = parsePosition(position);
                int x = pos[0];
                int y = pos[1];
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.displayError("Invalid position. Please enter a new position : ");
                    position = scanner.next();
                    pos = parsePosition(position);
                    x = pos[0];
                    y = pos[1];
                }
                _dungeon.addEntity(x, y, entity);
            }
        }
        else {
            //place les personnages aléatoirement
            _dungeon.randomSetEntity(_entitiesSortedByInitiative);
        }

        Display.display("DM, do you want to place the items manually ? (Y/N)");
        choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            for (Equipment equipment : _equipmentList) {
                Display.display("Enter the position of " + equipment + " ([A-Z]x) : ");
                String position = scanner.next();
                int[] pos = parsePosition(position);
                int x = pos[0];
                int y = pos[1];
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.displayError("Invalid position. Please enter a new position : ");
                    position = scanner.next();
                    pos = parsePosition(position);
                    x = pos[0];
                    y = pos[1];
                }
                _dungeon.addEquipment(x, y, equipment);
            }
        }
        else {
            _dungeon.randomSetEquipment(_equipmentList);
        }

        Display.display("DM, do you want to place the obstacles manually ? (Y/N)");
        choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            Display.display("How many ?");
            int nbObstacles = scanner.nextInt();
            for (int i = 0; i < nbObstacles; i++) {
                Display.display("Enter the position of obstacle " + (i + 1) + " ([A-Z]x) : ");
                String position = scanner.next();
                int[] pos = parsePosition(position);
                int x = pos[0];
                int y = pos[1];
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.displayError("Invalid position. Please enter a new position : ");
                    position = scanner.next();
                    pos = parsePosition(position);
                    x = pos[0];
                    y = pos[1];
                }
                _dungeon.addObstacle(x, y);
            }
        }
        else {//place les obstacles aléatoirement
            _dungeon.randomSetObstacles();
        }
    }


    public void showBoard() {
        _dungeon.updateMap();
    }

    public void createGame() {
        Display.display("----Setting up the game----");
        createCharacters();
        createMonsters();
        createEquipments();
        createDungeon(1);
        setDungeon();
        Display.display("----Start of the game----");
    }


    public void play(){
        int currentEntityIndex = 0;
        while (_winCondition){
            while (currentEntityIndex < _entitiesSortedByInitiative.size()) {
                turn(currentEntityIndex);
                currentEntityIndex++;
            }
            currentEntityIndex=0;
            _turn++;
        }


    }
    public void turn(int currentEntityNum){
        _currentEntity = _entitiesSortedByInitiative.get(currentEntityNum);
        Display.displayInfo(this);
        Display.displayMap(_dungeon);
        Display.displayEntityInfo(_currentEntity);
        for (int action = 3 ; action > 0; action--) {
            Display.displayActionMenu(_currentEntity, action);
            String choice = scanner.next();
            String actionChoice = scanner.next();
            Display.display("You chose: " + choice + " " + actionChoice);
            switch (choice) {
                case "att" -> attack(_currentEntity, actionChoice);
                case "equ" -> equip(_currentEntity);





                case "move" -> Display.display("muv");//move(_currentEntity, _currentEntity.getX() + 1, _currentEntity.getY());
                case "pick" -> Display.display("pikup");//pickUp(_currentEntity, _dungeon.getEquipmentAtPosition(_currentEntity.getX(), _currentEntity.getY()));

                case "com" -> Display.display("ekuip");//equip(_currentEntity);
                case "dm" -> Display.display("ekuip");//equip(_currentEntity);
                default -> Display.displayError("Invalid choice. Please try again.");
            }
        }

        Display.displayClear();
    }

    public void attack(Entity attacker, String pos) {
        int[] position = parsePosition(pos);
        int x = position[0];
        int y = position[1];

        Entity target = _dungeon.getEntityAtPosition(x, y);
        if (target == null) {
            Display.displayError("No entity at this position.");
            return;
        }

        if (!attacker.canAttack(target) && _dungeon.getPositions().canReach(attacker, position)) {
            Display.displayError("You cannot attack this entity.");
            return;
        }
        int attackRoll = GameUtils.roll(1, 20);
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
        } else {
            Display.displayError("You missed " + target.getName() + "!");
            return;
        }

        if (!target.isAlive()) {
            Display.display(target.getName() + " has been defeated!");
            _dungeon.getPosition().removeEntity(target); // Remove the target from the dungeon
        }

    }

    public void move(Entity entity, int x, int y) {
    }

    public void pickUp(Entity entity, Equipment equipment) {
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


    public List<Entity> getEntitiesSortedByInitiative() {
        return _entitiesSortedByInitiative;
    }

    public Dungeon getDungeon() {
        return _dungeon;
    }

    public int getTurn(){
        return _turn;
    }
    public Entity getCurrentEntity() {
        return _currentEntity;
    }
    public int getDungeonNumber() {
        return _dungeon.getDungeonNumber();
    }
}
