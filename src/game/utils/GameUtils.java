package game.utils;

import java.util.Random;
import java.util.Scanner;

public class GameUtils {


    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE_BG = "\u001B[47m";
    public static final Random random = new Random();
    public static final Scanner scanner = new Scanner(System.in);

    public static int roll(int num, int sides) {
        int total = 0;
        for (int i = 0; i < num; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }


    public static int[] parsePosition(String position) {
        char column = position.charAt(0);
        String rowPart = position.substring(1);

        int x = Integer.parseInt(rowPart);
        int y = Character.toUpperCase(column) - 'A' + 1;

        return new int[]{x, y};
    }
}
