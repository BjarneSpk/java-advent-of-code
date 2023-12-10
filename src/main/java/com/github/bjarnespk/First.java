package com.github.bjarnespk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

public class First {

    public static void main(String[] args) {
        try (var rs = Objects.requireNonNull(
                First.class.getResourceAsStream("/com/github/bjarnespk/input_first.txt"));
             var isr = new InputStreamReader(rs);
             var in = new BufferedReader(isr)) {
            String line;
            int sum = 0, sum2 = 0;
            while ((line = in.readLine()) != null) {
                int digitSum = getDigitSum(line);
                sum += digitSum;
                sum2 += getDigitSum2(line);
            }
            System.out.println("First Task: " + sum);
            System.out.println("Second Task: " + sum2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getDigitSum(String line) {
        String left = "", right = "";
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                left = line.substring(i, i + 1);
                break;
            }
        }
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i))) {
                right = line.substring(i, i + 1);
                break;
            }
        }
        return Integer.parseInt(left + right);
    }

    private static int getDigitSum2(String line) {
        int left = 0, right = 0;
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                left = line.charAt(i) - '0';
                break;
            }
            Optional<Integer> opt = getNumberFromString(String::startsWith, line.substring(i));
            if (opt.isPresent()) {
                left = opt.get();
                break;
            }

        }
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i))) {
                right = line.charAt(i) - '0';
                break;
            }
            Optional<Integer> opt = getNumberFromString(String::endsWith, line.substring(0, i + 1));
            if (opt.isPresent()) {
                right = opt.get();
                break;
            }
        }
        return left * 10 + right;
    }

    private static Optional<Integer> getNumberFromString(BiPredicate<String, String> pred, String line) {
        String[] nums = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 0; i < nums.length; i++) {
            if (pred.test(line, nums[i])) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
