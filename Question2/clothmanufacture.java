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

        // Iterate through the sewing machines and calculate the moves required to equalize dresses
        for (int i = 0; i < n; i++) {
            // Calculate the difference between the current dresses and the target dresses
            int diff = dresses[i] - targetDresses;

            // Accumulate the absolute difference as moves
            moves += Math.abs(diff);
        }

        return moves / 2; 
    }

    public static void main(String[] args) {
        int[] dresses = {2, 1, 3, 0, 2};
        int result = minMovesToEqualize(dresses);

        if (result != -1) {
            System.out.println("Minimum moves required to equalize dresses: " + result);
        } else {
            System.out.println("It is not possible to equalize the number of dresses.");
        }
    }
}
