package game.entities;


import game.utils.Dice;

public abstract class Entity {
    private Statistics _stats;


    protected Entity() {
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






    public abstract String toString(); //juste la pour etre overidden donc abstract
}
