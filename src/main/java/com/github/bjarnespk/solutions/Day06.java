package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Arrays;
import java.util.Scanner;

public class Day06 implements DayTemplate {

    private record Race(int time, long distance) {

    }

    @Override
    public String solve(Part part, Scanner scanner) {
        int[][] numbers = {parseLine(scanner.nextLine()), parseLine(scanner.nextLine())};
        Race[] races = new Race[4];
        for (int i = 0; i < 4; i++) {
            races[i] = new Race(numbers[0][i], numbers[1][i]);
        }
        if (part == Part.PART_ONE) {
            return solvePartOne(races);
        }
        return solvePartTwo(races);
    }

    private String solvePartTwo(Race[] races) {
        StringBuilder time = new StringBuilder();
        StringBuilder distance = new StringBuilder();
        for (Race race : races) {
            time.append(race.time());
            distance.append(race.distance());
        }
        Race largeRace = new Race(Integer.parseInt(time.toString()), Long.parseLong(distance.toString()));
        return solvePartOne(new Race[] {largeRace});
    }

    private static int[] parseLine(String line) {
        return Arrays.stream(line.substring(12).strip().split(" "))
                .filter(s -> !s.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private String solvePartOne(Race[] races) {
        int product = 1;
        for (Race race : races) {
            int winPossibilities = 0;
            for (int i = 0; i <= race.time(); i++) {
                if ((long) (race.time() - i) * i > race.distance()) {
                    winPossibilities++;
                }
            }
            product *= winPossibilities;
        }
        return String.valueOf(product);
    }
}
