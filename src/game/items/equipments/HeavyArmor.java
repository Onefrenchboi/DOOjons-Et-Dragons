package game.items.equipments;

import game.entities.Statistics;
import game.items.Armor;

public class HeavyArmor extends Armor {

    public HeavyArmor(String name, int AC){
        super(name, AC, new Statistics(0, 0, 0, -4, 0));

    }


}
