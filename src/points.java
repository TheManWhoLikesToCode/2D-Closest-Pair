import java.util.Arrays;

public class points {

    // Declare runCounter
    public static int runCounter = 0;
    // X and Y points
    public int x;
    public int y;

    // Constructor
    public points(int x, int y) {
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
    public static float dist(points p1, points p2) {
        return (float) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static int compareY(points p1, points p2) {
        // Compare y points
        return Integer.compare(p1.getY(), p2.getY());
    }

    public static int compareX(points p1, points p2) {
        // Compare x points
        return Integer.compare(p1.getX(), p2.getX());
    }

    // generate a distict set of points
    public static points[] generatePoints(int n) {
        points[] points = new points[n];

        for (int i = 0; i < n; i++) {
            points[i] = new points((int) (Math.random() * 100), (int) (Math.random() * 100));
        }

        // Remove duplicates
        points = Arrays.stream(points).distinct().toArray(points[]::new);

        return points;
    }

    // split array method
    public static points[] splitArray(points[] points, int start, int end) {
        // Split array into two halves
        points[] splitArray = new points[end - start];
        for (int i = start; i < end; i++) {
            splitArray[i - start] = points[i];
        }

        return splitArray;
    }

    // exhaustive search method
    public static float[] exhaustiveSearch(points[] points) {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        // Initialize minimum distance to infinity
        float min = Float.POSITIVE_INFINITY;
        // Loop through all points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                // Calculate distance between points
                float dist = dist(points[i], points[j]);
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
        return new float[] { x1, y1, x2, y2, min };
    }

    public static float[] efficientClosetPair(points[] P, points[] Q) {

        if (P.length <= 3) {
            return exhaustiveSearch(P);
        }

        // Split array into two halves
        points[] P1 = splitArray(P, 0, P.length / 2);
        points[] P2 = splitArray(P, P.length / 2, P.length);
        points[] Q1 = splitArray(Q, 0, Q.length / 2);
        points[] Q2 = splitArray(Q, Q.length / 2, Q.length);

        // Find closest pair in each half
        float[] dLeft = efficientClosetPair(P1, Q1);
        float[] dRight = efficientClosetPair(P2, Q2);

        // Find minimum distance
        float[] dMin = dLeft[4] < dRight[4] ? dLeft : dRight;
        float distanceMin = dMin[4];

        // Find the middle point
        int m = P[(P.length / 2) - 1].getX();

        // Find points in strip
        points[] strip = new points[P.length];

        // Find points in strip with distance less than dMin
        int j = 0;
        for (int i = 0; i < P.length; i++) {
            if (Math.abs(Q[i].getX() - m) < distanceMin) {
                strip[j] = Q[i];
                j++;
            }
        }

        // Set dminsq equal to the square of the minimum distance
        float dminsq = dMin[4] * dMin[4];

        // Find the closest pair in the strip
        for (int i = 0; i < j - 2; i++) {
            int k = i + 1;
            while (k <= j - 1 && Math.pow((strip[k].getY() - strip[i].getY()), 2) < dminsq) {
                dminsq = (int) Math.min(
                        Math.pow(strip[k].getX() - strip[i].getX(), 2) + Math.pow(strip[k].getY() - strip[i].getY(), 2),
                        dminsq);
                runCounter++;
                k++;
            }
        }

        // Return the closest pair
        return new float[] { dMin[0], dMin[1], dMin[2], dMin[3], (float) Math.sqrt(dminsq) };

    }

    // Method to do it all

    public static float[] closestPairSolver(int n) {
        points[] P = generatePoints(n);
        points[] Q = P.clone();
        Arrays.sort(P, points::compareX);
        Arrays.sort(Q, points::compareY);
        return efficientClosetPair(P, Q);
    }

    // Method to exhaustiveSearch
    public static float[] exhaustiveSearchSolver(int n) {
        points[] P = generatePoints(n);
        points[] Q = P.clone();
        Arrays.sort(P, points::compareX);
        Arrays.sort(Q, points::compareY);
        return exhaustiveSearch(P);
    }
}
