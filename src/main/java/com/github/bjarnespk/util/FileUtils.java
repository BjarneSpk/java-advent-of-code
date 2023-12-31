package com.github.bjarnespk.util;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static byte[][] readLinesAsArray(InputStream in, int size) {
        try {
            return readLines(in, size);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static byte[][] readLines(InputStream in, int size) throws IOException{
        byte[][] bytes = new byte[size][size];
        for (int i = 0; i < size; i++) {
            if (in.read(bytes[i]) != size) {
                throw new RuntimeException("Not enough bytes");
            }
            in.read();
        }
        return bytes;
    }
}
