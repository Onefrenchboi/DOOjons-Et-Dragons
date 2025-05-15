package game.entities;


import game.utils.Dice;

public abstract class Entity {
    private Statistics _stats;
    private int[] _coordinates;


    protected Entity() {
        this._coordinates = new int[]{0, 0};
        this._stats = new Statistics(
                (Dice.roll(4, 4) + 3),
                (Dice.roll(4, 4) + 3),
                (Dice.roll(4, 4) + 3),
                (Dice.roll(4, 4) + 3),
                (Dice.roll(4, 4) + 3));
    }

    public Statistics getStats() {
        return _stats;
    }

    public int[] getcoordinates() {
        return _coordinates;
    }

}
