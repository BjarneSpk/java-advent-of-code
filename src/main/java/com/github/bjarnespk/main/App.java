package com.github.bjarnespk.main;

import com.github.bjarnespk.main.DayTemplate.Result;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class App {

    // Modify these two for default values
    private static final Set<Integer> days = Set.of(7);
    private static final Set<Part> parts = Set.of(Part.PART_ONE);

    private static final String INPUT_PATH = "/com/github/bjarnespk/%sinput/input_%s.txt";
    private static final String SOLUTION_PATH = "com.github.bjarnespk.solutions.Day%s";
    private static Set<Part> partsToRun = new TreeSet<>();
    private static Set<Integer> daysToRun = new TreeSet<>();
    private static String test = "";
    public static final String TEST = "test_";

    public static void main(String[] args) {
        new App(args);
    }

    private App(String[] args) {
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

    public static void solveProblem(String zeroFilledDay, Part part, String test) {
        try (var rs = App.class.getResourceAsStream(INPUT_PATH.formatted(test, zeroFilledDay));
             var in = new BufferedInputStream(Objects.requireNonNull(rs))) {

            Class<?> cls = Class.forName(SOLUTION_PATH.formatted(zeroFilledDay));
            Method m = cls.getMethod("timer", Part.class, InputStream.class);
            Result answer = (Result) m.invoke(cls.getDeclaredConstructor().newInstance(), part, in);

            System.out.printf("Day: %s %s Solution: %s Time (ms): %.2f%n",
                    zeroFilledDay, part, answer.result(), answer.time());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void solveProblem(String zeroFilledDay, Part part) {
        solveProblem(zeroFilledDay, part, "");
    }
}
