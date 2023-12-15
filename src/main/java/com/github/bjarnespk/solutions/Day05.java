package com.github.bjarnespk.solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Day05 {

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

    private static final String PATH = "/com/github/bjarnespk/input/input_05.txt";

    public Day05() {
        // partOne();
        partTwo();
    }

    private void partTwo() {
        try (var rs = getClass().getResourceAsStream(PATH);
             var isr = new InputStreamReader(Objects.requireNonNull(rs));
             var in = new BufferedReader(isr)) {
            System.out.println(getMinimumLocationTwo(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<Tuple> getInitialSeeds(BufferedReader in) throws IOException {
        String firstLine = in.readLine();
        long[] nums = Arrays.stream(firstLine.substring(7).split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        Set<Tuple> set = new TreeSet<>();

        for (int i = 0; i < nums.length; i += 2) {
            set.add(new Tuple(nums[i], nums[i + 1]));
        }
        in.readLine();
        return set;
    }

    private long getMinimumLocationTwo(final BufferedReader in) throws IOException {
        final Set<Tuple> currentSeeds = getInitialSeeds(in);

        String line;
        while ((line = in.readLine()) != null) {
            if (Character.isDigit(line.charAt(0))) {
                final Set<Tuple> tempSeeds = new TreeSet<>();
                do {
                    extracted(currentSeeds, line, tempSeeds);
                } while ((line = in.readLine()) != null && !line.isBlank());
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
        final Tuple tuple = it.next();
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

    private void partOne() {
        try (var rs = getClass().getResourceAsStream(PATH);
             var isr = new InputStreamReader(Objects.requireNonNull(rs));
             var in = new BufferedReader(isr)) {
            System.out.println(getMinimumLocation(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getMinimumLocation(BufferedReader in) throws IOException {
        String firstLine = in.readLine();
        long[] nums = Arrays.stream(firstLine.substring(7).split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        in.readLine();
        String line;
        Set<Integer> set = new HashSet<>();
        while ((line = in.readLine()) != null) {
            set.clear();
            while ((line = in.readLine()) != null && !line.isBlank()) {
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

    public static void main(String[] args) {
        new Day05();
    }
}
