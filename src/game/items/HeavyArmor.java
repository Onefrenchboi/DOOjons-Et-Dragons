package game.items;

import game.entities.Statistics;

public class HeavyArmor extends Armor {

    public HeavyArmor(String name, int AC){
        super(name, AC, new Statistics(0, 0, 0, -4, 0));

    }


}
