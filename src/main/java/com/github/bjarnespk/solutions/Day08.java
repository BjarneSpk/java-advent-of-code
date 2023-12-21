package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day08 implements DayTemplate {

    @Override
    public String solve(Part part, Scanner scanner) {
        int[] path = scanner.nextLine().codePoints().map(direction -> direction / 'R').toArray();
        scanner.nextLine();

        Map<String, String[]> map = initNodeMap(scanner);

        if (part == Part.PART_ONE) {
            return String.valueOf(getPathLength(path, map));
        }
        return solveTwo(path, map);
    }

    private String solveTwo(int[] path, Map<String, String[]> map) {
        List<String> nodes = map.keySet().stream().filter(node -> node.charAt(2) == 'A').toList();

        long[] runs = getMinRunsPerNode(path, map, nodes);
        long lcm = Arrays.stream(runs).reduce(this::lcm).orElseThrow();
        return String.valueOf(lcm * path.length);
    }

    private static long[] getMinRunsPerNode(int[] path, Map<String, String[]> map, List<String> nodes) {
        long[] runs = new long[nodes.size()];
        Arrays.fill(runs, 0);

        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            while (node.charAt(2) != 'Z') {
                for (int direction : path) {
                    node = map.get(node)[direction];
                }
                runs[i]++;
            }
        }
        return runs;
    }

    private int getPathLength(int[] path, Map<String, String[]> map) {
        String node = "AAA";
        int counter = 0;

        while (!node.equals("ZZZ")) {
            for (int direction : path) {
                node = map.get(node)[direction];
            }
            counter++;
        }
        return counter * path.length;
    }

    private Map<String, String[]> initNodeMap(Scanner scanner) {
        Map<String, String[]> map = new HashMap<>(1500);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String start = line.substring(0, 3);

            map.put(start, new String[] {line.substring(7, 10), line.substring(12, 15)});
        }

        return map;
    }

    private long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
