import java.util.Arrays;
import java.util.Scanner;

public class Solver2DClosestPair {

    public static void main(String[] args) {
        // Ask the user for the number of points to test
        System.out.println("How many points would you like to test?");
        Scanner scanner = new Scanner(System.in);
        int pointsToTest = scanner.nextInt();

        // Ensure the user enters a number greater than 1 and integer
        while (pointsToTest < 1) {
            System.out.println("Please enter a number greater than 0");
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
        System.out.println();
        System.out.println("Using the divide and conq algorithm");
        // Print the closest pair
        double[] results = Solver2DClosestPair.efficientClosetPair(P, Q);
        System.out.println(
                "Closest pair: (" + results[0] + ", " + results[1] + ") and (" + results[2] + ", " + results[3] + ")");
        System.out.println("Distance: " + results[4]);

        // Print which algorithm is being used
        System.out.println();
        System.out.println("Using the brute force algorithm");
        // Brute force algorithm
        double[] bruteForcePoints = Solver2DClosestPair.exhaustiveSearch(P);
        System.out.println("Closest pair: (" + bruteForcePoints[0] + ", " + bruteForcePoints[1] + ") and ("
                + bruteForcePoints[2] + ", " + bruteForcePoints[3] + ")");
        System.out.println("Distance: " + bruteForcePoints[4]);

    }

    // X and Y points
    public int x;
    public int y;

    // Constructor
    public Solver2DClosestPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Print points
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Algebraic distance formula
    public static double dist(Solver2DClosestPair p1, Solver2DClosestPair p2) {
        return (double) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static int compareY(Solver2DClosestPair p1, Solver2DClosestPair p2) {
        // Compare y points
        return Integer.compare(p1.getY(), p2.getY());
    }

    public static int compareX(Solver2DClosestPair p1, Solver2DClosestPair p2) {
        // Compare x points
        return Integer.compare(p1.getX(), p2.getX());
    }

    // Generate random points
    public static Solver2DClosestPair[] generatePoints(int n) {
        int randomX = 0;
        Solver2DClosestPair[] p = new Solver2DClosestPair[n];
        // Fill coordinate pairs
        for (int i = 0; i < n; i++) {
            randomX = randomX + (int) (Math.random() * 10) + 1;
            int randomY = (int) (Math.random() * 100) + 1;
            p[i] = new Solver2DClosestPair(randomX, randomY);
        }
        return p;
    }

    // split array method
    public static Solver2DClosestPair[] splitArray(Solver2DClosestPair[] points, int start, int end) {
        // Split array into two halves
        Solver2DClosestPair[] splitArray = new Solver2DClosestPair[end - start];
        for (int i = start; i < end; i++) {
            splitArray[i - start] = points[i];
        }

        return splitArray;
    }

    // exhaustive search method
    public static double[] exhaustiveSearch(Solver2DClosestPair[] points) {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        // Initialize minimum distance to infinity
        double min = Float.POSITIVE_INFINITY;
        // Loop through all points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                // Calculate distance between points
                double dist = dist(points[i], points[j]);
                // Update minimum distance
                if (dist < min) {
                    min = dist;
                    // X and Y coordinates of closest points
                    x1 = points[i].getX();
                    y1 = points[i].getY();
                    x2 = points[j].getX();
                    y2 = points[j].getY();
                }
            }
        }
        return new double[] { x1, y1, x2, y2, min };
    }

    public static double[] efficientClosetPair(Solver2DClosestPair[] P, Solver2DClosestPair[] Q) {

        // If there is one point return that point
        if (P.length == 1) {
            return new double[] { P[0].getX(), P[0].getY(), P[0].getX(), P[0].getY(), Float.POSITIVE_INFINITY };
        }

        if (P.length <= 3) {
            return exhaustiveSearch(P);
        }

        // Split array into two halves using the ceiling
        Solver2DClosestPair[] pL = new Solver2DClosestPair[(int) Math.ceil(P.length / 2.0)];
        Solver2DClosestPair[] pR = new Solver2DClosestPair[P.length / 2];
        Solver2DClosestPair[] qL = new Solver2DClosestPair[pL.length];
        Solver2DClosestPair[] qR = new Solver2DClosestPair[pR.length];
        split(P, Q, pL, pR, qL, qR);

        // Find closest pair in each half
        double[] dLeft = efficientClosetPair(pL, qL);
        double[] dRight = efficientClosetPair(pR, qR);

        // Find minimum distance
        double[] dMin;
        if (dLeft[4] < dRight[4]) {
            dMin = dLeft;
        } else {
            dMin = dRight;
        }

        // Find the middle point
        int mid = P[P.length / 2].getX();

        // Find points in strip
        Solver2DClosestPair[] strip = new Solver2DClosestPair[P.length];

        int j = 0;
        for (int i = 0; i < Q.length; i++) {
            if (Math.abs(Q[i].getX() - mid) < dMin[4]) {
                strip[j] = Q[i];
                j++;
            }
        }

        double dminsq = dMin[4];

        // Find closest pair in strip
        for (int i = 0; i < j - 1; i++) {
            int k = i + 1;
            while (k <= j - 1 && (strip[k].getY() - strip[i].getY()) < dminsq) {
                double d = dist(strip[i], strip[k]);
                if (d < dminsq) {
                    dminsq = d;
                    dMin[0] = strip[i].getX();
                    dMin[1] = strip[i].getY();
                    dMin[2] = strip[k].getX();
                    dMin[3] = strip[k].getY();
                    dMin[4] = dminsq;
                }
                k++;
            }
        }
        return dMin;
    }

    public static void split(Solver2DClosestPair[] P, Solver2DClosestPair[] Q, Solver2DClosestPair[] pL, Solver2DClosestPair[] pR, Solver2DClosestPair[] qL, Solver2DClosestPair[] qR) {

        for (int i = 0; i < pL.length; i++) {
            pL[i] = P[i];
        }

        int j = 0;
        for (int i = pL.length; i < P.length; i++) {
            pR[j] = P[i];
            j++;
        }

        int lIndex = 0;
        int rIndex = 0;
        for (int i = 0; i < Q.length; i++) {
            if (Q[i].getX() < pR[0].getX()) {
                qL[lIndex] = Q[i];
                lIndex++;
            } else {
                qR[rIndex] = Q[i];
                rIndex++;
            }
        }
    }

}