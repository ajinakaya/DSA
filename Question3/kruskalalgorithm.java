package Question3;
import java.util.*;


// Class to represent an edge with source, destination, and weight
class Edge implements Comparable<Edge> {
    int source, destination;
    double weight;

    public Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

      // Comparing edges based on their weights
    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}


//  implementing Kruskal's algorithm for finding Minimum Spanning Tree
public class kruskalalgorithm {

    private int vertices;
    private List<Edge> edges;

    // Constructor to initialize the graph with a given number of vertices
    public kruskalalgorithm(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

      // Method to add an edge to the graph
    public void addEdge(int source, int destination, double weight) {
        edges.add(new Edge(source, destination, weight));
    }

    private int find(int[] parent, int i) {
        if (parent[i] == i) {
            return i;
        }
        return find(parent, parent[i]);
    }

     // Method to perform union of two sets
    private void union(int[] parent, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        parent[rootX] = rootY;
    }

     // Kruskal's algorithm to find Minimum Spanning Tree
    public List<Edge> kruskalMST() {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges); // Sort edges based on weights

        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
        }

        for (Edge edge : edges) {
            int rootSource = find(parent, edge.source);
            int rootDestination = find(parent, edge.destination);

             // If including this edge doesn't create a cycle, add it to the result
            if (rootSource != rootDestination) {
                result.add(edge);
                union(parent, rootSource, rootDestination);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        kruskalalgorithm graph = new kruskalalgorithm(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        List<Edge> mstEdges = graph.kruskalMST();

        System.out.println("Minimum Spanning Tree Edges:");
        for (Edge edge : mstEdges) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
        }
    }
}
