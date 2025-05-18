package game;

import game.entities.*;
import game.entities.races.*;
import game.entities.classes.*;

import game.entities.Character;
import game.items.*;
import game.utils.Repo;
import game.utils.Utils;

import java.util.*;

public class DM {
    private Dungeon _dungeon;
    private HashMap<Entity, int[]> _entitiesPosition;
    private List<Entity> _entitiesSortedByInitiative;
    private HashMap<Equipment, int[]> _equipmentPosition;
    private List<Equipment> _equipmentList;
    private Repo _equipmentRepo;

    public DM() {
        _entitiesPosition = new HashMap<>();
        _entitiesSortedByInitiative = new ArrayList<>();
        _equipmentPosition = new HashMap<>();
        _equipmentList = new ArrayList<>();
        _equipmentRepo= new Repo();
        _equipmentRepo.initializeEquipment();
    }

    public void addEntity(Entity entity, int x, int y) { //pour placer les entités
        if (_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
            _entitiesPosition.put(entity, new int[]{x, y});
        } else {
            System.out.println("Invalid position");
        }
    }
    public void removeEntity(Entity entity) { //pour les tuer >:D
        _entitiesPosition.remove(entity);
    }


    public void addEquipment(Equipment equipment, int x, int y) { //pour placer les equipements
        if (_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
            _equipmentPosition.put(equipment, new int[]{x, y});
        } else {
            System.out.println("Invalid position");
        }
    }
    public void removeEquipment(Equipment equipment) {
        _equipmentPosition.remove(equipment);
    }


    public void createCharacters(){
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
    }

    public void createMonsters(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Dm, veux-tu créer des monstres (Si tu choisis non, une selection par défaut sera appliquée) ? (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
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
                int damageroll = scanner.nextInt();
                Weapon weapon;
                if (range == 1) {
                    weapon = new MeleeWeapon("MonsterMeleeAttack", range, damageroll);
                } else {
                    weapon = new RangedWeapon("MonsterRangedAttack", range, damageroll);
                }
                int number = monsterCount.getOrDefault(species, 0) + 1;
                monsterCount.put(species, number);

                Monster monster = new Monster(species, number, weapon);
                _entitiesSortedByInitiative.add(monster);
            }
        }
        else {
            System.out.println("Création de monstres par défaut");
            _entitiesSortedByInitiative.add(new Monster("Fragon", 1, new MeleeWeapon("MonsterMeleeAttack", 1, 4)));
            _entitiesSortedByInitiative.add(new Monster("Fragon", 2, new MeleeWeapon("MonsterMeleeAttack", 1, 6)));
            _entitiesSortedByInitiative.add(new Monster("Bob", 1, new RangedWeapon("MonsterRangedAttack", 12, 8)));
        }
    }

    public void createEquipments(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Combien d'equipements ? (1-5) : ");
        int nbEquipment = scanner.nextInt();
        while (nbEquipment < 1 || nbEquipment > 5) {
            System.out.println("Nombre d'equipements invalide. Veuillez entrer un nombre entre 1 et 4 : ");
            nbEquipment = scanner.nextInt();
        }

        System.out.println("Voici la liste des equipements disponibles : ");
        for (Equipment equipment : _equipmentRepo.getEquipments()) {
            int n = _equipmentRepo.getEquipments().indexOf(equipment);
            System.out.println("(" + n + ")" + equipment.toString());
        }
        for (int i = 0; i < nbEquipment; i++) {
            System.out.println("Entrez le numéro de l'equipement à ajouter : ");
            int equipment = scanner.nextInt();
            while (equipment < 0 || equipment > _equipmentRepo.getEquipments().size()) {
                System.out.println("Nombre d'equipements invalide. Veuillez entrer un nombre entre 1 et 4 : ");
                equipment = scanner.nextInt();
            }
            Equipment selectedEquipment = _equipmentRepo.getEquipments().get(equipment);
            _equipmentList.add(selectedEquipment);
        }
    }

    public void createDungeon() {
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
        _dungeon = new Dungeon(width, height);
    }


    public void setDungeon() {
        //On sort les monstres et personnages par initiative
        Map<Entity, Integer> initiativeMap = new HashMap<>();
        for (Entity entity : _entitiesSortedByInitiative) {
            initiativeMap.put(entity, entity.getInitiative() + Utils.roll(1, 20));
        }

        _entitiesSortedByInitiative.sort((e1, e2) -> initiativeMap.get(e2) - initiativeMap.get(e1));
        //et on les affiche
        System.out.println("Voici la liste des personnages et monstres : ");
        for (Entity entity : _entitiesSortedByInitiative) {
            System.out.println(entity);
            //TODO : fix l'affichage de l'initiative, ptet meme pas le faire parce qu'en soit on a pas beosin de savoir
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("DM, veux-tu placer les personnages/monstres sur la carte ? (Y/N)");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            for (Entity entity : _entitiesSortedByInitiative) {
                System.out.println("Entrez la position de " + entity + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
                    System.out.println("Position invalide. Veuillez entrer une nouvelle position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                }
                addEntity(entity, x, y);
            }
        }
        else {
            //place les personnages aléatoirement
            for (Entity entity : _entitiesSortedByInitiative) {
                int x = Utils.roll(1, _dungeon.getSize()[0] - 2);
                int y = Utils.roll(1, _dungeon.getSize()[1] - 1);
                while (!_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
                    x = Utils.roll(1, _dungeon.getSize()[0] - 2);
                    y = Utils.roll(1, _dungeon.getSize()[1] - 1);
                }
                addEntity(entity, x, y);
            }
        }

        System.out.println("DM, veux-tu placer les items sur la carte ? (Y/N)");
        choice = scanner.next();
        if (choice.equalsIgnoreCase("Y")) {
            for (Equipment equipment : _equipmentList) {
                System.out.println("Entrez la position de " + equipment + " (x y) : ");
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                while (!_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
                    System.out.println("Position invalide. Veuillez entrer une nouvelle position : ");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                }
                addEquipment(equipment, x, y);
            }
        }
        else {
            for (Equipment equipment : _equipmentList) {
                int x = Utils.roll(1, _dungeon.getSize()[0] - 2);
                int y = Utils.roll(1, _dungeon.getSize()[1] - 1);
                while (!_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
                    x = Utils.roll(1, _dungeon.getSize()[0] - 2);
                    y = Utils.roll(1, _dungeon.getSize()[1] - 1);
                }
                addEquipment(equipment, x, y);
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
                while (!_dungeon.isValidPosition(x, y, _entitiesPosition, _equipmentPosition)) {
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


    public void showBoard() {
        _dungeon.displayMap(_entitiesPosition, _equipmentPosition);
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

    public HashMap<Entity, int[]> getEntitiesPosition() {
        return _entitiesPosition;
    }

}
