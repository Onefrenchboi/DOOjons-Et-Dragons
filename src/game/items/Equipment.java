package game.items;

public abstract class Equipment {
    private String _name;

    public Equipment(String name) {
        _name = name;
    }


    public String get_name(){
        return _name;
    }

}
