package Question4;

import java.util.*;

class State {
    int x, y, keys;

    public State(int x, int y, int keys) {
        this.x = x;
        this.y = y;
        this.keys = keys;
    }
}

public class collectkey {
    // Method to find the minimum number of moves to collect all keys
    public static int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int totalKeys = 0;
        int startX = 0, startY = 0;

        // Count the total number of keys and find the starting position
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    totalKeys++;
                }
            }
        }

         // Initialize BFS queue, visited set, and starting state
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        State startState = new State(startX, startY, 0);
        queue.offer(startState);
        visited.add(startState.x + "-" + startState.y + "-" + startState.keys);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                State currentState = queue.poll();

                 // Check if all keys are collected
                if (currentState.keys == (1 << totalKeys) - 1) {
                   // All keys collected, return the minimum moves
                    return moves;
                }

                for (int[] dir : directions) {
                    int newX = currentState.x + dir[0];
                    int newY = currentState.y + dir[1];

                      // Check if the new position is within bounds and not a wall
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] != 'W') {
                        char cell = grid[newX][newY];

                        if (Character.isLowerCase(cell) && (currentState.keys & (1 << (cell - 'a'))) == 0) {
                            // Collect key
                            int newKeys = currentState.keys | (1 << (cell - 'a'));
                            State newState = new State(newX, newY, newKeys);

                            if (visited.add(newState.x + "-" + newState.y + "-" + newState.keys)) {
                                queue.offer(newState);
                            }
                        } else if (Character.isUpperCase(cell) && (currentState.keys & (1 << (cell - 'A'))) != 0) {
                            // Use key to unlock door
                            State newState = new State(newX, newY, currentState.keys);

                            if (visited.add(newState.x + "-" + newState.y + "-" + newState.keys)) {
                                queue.offer(newState);
                            }

                                // Move to an open path or the starting point
                        } else if (cell == 'P' || cell == 'S') {
                            State newState = new State(newX, newY, currentState.keys);

                            if (visited.add(newState.x + "-" + newState.y + "-" + newState.keys)) {
                                queue.offer(newState);
                            }
                        }
                    }
                }
            }

            moves++;
        }

        // Unable to collect all keys
        return -1;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        int result = minMovesToCollectKeys(grid);
        System.out.println(result);
    }
}
