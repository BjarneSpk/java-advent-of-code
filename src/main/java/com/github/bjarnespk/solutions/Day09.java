package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.App;
import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.util.Arrays;
import java.util.Scanner;

public class Day09 implements DayTemplate {

    @Override
    public String solve(Part part, Scanner scanner) {
        if (part == Part.PART_ONE) {
            return String.valueOf(solveOne(scanner));
        }
        return String.valueOf(solveTwo(scanner));
    }

    private int solveTwo(Scanner scanner) {
        int sum = 0;
        while (scanner.hasNextLine()) {
            sum += extrapolateBackwards(parseLine(scanner.nextLine()));
        }
        return sum;
    }

    private int extrapolateBackwards(int[] nums) {
        int[] subLine = new int[nums.length - 1];
        boolean isZero = true;
        for (int i = 1; i < nums.length; i++) {
            subLine[i - 1] = nums[i] - nums[i - 1];
            if (subLine[i - 1] != 0) {
                isZero = false;
            }
        }
        if (isZero) {
            return nums[0];
        } else {
            return nums[0] - extrapolateBackwards(subLine);
        }
    }

    private int solveOne(Scanner scanner) {
        int sum = 0;
        while (scanner.hasNextLine()) {
            sum += extrapolateForwards(parseLine(scanner.nextLine()));
        }
        return sum;
    }

    private int extrapolateForwards(int[] nums) {
        int[] subLine = new int[nums.length - 1];
        boolean isZero = true;
        for (int i = 1; i < nums.length; i++) {
            subLine[i - 1] = nums[i] - nums[i - 1];
            if (subLine[i - 1] != 0) {
                isZero = false;
            }
        }
        if (isZero) {
            return nums[nums.length - 1];
        } else {
            return extrapolateForwards(subLine) + nums[nums.length - 1];
        }
    }

    private int[] parseLine(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
