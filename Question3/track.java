package Question3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class track {
    // List to store scores
    private List<Double> scores;

     // Constructor to initialize the list
    public track() {
        scores = new ArrayList<>();
    }

    // Method to add a score to the list and sort the list
    public void addScore(double score) {
        scores.add(score);
        Collections.sort(scores);
    }

     // Method to calculate and return the median score
    public double getMedianScore() {
        int size = scores.size();
        if (size == 0) {
            throw new IllegalStateException("No scores available.");
        }

        int middle = size / 2;

          // Check if there is an even number of scores
        if (size % 2 == 0) {
            // Even number of scores
            double middle1 = scores.get(middle - 1);
            double middle2 = scores.get(middle);

            // Calculate the average of the two middle scores
            return (middle1 + middle2) / 2.0;
        } else {
            // Return the middle score for an odd number of scores
            return scores.get(middle);
        }
    }

    public static void main(String[] args) {
        track scoreTracker = new track();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2);
    }
}
