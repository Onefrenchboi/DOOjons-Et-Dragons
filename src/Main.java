import Game.Dungeon;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        System.out.println("Ceci est un test");

        //creation nouveau donjon
        Dungeon d = new Dungeon(15,15);
        d.setDefaultObstacles();
        d.displayMap();


    }
}

/*
      A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
   *-----------------------------------------------*
 1 |  .  .  .  .  .  .  .  . 🧱 🧱 🧱 🧱 .  .  .  |
 2 |  .  .  .  .  .  .  .  .  .  .  . 🧱  .  .  .  |
 3 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
 4 |  .  .  .  .  .  .  .  .   .  .  .  .  .  .  |
 5 |  .  .  .  .  .  .  .  .  . 🧱 .  .  .  .  .  |
 6 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
 7 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
 8 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
 9 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
10 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
11 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
12 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
13 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
14 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
15 |  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
   *-----------------------------------------------*
*/