package game.entities.classes;

import game.entities.Statistics;
import game.utils.EquipmentRepository;

import java.util.Arrays;

public class Wizard extends CharacterClass{
    public Wizard() {
        super("Wizard", new Statistics(12, 0, 0, 0, 0),
                Arrays.asList(
                        EquipmentRepository.STICK.get(),
                        EquipmentRepository.SLING.get()
                ));
    }
}
