package Question1;

public class Venuetheme{

    //  calculating  the minimum cost to decorate all venues, making sure that adjacent venues don't have the same theme."
    public static int minCost(int[][] costs) {
        // Check if the input matrix is empty or null
        if (costs == null || costs.length == 0) {
            return 0;
        }

        // Get the number of venues and available themes
        int n = costs.length;
        int k = costs[0].length;

        // Initializing arrays to store the minimum and second minimum costs for each theme for the current venue
        int[] minCosts = new int[k];
        int[] secMinCosts = new int[k];

        // Initializing the first row of the arrays with the costs of decorating the first venue
        for (int j = 0; j < k; j++) {
            minCosts[j] = costs[0][j];
        }

        // Initializing secondMinCosts array with a large positive value
        int initialValue = 1000000;
        for (int j = 0; j < k; j++) {
            secMinCosts[j] = initialValue;
        }

        //  Iterate through venues starting from the second one
        for (int i = 1; i < n; i++) {
            int newMinCost, newSecMinCost;
            newMinCost = newSecMinCost = Integer.MAX_VALUE;

            // Calculating  the minimum and second minimum costs for each theme
            for (int j = 0; j < k; j++) {
                if (minCosts[j] < newMinCost) {
                    newSecMinCost = newMinCost;
                    newMinCost = minCosts[j];
                } else if (minCosts[j] < newSecMinCost) {
                    newSecMinCost = minCosts[j];
                }
            }

            // Updating  the arrays with the updated minimum and second minimum costs for the current venue
            for (int j = 0; j < k; j++) {
                minCosts[j] = costs[i][j] + ((minCosts[j] == newMinCost) ? newSecMinCost : newMinCost);
            }
        }

        // Find the minimum cost among the last row of the minCosts array
        int result = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            result = Math.min(result, minCosts[j]);
        }

        return result;
    }

   
    public static void main(String[] args) {
        
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}}; 
        int result = minCost(costs);
        System.out.println("Mini cost to decorate all venues: " + result);
    }
}
