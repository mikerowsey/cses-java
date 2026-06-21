#!/usr/bin/env bash

set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 ProblemClass" >&2
  exit 1
fi

problem_class="$1"

if [[ ! "$problem_class" =~ ^[A-Z][A-Za-z0-9]*$ ]]; then
  echo "Error: ProblemClass must be a Java-style class name, for example WeirdAlgorithm" >&2
  exit 1
fi

root_dir="$(cd "$(dirname "$0")" && pwd)"
"$root_dir/gradlew" :app:test --tests "cses.${problem_class}Test"
