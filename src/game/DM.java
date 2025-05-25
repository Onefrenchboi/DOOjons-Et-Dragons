package game;

import game.entities.*;
import game.entities.races.*;
import game.entities.classes.*;

import game.entities.Character;
import game.items.*;
import game.utils.Display;
import game.utils.Repo;
import game.utils.Dice;
import static game.utils.Dice.scanner;


import java.util.*;


public class DM {
    private Dungeon _dungeon;
    private List<Entity> _entitiesSortedByInitiative;
    private List<Equipment> _equipmentList;
    private Repo _equipmentRepo;
    private Entity _currentEntity;

    public DM() {
        _entitiesSortedByInitiative = new ArrayList<>();
        _equipmentList = new ArrayList<>();
        _equipmentRepo= new Repo();
        _equipmentRepo.initializeEquipment();
    }


    //? Functions to create everything
    public void createCharacters(){
        Display.display("How many players ? (1-4) : ");
        int nbPlayers = scanner.nextInt();
        while (nbPlayers < 1 || nbPlayers > 4) {
            Display.display("Invalid number of players, choose a number between 1 and 4 : ");
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
                    default -> Display.display("Invalid choice. Please try again.");
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
                    default -> Display.display("Invalid choice. Please try again.");
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
                Display.display("Invalid number of monsters, choose a number between 1 and 4 : ");
                nbMonstres = scanner.nextInt();
            }
            for (int i = 0; i < nbMonstres; i++) {
                Display.display("Enter the monsters race: ");
                String species = scanner.next();
                //todo : make this more readable
                Display.display("Enter the monsters range, and damage (x y z) : ");
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

                Monster monster = new Monster(species, number, weapon);
                _entitiesSortedByInitiative.add(monster);
            }
        }
        else {
            System.out.println("Applying default monster selection");
            _entitiesSortedByInitiative.add(new Monster("Fragon", 1, new MeleeWeapon("MonsterMeleeAttack", 1, 1,6)));
            _entitiesSortedByInitiative.add(new Monster("Fragon", 2, new MeleeWeapon("MonsterMeleeAttack", 1, 1,6)));
            _entitiesSortedByInitiative.add(new Monster("Bob", 1, new RangedWeapon("MonsterRangedAttack", 12, 1,4)));
        }
    }
    public void createEquipments(){
        Scanner scanner = new Scanner(System.in);
        Display.display("How many items ? (1-5) : ");
        int nbEquipment = scanner.nextInt();
        while (nbEquipment < 1 || nbEquipment > 5) {
            Display.display("Invalid number of items, choose a number between 1 and 5 : ");
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
                Display.display("Invalid number, choose a number between 0 and " + (_equipmentRepo.getEquipments().size() - 1) + " : ");
                equipment = scanner.nextInt();
            }
            Equipment selectedEquipment = _equipmentRepo.getEquipments().get(equipment);
            _equipmentList.add(selectedEquipment);
        }
    }
    public void createDungeon() {
        Scanner scanner = new Scanner(System.in);

        Display.display("DM, please enter the size of the dungeon ([W]idth [H]eight) : ");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        while (width < 15 || width > 26 || height < 15 || height > 26) {
            Display.display("The map must be between 15 and 26 squares wide and high");
            Display.display("Please enter the size of the dungeon ([W]idth [H}eight) : ");
            width = scanner.nextInt();
            height = scanner.nextInt();
        }
        _dungeon = new Dungeon(width, height);
    }

    public void setDungeon() {
        //On sort les monstres et personnages par initiative
        Map<Entity, Integer> initiativeMap = new HashMap<>();
        for (Entity entity : _entitiesSortedByInitiative) {
            initiativeMap.put(entity, entity.getInitiative() + Dice.roll(1, 20));
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
                Display.display("Enter the position of " + entity.toString() + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.display("Invalid position. Please enter a new position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
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
                Display.display("Enter the position of " + equipment + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.display("Invalid position. Please enter a new position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
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
                Display.display("Enter the position of obstacle " + (i + 1) + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.display("Invalid position. Please enter a new position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                }
                _dungeon.addObstacle(x, y);
            }
        }
        else {//place les obstacles aléatoirement
            _dungeon.randomSetObstacles();
        }
    }


    public void showBoard() {
        _dungeon.displayMap();
    }



    public void attack(Entity attacker, Entity target) {

    }

    public void move(Entity entity, int x, int y) {
    }

    public void pickUp(Entity entity, Equipment equipment) {
    }

    public void equip(Entity entity) {

    }


    public List<Entity> getEntitiesSortedByInitiative() {
        return _entitiesSortedByInitiative;
    }

    public Dungeon getDungeon() {
        return _dungeon;
    }


}
