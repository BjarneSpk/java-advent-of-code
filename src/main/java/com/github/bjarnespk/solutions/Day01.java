package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiPredicate;

public class Day01 implements DayTemplate {

    @Override
    public String solve(Part part, Scanner scanner) {
        if (Objects.requireNonNull(part) == Part.PART_ONE) {
            int sum = 0;
            while (scanner.hasNext()) {
                sum += getDigitSum(scanner.nextLine());
            }
            return String.valueOf(sum);
        }
        int sum = 0;
        while (scanner.hasNext()) {
            sum += getDigitSum2(scanner.nextLine());
        }
        return String.valueOf(sum);
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
