package com.github.bjarnespk.main;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public interface DayTemplate {

    default Result timer(Part part, Scanner scanner) {
        long startTime = System.nanoTime();
        String result = solve(part, scanner);
        long endTime = System.nanoTime();
        long runTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        return new Result(runTime, result);
    }

    String solve(Part part, Scanner scanner);
}
