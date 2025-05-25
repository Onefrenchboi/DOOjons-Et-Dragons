package game.entities.classes;

import game.entities.Statistics;
import game.utils.Repo;

import java.util.Arrays;

public class Wizard extends CharacterClass{
    public Wizard() {
        super("Wizard", new Statistics(12, 0, 0, 0, 0),
                Arrays.asList(
                        Repo.getEquipmentByName("Stick"),
                        Repo.getEquipmentByName("Sling")
                ));
    }
}
