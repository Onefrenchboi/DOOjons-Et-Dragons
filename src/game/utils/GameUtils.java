package game.utils;

import game.Dungeon;
import game.entities.Entity;
import game.spells.Spell;

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


    /**
     * Rolls a number of dice with a specified number of sides.
     *
     * @param num   the number of dice to roll
     * @param sides the number of sides on each die
     * @return the total value of the rolled dice
     */
    public static int roll(int num, int sides) {
        int total = 0;
        for (int i = 0; i < num; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }

    /**
     * Parses a position string in the format "A3" or smth like that to an int[]
     *
     * @param position the position string
     * @return array with the coordinates
     */
    public static int[] parsePosition(String position) {
        char column = position.charAt(0);
        String rowPart = position.substring(1);

        int x = Integer.parseInt(rowPart);
        int y = Character.toUpperCase(column) - 'A' + 1;

        return new int[]{x, y};
    }


    /**
     * ask user for a valid integer input within a range
     *
     * @param prompt what we say to the user
     * @param min    minimum value
     * @param max    maximum value
     * @return int value within the range
     */
    public static int askValidInt(String prompt, int min, int max) {
        int value;
        while (true) {
            Display.display(prompt);
            String line = scanner.nextLine().trim();
            String[] valeurs = line.split("\\s+");
            if (valeurs.length != 1) {
                Display.displayError("Please enter a single number.");
                continue;
            }
            try {
                value = Integer.parseInt(valeurs[0]);
                if (value < min || value > max) {
                    Display.displayError("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (InputMismatchException | NumberFormatException e) {
                Display.displayError("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * ask user for a valid position input
     *
     * @param prompt what we say to the user
     * @param dungeon the dungeon to check the pos
     * @return array with the coordinates
     */
    public static int[] askValidPosition(String prompt, Dungeon dungeon) {
        int[] pos;
        while (true) {
            Display.display(prompt);
            String positionInput = scanner.nextLine().trim();
            String[] valeurs = positionInput.split("\\s+");
            if (valeurs.length != 1) {
                Display.displayError("Please enter a single number.");
                continue;
            }
            try {
                pos = parsePosition(positionInput);
                int x = pos[0];
                int y = pos[1];

                if (dungeon.isValidPosition(x, y)) {
                    break;
                }
                else {
                    Display.displayError("Invalid position on the map. Please try again.");
                }
            } catch (Exception e) {
                Display.displayError("Invalid format. Please enter a position like A3 or B7.");
            }
        }
        return pos;
    }
    public static int[] askPositionInBound(String prompt, Dungeon dungeon) {
        int[] pos;
        while (true) {
            Display.display(prompt);
            String positionInput = scanner.nextLine().trim();
            String[] valeurs = positionInput.split("\\s+");
            if (valeurs.length != 1) {
                Display.displayError("Please enter a single number.");
                continue;
            }
            try {
                pos = parsePosition(positionInput);
                int x = pos[0];
                int y = pos[1];

                if (x >= 0 && x < dungeon.getWidth() && y >= 0 && y < dungeon.getHeight()) {
                    break;
                } else {
                    Display.displayError("Position out of bounds. Please try again.");
                }
            } catch (Exception e) {
                Display.displayError("Invalid format. Please enter a position like A3 or B7.");
            }
        }
        return pos;
    }
    public static boolean askYesOrNoAnswer(String prompt) {
        while (true) {
            Display.display(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                Display.displayError("Please answer with Y or N.");
            }
        }
    }

}
