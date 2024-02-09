package Question5;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ISP {

       // Method to find the impacted devices given the network edges and target device
    public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
        int numDevices = getMaxDeviceNumber(edges);
        List<Integer> impactedDevices = bfs(edges, numDevices, targetDevice);
        return impactedDevices;
    }

     // BFS to find impacted devices
    private static List<Integer> bfs(int[][] edges, int numDevices, int targetDevice) {
        List<Integer> impactedDevices = new ArrayList<>();
        boolean[] visited = new boolean[numDevices + 1];// +1 to account for 0-based indexing
        Queue<Integer> queue = new LinkedList<>();

        visited[targetDevice] = true;
        queue.offer(targetDevice);

        while (!queue.isEmpty()) {
            int currentDevice = queue.poll();
            impactedDevices.add(currentDevice);

            // Get neighbors of the current device and enqueue them if not visited
            for (int neighbor : getNeighbors(edges, currentDevice)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return impactedDevices;
    }

    private static List<Integer> getNeighbors(int[][] edges, int currentDevice) {
        List<Integer> neighbors = new ArrayList<>();
        for (int[] edge : edges) {
              // Check both ends of the edge to find neighbors
            if (edge[0] == currentDevice) {
                neighbors.add(edge[1]);
            } else if (edge[1] == currentDevice) {
                neighbors.add(edge[0]);
            }
        }
        return neighbors;
    }

    private static int getMaxDeviceNumber(int[][] edges) {
        int maxDeviceNumber = 0;
        for (int[] edge : edges) {
            maxDeviceNumber = Math.max(maxDeviceNumber, Math.max(edge[0], edge[1]));
        }
        return maxDeviceNumber;
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}};
        int targetDevice = 4;

        List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);

        System.out.println("Impacted Device List: " + impactedDevices);
    }
}
                   