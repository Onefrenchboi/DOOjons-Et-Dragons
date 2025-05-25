
package game.utils;
import game.DM;
import game.Dungeon;
import game.entities.Entity;

import static game.utils.Dice.*;


public class Display {
    public static void display(String message) {
        System.out.println(message);
    }


    public static void displayError(String message) {
        System.out.println(RED + message + RESET);
    }

    public static void displaySuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public static void displayInfo(DM dm) {
        System.out.println("********************************************************************************");
        System.out.println("Dungeon : "+ dm.getDungeonNumber());
       ;
        System.out.println(dm.getCurrentEntity().toString());
        System.out.println("********************************************************************************");


        System.out.println("Turn " + dm.getTurn() + " : ");
        for (Entity entity : dm.getEntitiesSortedByInitiative()) {
            String prefix = (entity == dm.getCurrentEntity()) ? "-> " : "   ";
            System.out.println(prefix + entity.getPseudo() + "   " + entity + " (" + entity.getHp() + "/" + entity.getMaxHp() + ")");
        }

    }

}
