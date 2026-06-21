package cses;

import java.io.IOException;

public class WeirdAlgorithm {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        FastWriter writer = new FastWriter();

        long n = scanner.nextLong();
        writer.print(n);

        while (n > 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n = n * 3 + 1;
            }
            writer.print(" " + n);
        }

        writer.println("");
        writer.flush();
    }
}
