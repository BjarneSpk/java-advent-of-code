package com.github.bjarnespk.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public interface DayTemplate {

    record Result(double time, String result) { }

    default Result timer(Part part, InputStream in) {
        long startTime = System.nanoTime();
        String result;
        try {
            result = solve(part, in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        long runTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        return new Result(runTime, result);
    }

    default String solve(Part part, InputStream in) throws IOException {
        try (Scanner scanner = new Scanner(in)) {
            return solve(part, scanner);
        }
    }

    default String solve(Part part, Scanner scanner) {
        throw new IllegalStateException("Wrong method i guess?");
    }
}
