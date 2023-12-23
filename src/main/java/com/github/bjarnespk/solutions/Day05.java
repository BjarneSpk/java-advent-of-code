package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Day05 implements DayTemplate {

    private record Tuple(long start, long range) implements Comparable<Tuple> {

        @Override
        public int compareTo(Tuple other) {
            if (this.start < other.start) {
                return -1;
            } else if (this.start > other.start) {
                return 1;
            } else {
                return Long.compare(this.range, other.range);
            }
        }
    }

    @Override
    public String solve(Part part, Scanner scanner) {
        if (part == Part.PART_ONE) {
            return String.valueOf(getMinimumLocation(scanner));
        }
        return String.valueOf(getMinimumLocationTwo(scanner));
    }

    private Set<Tuple> getInitialSeeds(Scanner scanner) {
        String firstLine = scanner.nextLine();
        long[] nums = Arrays.stream(firstLine.substring(7).split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        Set<Tuple> set = new TreeSet<>();

        for (int i = 0; i < nums.length; i += 2) {
            set.add(new Tuple(nums[i], nums[i + 1]));
        }
        scanner.nextLine();
        return set;
    }

    private long getMinimumLocationTwo(Scanner scanner) {
        final Set<Tuple> currentSeeds = getInitialSeeds(scanner);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (Character.isDigit(line.charAt(0))) {
                final Set<Tuple> tempSeeds = new TreeSet<>();
                do {
                    extracted(currentSeeds, line, tempSeeds);
                } while (scanner.hasNextLine() && !(line = scanner.nextLine()).isBlank());
                currentSeeds.addAll(tempSeeds);
            }
        }
        return currentSeeds.stream().mapToLong(Tuple::start).min().orElse(-1);
    }

    private void extracted(Set<Tuple> currentSeeds, String line, Set<Tuple> tempSeeds) {
        final long[] transformations = getTransformations(line);

        final Iterator<Tuple> it = currentSeeds.iterator();
        while (it.hasNext()) {
            transform(tempSeeds, transformations, it);
        }
    }

    private void transform(Set<Tuple> tempSeeds, long[] transformations, Iterator<Tuple> it) {
        Tuple tuple = it.next();
        long lower = tuple.start();
        long upper = tuple.start() + tuple.range() - 1;
        long range = tuple.range();

        long move = transformations[0] - transformations[1];

        long lowerTrans = transformations[1];
        long upperTrans = transformations[1] + transformations[2] - 1;
        long rangeTrans = transformations[2];

        if ((lower >= lowerTrans) && (upper <= upperTrans)) {
            tempSeeds.add(new Tuple(lower + move, range));
            it.remove();
        } else if ((lower < lowerTrans) && (upper > upperTrans)) {
            tempSeeds.add(new Tuple(lowerTrans + move, rangeTrans));
            tempSeeds.add(new Tuple(lower, lowerTrans - lower));
            tempSeeds.add(new Tuple(upperTrans + 1, upper - upperTrans));
            it.remove();
        } else if ((lower < lowerTrans) && (upper >= lowerTrans) && (upper <= upperTrans)) {
            tempSeeds.add(new Tuple(lower, lowerTrans - lower));
            tempSeeds.add(new Tuple(lowerTrans + move, upper - lowerTrans + 1));
            it.remove();
        } else if ((lower >= lowerTrans) && (lower <= upperTrans) && (upper > upperTrans)) {
            tempSeeds.add(new Tuple(lower + move, upperTrans - lower + 1));
            tempSeeds.add(new Tuple(upperTrans + 1, upper - upperTrans));
            it.remove();
        }
    }

    private static long[] getTransformations(String line) {
        return Arrays.stream(line.split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    private long getMinimumLocation(Scanner scanner) {
        String firstLine = scanner.nextLine();
        long[] nums = Arrays.stream(firstLine.substring(7).split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        scanner.nextLine();
        Set<Integer> set = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            set.clear();
            while (scanner.hasNextLine() && !(line = scanner.nextLine()).isBlank()) {
                long[] transformations = Arrays.stream(line.split(" "))
                        .mapToLong(Long::parseLong)
                        .toArray();
                for (int i = 0; i < nums.length; i++) {
                    if (transformations[1] <= nums[i]
                            && transformations[1] + transformations[2] > nums[i]
                            && !set.contains(i)) {
                        nums[i] = transformations[0] + nums[i] - transformations[1];
                        set.add(i);
                    }
                }
            }
        }
        return Arrays.stream(nums).min().orElse(-1);
    }
}
