package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.App;
import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class Day03 implements DayTemplate {

    @Override
    public String solve(Part part, InputStream in) throws IOException {
        if (part == Part.PART_TWO) {
            bytes = in.readAllBytes();
            return String.valueOf(getGearRatio());
        }
        byte[][] bytes2 = readLinesAsArray(in);
        return String.valueOf(getPartSum(bytes2));
    }

    private class ByteTuple implements Comparable<ByteTuple> {

        private final LinkedList<Integer> indices;
        private final int index;

        public ByteTuple(int index) {
            indices = new LinkedList<>();
            this.index = index;
            indices.add(index);
            calculateNumber();
        }

        public int getNumber() {
            int num = 0;
            for (int i : indices) {
                num *= 10;
                num += bytes[i] - '0';
            }
            return num;
        }

        private void calculateNumber() {
            int cursor = index - 1;
            while (cursor >= 0 && Character.isDigit(bytes[cursor])) {
                indices.addFirst(cursor--);
            }
            cursor = index + 1;
            while (cursor < bytes.length && Character.isDigit(bytes[cursor])) {
                indices.addLast(cursor++);
            }
        }

        @Override
        public int compareTo(ByteTuple o) {
            int sizeComparison = Integer.compare(indices.size(), o.indices.size());
            if (sizeComparison != 0) {
                return sizeComparison;
            }

            Iterator<Integer> thisIterator = indices.iterator();
            Iterator<Integer> otherIterator = o.indices.iterator();

            while (thisIterator.hasNext() && otherIterator.hasNext()) {
                int thisIndex = thisIterator.next();
                int otherIndex = otherIterator.next();
                int indexComparison = Integer.compare(thisIndex, otherIndex);

                if (indexComparison != 0) {
                    return indexComparison;
                }
            }
            return 0;
        }

    }

    private byte[] bytes;
    private static final int SIZE = 140;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static byte[][] readLinesAsArray(InputStream in) throws IOException {
        byte[][] bytes2 = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (in.read(bytes2[i]) != SIZE) {
                throw new RuntimeException("Not enough bytes");
            }
            in.read();
        }
        return bytes2;
    }

    private int getGearRatio() {
        int sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '*') {
                sum += calculateRatio(i);
            }
        }
        return sum;
    }

    private int calculateRatio(int index) {
        Set<ByteTuple> set = new TreeSet<>();
        for (int i = -(SIZE + 1); i <= (SIZE + 1); i += (SIZE + 1)) {
            for (int j = -1; j <= 1; j++) {
                final int cursor = index - i - j;
                if (cursor < 0 || cursor >= bytes.length) {
                    continue;
                }
                if (Character.isDigit(bytes[cursor])) {
                    ByteTuple b = new ByteTuple(cursor);
                    set.add(b);
                }
            }
        }
        if (set.size() != 2) {
            return 0;
        }
        return set.stream().mapToInt(ByteTuple::getNumber).reduce(1, Math::multiplyExact);
    }

    private int getPartSum(byte[][] bytes2) {
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int skips = 0;
                while (j + skips < SIZE && Character.isDigit(bytes2[i][j + skips])) {
                    skips++;
                }
                if (isValidRegion(bytes2, i, j, skips)) {
                    sum += getNumber(bytes2[i], j, skips);
                }
                j += skips;
            }
        }
        return sum;
    }

    private int getNumber(byte[] bytes2, int j, int skips) {
        int num = 0;
        for (int i = j; i < j + skips; i++) {
            num *= 10;
            num += bytes2[i] - '0';
        }
        return num;
    }

    private boolean isValidRegion(byte[][] bytes2, int i, int j, int skips) {
        if (j > 0 && bytes2[i][j - 1] != '.') {
            return true;
        }
        if (j + skips < (SIZE - 1) && bytes2[i][j + skips] != '.') {
            return true;
        }
        for (int k = 0; k < skips + 2; k++) {
            if (j + k == 0 || j + k >= SIZE) {
                continue;
            }
            if (i > 0 && bytes2[i - 1][j - 1 + k] != '.') {
                return true;
            }
            if (i < SIZE - 1 && bytes2[i + 1][j - 1 + k] != '.') {
                return true;
            }
        }
        return false;
    }
}
