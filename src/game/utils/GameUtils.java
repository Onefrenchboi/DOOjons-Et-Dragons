package game.utils;

import game.Dungeon;
import game.entities.Entity;

import java.util.InputMismatchException;
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

    public static int askValidInt(String prompt, int min, int max) {
        int value = -1;
        while (true) {
            Display.display(prompt);
            try {
                value = scanner.nextInt();
                if (value < min || value > max) {
                    Display.displayError("Please enter a number between " + min + " and " + max + ".");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                Display.displayError("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
        }
        return value;
    }

    public static int[] askValidPosition(String prompt, Dungeon dungeon) {
        int[] pos;
        while (true) {
            Display.display(prompt);
            String positionInput = scanner.next();
            try {
                pos = parsePosition(positionInput);
                int x = pos[0];
                int y = pos[1];
                if (dungeon.isValidPosition(x, y)) {
                    break;
                } else {
                    Display.displayError("Invalid position on the map. Please try again.");
                }
            } catch (Exception e) {
                Display.displayError("Invalid format. Please enter a position like A3 or B7.");
            }
        }
        return pos;
    }
}
