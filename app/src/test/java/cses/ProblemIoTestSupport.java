package cses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ProblemIoTestSupport {
    private ProblemIoTestSupport() {
    }

    static String runProblem(String className, String input) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();

        try {
            System.setIn(testIn);
            System.setOut(new PrintStream(testOut, true, StandardCharsets.UTF_8));

            Class<?> problemClass = Class.forName("cses." + className);
            Method mainMethod = problemClass.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return testOut.toString(StandardCharsets.UTF_8).trim();
    }

    static Stream<Path> caseFiles(String problemName) throws IOException {
        Path basePath = Path.of("src", "test", "resources", "cases", problemName);
        if (!Files.isDirectory(basePath)) {
            return Stream.empty();
        }

        List<Path> files;
        try (Stream<Path> stream = Files.list(basePath)) {
            files = stream
                    .filter(path -> path.toString().endsWith(".in"))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .collect(Collectors.toList());
        }
        return files.stream();
    }

    static String readInput(Path inputFile) throws IOException {
        return Files.readString(inputFile, StandardCharsets.UTF_8);
    }

    static String readExpected(Path inputFile) throws IOException {
        String fileName = inputFile.getFileName().toString();
        String expectedFileName = fileName.substring(0, fileName.length() - 3) + ".out";
        Path expectedPath = inputFile.getParent().resolve(expectedFileName);
        return Files.readString(expectedPath, StandardCharsets.UTF_8).trim();
    }
}
