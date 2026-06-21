package cses;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncreasingArrayTest {
    @ParameterizedTest(name = "case: {0}")
    @MethodSource("cases")
    void solvesKnownCases(Path inputFile) throws Exception {
        String input = ProblemIoTestSupport.readInput(inputFile);
        String expected = ProblemIoTestSupport.readExpected(inputFile);
        String output = ProblemIoTestSupport.runProblem("IncreasingArray", input);

        assertEquals(expected, output);
    }

    static Stream<Path> cases() throws Exception {
        return ProblemIoTestSupport.caseFiles("IncreasingArray");
    }
}
