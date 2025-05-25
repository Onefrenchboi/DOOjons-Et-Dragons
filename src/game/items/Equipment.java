package game.items;

public abstract class Equipment {
    private String _name;

    protected Equipment(String name) {
        _name = name;
    }


    public String getName(){
        return _name;
    }


    public String toString(){
        return _name;
    }


    //!encore un instanceof du pauvre
    public boolean isWeapon() {
        return false;
    }
    public boolean isArmor() {
        return false;
    }
}
