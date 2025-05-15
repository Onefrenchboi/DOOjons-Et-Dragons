package game.utils;

import java.util.Random;

public class Dice {

    private static final Random random = new Random();

    public static int roll(int num, int sides) {
        int total = 0;
        for (int i = 0; i < num; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }

}
