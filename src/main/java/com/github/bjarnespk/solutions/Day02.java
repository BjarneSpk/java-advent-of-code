package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Day02 implements DayTemplate {

    private static final int RED = 12, GREEN = 13, BLUE = 14;

    private int getPowerSum(String line) {
        StringTokenizer st = new StringTokenizer(line, " ,;:");
        st.nextToken();
        st.nextToken();
        int red = 0, green = 0, blue = 0;
        while (st.hasMoreTokens()) {
            int num = Integer.parseInt(st.nextToken());
            switch (st.nextToken()) {
                case "red" -> red = Math.max(num, red);
                case "green" -> green = Math.max(num, green);
                default -> blue = Math.max(num, blue);
            }
        }
        return red * green * blue;
    }

    private boolean gameIsValid(String line) {
        StringTokenizer st = new StringTokenizer(line, " ,;:");
        st.nextToken();
        st.nextToken();
        while (st.hasMoreTokens()) {
            int num = Integer.parseInt(st.nextToken());
            if (switch (st.nextToken()) {
                case "red" -> num > RED;
                case "green"-> num > GREEN;
                default -> num > BLUE;
            }) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String solve(Part part, Scanner scanner) {
        if (Objects.requireNonNull(part) == Part.PART_ONE) {
            int idSum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (gameIsValid(line)) {
                    idSum += Integer.parseInt(line.substring(5, line.indexOf(":")));
                }
            }
            return String.valueOf(idSum);
        }
        int powerSum = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            powerSum += getPowerSum(line);
        }
        return String.valueOf(powerSum);
    }
}