package game;

import game.entities.*;
import game.entities.races.*;
import game.entities.classes.*;

import game.entities.Character;
import game.items.*;
import game.items.equipments.MeleeWeapon;
import game.items.equipments.RangedWeapon;
import game.utils.Display;
import game.utils.EquipmentRepository;
import game.utils.GameUtils;


import java.util.*;

import static game.utils.GameUtils.*;


public class DM {
        private Dungeon _dungeon;
        private List<Entity> _entitiesSortedByInitiative;
        private List<Equipment> _equipmentList;
        private Entity _currentEntity;
        private int _turn;
        private boolean _winCondition;

    public DM() {
        _entitiesSortedByInitiative = new ArrayList<>();
        _equipmentList = new ArrayList<>();
        _winCondition= false;
    }


    //? Functions to create everything

    /**
     * Creates characters, monsters, equipment based on user input.
     * Demande a l'user des precisions (nom, race, classe, AC (pour monstres) et autres
     *
     */
    public void createCharacters() {
        int nbPlayers = askValidInt("How many players ? (1-4) : ", 1, 4);

        String name = "Z";

        Race heroRace = null;
        CharacterClass heroClass = null;
        for (int i = 0; i < nbPlayers; i++) {
            heroRace = null;
            heroClass = null;
            Display.display("Player " + (i + 1) + ", enter your name : ");
            name = scanner.next();

            while (heroRace == null) {
                int raceChoice = askValidInt("Choose your race (1-Dwarf, 2-Elf, 3-Halfling, 4-Human): ", 1, 4);
                switch (raceChoice) {
                    case 1 -> heroRace = new Dwarf();
                    case 2 -> heroRace = new Elf();
                    case 3 -> heroRace = new Halfling();
                    case 4 -> heroRace = new Human();
                    default -> Display.displayError("Invalid choice. Please enter a number between 1 and 4.");
                };
            }

            while (heroClass == null) {
                int raceChoice = askValidInt("Choose your class (1-Cleric, 2-Rogue, 3-Warrior, 4-Wizard) : ", 1, 4);
                switch (raceChoice) {
                    case 1 -> heroClass = new Cleric();
                    case 2 -> heroClass = new Rogue();
                    case 3 -> heroClass = new Warrior();
                    case 4 -> heroClass = new Wizard();
                    default -> Display.displayError("Invalid choice. Please enter a number between 1 and 4.");
                }
            }
            Character hero = new Character(name, heroRace, heroClass);
            _entitiesSortedByInitiative.add(hero);
        }
    }
    public void createMonsters(){
        Display.display("DM, do you want to create monsters ? (if not, a predetermined selection will be applied) (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            int nbMonstres = askValidInt("How many monsters ? (1-4) : ", 1, 4);

            Map<String, Integer> monsterCount = new HashMap<>();
            for (int i = 0; i < nbMonstres; i++) {
                Display.display("Enter the monster's race : ");
                String species = scanner.next();
                int AC = -1;
                while (AC < 0) {
                    AC = askValidInt("Enter the monster's armor class : ",0, 99);
                }
                int range = -1, dicenum = -1, damageroll = -1;
                boolean validAttackInput = false;
                while (!validAttackInput) {
                    Display.display("Enter the monster's attack range, number of dice, and dice value (e.g., 1 2 6 for range 1, 2d6 damage) : ");
                    try {
                        range = scanner.nextInt();
                        dicenum = scanner.nextInt();
                        damageroll = scanner.nextInt();
                        if (range < 1 || dicenum < 1 || damageroll < 1) {
                            Display.displayError("All values must be positive numbers.");
                        } else {
                            validAttackInput = true;
                        }
                    } catch (InputMismatchException e) {
                        Display.displayError("Invalid input, please enter three numbers.");
                        scanner.nextLine(); //pr clear le mauvais input
                    }
                }
                Weapon weapon;
                if (range == 1) {
                    weapon = new MeleeWeapon("MonsterMeleeAttack", range, dicenum, damageroll);
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
    public void createEquipments() {
        int nbEquipment = askValidInt("How many items? (1-5): ", 1, 5);
        Display.display("Here is the list of available items:");

        List<Equipment> allEquipment = EquipmentRepository.getAllEquipment();
        for (int i = 0; i < allEquipment.size(); i++) {
            Display.display("(" + i + ") " + allEquipment.get(i).toString());
        }
        for (int i = 0; i < nbEquipment; i++) {
            int equipmentIndex = askValidInt("Enter the number of the item to add:", 0, allEquipment.size() - 1);
            Equipment selectedEquipment = allEquipment.get(equipmentIndex);
            _equipmentList.add(selectedEquipment);
        }
    }

    /**
     * Creates a dungeon with a specified width and height.
     * The dungeon number is passed as an argument to the Dungeon constructor.
     *
     * @param number the dungeon number
     */
    public void createDungeon(int number) {
        int width = askValidInt("DM, please enter dungeon width (15-26): ", 15, 26);
        int height = askValidInt("DM, please enter dungeon height (15-26): ", 15, 26);
        _dungeon = new Dungeon(width, height, number);
    }


    /**
     * Sets up the dungeon by placing entities, equipment, and obstacles.<br>
     * sorts entities by initiative and allows the DM to place them manually or randomly.
     * Also allows the DM to place items and obstacles manually or randomly.
     */
    public void setDungeon() {
        _turn = 1;
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
                int[] pos = askValidPosition("Enter the position of " + entity.getName() + " ([A-Z]x) : ", _dungeon);
                int x = pos[0];
                int y = pos[1];
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
                int[] pos = askValidPosition("Enter the position of " + equipment + " ([A-Z]x) : ", _dungeon);
                int x = pos[0];
                int y = pos[1];
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
                int[] pos = askValidPosition("Enter the position of obstacle " + (i + 1) + " ([A-Z]x) : ", _dungeon);
                int x = pos[0];
                int y = pos[1];
                _dungeon.addObstacle(x, y);
            }
        }
        else {//place les obstacles aléatoirement
            _dungeon.randomSetObstacles();
        }
        for (Entity entity : _entitiesSortedByInitiative) {
            if (entity.isPlayer()) {
                Display.display(entity.getName() + ", choose an item to equip from your inventory : ");
                String inventory = entity.displayInventory();
                if (inventory.equals("Inventory is empty.")) {
                    Display.displayError("You have no items to equip.");
                    return;
                }
                Display.display(inventory);
                int choice2 = scanner.nextInt();
                while (choice2 < 0 || choice2 >= entity.getInventory().size()) {
                    Display.displayError("Invalid choice. Please choose a valid item number.");
                    choice2 = scanner.nextInt();
                }
                Equipment equipment = entity.getInventory().get(choice2);
                if (equipment.isArmor()) {
                    entity.equipArmor(equipment);
                    Display.display("You equipped " + equipment.getName() + ".");
                } else if (equipment.isWeapon()) {
                    entity.equipWeapon(equipment);
                    Display.display("You equipped " + equipment.getName() + ".");
                }

            }
        }
    }


    /**
     * Creates the game by calling all above methods.<br>
     * Displays the start of the game message.<br>
     * Note : Only used one time, for the other dungeons we use nextDungeon
     */
    public void createGame() {
        Display.display("----Setting up the game----");
        createCharacters();
        createMonsters();
        createEquipments();
        createDungeon(1);
        setDungeon();
        Display.display("----Start of the game----");
    }

    /**
     * Recreates a new dungeon with only the players from the last
     * Heals players
     * Checks if its the last dungeon
     * <br>
     * Note : the list of entities is cleared of all monsters before calling this
     *
     */
    private void nextDungeon(List<Entity> entitiesSortedByInitiative) {
        int newDungeonNumber = _dungeon.getNumber()+1;
        if (newDungeonNumber > 3) {
            return;
        }
        Display.display("You managed to kill all the monsters on this floor, Well done !");
        Display.display("Healing players...");
        for (Entity entity : entitiesSortedByInitiative) {
            if (entity.isPlayer()) {
                entity.setHp(entity.getMaxHp() - entity.getHp());
            }
        }
        Display.display("All players healed :D");
        Display.display("And now....");
        Display.display("----Onto the next Dungeon !----");
        createMonsters();
        createEquipments();
        createDungeon(newDungeonNumber);
        setDungeon();
    }


    /**
     * Starts the game loop. <br>
     * Runs until the dungeon is the 3rd and all monsters are dead (i just realised WinCondition might not even be useful-)
     * call turn on each entity, check at the end of each turn if every monster OR player is dead, react accordingly<br>
     * if won, display the end
     *<br>
     * Note : endGame is separated, see endGame for why
     */
    public void play() {
        while (!_winCondition && _dungeon.getNumber() <= 3) {
            for (Entity entity : _entitiesSortedByInitiative) {
                _currentEntity = entity;
                turn(_entitiesSortedByInitiative.indexOf(_currentEntity));
            }
            _turn++;
            if (allMonstersDead()) {
                if (_dungeon.getNumber() < 3) {
                    nextDungeon(_entitiesSortedByInitiative);
                } else {
                    _winCondition = true;
                }
            }
        }
        if (_winCondition) {
            Display.displayLore("You and your party forced back the darkness,\n" +
                    "but the shadows still writhe beyond the edge of the light.\n" +
                    "For now, the Depths are silent—but the true threat stirs deeper below,\n" +
                    "waiting for the moment to rise again and engulf the world in eternal night.\n" +
                    "Your victory is but a single spark in a gathering storm.\n" +
                    "Prepare yourselves, for the battle to save the kingdom has only just begun. \n");
            Display.displaySuccess("Congratulations, you have completed the game!");
        }
    }

    /**
     * Ends the game when all players are dead.<br>
     * <br>
     * Note : Je passe en francais carrement. En gros j'ai eu des problemes pour finir le jeu, avec des return dans ma fonction tour, donc j'ai décidé de faire une methode gameEnd qui exit le programme direct pour faire plus simple
     */
    public void gameEnd(){
        Display.displayLore("Overwhelmed by the forces of the Demon King, you fell in battle.\n" +
                "The shadows gain ground, and the future of the kingdom seems bleak...\n");
        Display.displayError("You just lost the Game.");
        System.exit(0);
    }


    /**
     * Handles the turn of the current entity<br>
     * Checks if it a dead player (if so, skip his turn since he isnt removed from the list like dead monsters)
     * if not, repeat 3 times (3 actions) a switch case that asks the user what they want to do.<br>
     * Afterwards, checks if the DM wants to intervene, and if so, calls dmActions<br>
     * Checks if all monsters are dead, if so, ends the game<br>
     * Checks if all players are dead, if so, calls gameEnd
     * <br>
     * Note : Technically, everyone can do what they want, BUT, we only print what they can do
     *
     */
    public void turn(int currentEntityNum) {
        while (currentEntityNum < _entitiesSortedByInitiative.size()) {
            _currentEntity = _entitiesSortedByInitiative.get(currentEntityNum);
            Display.displayInfo(this);

            if (!_currentEntity.isAlive() && _currentEntity.isPlayer()) {
                Display.display("This player is dead. Skipping their turn...");
                currentEntityNum++;
                continue;
            }

            for (int action = 3; action > 0;) {
                Display.displayMap(_dungeon);
                Display.displayEntityInfo(_currentEntity);

                Display.displayActionMenu(_currentEntity, action);
                String choice = scanner.next();
                Display.display("You chose: " + choice);
                switch (choice) {
                    case "att" -> {
                        String actionChoice = scanner.next();
                        _dungeon.attack(_currentEntity, actionChoice);
                        action--;
                        scanner.nextLine();
                    }
                    case "equ" -> {
                        _dungeon.equip(_currentEntity);
                        action--;
                        scanner.nextLine();
                    }
                    case "move" -> {
                        String actionChoice = scanner.next();
                        _dungeon.move(_currentEntity, actionChoice);
                        action--;
                        scanner.nextLine();
                    }
                    case "pick" -> {
                        String actionChoice = scanner.next();
                        _dungeon.pickUp(_currentEntity, actionChoice);
                        action--;
                        scanner.nextLine();
                    }
                    case "com" -> {
                        String actionChoice = scanner.nextLine();
                        _dungeon.comment(_currentEntity, actionChoice);
                    }
                    case "spell" -> {
                        _dungeon.castSpell(_currentEntity);
                        action--;
                    }
                    case "skip" -> {
                        Display.display("... ok ?");
                        action--;
                    }
                    default -> Display.displayError("Invalid choice. Please try again.");
                }

                Display.display("Press Any Key to continue...");
                scanner.nextLine();

                Display.display("Dm, would you like to intervene ? (Y/N)");
                String dmChoice = scanner.nextLine();
                if (dmChoice.equalsIgnoreCase("Y")) {
                    dmActions();
                }

                _entitiesSortedByInitiative.removeIf(entity -> entity.getHp() <= 0 && entity.isMonster());

                if (allMonstersDead()) {
                    return;
                }

                if (allPlayersDead()) {
                    gameEnd();
                }

                if (!_currentEntity.isAlive()) {
                    Display.display("The current entity is dead. Skipping to the next turn...");
                    break;
                }
            }

            Display.displayClear();
            currentEntityNum++;
        }
    }



    /**
     * Handles the DM actions during the game.<br>
     * Displays a menu with options for the DM to choose from.<br>
     *
     * <br>
     * Note : recursive, so it will keep asking for actions until the DM chooses to stop.<br>
     */
    private void dmActions(){
        Display.displayDmActions();
        String choice = scanner.next();
        switch (choice){
            case "com" -> {
                String comment = scanner.nextLine();
                Display.display("The DM says : " + comment);
            }
            case "move" -> {
                String entityPos = scanner.next();
                String newPos = scanner.next();
                int[] entitypos = parsePosition(entityPos);
                int[] newpos = parsePosition(newPos);
                int x = entitypos[0];
                int y = entitypos[1];
                Entity entity = _dungeon.getEntityAtPosition(x,y);
                x = newpos[0];
                y = newpos[1];
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.displayError("Invalid position. Please enter a new position : ");
                    String position = scanner.nextLine();
                    newpos = parsePosition(position);
                    x = newpos[0];
                    y = newpos[1];
                }
                _dungeon.moveEntity(entity, x, y);

            }
            case "add" -> {
                String position = scanner.nextLine();
                String[] parts = position.trim().split("\\s+");
                String posStr = parts[parts.length - 1];
                int[] pos = parsePosition(posStr);
                int x = pos[0];
                int y = pos[1];
                while (!_dungeon.isValidPosition(x, y)) {
                    Display.displayError("Invalid position. Please enter a new position : ");
                    position = scanner.nextLine();
                    pos = parsePosition(position);
                    x = pos[0];
                    y = pos[1];
                }
                _dungeon.addObstacle(x, y);
                Display.display("Added obstacle successfully.");
            }
            case "hurt" -> {
                String target = scanner.next();
                int dices = scanner.nextInt();
                int faces = scanner.nextInt();
                _dungeon.hurtEntity(target, dices, faces);
            }
            case "display" -> {
                Display.displayMap(_dungeon);
            }
            case "stop" -> {
                return;
            }
        }
        dmActions();
    }



    /**
     * Checks if all players are dead and returns true if so, false otherwise.<br>
     * Checks if all monsters are dead and returns true if so, false otherwise.<br>
     * Used to end the game when all players are dead or when all monsters are dead.
     */
    private boolean allPlayersDead() {
        for (Entity entity : _entitiesSortedByInitiative) {
            if (entity.isPlayer() && entity.getHp() > 0) {
                return false;
            }
        }
        return true;
    }
    private boolean allMonstersDead() {
        for (Entity entity : _entitiesSortedByInitiative) {
            if (entity.isMonster()) {
                return false;
            }
        }
        return true;
    }





    //? Getters
    public List<Entity> getEntitiesSortedByInitiative() {
        return _entitiesSortedByInitiative;
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
