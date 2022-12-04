import java.util.Arrays;

public class validation {

    public static void main(String[] args) {
        // Check if the fast algorithm returns the same result as the slow algorithm
        int pointsToTest = 4;
        int trials = 100;
        int highestRun = 0;

        // While loop to check if the fast algorithm returns the same result as the slow algorithm
        for (int i = 0; i < trials; i++) {

            points[] P = points.generatePoints(pointsToTest);
            points[] Q = P.clone();
            Arrays.sort(P, points::compareX);
            Arrays.sort(Q, points::compareY);

            // Brute force algorithm
            float[] bruteForcePoints = points.exhaustiveSearch(P);
            // Fast algorithm
            float[] fastAlgoPoints = points.efficientClosetPair(P, Q);
            if(points.runCounter > highestRun) {
                highestRun = points.runCounter;
            }
            points.runCounter = 0;

            // Check if the fast algorithm returns the same result as the slow algorithm
            if (fastAlgoPoints[4] != bruteForcePoints[4]) {

                // Print the points
                System.out.println("Points: ");
                for (int j = 0; j < pointsToTest; j++) {
                    System.out.println(P[j].toString());
                }


                System.out.println("The fast algorithm does not return the same result as the slow algorithm");
                System.out.println("Slow Algorithm: (" + bruteForcePoints[0] +", "+ bruteForcePoints[1] + ") and (" + bruteForcePoints[2] +", "+ bruteForcePoints[3] + ")");
                System.out.println("Distance: " + bruteForcePoints[4]);
                System.out.println("Fast Algorithm: (" + fastAlgoPoints[0] +", "+ fastAlgoPoints[1] + ") and (" + fastAlgoPoints[2] +", "+ fastAlgoPoints[3] + ")");
                System.out.println("Distance: " + fastAlgoPoints[4]);

                // It took x trials to find a problem
                System.out.println("It took " + i + " trials to find a problem");
                break;
            }else if(i == trials - 1){
                System.out.println("The fast algorithm returns the same result as the slow algorithm");
            }
        }

        System.out.println("The highest number of runs was: " + highestRun);


    }
    
}
