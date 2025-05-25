package game.entities.classes;

import game.entities.Statistics;
import game.utils.Repo;

import java.util.Arrays;

public class Warrior extends CharacterClass{
    public Warrior() {
        super("Warrior", new Statistics(20, 0, 0, 0, 0),
                Arrays.asList(
                Repo.getEquipmentByName("Chainmail"),
                Repo.getEquipmentByName("Longsword"),
                Repo.getEquipmentByName("Light crossbow")
        ));
    }
}
