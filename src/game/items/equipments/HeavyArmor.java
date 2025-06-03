package game.items.equipments;

import game.entities.Statistics;
import game.items.Armor;
import game.items.enums.ArmorType;

public class HeavyArmor extends Armor {

    public HeavyArmor(String name, int AC){
        super(name, AC, new Statistics(0, 0, 0, -4, 0), ArmorType.HEAVY);

    }


}
