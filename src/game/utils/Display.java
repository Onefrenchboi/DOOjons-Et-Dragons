
package game.utils;
import game.DM;
import game.Dungeon;
import game.entities.Character;
import game.entities.Entity;
import game.entities.EntityType;

import static game.utils.GameUtils.*;


public class Display {

    //? All functions in here are pretty straightforward, donc flemme de commenter tout
    public static void display(String message) {
        System.out.println(message);
    }

    public static void displayError(String message) {
        System.out.println(RED + message + RESET);
    }

    public static void displayLore(String message) {
        System.out.println(YELLOW + message + RESET);
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
        if (currentEntity.getType()==EntityType.MONSTER){
            System.out.println("  - Comment the previous action (com <text>)");
            System.out.println("  - Attack (att <position>)");
            System.out.println("  - Move (move <position>)");
            System.out.println("  - Skip turn (skip turn)");

        }else {
            System.out.println("  - Comment the previous action (com <text>)");
            System.out.println("  - Attack (att <position>)");
            System.out.println("  - Move (move <position>)");
            System.out.println("  - Show inventory to equip an item (equ show)");
            System.out.println("  - Pick up an item (pick <position>)");
            System.out.println("  - Open Spellbook (spell)");
            System.out.println("  - Skip turn (skip turn)");

        }
        System.out.println("-------------------------------");
    }

    public static void displayDmActions() {
        System.out.println("-------------------------------");
        System.out.println("Possible DM actions :");
        System.out.println("  - Comment the previous action (com <text>)");
        System.out.println("  - Move an entity (move)");
        System.out.println("  - Add an obstacle (add)");
        System.out.println("  - Damage an entity (hurt <position> <Number of dice(s)> <faces>)");
        System.out.println("  - Display map (display)");
        System.out.println("  - Stop (stop)");
        System.out.println("-------------------------------");
    }

    public static void displaySpellsMenu(Entity currentEntity) {
        System.out.println("-------------------------------");
        System.out.println(currentEntity.toString() + ", you can cast the following spells:");
        if (currentEntity.getType()== EntityType.PLAYER) {
                System.out.println("  - Heal (heal <position>)");
                System.out.println("  - Magic Weapon (magicweapon <position>)");
                System.out.println("  - Boogie Woogie (boogiewoogie <position1>)");
                System.out.println("  - Stop casting (stop)");
        } else {
            System.out.println("  - No spells available for monsters.");
            return;
        }
        System.out.println("-------------------------------");
    }
}
