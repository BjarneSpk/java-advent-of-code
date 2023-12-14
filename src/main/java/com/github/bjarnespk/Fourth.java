package com.github.bjarnespk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

public class Fourth {

    private static final String PATH = "/com/github/bjarnespk/input_fourth.txt";
    private Map<Integer, Integer> map;
    private static final int LENGTH = 198;
    private static final int WINNING_CARDS = 10;

    public static void main(String[] args) {
        new Fourth();
    }

    public Fourth() {
        start();
    }

    private void start() {
        try (var rs = Fourth.class.getResourceAsStream(PATH);
             var isr = new InputStreamReader(Objects.requireNonNull(rs));
             var in = new BufferedReader(isr)) {

            String line;
            int score = 0;
            int scorePartTwo = 0;
            initializeMap();
            while ((line = in.readLine()) != null) {
                score += getScore(line);
                int n = getScorePartTwo(line);
                scorePartTwo += n;
            }

            System.out.println(score);
            System.out.println(scorePartTwo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMap() {
        map = new HashMap<>();
        for (int i = 1; i <= LENGTH; i++) {
            map.put(i, 1);
        }
    }

    private int getScorePartTwo(String line) {
        StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:|");
        st.nextToken();
        int cardNumber = Integer.parseInt(st.nextToken());
        int cardAmount = map.get(cardNumber);

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < WINNING_CARDS; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }

        int wins = 0;
        while (st.hasMoreTokens()) {
            if (set.contains(Integer.parseInt(st.nextToken()))) {
                wins++;
            }
        }
        for (int i = cardNumber + 1; i <= cardNumber + wins; i++) {
            map.put(i, map.get(i) + cardAmount);
        }

        return cardAmount;
    }

    private int getScore(String line) {
        StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:|");
        st.nextToken();
        st.nextToken();

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < WINNING_CARDS; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }

        int exponent = -1;
        while (st.hasMoreTokens()) {
            if (set.contains(Integer.parseInt(st.nextToken()))) {
                exponent++;
            }
        }

        return (exponent != -1) ? ((int) Math.pow(2, exponent)) : 0;
    }
}
