package Question2;

public class clothmanufacture {
  
    //  calculateing  the minimum moves required to equalize the number of dresses on sewing machines.
    public static int minMovesToEqualize(int[] dresses) {
        int n = dresses.length;

        // Calculating the total number of dresses on the production line
        int totalDresses = 0;
        for (int dress : dresses) {
            totalDresses += dress;
        }

        // If the total number of dresses cannot be evenly distributed among sewing machines, return -1
        if (totalDresses % n != 0) {
            return -1;
        }

        // Calculate the target number of dresses on each sewing machine
        int targetDresses = totalDresses / n;

        int moves = 0;
        int currentSum = 0;

        // Iterate through each sewing machine and calculate the moves required
        for (int dress : dresses) {
            currentSum += dress - targetDresses;
            moves += Math.abs(currentSum);
        }
 

        return moves/2;
    }


    public static void main(String[] args) {
        int[] dresses = {1, 0, 5};
        int result = minMovesToEqualize(dresses);

        if (result != -1) {
            System.out.println("Minimum moves required to equalize dresses: " + result);
        } else {
            System.out.println("It is not possible to equalize the number of dresses.");
        }
    }
}
