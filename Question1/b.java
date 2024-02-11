package Question1;


public class b {
    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        return buildEngines(engines, splitCost, 0, engines.length);
    }
 
    private static int buildEngines(int[] engines, int splitCost, int start, int end) {
        if (end - start == 1) {
            return engines[start];
        }
 
        int minTime = Integer.MAX_VALUE;
        for (int i = start + 1; i < end; i++) {
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
 