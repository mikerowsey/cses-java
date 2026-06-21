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

Run a fast development test for one problem:

```bash
./test-dev.sh WeirdAlgorithm
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

Use the scaffold script to create the source file, a disabled test skeleton, and a case folder:

```bash
./new-problem.sh YourProblem
```

Then implement `main` in package `cses` and add small `.in/.out` cases under:

- `app/src/test/resources/cases/YourProblem/`

Run it with:

```bash
./run.sh YourProblem
```

## Comprehensive Tests From CSES Download

You can keep downloaded test packs zipped and run them on demand:

```bash
./test-cses-zip.sh WeirdAlgorithm /path/to/WeirdAlgorithm.zip
```

What this does:

- Builds classes once.
- Unzips into a temporary folder.
- Runs every `.in` against `cses.ProblemClass`.
- Compares against matching `.out`.
- Deletes the temporary folder automatically.

Recommended practice:

- Keep small dev/regression tests in this repo.
- Keep comprehensive zip packs outside this repo.
- Use a private dataset source (for example a Tailscale-hosted server) for cross-language reuse.

Future architecture notes:

- `docs/dataset-strategy.md`
