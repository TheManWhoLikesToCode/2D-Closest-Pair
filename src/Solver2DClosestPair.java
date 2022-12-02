import java.util.Arrays;

public class Solver2DClosestPair {

    // plot points
    public static void main(String[] args) {

        long startTime, endTime, duration;
        int pointsToTest = 5000;
        int trials = 1000;
        double [] fastAlgoTime = new double [trials];
        double [] slowAlgoTime = new double [trials];

        for (int i = 0; i < trials; i++) {

            points[] P = points.generatePoints(pointsToTest);
            points[] Q = P.clone();
            Arrays.sort(P, points::compareX);
            Arrays.sort(Q, points::compareY);

            // Start time
            startTime = System.nanoTime();
            float[] fastAlgoPoints = points.efficientClosetPair(P, Q);
            // End time
            endTime = System.nanoTime();
            // Duration
            duration = (endTime - startTime);

            // Add duration to array
            fastAlgoTime[i] = duration;

            // Start time
            startTime = System.nanoTime();
            float[] bruteForcePoints = points.exhaustiveSearch(P);
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
