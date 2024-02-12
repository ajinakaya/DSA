package Question1;


public class b {
    // Method to find the minimum time needed to build all engines
    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        return buildEngines(engines, splitCost, 0, engines.length);
    }
 
     // Recursive helper function to calculate minimum time to build engines within a given range
    private static int buildEngines(int[] engines, int splitCost, int start, int end) {

        // If there is only one engine, return its build time
        if (end - start == 1) {
            return engines[start];
        }
 
          // Initialize minimum time to a large value
        int minTime = Integer.MAX_VALUE;
        for (int i = start + 1; i < end; i++) {
            
          // Calculate time taken for the current split point
            int timeTaken = engines[i] + Math.max(buildEngines(engines, splitCost, start, i), buildEngines(engines, splitCost, i, end));
            minTime = Math.min(minTime, timeTaken);
        }
 
        return Math.min(minTime, splitCost + end - start);
    }
 
    public static void main(String[] args) {
        int[] engines = {1, 2, 3};
        int splitCost = 1;
        int minTime = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time needed to build all engines: " + minTime);
    }
}
 