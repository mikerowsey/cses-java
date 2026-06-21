package cses;

import java.io.IOException;

public class MissingNumber {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        FastWriter writer = new FastWriter();

        long n = scanner.nextInt();
        long expectedSum = n * (n + 1) / 2;
        long actualSum = 0;

        for (int i = 0; i < n - 1; i++) {
            actualSum += scanner.nextInt();
        }

        writer.println(expectedSum - actualSum);
        writer.flush();
    }
}
