package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.App;
import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;
import com.github.bjarnespk.util.FileUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day11 implements DayTemplate {

    private static byte[][] bytes;
    private static final int SIZE = 140;
    private final List<Node> galaxies = new ArrayList<>();

    private record Node(int y, int x) {
        public int getDistance(Node node) {
            return Math.abs(x - node.x) + Math.abs(y - node.y);
        }
    }

    @Override
    public String solve(Part part, InputStream in) {
        bytes = FileUtils.readLinesAsArray(in, SIZE);

        long expansionFactor = 2;
        if (part == Part.PART_TWO) {
            expansionFactor = 1000000;
            System.out.println(expansionFactor);
        }
        return String.valueOf(solve(expansionFactor));
    }

    private long solve(long expansionFactor) {
        Map<Character, Set<Integer>> freeLines = getFreeLines();

        expansionFactor -= 1;
        long distanceSum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Node first = galaxies.get(i);
                Node second = galaxies.get(j);
                long distance = first.getDistance(second);
                distance += (addEnlargedDistance(first, second, freeLines) * expansionFactor);
                distanceSum += distance;
            }
        }
        return distanceSum;
    }

    private long addEnlargedDistance(Node start, Node end, Map<Character, Set<Integer>> freeLines) {
        long enlargedLines = 0;

        int startX = Math.min(start.x(), end.x());
        int endX = Math.max(start.x(), end.x());

        for (int i = startX + 1; i < endX; i++) {
            if (freeLines.get('Y').contains(i)) {
                enlargedLines++;
            }
        }
        int startY = Math.min(start.y(), end.y());
        int endY = Math.max(start.y(), end.y());

        for (int i = startY + 1; i < endY; i++) {
            if (freeLines.get('X').contains(i)) {
                enlargedLines++;
            }
        }
        return enlargedLines;
    }

    private Map<Character, Set<Integer>> getFreeLines() {
        Map<Character, Set<Integer>> map = Map.of('X', new HashSet<>(), 'Y', new HashSet<>());
        for (int i = 0; i < SIZE; i++) {
            boolean xFree = true, yFree = true;
            for (int j = 0; j < SIZE; j++) {
                if (bytes[i][j] == '#') {
                    xFree = false;
                    galaxies.add(new Node(i, j));
                }
                if (bytes[j][i] == '#') {
                    yFree = false;
                }
            }
            if (xFree) {
                map.get('X').add(i);
            }
            if (yFree) {
                map.get('Y').add(i);
            }
        }
        return map;
    }
}
