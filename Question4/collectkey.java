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

        int[] dirs = {-1, 0, 1, 0, -1};// Possible directions: Up, Right, Down, Left

        int steps = 0;

         // BFS to explore the maze and collect keys
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                State current = queue.poll();

                 // Check if the player reaches the exit with all keys collected
                if (grid[current.x][current.y] == 'E' && current.keys == allKeys) {
                    return steps;
                }

                for (int k = 0; k < 4; k++) {
                    int nx = current.x + dirs[k];
                    int ny = current.y + dirs[k + 1];

                     // Check if the new position is within the maze boundaries and not blocked by walls
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') {
                        char cell = grid[nx][ny];

                           // If the cell contains a key
                        if ('a' <= cell && cell <= 'f') {
                            int newKeys = current.keys | (1 << (cell - 'a'));

                            // If the new set of keys has not been visited, mark it as visited and enqueue the state
                            if (!visited[nx][ny][newKeys]) {
                                visited[nx][ny][newKeys] = true;
                                queue.offer(new State(nx, ny, newKeys));
                            }
                        }
                        
                          // If the cell contains a locked door
                        else if ('A' <= cell && cell <= 'F') {
                            // Check if the player has the corresponding key to open the door
                            if ((current.keys & (1 << (cell - 'A'))) > 0 && !visited[nx][ny][current.keys]) {
                                visited[nx][ny][current.keys] = true;
                                queue.offer(new State(nx, ny, current.keys));
                            }
                        }
                        // If the cell is a passage or a cell to collect a key or the exit
                        else if (cell == 'P' && !visited[nx][ny][current.keys]) {
                            visited[nx][ny][current.keys] = true;
                            queue.offer(new State(nx, ny, current.keys));
                        }
                    }
                }
            }

            steps++;
        }

        return -1; // Unable to collect all keys and reach the exit
    }

    public static void main(String[] args) {
        collectkey mazeSolver = new collectkey();

        char[][] grid = {
                {'S', 'P', 'P', 'P'},
                {'W', 'P', 'P', 'E'},
                {'P', 'b', 'W', 'P'},
                {'P', 'P', 'P', 'P'}
        };

        int result = mazeSolver.minSteps(grid);
        System.out.println("Minimum number of steps to collect all keys and reach the exit: " + result);
    }
}
