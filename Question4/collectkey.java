package Question4;

import java.util.LinkedList;
import java.util.Queue;

// Class to represent the state of the player
class State {
    int x, y, keys;

    public State(int x, int y, int keys) {
        this.x = x;
        this.y = y;
        this.keys = keys;
    }
}

public class collectkey {

    // Method to find the minimum steps to collect all keys and reach the exit
    public int minSteps(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int allKeys = 0;
        int startX = 0, startY = 0;

        // Find the initial position of the player (S) and collect all keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if ('a' <= grid[i][j] && grid[i][j] <= 'f') {
                    allKeys |= (1 << (grid[i][j] - 'a'));
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[m][n][64];

        queue.offer(new State(startX, startY, 0));
        visited[startX][startY][0] = true;

        int[] directions = {0, 1, 0, -1, 0}; // Possible directions: Up, Right, Down, Left

        int steps = 0;

        // BFS to explore the maze and collect keys
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                State current = queue.poll();

                if (current.keys == allKeys) {
                    return steps;
                }

                for (int d = 0; d < 4; d++) {
                    int newX = current.x + directions[d];
                    int newY = current.y + directions[d + 1];

                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] != 'W') {
                        char cell = grid[newX][newY];

                        if (cell == 'P') {
                            int newKeys = current.keys;

                            if (!visited[newX][newY][newKeys]) {
                                queue.offer(new State(newX, newY, newKeys));
                                visited[newX][newY][newKeys] = true;
                            }
                        } else if ('a' <= cell && cell <= 'f') {
                            int newKeys = current.keys | (1 << (cell - 'a'));

                            if (!visited[newX][newY][newKeys]) {
                                queue.offer(new State(newX, newY, newKeys));
                                visited[newX][newY][newKeys] = true;
                            }
                        } else if ('A' <= cell && cell <= 'F' && ((current.keys >> (cell - 'A')) & 1) == 1) {
                            int newKeys = current.keys;

                            if (!visited[newX][newY][newKeys]) {
                                queue.offer(new State(newX, newY, newKeys));
                                visited[newX][newY][newKeys] = true;
                            }
                        }
                    }
                }
            }

            steps++;
        }

        return -1; // If no path to collect all keys is found
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        collectkey collectKey = new collectkey();
        int result = collectKey.minSteps(grid);

        System.out.println("Minimum number of moves to collect all keys: " + result);
    }
}
