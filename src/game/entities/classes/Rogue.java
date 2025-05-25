package game.entities.classes;

import game.entities.Statistics;
import game.utils.Repo;

import java.util.Arrays;

public class Rogue extends CharacterClass{
    public Rogue() {
        super("Rogue", new Statistics(16, 0, 0, 0, 0),
                Arrays.asList(
                Repo.getEquipmentByName("Rapier"),
                Repo.getEquipmentByName("Shortbow")
        ));
    }
}
