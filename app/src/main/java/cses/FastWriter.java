package cses;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FastWriter {
    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) throws IOException {
        bw.append(String.valueOf(object));
    }

    public void println(Object object) throws IOException {
        print(object);
        bw.append("\n");
    }

    public void flush() throws IOException {
        bw.flush();
    }
}
