package game.entities;


import game.Dungeon;
import game.utils.Dice;

public abstract class Entity {
    private String _name;
    private String _pseudo;
    private Statistics _stats;
    private int _maxHp;


    protected Entity(String name) {
        this._name = name;
        this._stats = new Statistics(
                0,
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




    public int getInitiative() {
        return _stats.getInitiative();
    }

    public void setHp(int hp) {
        this._stats.addStatistics(new Statistics(hp, 0, 0, 0, 0));
    }
    public void setMaxHp(int maxHp) {
        this._maxHp = maxHp;
    }
    public int getHp() {
        return _stats.getHp();
    }

    public int get_maxHp() {
        return _maxHp;
    }

    public void setPseudo(String pseudo) {
        this._pseudo = pseudo;
    }

    public String getColor() {
        return Dungeon.PURPLE;
    }

    public boolean isAlive() {
        if (_stats.getHp() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
