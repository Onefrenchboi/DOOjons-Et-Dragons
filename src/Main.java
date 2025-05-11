import Game.Dungeon;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        //creation nouveau donjon
        Dungeon d = new Dungeon(15,15);
        d.setDefaultObstacles();
        d.displayMap();


    }
}