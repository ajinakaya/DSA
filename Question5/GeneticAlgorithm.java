package Question5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Class representing a City with X and Y coordinates
class City {
    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

     // Calculate the Euclidean distance between two cities
    public double distanceTo(City otherCity) {
        int deltaX = otherCity.getX() - this.x;
        int deltaY = otherCity.getY() - this.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}

class Tour {
    private List<City> tour;
    private double fitness;

     // Constructor for creating a tour with a given order of cities
    public Tour(List<City> tour) {
        this.tour = new ArrayList<>(tour);
        calculateFitness();
    }

    public List<City> getTour() {
        return tour;
    }

    public double getFitness() {
        return fitness;
    }


    // Calculate the fitness of the tour based on the total tour distance
    public void calculateFitness() {
        double totalDistance = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalDistance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        totalDistance += tour.get(tour.size() - 1).distanceTo(tour.get(0)); // Complete the tour
        fitness = 1.0 / totalDistance;
    }

    // Shuffle the order of cities in the tour
    public void shuffleTour() {
        Collections.shuffle(tour);
        calculateFitness();
    }

       // Swap the positions of two cities in the tour
    public void swapCities(int index1, int index2) {
        Collections.swap(tour, index1, index2);
        calculateFitness();
    }
}

class Population {
    private List<Tour> tours;

      // Constructor for creating a population with random tours
    public Population(int populationSize, List<City> cities) {
        tours = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<City> tourOrder = new ArrayList<>(cities);
            Collections.shuffle(tourOrder);
            tours.add(new Tour(tourOrder));
        }
    }

    public List<Tour> getTours() {
        return tours;
    }

      // Sort the population based on tour fitness
    public void sortPopulation() {
        Collections.sort(tours, (tour1, tour2) -> Double.compare(tour2.getFitness(), tour1.getFitness()));
    }
}

public class GeneticAlgorithm {

    public static Tour evolvePopulation(Population population, double mutationRate) {
        Population newPopulation = new Population(population.getTours().size(), population.getTours().get(0).getTour());

        for (int i = 0; i < population.getTours().size(); i++) {
            Tour parent1 = selectParent(population);
            Tour parent2 = selectParent(population);
            Tour child = crossover(parent1, parent2);
            mutate(child, mutationRate);
            newPopulation.getTours().set(i, child);
        }

        newPopulation.sortPopulation();
        return newPopulation.getTours().get(0);
    }

    private static Tour selectParent(Population population) {
        Random random = new Random();
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (Tour tour : population.getTours()) {
            cumulativeProbability += tour.getFitness() / population.getTours().get(0).getFitness();
            if (randomValue <= cumulativeProbability) {
                return tour;
            }
        }

        // Should not reach here
        return population.getTours().get(0);
    }

    private static Tour crossover(Tour parent1, Tour parent2) {
        Random random = new Random();
        int size = parent1.getTour().size();
        int start = random.nextInt(size);
        int end = random.nextInt(size);

        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }

        List<City> childOrder = new ArrayList<>(Collections.nCopies(size, null));

        for (int i = start; i <= end; i++) {
            childOrder.set(i, parent1.getTour().get(i));
        }

        int currentIndex = (end + 1) % size;

        for (City city : parent2.getTour()) {
            if (!childOrder.contains(city)) {
                childOrder.set(currentIndex, city);
                currentIndex = (currentIndex + 1) % size;
            }
        }

        return new Tour(childOrder);
    }

    private static void mutate(Tour tour, double mutationRate) {
        Random random = new Random();
        if (random.nextDouble() < mutationRate) {
            int index1 = random.nextInt(tour.getTour().size());
            int index2 = random.nextInt(tour.getTour().size());
            tour.swapCities(index1, index2);
        }
    }

    public static void main(String[] args) {
        // Example cities (replace with your own data)
        List<City> cities = new ArrayList<>();
        cities.add(new City(0, 0));
        cities.add(new City(1, 2));
        cities.add(new City(3, 1));
        cities.add(new City(4, 4));
        cities.add(new City(2, 3));

        int populationSize = 50;
        double mutationRate = 0.01;
        int generations = 100;

        Population initialPopulation = new Population(populationSize, cities);

        for (int i = 0; i < generations; i++) {
            Tour bestTour = evolvePopulation(initialPopulation, mutationRate);
            System.out.println("Generation " + i + ": Best Tour Fitness = " + bestTour.getFitness());
        }

        Tour finalBestTour = evolvePopulation(initialPopulation, mutationRate);
        System.out.println("Final Best Tour Order: " + finalBestTour.getTour());
        System.out.println("Final Best Tour Fitness: " + finalBestTour.getFitness());
    }
}
