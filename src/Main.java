import Game.Dungeon;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");

        //creation nouveau donjon
        Dungeon d = new Dungeon(25,25);
        d.CreateDefaultObstacles();
        d.displayMap();




    }
}