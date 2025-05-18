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
}
