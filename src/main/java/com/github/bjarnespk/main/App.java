package com.github.bjarnespk.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Scanner;

public class App {

    private static final int[] days = {6};
    private static final Part[] parts = {Part.PART_ONE};

    private static final String INPUT_PATH = "/com/github/bjarnespk/input/input_%s.txt";
    private static final String SOLUTION_PATH = "com.github.bjarnespk.solutions.Day%s";
    private static Part[] partsToRun;
    private static int[] daysToRun;

    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            parseCLI(args);
        } else {
            daysToRun = days;
            partsToRun = parts;
        }
        for (int day : daysToRun) {
            String zeroFilledDay = (day < 10 ? "0" : "") + day;
            for (Part part : partsToRun) {
                solveProblem(zeroFilledDay, part);
            }
        }
    }

    private static void parseCLI(String[] args) {
        int j = 0;
        if (args[0].charAt(0) == '-') {
            partsToRun = new Part[args[0].length() - 1];
            for (int i = 1; i < args[0].length(); i++) {
                partsToRun[i - 1] = switch (args[0].charAt(i)) {
                    case '1' -> Part.PART_ONE;
                    case '2' -> Part.PART_TWO;
                    default -> throw new IllegalArgumentException();
                };
            }
            j++;
        } else {
            partsToRun = parts;
        }
        daysToRun = new int[args.length - j];
        int k = 0;
        for (;j < args.length; j++) {
            daysToRun[k++] = Integer.parseInt(args[j]);
        }
    }

    private static void solveProblem(String zeroFilledDay, Part part) throws Exception {
        try (var rs = App.class.getResourceAsStream(INPUT_PATH.formatted(zeroFilledDay));
             var isr = new InputStreamReader(Objects.requireNonNull(rs));
             var in = new BufferedReader(isr);
             var scan = new Scanner(in)) {
            Class<?> cls = Class.forName(SOLUTION_PATH.formatted(zeroFilledDay));
            Method m = cls.getDeclaredMethod("solve", Part.class, Scanner.class);
            String answer = (String) m.invoke(cls.getDeclaredConstructor().newInstance(), part, scan);
            System.out.printf("Day: %s %s Solution: %s%n", zeroFilledDay, part, answer);
        }
    }
}
