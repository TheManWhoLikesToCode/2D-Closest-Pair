import java.util.Arrays;

public class Solver2DClosestPair {

    // plot points
    public static void main(String[] args) {

        // Generate 10 random points
        points[] P = points.generatePoints(10);

        // sort points by x
        Arrays.sort(P, points::compareX);

        // Set int[] P equal to a copy of pointsArray
        points[] Q = Arrays.copyOf(P, P.length);

        Arrays.sort(Q, points::compareY);

        int[] closestPair = points.efficientClosetPair(P, Q);
        System.out.println(Arrays.toString(closestPair));
        int[] check = points.exhaustiveSearch(P);
        System.out.println(Arrays.toString(check));

    }

}
