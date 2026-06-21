# CSES Dataset Strategy (Future)

This note captures a scalable test-data strategy for maintaining CSES solutions across multiple languages.

## Goals

- Keep each language repository small and fast to clone.
- Keep daily development feedback fast.
- Share comprehensive test packs across Java, C++, Python, and other repositories.
- Ensure reproducibility through versioned datasets.

## Proposed Architecture

1. Keep small, curated development tests in each solution repository.
2. Store comprehensive CSES downloads as zip archives on a private server (Tailscale VPN).
3. Use one zip archive per problem and dataset version.
4. Maintain a manifest with checksums and metadata.

Suggested naming:

- `ProblemName-v1.zip`
- `ProblemName-v2.zip`

Suggested manifest fields:

- problem
- version
- sha256
- size
- createdAt
- source

## Why This Works

- Fast local iteration: only small tests run during development.
- Strong verification: large comprehensive packs run on demand.
- Multi-language reuse: the same in/out zip packs validate implementations in different languages.
- Better storage management: large binaries stay out of main solution repositories.

## Java Workflow In This Repository

- Fast development test:
  - `./test-dev.sh ProblemName`
- Comprehensive run against a downloaded zip:
  - `./test-cses-zip.sh ProblemName /path/to/ProblemName.zip`

The comprehensive script unzips to a temporary folder, runs all matching `.in/.out` pairs, prints a summary, and deletes the temporary folder automatically.

## Guardrails

- Verify terms and licensing for test data usage and redistribution.
- Treat private server access as sensitive infrastructure.
- Validate zip integrity before use (checksum check against manifest).
- Keep committed tests readable and minimal (sample + edge + regression).

## Open Next Steps

1. Add a download-and-cache helper that pulls zip files from the private dataset server.
2. Add manifest verification (`sha256`) before running comprehensive tests.
3. Optionally add a `--keep-temp-on-fail` mode for debugging failed comprehensive cases.
