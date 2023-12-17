package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Day04 implements DayTemplate {

    private Map<Integer, Integer> map;
    private static final int LENGTH = 198;
    private static final int WINNING_CARDS = 10;

    @Override
    public String solve(Part part, Scanner scanner) {
        initializeMap();
        if (part == Part.PART_ONE) {
            int score = 0;
            while (scanner.hasNext()) {
                score += getScore(scanner.nextLine());
            }
            return String.valueOf(score);
        }
        int score = 0;
        while (scanner.hasNext()) {
            score += getScorePartTwo(scanner.nextLine());
        }
        return String.valueOf(score);
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
