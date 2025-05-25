package game.entities.classes;

import game.entities.Statistics;
import game.utils.*;

import java.util.Arrays;

public class Cleric extends CharacterClass{
    public Cleric() {
        super("Cleric", new Statistics(16, 0, 0, 0, 0),
                Arrays.asList(
                        Repo.getEquipmentByName("Mace"),
                        Repo.getEquipmentByName("Scale armor"),
                        Repo.getEquipmentByName("Light crossbow")
                ));
    }
}
