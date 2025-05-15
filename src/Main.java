import game.Dungeon;
import game.entities.Character;
import game.entities.Statistics;
import game.entities.classes.CharacterClass;
import game.entities.classes.Warrior;
import game.entities.classes.Wizard;
import game.entities.races.Dwarf;
import game.entities.races.Race;
import game.utils.Dice;

import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        System.out.println("eeuhhhhh");
//        //creation nouveau donjon
//        Dungeon d = new Dungeon(25,25);
//        d.CreateDefaultObstacles();
//        d.displayMap();


        //todo : j'ai mis les trucs en abstract, refait uml



        // Create a Character
        game.entities.Character hero = new Character("Aragorn", new Dwarf(),new Wizard());

        // Display the Character's stats
        System.out.println("Character Name: " + hero.getName());
        System.out.println("Character hp :" + hero.getStats().getHp());
        System.out.println("Character strength :" + hero.getStats().getStrength());
        System.out.println("Character dexterity :" + hero.getStats().getDexterity());
        System.out.println("Character speed :" + hero.getStats().getSpeed());
        System.out.println("Character initiative :" + hero.getStats().getInitiative());
    }
}