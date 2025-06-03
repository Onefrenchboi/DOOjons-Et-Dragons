import game.DM;
import game.entities.Character;
import game.entities.Monster;
import game.entities.classes.*;
import game.entities.races.*;
import game.items.MeleeWeapon;
import game.utils.Display;

import static game.utils.Repo.getEquipmentByName;

public class Main {
    public static void main(String args[]){


        Display.display("\n                                                Welcome To D&D !\n");
        Display.displayLore(
                "An ancient cataclysm, awoken by the Hylian Princess and her Silent Knight, cracked the world open. " +
                "From those wounds, black chasms bled into the earth — gateways to The Depths.\n" +
                "Down there, sunlight dies. A crimson miasma corrupts all life.\n" +
                "Whispers tell of ancient Zonai ruins, long buried beneath the earth, and strange energies that still pulse deep within the darkness.\n" +
                "You are Flamebearers, chosen to descend with light in hand. Each dungeon you conquer pushes back the gloom.\n" +
                "But something watches from below — a mind that stirs the ground, dreaming of the surface.\n");

        DM dm = new DM();
        dm.createGame();
        dm.play();


        //todo : commentaires
        //todo : UML :(
        //todo : see what i can put in private, protected, etc...

    }
}
