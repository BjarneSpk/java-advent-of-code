package com.github.bjarnespk.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.*;

public class App {

    // Modify these two for default values
    private static final Set<Integer> days = Set.of(7);
    private static final Set<Part> parts = Set.of(Part.PART_ONE);

    private static final String INPUT_PATH = "/com/github/bjarnespk/%sinput/input_%s.txt";
    private static final String SOLUTION_PATH = "com.github.bjarnespk.solutions.Day%s";
    private static Set<Part> partsToRun = new TreeSet<>();
    private static Set<Integer> daysToRun = new TreeSet<>();
    private static String test = "";

    public static void main(String[] args) throws Exception {
        new App(args);
    }

    private App(String[] args) throws Exception {
        if (args.length != 0) {
            parseCLI(args);
        } else {
            daysToRun = days;
            partsToRun = parts;
        }
        for (int day : daysToRun) {
            String zeroFilledDay = (day < 10 ? "0" : "") + day;
            for (Part part : partsToRun) {
                solveProblem(zeroFilledDay, part, test);
            }
        }
    }

    private void parseCLI(String[] args) {
        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                setFlags(arg);
            } else {
                daysToRun.add(Integer.parseInt(arg));
            }
        }
        if (partsToRun.isEmpty()) {
            partsToRun = parts;
        }
    }

    private void setFlags(String arg) {
        for (int j = 1; j < arg.length(); j++) {
            switch (arg.charAt(j)) {
                case '1' -> partsToRun.add(Part.PART_ONE);
                case '2' -> partsToRun.add(Part.PART_TWO);
                case 't' -> test = "test_";
                default -> throw new IllegalArgumentException("Invalid flag");
            }
        }
    }

    public static void solveProblem(String zeroFilledDay, Part part, String test) throws Exception {
        try (var rs = App.class.getResourceAsStream(INPUT_PATH.formatted(test, zeroFilledDay));
             var isr = new InputStreamReader(Objects.requireNonNull(rs));
             var in = new BufferedReader(isr);
             var scan = new Scanner(in)) {
            Class<?> cls = Class.forName(SOLUTION_PATH.formatted(zeroFilledDay));
            Method m = cls.getMethod("timer", Part.class, Scanner.class);
            Result answer = (Result) m.invoke(cls.getDeclaredConstructor().newInstance(), part, scan);
            System.out.printf("Day: %s %s Solution: %s Time (ms): %.1f%n", zeroFilledDay, part, answer.result(), answer.time());
        }
    }

    public static void solveProblem(String zeroFilledDay, Part part) throws Exception {
        solveProblem(zeroFilledDay, part, "");
    }
}
