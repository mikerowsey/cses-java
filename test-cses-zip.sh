#!/usr/bin/env bash

set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 ProblemClass path/to/tests.zip" >&2
  exit 1
fi

problem_class="$1"
zip_path="$2"

if [[ ! "$problem_class" =~ ^[A-Z][A-Za-z0-9]*$ ]]; then
  echo "Error: ProblemClass must be a Java-style class name, for example WeirdAlgorithm" >&2
  exit 1
fi

if [[ ! -f "$zip_path" ]]; then
  echo "Error: zip file not found: $zip_path" >&2
  exit 1
fi

if ! command -v unzip >/dev/null 2>&1; then
  echo "Error: unzip is required but was not found on PATH" >&2
  exit 1
fi

root_dir="$(cd "$(dirname "$0")" && pwd)"
work_dir="$(mktemp -d "$root_dir/.tmp-cses-zip.XXXXXX")"

cleanup() {
  rm -rf "$work_dir"
}
trap cleanup EXIT

echo "Preparing build..."
"$root_dir/gradlew" -q :app:classes

echo "Unzipping test data..."
unzip -q "$zip_path" -d "$work_dir/unpacked"

mapfile -t in_files < <(find "$work_dir/unpacked" -type f -name '*.in' | sort)
if [[ ${#in_files[@]} -eq 0 ]]; then
  echo "Error: no .in files found in zip: $zip_path" >&2
  exit 1
fi

total=0
passed=0
failed=0
missing=0

for in_file in "${in_files[@]}"; do
  out_file="${in_file%.in}.out"
  case_name="$(basename "${in_file%.in}")"
  total=$((total + 1))

  if [[ ! -f "$out_file" ]]; then
    echo "MISSING: $case_name (missing $(basename "$out_file"))"
    missing=$((missing + 1))
    continue
  fi

  actual_file="$work_dir/actual.out"
  if ! java -cp "$root_dir/app/build/classes/java/main" "cses.$problem_class" < "$in_file" > "$actual_file"; then
    echo "FAIL: $case_name (program error)"
    failed=$((failed + 1))
    continue
  fi

  if cmp -s <(tr -d '\r' < "$out_file") <(tr -d '\r' < "$actual_file"); then
    echo "PASS: $case_name"
    passed=$((passed + 1))
  else
    echo "FAIL: $case_name"
    failed=$((failed + 1))
    diff -u <(tr -d '\r' < "$out_file") <(tr -d '\r' < "$actual_file") | head -n 40 || true
  fi
done

echo
echo "Summary: total=$total passed=$passed failed=$failed missing=$missing"

if [[ $failed -gt 0 || $missing -gt 0 ]]; then
  exit 1
fi
