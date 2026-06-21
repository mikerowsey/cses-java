package cses;

import java.io.IOException;

public class IncreasingArray {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        FastWriter writer = new FastWriter();

        int n = scanner.nextInt();
        long[] array = new long[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextLong();
        }

        long moves = 0;
        for (int i = 1; i < n; i++) {
            if (array[i] < array[i - 1]) {
                moves += array[i - 1] - array[i];
                array[i] = array[i - 1];
            }
        }
        writer.println(moves);

        writer.flush();
    }
}
