package com.github.bjarnespk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.StringTokenizer;

public class Second {

    private static final int RED = 12, GREEN = 13, BLUE = 14;

    public static void main(String[] args) {
        new Second();
    }

    public Second() {
        try (var rs = Objects.requireNonNull(
                First.class.getResourceAsStream("/com/github/bjarnespk/input_second.txt"));
             var isr = new InputStreamReader(rs);
             var in = new BufferedReader(isr)) {
            int idSum = getValidIds(in);
            System.out.println(idSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getValidIds(BufferedReader in) throws IOException {
        String line;
        int idSum = 0;
        int powerSum = 0;
        while ((line = in.readLine()) != null) {
            if (gameIsValid(line)) {
                idSum += Integer.parseInt(line.substring(5, line.indexOf(":")));
            }
            powerSum += getPowerSum(line);
        }
        System.out.println("Powersum: " + powerSum);
        return idSum;
    }

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
}