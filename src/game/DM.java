package game;

import game.entities.*;
import game.entities.races.*;
import game.entities.classes.*;

import game.entities.Character;
import game.items.MeleeWeapon;
import game.items.RangedWeapon;
import game.items.Weapon;
import game.utils.Dice;

import java.util.*;

public class DM {
    private Dungeon _dungeon;
    private HashMap<Entity, int[]> _entitiesPosition;
    private List<Entity> _entitiesSortedByInitiative;

    public DM() {
        _entitiesPosition = new HashMap<>();
        _entitiesSortedByInitiative = new ArrayList<>();
    }

    public void addEntity(Entity entity, int x, int y) { //pour placer les entités
        if (_dungeon.isValidPosition(x, y)) {
            _entitiesPosition.put(entity, new int[]{x, y});
        } else {
            System.out.println("Invalid position");
        }
    }

    public void removeEntity(Entity entity) { //pour les tuer >:D
        _entitiesPosition.remove(entity);
    }


    public HashMap<Entity, int[]> getEntitiesPosition() {
        return _entitiesPosition;
    }

    public void createEntities(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Combien de joueurs ? (1-4) : ");
        int nbPlayers = scanner.nextInt();

        while (nbPlayers < 1 || nbPlayers > 4) {
            System.out.println("Nombre de joueurs invalide. Veuillez entrer un nombre entre 1 et 4 : ");
            nbPlayers = scanner.nextInt();
        }

        for (int i = 0; i < nbPlayers; i++) {
            System.out.println("Joueur " + (i + 1) + ", entrez votre nom : ");
            String name = scanner.next();

            Race heroRace = null;
            while (heroRace == null) {
                System.out.println("Choisissez votre Race (1-Dwarf, 2-Elf, 3-Halfling, 4-Human) : ");
                int raceChoice = scanner.nextInt();
                switch (raceChoice) {
                    case 1 -> heroRace = new Dwarf();
                    case 2 -> heroRace = new Elf();
                    case 3 -> heroRace = new Halfling();
                    case 4 -> heroRace = new Human();
                    default -> System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }

            CharacterClass heroClass = null;
            while (heroClass == null) {
                System.out.println("Choisissez votre Classe (1-Cleric, 2-Rogue, 3-Warrior, 4-Wizard) : ");
                int classChoice = scanner.nextInt();
                switch (classChoice) {
                    case 1 -> heroClass = new Cleric();
                    case 2 -> heroClass = new Rogue();
                    case 3 -> heroClass = new Warrior();
                    case 4 -> heroClass = new Wizard();
                    default -> System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }

            Character hero = new Character(name, heroRace, heroClass);
            _entitiesSortedByInitiative.add(hero);
        }

        System.out.println("Combien de monstres ? (1-4) : ");
        int nbMonstres = scanner.nextInt();
        Map<String, Integer> monsterCount = new HashMap<>();
        while (nbMonstres < 1 || nbMonstres > 4) {
            System.out.println("Nombre de monstres invalide. Veuillez entrer un nombre entre 1 et 4 : ");
            nbMonstres = scanner.nextInt();
        }
        for (int i = 0; i < nbMonstres; i++) {
            System.out.println("Entrez la race du monstre : ");
            String species = scanner.next();
            System.out.println("Entrez la distance à laquelle le monstre peut attaquer et les dégâts : ");
            int range = scanner.nextInt();
            int damageroll= scanner.nextInt();
            Weapon weapon;
            if (range == 1) {
                weapon = new MeleeWeapon("MonsterMeleeAttack", range, damageroll);
            } else {
                weapon = new RangedWeapon("MonsterRangedAttack", range, damageroll);
            }

            // Determine the monster number
            int number = monsterCount.getOrDefault(species, 0) + 1;
            monsterCount.put(species, number);

            Monster monster = new Monster(species, number, weapon);
            _entitiesSortedByInitiative.add(monster);
        }



//todo : creer les monstres

        //sort les personnages/monstres par initiative
        _entitiesSortedByInitiative.sort((e1, e2) -> e2.getStats().getInitiative() - e1.getStats().getInitiative());


        //show all mosnter and player
        System.out.println("Voici la liste des personnages et monstres : ");
        for (Entity entity : _entitiesSortedByInitiative) {
            System.out.println(entity + " : " + entity.getStats().getInitiative());
        }

    }


    public void createDungeon(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("DM, quelle taille pour le donjon ? ([L]argeur [H]auteur) : ");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        while (width < 15 || width > 26 || height < 15 || height > 26) {
            System.out.println("La carte doit faire entre 15 et 26 cases de large et de haut.");
            System.out.println("Veuillez entrer une nouvelle taille : ");
            width = scanner.nextInt();
            height = scanner.nextInt();
        }
        _dungeon= new Dungeon(width, height);

        System.out.println("DM, veux-tu placer les personnages/monstres sur la carte ? (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            for (Entity entity : _entitiesSortedByInitiative) {
                System.out.println("Entrez la position de " + entity + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y)) {
                    System.out.println("Position invalide. Veuillez entrer une nouvelle position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                }
                addEntity(entity, x, y);
            }
        } else {
            //place les personnages aléatoirement
            for (Entity entity : _entitiesSortedByInitiative) {
                int x = Dice.roll(1, _dungeon.getSize()[0] - 2);
                int y = Dice.roll(1, _dungeon.getSize()[1] - 1);
                while (!_dungeon.isValidPosition(x, y)) {
                    x = Dice.roll(1, _dungeon.getSize()[0] - 2);
                    y = Dice.roll(1, _dungeon.getSize()[1] - 1);
                }
                addEntity(entity, x, y);
            }
        }

        System.out.println("DM, veux-tu placer les obstacles sur la carte ? (Y/N)");
        choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Combien ?");
            int nbObstacles = scanner.nextInt();
            for (int i = 0; i < nbObstacles; i++) {
                System.out.println("Entrez la position de l'obstacle " + (i + 1) + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y)) {
                    System.out.println("Position invalide. Veuillez entrer une nouvelle position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                }
                _dungeon.addObstacle(x, y);
            }
        }
        else {//place les obstacles aléatoirement
            _dungeon.CreateDefaultObstacles();
        }
    }


    public void createGame() {
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        System.out.println("----Préparation de la partie----");

        createEntities();
        createDungeon();

        System.out.println("----Début de la partie----");
        _dungeon.displayMap(_entitiesPosition);
    }


    public void showBoard() {
    }


    public void runGame() {

    }

    public Dungeon getDungeon() {
        return _dungeon;
    }
}
