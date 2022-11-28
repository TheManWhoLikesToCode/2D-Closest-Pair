import java.util.Arrays;

public class points {

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
            points[i] = new points((int) (Math.random() * 1000), (int) (Math.random() * 1000));
        }

        return points;
    }

    // split array method
    public static points[] splitArray(points[] points, int start, int end) {
        // Split array into two halves
        points[] split = new points[end - start];
        for (int i = start; i < end; i++) {
            split[i - start] = points[i];
        }
        return split;
    }

    // exhaustive search method
    public static int[] exhaustiveSearch(points[] points) {
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
        return new int[] { x1, y1, x2, y2, (int) min };
    }

    public static int[] efficientClosetPair(points[] P, points[] Q) {

        if (P.length <= 3) {
            return exhaustiveSearch(P);
        }

        // Split array into two halves
        points[] P1 = splitArray(P, 0, P.length / 2);
        points[] P2 = splitArray(P, P.length / 2, P.length);
        points[] Q1 = splitArray(Q, 0, Q.length / 2);
        points[] Q2 = splitArray(Q, Q.length / 2, Q.length);

        // Find closest pair in each half
        int[] dLeft = efficientClosetPair(P1, Q1);
        int[] dRight = efficientClosetPair(P2, Q2);

        // Find minimum distance
        int[] d = dLeft[4] < dRight[4] ? dLeft : dRight;

        // Find points in strip
        points[] strip = new points[Q.length];

        // Get the x coordinate of the middle point
        int m = P[P.length / 2].getX();

        // Copy all the points of Q into strip for which abs(x - m) < d
        int j = 0;
        for (int i = 0; i < Q.length; i++) {
            if (Math.abs(Q[i].getX() - m) < d[4]) {
                strip[j] = Q[i];
                j++;
            }
        }

        // Set dminsq equal to the square of the minimum distance
        int dminsq = d[4] * d[4];
        
        // for loop
        for(int i = 0; i < strip.length; i++){
            int k = i + 1;
            while(k < strip.length && (strip[k].getY() - strip[i].getY()) < dminsq){
                int dist = (int) dist(strip[i], strip[k]);
                if(dist < dminsq){
                    dminsq = dist;
                    d[0] = strip[i].getX();
                    d[1] = strip[i].getY();
                    d[2] = strip[k].getX();
                    d[3] = strip[k].getY();
                    d[4] = dminsq;
                }
                k++;
            }
        }

        // TODO: Fix ts \/
        // for (int i = 0; i < strip.length - 2; i++) {
        //     int k = i + 1;
        //     while (k <= strip.length - 1 && (strip[k].getY() - strip[i].getY()) < dminsq) {
        //         dminsq = Math.min(dminsq, (int) (Math.pow((strip[i].getX() - strip[k].getY()), 2)
        //                 + Math.pow((strip[i].getY() - strip[k].getY()), 2)));
        //         k++;
        //     }
        // }

        // dminsq = (int) Math.sqrt(dminsq);
        
        // return new int[] { d[0], d[1], d[2], d[3], dminsq };
        return d;

    }

    // Method to do it all

    public static int[] closestPairSolver(int n) {
        points[] P = generatePoints(n);
        points[] Q = P.clone();
        Arrays.sort(P, points::compareX);
        Arrays.sort(Q, points::compareY);
        return efficientClosetPair(P, Q);
    }

    // Method to exhaustiveSearch
    public static int[] exhaustiveSearchSolver(int n) {
        points[] P = generatePoints(n);
        points[] Q = P.clone();
        Arrays.sort(P, points::compareX);
        Arrays.sort(Q, points::compareY);
        return exhaustiveSearch(P);
    }
}
