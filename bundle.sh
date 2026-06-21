#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: ./bundle.sh ProblemClass [gradle args...]" >&2
  exit 1
fi

problem_class="$1"
shift

./gradlew :app:bundleMain -Pprob="$problem_class" "$@"
