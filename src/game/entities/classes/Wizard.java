package game.entities.classes;

import game.entities.Statistics;
import game.spells.BoogieWoogie;
import game.spells.Heal;
import game.spells.MagicWeapon;
import game.utils.EquipmentRepository;

import java.util.Arrays;

public class Wizard extends CharacterClass{
    public Wizard() {
        super("Wizard", new Statistics(12, 0, 0, 0, 0),
                Arrays.asList(
                        EquipmentRepository.STICK.get(),
                        EquipmentRepository.SLING.get()
                ));
        addSpell(new Heal());
        addSpell(new BoogieWoogie());
        addSpell(new MagicWeapon());

    }
}
