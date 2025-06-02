
package game.utils;
import game.DM;
import game.Dungeon;
import game.entities.Entity;

import static game.utils.GameUtils.*;


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

    public static void displayClear() {
        System.out.print("\n".repeat(50));
    }

    public static void displayActionMenu(Entity currentEntity, int actions) {
        System.out.println("-------------------------------");
        System.out.println(currentEntity.toString() + ", you have " + actions + " actions left this turn.");
        System.out.println("Possible actions:");
        if (currentEntity.isMonster()){
            System.out.println("  - Let the DM comment the previous action (dm <text>)");
            System.out.println("  - Open the commenting menu (com menu)");
            System.out.println("  - Attack (att <position>)");
            System.out.println("  - Move (move <position>)");

        }else {
            System.out.println("  - Let the DM comment the previous action (dm <text>)");
            System.out.println("  - Comment the previous action (com)");
            System.out.println("  - Attack (att <position>)");
            System.out.println("  - Move (move <position>)");
            System.out.println("  - Show inventory to equip an item (equ show)");
            System.out.println("  - Pick up an item (pick <item number>)");

        }
        System.out.println("-------------------------------");
    }
}
