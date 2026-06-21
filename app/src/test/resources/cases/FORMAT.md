# Case Files

Keep committed tests small and readable.

Use one file per case:

- `src/test/resources/cases/<ProblemName>/<case>.in`
- `src/test/resources/cases/<ProblemName>/<case>.out`

Recommended cases:

- sample
- edge-small
- edge-large
- regression-<id>

For comprehensive downloaded datasets, keep zip packs external and run them with:

- `./test-cses-zip.sh ProblemName /path/to/ProblemName.zip`

See also:

- `docs/dataset-strategy.md`
