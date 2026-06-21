package cses;

import java.io.IOException;

public class Repetitions {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        FastWriter writer = new FastWriter();

        String dna = scanner.next();
        if (dna == null || dna.isEmpty()) {
            writer.println(0);
            writer.flush();
            return;
        }

        int longest = 1;
        int streak = 1;

        for (int i = 1; i < dna.length(); i++) {
            if (dna.charAt(i) == dna.charAt(i - 1)) {
                streak++;
            } else {
                streak = 1;
            }
            if (streak > longest) {
                longest = streak;
            }
        }

        writer.println(longest);
        writer.flush();
    }
}
