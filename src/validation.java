import java.util.Arrays;

public class validation {

    public static void main(String[] args) {
        // Check if the fast algorithm returns the same result as the slow algorithm
        int pointsToTest = 100;
        int trials = 1000;
  


        // While loop to check if the fast algorithm returns the same result as the slow algorithm
        for (int i = 0; i < trials; i++) {

            Solver2DClosestPair[] P = Solver2DClosestPair.generatePoints(pointsToTest);
            Solver2DClosestPair[] Q = P.clone();
            Arrays.sort(P, Solver2DClosestPair::compareX);
            Arrays.sort(Q, Solver2DClosestPair::compareY);

            // Brute force algorithm
            double[] bruteForcePoints = Solver2DClosestPair.exhaustiveSearch(P);
            // Fast algorithm
            double[] fastAlgoPoints = Solver2DClosestPair.efficientClosetPair(P, Q);


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




    }
    
}
