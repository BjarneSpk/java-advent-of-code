package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day07 implements DayTemplate {

    @Override
    public String solve(Part part, Scanner scanner) {
        Map<String, Integer> games = new HashMap<>(1 << 10);
        while (scanner.hasNext()) {
            games.put(scanner.next(), scanner.nextInt());
        }
        if (part == Part.PART_ONE) {
            return solvePartOne(games);
        }
        return solvePartTwo(games);
    }

    private String solvePartTwo(Map<String, Integer> games) {
        return null;
    }

    private String solvePartOne(Map<String, Integer> games) {
        return null;
    }
}
