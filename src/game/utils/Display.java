
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
            System.out.println(prefix + entity.getPseudo() + "   " + entity.toString() + " (" + entity.getHp() + "/" + entity.getMaxHp() + ")");
        }

    }

    public static void displayEntityInfo(Entity currentEntity) {
        System.out.println(currentEntity.toString());
        System.out.println(currentEntity.getInfo());
    }

    public static void displayMap(Dungeon dungeon) {
        dungeon.updateMap();
    }

    public static void displayTurnInfo(Entity currentEntity, int actions) {
        System.out.println(currentEntity.getName() + ", you have " + actions + " actions left this turn.");
        System.out.println("Possible actions:");
        System.out.println("  - Let the DM comment the previous action (dm <text>)");
        System.out.println("  - Comment the previous action (com <text>)");
        System.out.println("  - Attack (att <Case>)");
        System.out.println("  - Move (dep <Case>)");
        System.out.println("  - Equip an item (equ <item number>)");
        System.out.println();
    }
}
