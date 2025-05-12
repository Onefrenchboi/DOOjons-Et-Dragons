package Game.Equipment;

import Game.Entities.Statistics;

public class LightArmor extends Armor {
    public LightArmor(String name, int AC) {
        super(name, AC, new Statistics(0, 0, 0, 0, 0));
    }
}
