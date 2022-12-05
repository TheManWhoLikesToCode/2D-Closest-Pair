import java.util.Arrays;

public class failureAnal{

    public static void main(String[] args) {
        // Make a new point
        Solver2DClosestPair p1 = new Solver2DClosestPair(14, 85);
        Solver2DClosestPair p2 = new Solver2DClosestPair(24, 67);
        Solver2DClosestPair p3 = new Solver2DClosestPair(25, 70);
        Solver2DClosestPair p4 = new Solver2DClosestPair(32, 71);
        Solver2DClosestPair p5 = new Solver2DClosestPair(55, 83);
        Solver2DClosestPair p6 = new Solver2DClosestPair(79, 70);
        Solver2DClosestPair p7 = new Solver2DClosestPair(84, 8);
        Solver2DClosestPair p8 = new Solver2DClosestPair(91, 50);
        Solver2DClosestPair p9 = new Solver2DClosestPair(95, 13);
        Solver2DClosestPair p10 = new Solver2DClosestPair(97, 31);


        // Make an array of points
        Solver2DClosestPair[] P = {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10};
        Solver2DClosestPair[] Q = P.clone();

        // Sort the array by x
        Arrays.sort(P, Solver2DClosestPair::compareX);
        // Sort the array by y in non decreasing order
        Arrays.sort(Q, Solver2DClosestPair::compareY);

        // Call the fast algorithm
        double[] fastAlgoPoints = Solver2DClosestPair.efficientClosetPair(P, Q);

        // Call brute force algorithm
        double[] bruteForcePoints = Solver2DClosestPair.exhaustiveSearch(P);

        // Print outputs
        System.out.println("Fast Algorithm: (" + fastAlgoPoints[0] +", "+ fastAlgoPoints[1] + ") and (" + fastAlgoPoints[2] +", "+ fastAlgoPoints[3] + ")");
        System.out.println("Distance: " + fastAlgoPoints[4]);
        System.out.println("Slow Algorithm: (" + bruteForcePoints[0] +", "+ bruteForcePoints[1] + ") and (" + bruteForcePoints[2] +", "+ bruteForcePoints[3] + ")");
        System.out.println("Distance: " + bruteForcePoints[4]);
        
    }
}