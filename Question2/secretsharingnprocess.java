package Question2;

import java.util.ArrayList;
import java.util.List;

public class secretsharingnprocess {


    // methos to find individuals who will eventually know the secret
    public static List<Integer> findIndividuals(int n, int[][] intervals, int firstPerson) {
        // Array to track whether an individual knows the secret or not 
        boolean[] knowsSecret = new boolean[n];
        knowsSecret[firstPerson] = true;// The first person knows the secret

        // Iterate through each interval
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            // Mark individuals in the current interval as knowledgeable
            for (int i = start; i <= end; i++) {
                knowsSecret[i] = true;
            }
        }

        // Collect the indices of individuals who know the secret
        List<Integer> knownIndividuals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                knownIndividuals.add(i); 

            }
        }

        return knownIndividuals;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;
        
         // Find individuals who will eventually know the secret
        List<Integer> result = findIndividuals(n, intervals, firstPerson);

        System.out.println("Individuals who will eventually know the secret: " + result);
    }
}
