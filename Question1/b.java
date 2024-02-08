package Question1;
import java.util.PriorityQueue;

public class b {

    public static int minTime(int[] engines, int splitCost) {
        int totalTime = 0;

        // Using  a min heap to keep track of the time required to build each engine
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Initially, there is only one engineer
        minHeap.add(splitCost);

        for (int engineTime : engines) {
            int currentEngineTime = minHeap.poll();
            totalTime = Math.max(totalTime, currentEngineTime); // Choose the max time spent so far

            // Spliting the engineer into two if needed
            minHeap.add(currentEngineTime + splitCost);

            // Add the time required to build the current engine
            minHeap.add(engineTime);
        }

        // The total time required to build all engines is the max time spent
        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {3, 4, 5, 2};
        int splitCost = 2;

        int result = minTime(engines, splitCost);
        System.out.println("Minimum time needed to build all engines: " + result);
    }
}
