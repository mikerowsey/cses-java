package cses;

public class CsesHelp {
    public static void main(String[] args) {
        System.out.println("CSES Java workspace help");
        System.out.println();
        System.out.println("Run a problem:");
        System.out.println("  ./run.sh WeirdAlgorithm");
        System.out.println("  or ./gradlew :app:runProblem -Pprob=WeirdAlgorithm");
        System.out.println();
        System.out.println("Run a problem with piped input:");
        System.out.println("  echo 3 | ./run.sh WeirdAlgorithm --quiet");
        System.out.println();
        System.out.println("Bundle a single-file Main.java for submission:");
        System.out.println("  ./bundle.sh WeirdAlgorithm");
        System.out.println("  or ./gradlew :app:bundleMain -Pprob=WeirdAlgorithm");
        System.out.println();
        System.out.println("Template file:");
        System.out.println("  app/src/main/java/cses/ProblemTemplate.java");
    }
}
