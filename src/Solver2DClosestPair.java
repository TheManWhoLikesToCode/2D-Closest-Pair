import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class Solver2DClosestPair {

   public static void main(String[] args) {

      Hashtable<Integer, int[]> hash;
      Hashtable<Integer, int[]> hashLeft;
      Hashtable<Integer, int[]> hashRight;

      hash = generate2DArray(100);

      printHashTable(hash);

      // Sort the hash table by x coordinate

      hash = sortTable(hash, 'X');

      System.out.println();
      printHashTable(hash);

      hashLeft = splitHashTable(hash, "left");
      hashRight = splitHashTable(hash, "right");

      System.out.println("\n" + "Left");
      printHashTable(hashLeft);

      System.out.println("\n" + "Right");
      printHashTable(hashRight);

      // Find the closest pair in the left and right subarrays

      int[] closestPairLeft = findClosestPair(hashLeft);
      int[] closestPairRight = findClosestPair(hashRight);

      // Print the closest pair in the left and right subarrays

      System.out.println("\n" + "Closest Pair Left");
      System.out.println(closestPairLeft[0] + " " + closestPairLeft[1]);
      System.out.println(closestPairLeft[2] + " " + closestPairLeft[3]);

      System.out.println("\n" + "Closest Pair Right");
      System.out.println(closestPairRight[0] + " " + closestPairRight[1]);
      System.out.println(closestPairRight[2] + " " + closestPairRight[3]);

      // Compare the distance between the closest pair in the left and right subarrays

      if (closestPairLeft[4] < closestPairRight[4]) {
         System.out.println("\n" + "Closest pair of the two plots are: " + closestPairLeft[0] + ", "
               + closestPairLeft[1] + " and "
               + closestPairLeft[2] + ", " + closestPairLeft[3] + " with a distance of " + closestPairLeft[4]);
      } else {
         System.out.println("\n" + "Closest pair of the two plots are: " + closestPairRight[0] + ", "
               + closestPairRight[1] + " and "
               + closestPairRight[2] + ", " + closestPairRight[3] + " with a distance of " + closestPairRight[4]);
      }

      // Create a middle strip of points that are within the distance of the closest
      // pair

      Hashtable<Integer, int[]> middleStrip = new Hashtable<Integer, int[]>();

      // Find the middle point
      int middlePoint = (int) Math.floor(hash.size() / 2);

      // Find the x coordinate of the middle point
      int middlePointX = hash.get(middlePoint)[0];

      // add distance to the middle point x to get the left
      int left = middlePointX - closestPairLeft[2];

      // subtract distance to the middle point x to get the right
      int right = middlePointX + closestPairLeft[2];

      // Add the points in the middle strip to the middle strip hash table
      for (int i = 0; i < hash.size(); i++) {
         if (hash.get(i)[0] >= left && hash.get(i)[0] <= right) {
            middleStrip.put(i, hash.get(i));
         }
      }

      // If the length of the middle strip is less than 3, then the closest pair is
      // already found
      if (middleStrip.size() < 3) {
         System.out.println("\n" + "Closest pair is: " + closestPairLeft[0] + ", " + closestPairLeft[1]
               + " with a distance of " + closestPairLeft[4]);

      } else {

         // Remove empty elements from the middle strip hash table
         middleStrip = removeEmptyElements(middleStrip);
         // Sort the middle strip hash table by y coordinate
         middleStrip = sortTable(middleStrip, 'Y');
         // Find the closest pair in the middle strip
         int[] closestPairMiddle = findClosestPair(middleStrip);

         // Print the closest pair in the middle strip
         System.out.println("\n" + "Closest Pair Middle");
         System.out.println(closestPairMiddle[0] + " " + closestPairMiddle[1]);
         System.out.println(closestPairMiddle[2] + " " + closestPairMiddle[3]);

         System.out.println("\n" + "The distance between the two points is: " + closestPairMiddle[4]);

      }

   }

   private static Hashtable<Integer, int[]> removeEmptyElements(Hashtable<Integer, int[]> middleStrip) {
      Hashtable<Integer, int[]> newMiddleStrip = new Hashtable<Integer, int[]>();
      int j = 0;
      for (int i = 0; i < middleStrip.size(); i++) {
         if (middleStrip.get(i) != null) {
            newMiddleStrip.put(j, middleStrip.get(i));
            j++;
         }
      }
      return newMiddleStrip;

   }

   private static int[] findClosestPair(Hashtable<Integer, int[]> hashtable) {

      int[] closestPair = new int[5];

      int[] point1 = new int[2];
      int[] point2 = new int[2];

      // set point 1 to 0,0
      point1[0] = 0;
      point1[1] = 0;

      // set point 2 to 100, 100
      point2[0] = 100;
      point2[1] = 100;

      // get the distance between point 1 and point 2
      double distance = calculateDistance(point1, point2);

      // loop through the hashtable
      for (int i = 0; i < hashtable.size(); i++) {

         // get the first point
         point1 = hashtable.get(i);

         // loop through the hashtable again
         for (int j = 0; j < hashtable.size(); j++) {

            // Check to see if the points are the same
            if (point1[0] == hashtable.get(j)[0] && point1[1] == hashtable.get(j)[1]) {
               continue;
            }

            // Make sure we are not comparing the same point
            if (i != j) {

               // get the second point
               point2 = hashtable.get(j);

               // calculate the distance between the two points
               double newDistance = calculateDistance(point1, point2);

               // if the new distance is less than the old distance
               if (newDistance < distance) {

                  // set the new distance to the old distance
                  distance = newDistance;

                  // set the closest pair to the two points
                  closestPair[0] = point1[0];
                  closestPair[1] = point1[1];
                  closestPair[2] = point2[0];
                  closestPair[3] = point2[1];

                  // set the distance to the closest pair
                  closestPair[4] = (int) distance;

               }
            }
         }

      }

      return closestPair;
   }

   private static double calculateDistance(int[] point1Temp, int[] point2Temp) {

      double distance = 0;

      // get the x and y coordinates of the first point
      int x1 = point1Temp[0];
      int y1 = point1Temp[1];

      // get the x and y coordinates of the second point
      int x2 = point2Temp[0];
      int y2 = point2Temp[1];

      // calculate the distance between the two points
      distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

      return distance;
   }

   // Split the hashtable into two halves
   public static Hashtable<Integer, int[]> splitHashTable(Hashtable<Integer, int[]> hash, String direction) {

      Hashtable<Integer, int[]> right = new Hashtable<Integer, int[]>();
      Hashtable<Integer, int[]> left = new Hashtable<Integer, int[]>();

      int i = 0;
      int j = 0;

      for (int[] value : hash.values()) {

         if (i < hash.size() / 2) {
            right.put(i, value);
            i++;
         } else {
            left.put(j, value);
            j++;
         }
      }

      switch (direction) {
         case "right":
            return right;

         case "left":
            return left;

      }

      return right;
   }

   // Print hash table method
   private static void printHashTable(Hashtable<Integer, int[]> hashTable) {

      // Print by key's
      for (int i = 0; i < hashTable.size(); i++) {
         System.out.println(hashTable.get(i)[0] + " " + hashTable.get(i)[1]);
      }

   }

   // Method to sort the hash table by x values
   private static Hashtable<Integer, int[]> sortTable(Hashtable<Integer, int[]> hashtable, char XorY) {

      List<int[]> list = new ArrayList<int[]>(hashtable.values());

      switch (XorY) {
         case 'X':
            Collections.sort(list,
                  new Comparator<int[]>() {
                     public int compare(int[] o1, int[] o2) {
                        return Integer.compare(o1[0], o2[0]);
                     }
                  });
            break;

         case 'Y':
            Collections.sort(list,
                  new Comparator<int[]>() {
                     public int compare(int[] o1, int[] o2) {
                        return Integer.compare(o1[1], o2[1]);
                     }
                  });
            break;

      }

      Hashtable<Integer, int[]> sorted = new Hashtable<Integer, int[]>();

      // Add the sorted list to the hash table in inverse order
      for (int i = 0; i < list.size(); i++) {
         sorted.put(i, list.get(i));
      }

      return sorted;
   }

   // Method to genereate a unique hashtable of points
   private static Hashtable<Integer, int[]> generate2DArray(int n) {

      Hashtable<Integer, int[]> array = new Hashtable<Integer, int[]>();

      int[] possibleX;
      int[] possibleY;

      if (n < 100) {
         possibleX = new int[100];
         possibleY = new int[100];

         for (int i = 0; i < n; i++) {
            possibleX[i] = i;
            possibleY[i] = i;
         }

         /// Select a random x and y point and add to the hash table
         for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * 100);
            int y = (int) (Math.random() * 100);
            int[] point = { x, y };
            array.put(i, point);

            // Remove the x and y from the possible x and y arrays
            possibleX[x] = -1;
            possibleY[y] = -1;

         }

      } else {

         possibleX = new int[n];
         possibleY = new int[n];

         for (int i = 0; i < n; i++) {
            possibleX[i] = i;
            possibleY[i] = i;
         }

         /// Select a random x and y point and add to the hash table

         for (int i = 0; i < n; i++) {

            Random rand = new Random();
            int x = rand.nextInt(n);
            int y = rand.nextInt(n);

            while (possibleX[x] == -1) {
               x = rand.nextInt(n);
            }
            while (possibleY[y] == -1) {
               y = rand.nextInt(n);
            }

            int[] point = { x, y };
            array.put(i, point);

            // Remove the selected x and y from the possible x and y arrays
            possibleX[x] = -1;
            possibleY[y] = -1;

         }
      }

      // Check
      return array;
   }

   // Method to sort the hash ta

}
