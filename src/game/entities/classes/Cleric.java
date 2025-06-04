package game.entities.classes;

import game.entities.Statistics;
import game.spells.Heal;
import game.utils.*;

import java.util.Arrays;

public class Cleric extends CharacterClass{
    public Cleric() {
        super("Cleric", new Statistics(16, 0, 0, 0, 0),
                Arrays.asList(
                        EquipmentRepository.MACE.get(),
                        EquipmentRepository.SCALE_ARMOR.get(),
                        EquipmentRepository.LIGHT_CROSSBOW.get()
                ));
        addSpell(new Heal());
    }
}
