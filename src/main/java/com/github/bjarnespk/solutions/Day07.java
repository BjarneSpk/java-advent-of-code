package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Day07 implements DayTemplate {

    private static class Hand {

        private final long hash;
        private final int multiplier;

        public Hand(String hand, int multiplier, Part part) {
            if (part == Part.PART_ONE) {
                this.hash = calculateHash(hand);
            } else {
                this.hash = calculateHashTwo(hand);
            }
            this.multiplier = multiplier;
        }

        private long calculateHashTwo(String hand) {
            long hash = 0;
            HashMap<Character, Integer> map = new HashMap<>();
            int joker = 0;
            for (int i = 0; i < hand.length(); i++) {
                char c = hand.charAt(i);
                map.put(c, (map.get(c) != null) ? map.get(c) + 1 : 1);
                hash += (long) Math.pow(10, (4 - i) * 2) * switch (c) {
                    case '2' -> 2;
                    case '3' -> 3;
                    case '4' -> 4;
                    case '5' -> 5;
                    case '6' -> 6;
                    case '7' -> 7;
                    case '8' -> 8;
                    case '9' -> 9;
                    case 'T' -> 10;
                    case 'J' -> {
                        joker++;
                        yield 1;
                    }
                    case 'Q' -> 11;
                    case 'K' -> 12;
                    default -> 13;
                };
            }
            return hash + getHandValue(map, joker);
        }

        private static long getHandValue(HashMap<Character, Integer> map, int joker) {
            if (map.containsValue(5)) {
                return 6 * (long) Math.pow(10, 10);
            }
            if (map.containsValue(4)) {
                return (joker > 0 ? 6 : 5) * (long) Math.pow(10, 10);
            }
            if (map.containsValue(3)) {
                if (map.containsValue(2)) {
                    return (joker > 0 ? 6 : 4) * (long) Math.pow(10, 10);
                }
                if (joker == 1 || joker == 3) {
                    return 5 * (long) Math.pow(10, 10);
                }
                return 3 * (long) Math.pow(10, 10);

            }
            if (map.containsValue(2)) {
                int num = 0;
                for (int i : map.values()) {
                    if (i == 2) {
                        num++;
                    }
                }
                if (num == 2) {
                    if (joker > 0) {
                        return (3 + joker) * (long) Math.pow(10, 10);
                    }
                    return 2 * (long) Math.pow(10, 10);
                }
                return (joker > 0 ? 3 : 1) * (long) Math.pow(10, 10);
            }
            if (map.containsKey('J')) {
                return (long) Math.pow(10, 10);
            }
            return 0;
        }

        private long calculateHash(String hand) {
            long hash = 0;
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < hand.length(); i++) {
                char c = hand.charAt(i);
                map.put(c, (map.get(c) != null) ? map.get(c) + 1 : 1);
                hash += (long) Math.pow(10, (4 - i) * 2) * switch (c) {
                    case '2' -> 1;
                    case '3' -> 2;
                    case '4' -> 3;
                    case '5' -> 4;
                    case '6' -> 5;
                    case '7' -> 6;
                    case '8' -> 7;
                    case '9' -> 8;
                    case 'T' -> 9;
                    case 'J' -> 10;
                    case 'Q' -> 11;
                    case 'K' -> 12;
                    default -> 13;
                };
            }
            if (map.containsValue(5)) {
                hash += 6 * (long) Math.pow(10, 10);
            } else if (map.containsValue(4)) {
                hash += 5 * (long) Math.pow(10, 10);
            } else if (map.containsValue(3)) {
                if (map.containsValue(2)) {
                    hash += 4 * (long) Math.pow(10, 10);
                } else {
                    hash += 3 * (long) Math.pow(10, 10);
                }
            } else if (map.containsValue(2)) {
                int num = 0;
                for (int i : map.values()) {
                    if (i == 2) {
                        num++;
                    }
                }
                if (num == 2) {
                    hash += 2 * (long) Math.pow(10, 10);
                } else {
                    hash += (long) Math.pow(10, 10);
                }
            }
            return hash;
        }

        public long getHash() {
            return this.hash;
        }

        public int getMultiplier() {
            return multiplier;
        }
    }

    @Override
    public String solve(Part part, Scanner scanner) {
        Set<Hand> hands = new TreeSet<>(Comparator.comparing(Hand::getHash));
        while (scanner.hasNext()) {
            hands.add(new Hand(scanner.next(), scanner.nextInt(), part));
        }
        long result = 0;
        int i = 1;
        for (Hand hand : hands) {
            result += (long) i++ * hand.getMultiplier();
        }
        return String.valueOf(result);
    }
}
