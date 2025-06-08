package game.entities;

public class Statistics {
    private int _hp;
    private int _strength;
    private int _dexterity;
    private int _speed;
    private int _initiative;

    public Statistics() {
        this._hp = 0;
        this._strength = 0;
        this._dexterity = 0;
        this._speed = 0;
        this._initiative = 0;
    }

    public Statistics(int hp, int strength, int dexterity, int speed, int initiative) {
        this._hp = hp;
        this._strength = strength;
        this._dexterity = dexterity;
        this._speed = speed;
        this._initiative = initiative;
    }


    //? add and remove methods for Statistics
    public void addStatistics(Statistics s) {
        this._hp += s._hp;
        this._strength += s._strength;
        this._dexterity += s._dexterity;
        this._speed += s._speed;
        this._initiative += s._initiative;
    }
    public void removeStatistics(Statistics s) {
        this._hp -= s._hp;
        this._strength -= s._strength;
        this._dexterity -= s._dexterity;
        this._speed -= s._speed;
        this._initiative -= s._initiative;
    }


    //? Getters
    public int getHp() {
        return _hp;
    }
    public int getStrength() {
        return _strength;
    }
    public int getDexterity() {
        return _dexterity;
    }
    public int getSpeed() {
        return _speed;
    }
    public int getInitiative() {
        return _initiative;
    }


    @Override
    public String toString() {
        return "Statistics{" +
                "_hp=" + _hp +
                ", _strength=" + _strength +
                ", _dexterity=" + _dexterity +
                ", _speed=" + _speed +
                ", _initiative=" + _initiative +
                '}';
    }
}
