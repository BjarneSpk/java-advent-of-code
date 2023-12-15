package com.github.bjarnespk.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Scanner;

public class App {

    private static final int[] days = {6};
    private static final Part[] parts = {Part.PART_TWO};

    private static final String INPUT_PATH = "/com/github/bjarnespk/input/input_%s.txt";
    private static final String SOLUTION_PATH = "com.github.bjarnespk.solutions.Day%s";

    public static void main(String[] args) throws Exception {
        for (int day : days) {
            String zeroFilledDay = (day < 10 ? "0" : "") + day;
            for (Part part : parts) {
                solveProblem(zeroFilledDay, part);
            }
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
            System.out.println("Day " + zeroFilledDay + " " + part + " solution: " + answer);
        }
    }
}
