package game;

import game.entities.*;
import game.entities.races.*;
import game.entities.classes.*;

import game.entities.Character;
import game.items.*;
import game.items.EquipmentType;
import game.items.equipments.MeleeWeapon;
import game.items.equipments.RangedWeapon;
import game.utils.ActionResult;
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
            name = scanner.nextLine().trim();

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
        boolean choice = askYesOrNoAnswer("DM, do you want to create monsters ? (if not, a predetermined selection will be applied) (Y/N)");
        if (choice) {
            int nbMonstres = askValidInt("How many monsters ? (1-4) : ", 1, 4);

            Map<String, Integer> monsterCount = new HashMap<>();
            for (int i = 0; i < nbMonstres; i++) {
                Display.display("Enter the monster's race : ");
                String species = scanner.nextLine().trim();
                int AC = -1;
                while (AC < 0) {
                    AC = askValidInt("Enter the monster's armor class : ",0, 99);
                }
                int range = -1, dicenum = -1, damageroll = -1;
                boolean validAttackInput = false;

                while (!validAttackInput) {
                    Display.display("Enter the monster's attack range, number of dice, and dice value (e.g., 1 2 6 for range 1, 2d6 damage) : ");
                    String line = scanner.nextLine().trim();
                    String[] tokens = line.split("\\s+");

                    if (tokens.length != 3) {
                        Display.displayError("Please enter exactly three numbers separated by spaces.");
                        continue;
                    }

                    try {
                        range = Integer.parseInt(tokens[0]);
                        dicenum = Integer.parseInt(tokens[1]);
                        damageroll = Integer.parseInt(tokens[2]);

                        if (range < 1 || dicenum < 1 || damageroll < 1) {
                            Display.displayError("All values must be positive numbers.");
                        } else {
                            validAttackInput = true;
                        }
                    } catch (NumberFormatException e) {
                        Display.displayError("Invalid input, please enter valid numbers.");
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
        // On sort les monstres et personnages par initiative
        Map<Entity, Integer> initiativeMap = new HashMap<>();
        for (Entity entity : _entitiesSortedByInitiative) {
            initiativeMap.put(entity, entity.getInitiative() + GameUtils.roll(1, 20));
        }

        _entitiesSortedByInitiative.sort((e1, e2) -> initiativeMap.get(e2) - initiativeMap.get(e1));
        // et on les affiche
        Display.display("Here is the list of players and monsters in the game : ");
        for (Entity entity : _entitiesSortedByInitiative) {
            Display.display(entity.toString());
        }

        boolean choice = askYesOrNoAnswer("DM, do you want to place the entities manually ? (Y/N)");
        if (choice) {
            for (Entity entity : _entitiesSortedByInitiative) {
                int[] pos = askValidPosition("Enter the position of " + entity.getName() + " ([A-Z]x) : ", _dungeon);
                _dungeon.addEntity(entity, pos);
            }
        } else {
            // place les personnages aléatoirement
            _dungeon.randomlyAddEntity(_entitiesSortedByInitiative);
        }

        choice = askYesOrNoAnswer("DM, do you want to place the items manually ? (Y/N)");
        if (choice) {
            for (Equipment equipment : _equipmentList) {
                int[] pos = askValidPosition("Enter the position of " + equipment + " ([A-Z]x) : ", _dungeon);
                _dungeon.addEquipment(equipment, pos);
            }
        } else {
            _dungeon.randomlyAddEquipment(_equipmentList);
        }

        choice = askYesOrNoAnswer("DM, do you want to place the obstacles manually ? (Y/N)");
        if (choice) {
            int nbObstacles = askValidInt("How many (1-15) ?", 1, 15);
            for (int i = 0; i < nbObstacles; i++) {
                int[] pos = askValidPosition("Enter the position of obstacle " + (i + 1) + " ([A-Z]x) : ", _dungeon);
                int x = pos[0];
                int y = pos[1];
                _dungeon.addObstacle(x, y);
            }
        } else {
            // place les obstacles aléatoirement
            _dungeon.randomlyAddObstacles();
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
        //equip entities
        for (Entity entity : _entitiesSortedByInitiative) {
            if (entity.getType()==EntityType.PLAYER) {
                Display.display(entity.getName() + ", choose an item to equip from your inventory : ");
                String inventory = entity.displayInventory();
                if (inventory.equals("Inventory is empty.")) {
                    Display.displayError("You have no items to equip.");
                    return;
                }
                Display.display(inventory);

                int choice2 = askValidInt("Choose an item to equip",0,entity.getInventory().size());
                Equipment equipment = entity.getInventory().get(choice2);
                if (equipment.getType() == EquipmentType.ARMOR) {
                    entity.equipArmor(equipment);
                    Display.display("You equipped " + equipment.getName() + ".");
                } else if (equipment.getType()== EquipmentType.WEAPON) {
                    entity.equipWeapon(equipment);
                    Display.display("You equipped " + equipment.getName() + ".");
                }

            }
        }
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
            if (entity.getType()==EntityType.PLAYER) {
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
            turn();
            _turn++;
            if (checkIfAllMonstersDead()) {
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
    public void turn() {
        int currentEntityNum = 0;
        while (currentEntityNum < _entitiesSortedByInitiative.size()) {
            _currentEntity = _entitiesSortedByInitiative.get(currentEntityNum);
            Display.displayInfo(this);

            for (int action = 3; action > 0;) {
                Display.displayMap(_dungeon);
                Display.displayEntityInfo(_currentEntity);

                Display.displayActionMenu(_currentEntity, action);
                String choice = scanner.next();
                scanner.nextLine(); //pour clear
                Display.display("You chose: " + choice);
                switch (choice) {
                    case "att" -> {
                        int[] position = askPositionInBound("Enter the position of the entity to attack ? ([A-Z]x) : ", _dungeon);
                        int x = position[0];
                        int y = position[1];
                        ActionResult result = _dungeon.attack(_currentEntity, x, y);

                        switch (result) {
                            case NO_TARGET -> Display.displayError("No entity at this position.");
                            case POSITION_TOO_FAR -> Display.displayError("Target is too far.");
                            case TARGET_MISSED -> Display.displayError("You missed.");
                            case WRONG_TYPE -> Display.displayError("You cannot attack this entity.");
                            case TARGET_HIT -> Display.displaySuccess("");
                            case TARGET_KILLED -> Display.displaySuccess("Target has been defeated!");
                        }
                        action--;
                        scanner.nextLine();
                    }
                    case "equ" -> {
                        _currentEntity.equip();
                        action--;
                        scanner.nextLine();
                    }
                    case "move" -> {
                        int[] position = askValidPosition("Where do you want to move ? ([A-Z]x) : ", _dungeon);
                        int x = position[0];
                        int y = position[1];
                        ActionResult result = _dungeon.move(_currentEntity, x,y);

                        switch (result) {
                            case POSITION_BLOCKED -> Display.displayError("There is already something here.");
                            case POSITION_TOO_FAR -> Display.displayError("Too far.");
                            case SUCCESS -> Display.displaySuccess("Moved successfully.");
                            case FAILURE -> Display.displayError("Couldn't move.");
                        }
                        action--;
                        scanner.nextLine();
                    }
                    case "pick" -> {
                        int[] position = askPositionInBound("Enter the position of the item to pick up ? ([A-Z]x) : ", _dungeon);
                        int x = position[0];
                        int y = position[1];
                        ActionResult result = _dungeon.pickUp(_currentEntity, x, y);

                        switch (result) {
                            case POSITION_EMPTY -> Display.displayError("No equipment at this position.");
                            case WRONG_TYPE -> Display.displayError("You cannot pick up this equipment.");
                            case POSITION_TOO_FAR -> Display.displayError("Too far.");
                            case SUCCESS -> Display.displaySuccess("Successfully picked up the equipment.");
                        }
                        action--;
                        scanner.nextLine();
                    }
                    case "com" -> {
                        Display.display("What do you want to say ? :");
                        String actionChoice = scanner.nextLine();
                        Display.display(_dungeon.comment(_currentEntity, actionChoice));
                    }
                    case "spell" -> {
                        ActionResult result = _dungeon.castSpell(_currentEntity);

                        switch (result){
                            case SUCCESS -> {
                                Display.displaySuccess("Successfully cast a spell");
                                action--;
                            }
                            case FAILURE -> Display.displayError("Failed to cast a spell");
                            case WRONG_TYPE -> Display.displayError("Monsters cannot cast spells");
                            case STOP -> Display.displayError("Stopped casting.");
                            case UNKNOWN_SPELL -> Display.displayError("You dont know this spell.");
                        }
                    }
                    case "skip" -> {
                        Display.display("... ok ?");
                        action--;
                    }
                    default -> Display.displayError("Invalid choice. Please try again.");
                }

                Display.display("Press Any Key to continue...");
                scanner.nextLine();


                boolean dmChoice = askYesOrNoAnswer("Dm, would you like to intervene ? (Y/N)");
                if (dmChoice) {
                    dmActions();
                }

                _entitiesSortedByInitiative.removeIf(entity -> entity.getHp() <= 0 && entity.getType()==EntityType.MONSTER);

                if (checkIfAllMonstersDead()) {
                    scanner.nextLine();
                    return;
                }

                if (isAnyPlayerDead()) {
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
    private void dmActions() {
        while (true) {
            Display.displayDmActions();
            String choice = scanner.next();
            switch (choice) {
                case "com" -> {
                    scanner.nextLine();
                    String comment = scanner.nextLine();
                    Display.display("The DM says: " + comment);
                }
                case "move" -> {
                    scanner.nextLine();
                    int[] entityPos = askPositionInBound("Enter the position of the entity to move ([A-Z]x): ", _dungeon);
                    int x = entityPos[0];
                    int y = entityPos[1];
                    int[] newPos = askValidPosition("Enter the new position of the entity ([A-Z]x): ", _dungeon);
                    Entity entity = _dungeon.getEntityAtPosition(x, y);
                    if (entity == null) {
                        Display.displayError("No entity found at the specified position.");
                        continue;
                    }
                    ActionResult result = _dungeon.moveEntity(entity, newPos[0], newPos[1]);
                    switch (result) {
                        case SUCCESS -> Display.displaySuccess("Successfully moved the entity.");
                        case FAILURE -> Display.displayError("Failed to move the entity.");
                    }
                }
                case "add" -> {
                    scanner.nextLine();
                    int[] pos = askValidPosition("Enter the position to add an obstacle ([A-Z]x): ", _dungeon);
                    _dungeon.addObstacle(pos[0], pos[1]);
                    Display.display("Added obstacle successfully.");
                }
                case "hurt" -> {
                    String input = scanner.nextLine().trim();
                    String[] parts = input.split("\\s+");
                    if (parts.length < 3) {
                        Display.displayError("Invalid input. Usage: hurt <name> <dices> <faces>");
                        continue;
                    }
                    String target = parts[0];
                    String diceStr = parts[1];
                    String faceStr = parts[2];

                    int dices, faces;
                    try {
                        dices = Integer.parseInt(diceStr);
                        faces = Integer.parseInt(faceStr);
                        if (dices <= 0 || faces <= 0) {
                            Display.displayError("Number of dice and faces must be positive numbers.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        Display.displayError("Invalid number of dice or faces. Usage: hurt <name> <dices> <faces>");
                        continue;
                    }

                    ActionResult result = _dungeon.hurtEntity(target, dices, faces);
                    switch (result) {
                        case NO_TARGET -> Display.displayError("No entity at this position.");
                        case TARGET_KILLED -> Display.display("Killed the entity.");
                        case TARGET_HIT -> Display.display("Successfully hit the entity.");
                    }
                }
                case "display" -> {
                    Display.displayMap(_dungeon);
                }
                case "stop" -> {
                    return;
                }
                default -> Display.displayError("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Checks if all players are dead and returns true if so, false otherwise.<br>
     * Checks if all monsters are dead and returns true if so, false otherwise.<br>
     * Used to end the game when all players are dead or when all monsters are dead.
     */
    private boolean isAnyPlayerDead() {
        for (Entity entity : _entitiesSortedByInitiative) {
            if (entity.getType()==EntityType.PLAYER && entity.getHp() <= 0) {
                return true;
            }
        }
        return false;
    }
    private boolean checkIfAllMonstersDead() {
        for (Entity entity : _entitiesSortedByInitiative) {
            if (entity.getType()==EntityType.MONSTER) {
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
