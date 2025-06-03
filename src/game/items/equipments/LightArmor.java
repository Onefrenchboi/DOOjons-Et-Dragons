package game.items.equipments;

import game.entities.Statistics;
import game.items.Armor;
import game.items.enums.ArmorType;

public class LightArmor extends Armor {
    public LightArmor(String name, int AC) {
        super(name, AC, new Statistics(0, 0, 0, 0, 0), ArmorType.LIGHT);
    }
}
