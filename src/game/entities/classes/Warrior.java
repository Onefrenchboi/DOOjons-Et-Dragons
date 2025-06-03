package game.entities.classes;

import game.entities.Statistics;
import game.utils.EquipmentRepository;
import game.utils.Repo;

import java.util.Arrays;

public class Warrior extends CharacterClass{
    public Warrior() {
        super("Warrior", new Statistics(20, 0, 0, 0, 0),
                Arrays.asList(
                        EquipmentRepository.CHAINMAIL.get(),
                        EquipmentRepository.LONGSWORD.get(),
                        EquipmentRepository.LIGHT_CROSSBOW.get()
        ));
    }
}
