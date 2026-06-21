#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: ./run.sh ProblemClass [gradle args...]" >&2
  exit 1
fi

problem_class="$1"
shift

./gradlew :app:runProblem -Pprob="$problem_class" "$@"
