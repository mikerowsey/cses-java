#!/usr/bin/env bash

set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 ProblemName" >&2
  exit 1
fi

problem_name="$1"

if [[ ! "$problem_name" =~ ^[A-Z][A-Za-z0-9]*$ ]]; then
  echo "Error: ProblemName must be a Java-style class name, for example WeirdAlgorithm" >&2
  exit 1
fi

root_dir="$(cd "$(dirname "$0")" && pwd)"
main_dir="$root_dir/app/src/main/java/cses"
test_dir="$root_dir/app/src/test/java/cses"
case_dir="$root_dir/app/src/test/resources/cases/$problem_name"
main_file="$main_dir/$problem_name.java"
test_file="$test_dir/${problem_name}Test.java"

if [[ -e "$main_file" || -e "$test_file" ]]; then
  echo "Error: $problem_name already exists" >&2
  exit 1
fi

mkdir -p "$main_dir" "$test_dir" "$case_dir"

sed "s/ProblemTemplate/$problem_name/g" "$root_dir/app/src/main/java/cses/ProblemTemplate.java" > "$main_file"

cat > "$test_file" <<EOF
package cses;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ${problem_name}Test {
    @Disabled("Add sample cases under app/src/test/resources/cases/$problem_name")
    @ParameterizedTest(name = "case: {0}")
    @MethodSource("cases")
    void solvesKnownCases(Path inputFile) throws Exception {
        String input = ProblemIoTestSupport.readInput(inputFile);
        String expected = ProblemIoTestSupport.readExpected(inputFile);
        String output = ProblemIoTestSupport.runProblem("$problem_name", input);

        assertEquals(expected, output);
    }

    static Stream<Path> cases() throws Exception {
        return ProblemIoTestSupport.caseFiles("$problem_name");
    }
}
EOF

cat > "$case_dir/.gitkeep" <<'EOF'
EOF

echo "Created: $main_file"
echo "Created: $test_file"
echo "Created: $case_dir/.gitkeep"
