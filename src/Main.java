import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Ask the user for the number of points to test
        System.out.println("How many points would you like to test?");
        Scanner scanner = new Scanner(System.in);
        int pointsToTest = scanner.nextInt();
        

        // While loop to enter the number of points to test
        while (pointsToTest < 2) {
            System.out.println("Please enter a number greater than 1");
            pointsToTest = scanner.nextInt();
        }
        scanner.close();
        
        // While loop to add points to a points array
        Solver2DClosestPair[] P = Solver2DClosestPair.generatePoints(pointsToTest);
        Solver2DClosestPair[] Q = P.clone();
        Arrays.sort(P, Solver2DClosestPair::compareX);
        Arrays.sort(Q, Solver2DClosestPair::compareY);

        // print the points if less than 15 points
        if (pointsToTest < 15) {
            System.out.println("Points: ");
            for (int i = 0; i < pointsToTest; i++) {
                System.out.println(P[i].toString());
            }
        }

        // Print which algorithm is being used
        System.out.println();System.out.println("Using the fast algorithm");
        // Print the closest pair
        double[] results = Solver2DClosestPair.efficientClosetPair(P, Q);
        System.out.println("Closest pair: (" + results[0] +", "+ results[1] + ") and (" + results[2] +", "+ results[3] + ")");
        System.out.println("Distance: " + results[4]); 

        // Print which algorithm is being used
        System.out.println(); System.out.println("Using the slow algorithm");
        // Brute force algorithm
        double[] bruteForcePoints = Solver2DClosestPair.exhaustiveSearch(P);
        System.out.println("Closest pair: (" + bruteForcePoints[0] +", "+ bruteForcePoints[1] + ") and (" + bruteForcePoints[2] +", "+ bruteForcePoints[3] + ")");
        System.out.println("Distance: " + bruteForcePoints[4]);
        
    }
    
}
