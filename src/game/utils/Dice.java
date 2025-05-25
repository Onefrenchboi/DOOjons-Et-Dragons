package game.utils;

import java.util.Random;

public class Dice {


    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String WHITE_BG = "\u001B[47m";
    private static final Random random = new Random();

    public static int roll(int num, int sides) {
        int total = 0;
        for (int i = 0; i < num; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }
}
