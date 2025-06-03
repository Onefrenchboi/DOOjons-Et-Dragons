package game.entities.classes;

import game.entities.Statistics;
import game.utils.EquipmentRepository;

import java.util.Arrays;

public class Rogue extends CharacterClass{
    public Rogue() {
        super("Rogue", new Statistics(16, 0, 0, 0, 0),
                Arrays.asList(
                        EquipmentRepository.RAPIER.get(),
                        EquipmentRepository.SHORTBOW.get()
        ));
    }
}
