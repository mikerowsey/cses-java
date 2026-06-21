package cses;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    @Test
    void helpShowsRunAndBundleHints() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));
            CsesHelp.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String rendered = output.toString();
        assertTrue(rendered.contains("Run a problem:"));
        assertTrue(rendered.contains("Bundle a single-file Main.java for submission:"));
    }
}
