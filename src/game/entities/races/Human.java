package game.entities.races;

import game.entities.Statistics;

public class Human extends Race{
    public Human() {
        super("Human", new Statistics(2, 2, 2, 2, 2));
    }
}
