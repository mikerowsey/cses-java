# cses-java

Java solutions and utilities for the CSES Problem Set.

## Requirements

- Java 11 (toolchain is configured in Gradle)
- Bash (for helper scripts)

## Project Layout

- Root project: Gradle multi-project build
- App module: `app`
- Problem sources: `app/src/main/java/cses`
- Tests: `app/src/test/java/cses`

## Quick Start

Run tests:

```bash
./gradlew clean test
```

Show workspace help:

```bash
./gradlew :app:run
```

## Running a Problem

Use the helper script:

```bash
./run.sh WeirdAlgorithm
```

Or use Gradle directly:

```bash
./gradlew :app:runProblem -Pprob=WeirdAlgorithm
```

Run with piped input:

```bash
echo 3 | ./run.sh WeirdAlgorithm --quiet
```

## Bundling for CSES Submission

Generate a single-file `Main.java` for a selected problem:

```bash
./bundle.sh WeirdAlgorithm
```

Or:

```bash
./gradlew :app:bundleMain -Pprob=WeirdAlgorithm
```

Output file:

- `app/build/distributions/Main.java`

## Adding a New Problem

1. Copy `app/src/main/java/cses/ProblemTemplate.java` to a new class, for example `YourProblem.java`.
2. Implement `main` in package `cses`.
3. Run it with:

```bash
./run.sh YourProblem
```
