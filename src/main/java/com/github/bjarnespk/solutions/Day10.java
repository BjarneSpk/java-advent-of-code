package com.github.bjarnespk.solutions;

import com.github.bjarnespk.main.DayTemplate;
import com.github.bjarnespk.main.Part;

import java.io.IOException;
import java.io.InputStream;

public class Day10 implements DayTemplate {
    @Override
    public String solve(Part part, InputStream in) throws IOException {
        if (part == Part.PART_ONE) {
            return String.valueOf(solveOne(in));
        }
        return String.valueOf(solveTwo(in));
    }

    private int solveTwo(InputStream in) {
        return 0;
    }

    private int solveOne(InputStream in) throws IOException {
        in.read();
        return 0;
    }
}
