package game.entities;


import game.Dungeon;
import game.utils.Dice;

public abstract class Entity {
    private String _name;
    private String _pseudo;
    private Statistics _stats;


    protected Entity(String name) {
        this._name = name;
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

    public String getName() {
        return _name;
    }

    public String getPseudo() {
        return _pseudo;
    }

    public void setPseudo(String pseudo) {
        this._pseudo = pseudo;
    }

    public String getColor() {
        return Dungeon.PURPLE;
    }

}
