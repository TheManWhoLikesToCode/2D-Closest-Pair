import java.util.Arrays;

public class runTime{

    // plot points
    public static void main(String[] args) {

        long startTime, endTime, duration;
        int pointsToTest = 5000;
        int trials = 1000;
        double [] fastAlgoTime = new double [trials];
        double [] slowAlgoTime = new double [trials];

        for (int i = 0; i < trials; i++) {

            Solver2DClosestPair[] P = Solver2DClosestPair.generatePoints(pointsToTest);
            Solver2DClosestPair[] Q = P.clone();
            Arrays.sort(P, Solver2DClosestPair::compareX);
            Arrays.sort(Q, Solver2DClosestPair::compareY);

            // Start time
            startTime = System.nanoTime();
            double[] fastAlgoPoints = Solver2DClosestPair.efficientClosetPair(P, Q);
            // End time
            endTime = System.nanoTime();
            // Duration
            duration = (endTime - startTime);

            // Add duration to array
            fastAlgoTime[i] = duration;

            // Start time
            startTime = System.nanoTime();
            double[] bruteForcePoints = Solver2DClosestPair.exhaustiveSearch(P);
            // End time
            endTime = System.nanoTime();
            // Duration
            duration = (endTime - startTime);

            // Add duration to array
            slowAlgoTime[i] = duration;

        }

        // Print results
        System.out.println("Fast Algorithm: " + Arrays.toString(fastAlgoTime));
        System.out.println("Slow Algorithm: " + Arrays.toString(slowAlgoTime));

        // Calculate average
        int fastAlgoAverage = 0;
        int slowAlgoAverage = 0;
        
        for (int i = 0; i < trials; i++) {
            fastAlgoAverage += fastAlgoTime[i];
            slowAlgoAverage += slowAlgoTime[i];
        }

        fastAlgoAverage /= trials;
        slowAlgoAverage /= trials;

        System.out.println("Fast Algorithm Average: " + fastAlgoAverage);
        System.out.println("Slow Algorithm Average: " + slowAlgoAverage);

    }
}
